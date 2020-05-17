package com.melv.gameofthree.main.implementation.game;

import com.melv.gameofthree.main.model.Player;
import com.melv.gameofthree.main.model.event.game.Turn;
import com.melv.gameofthree.main.model.GameRuleValidator;
import com.melv.gameofthree.main.api.game.GameLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Random;

@Service
public class AutomatedGameLogicImpl extends GameLogic {
  private Logger logger = LoggerFactory.getLogger("Game");

  private final @NotNull GameRuleValidator validator;

  public AutomatedGameLogicImpl(@Autowired @NotNull GameRuleValidator validator) {
    this.validator = validator;
  }

  @Override
  public Turn startGame(@NotNull Player player1, @NotNull Player player2, long startNumber) {
    validator.validateStartGameNumber(startNumber);
    Turn myturn = computeTurnFromNumber(startNumber);
    myturn.senderID = player2.playerID;
    myturn.receiverID = player1.playerID;
    return myturn;
  }

  @Override
  public long getStartingNumber() {
    return 1+ new Random().nextInt(Integer.MAX_VALUE);
  }

  @Override
  public Turn doTurn(Player activePlayer, Turn previousTurn) {
    validator.validateTurn(previousTurn);
    Turn myturn = computeTurnFromNumber(previousTurn.resultingNumber);
    myturn.senderID = previousTurn.receiverID;
    myturn.receiverID = previousTurn.senderID;
    validator.validateTurn(myturn);
    logger.info(previousTurn.resultingNumber + " -> "+myturn);

    return myturn;
  }

  private @NotNull Turn computeTurnFromNumber(long inputNumber) {
    long remainder = inputNumber % 3;
    Turn.Operation operation;

    if (remainder == 2) {
      operation = Turn.Operation.INCREMENT_ONE;
    }
    else if (remainder == 1) {
      operation = Turn.Operation.DECREMENT_ONE;
    }
    else {
      operation = Turn.Operation.NOTHING;
    }
    return new Turn(operation, inputNumber, operation.apply(inputNumber) / 3);
  }

}
