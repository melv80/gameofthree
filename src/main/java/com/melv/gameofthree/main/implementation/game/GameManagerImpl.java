package com.melv.gameofthree.main.implementation.game;

import com.melv.gameofthree.main.config.ServiceConfig;
import com.melv.gameofthree.main.implementation.ui.UIEventEmitter;
import com.melv.gameofthree.main.model.Player;
import com.melv.gameofthree.main.model.event.game.*;
import com.melv.gameofthree.main.model.GameRuleValidator;
import com.melv.gameofthree.main.api.game.GameLogic;
import com.melv.gameofthree.main.api.game.GameManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Service
public class GameManagerImpl implements GameManager {
  private Logger logger = LoggerFactory.getLogger("GameManager");

  @Autowired
  private ServiceConfig configuration;

  @Autowired
  private GameLogic gameLogic;

  @Autowired
  private JmsTemplate eventQueue;

  @Autowired
  private UIEventEmitter emitter;

  @Autowired
  private GameRuleValidator gameRules;

  @NotNull
  private Player localPlayer;

  @Nullable
  private Player opponent;

  @PostConstruct
  private void init() {
    this.localPlayer = configuration.createLocalPlayer();
    if (configuration.getRemote() != null) {
      final ReadyToPlay event = new ReadyToPlay(localPlayer, configuration.getLocal());
      event.senderID = localPlayer.playerID;
      logEvent(event);
      eventQueue.convertAndSend("outbox", event);
    }
  }

  @Override
  public String localPlayerName() {
    return localPlayer.name;
  }

  @Override
  public String opponentName() {
    return opponent == null ? "-" : opponent.name;
  }

  public void startNewGame() {
    if (opponent != null) {
      GameStarted event = new GameStarted(localPlayer, opponent, configuration.getStartingNumber() == -1 ? gameLogic.getStartingNumber() : configuration.getStartingNumber());
      event.senderID = localPlayer.playerID;
      event.receiverID = opponent.playerID;
      logEvent(event);
      eventQueue.convertAndSend("outbox", event);
    } else {
      logger.warn("game can not be started, opponent missing. Waiting for opponent");
    }
  }

  public void gameEnded(GameOver gameOver) {
    if(gameRules.testWin(gameOver.winningTurn)) {
      logEvent(gameOver);
    }
  }

  public void playerReady(ReadyToPlay event) {
    configuration.setRemote(event.responseEndpoint);
    if (opponent == null) {
      opponent = event.player;
      logEvent(event);
      final ReadyToPlay myResponse = new ReadyToPlay(localPlayer, configuration.getLocal());
      myResponse.senderID = localPlayer.playerID;
      myResponse.receiverID = event.senderID;
      logEvent(myResponse);
      eventQueue.convertAndSend("outbox", myResponse);
    }
  }

  public void handleTurn(Turn event) {
    if (opponent == null) throw new IllegalStateException(" not ready to play, no opponent present.");

    logEvent(event);
    gameRules.validateTurn(event);
    Turn myturn = gameLogic.doTurn(localPlayer, event);
    gameRules.validateTurn(myturn);
    if (gameRules.testWin(myturn)) {
      final GameOver gameOver = new GameOver(localPlayer, myturn);
      gameOver.senderID = event.receiverID;
      gameOver.receiverID = event.senderID;
      logEvent(gameOver);
      eventQueue.convertAndSend("outbox", gameOver);
    } else {
      logEvent(myturn);
      eventQueue.convertAndSend("outbox", myturn);
    }
  }

  public void gameStarted(GameStarted event) {
    logEvent(event);
    gameRules.validateStartGameNumber(event.startNumber);
    Turn myturn = gameLogic.startGame(event.first, event.second, event.startNumber);
    gameRules.validateInput(myturn.resultingNumber);
    logEvent(myturn);
    eventQueue.convertAndSend("outbox", myturn);
  }

  private void logEvent(Event event) {
    logger.info(event.toString());
    emitter.emitUIEvent(getPlayerByID(event.senderID), event);
  }

  private @NotNull Player getPlayerByID(int playerID) {
    if (playerID == localPlayer.playerID)
      return localPlayer;
    else
      return Objects.requireNonNull(opponent, "not ready to play, second player not known");
  }
}
