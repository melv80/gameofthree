package com.melv.gameofthree.main.implementation.ui;

import com.melv.gameofthree.main.model.Player;
import com.melv.gameofthree.main.model.event.ui.UIEvent;
import com.melv.gameofthree.main.model.event.game.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UIEventCreator {

  private static final MessageFormatter DEFAULT = Object::toString;
  private Map<Class<?>, MessageFormatter> formatters = new HashMap<>();

  public UIEventCreator() {
    formatters.put(ReadyToPlay.class, UIEventCreator::readyToPlay);
    formatters.put(GameStarted.class, UIEventCreator::formatGameStarted);
    formatters.put(Turn.class       , UIEventCreator::formatTurn);
    formatters.put(GameOver.class   , UIEventCreator::formatGameOver);
  }

  public UIEvent fromGameEvent(Player player, Event event) {
    MessageFormatter messageFormatter = formatters.getOrDefault(event.getClass(), DEFAULT);
    return new UIEvent(player.name, messageFormatter.formatEvent(event));
  }

  private static String readyToPlay(Event event) {
    return  "is ready to play";
  }

  private static String formatGameStarted(Event event) {
    return  "starts the game with number: "+ ((GameStarted) event).startNumber;
  }

  private static String formatGameOver(Event eve) {
    GameOver event = (GameOver) eve;
    return formatTurn(event.winningTurn) + " makes "+ event.winner.name + " the winner.";
  }

  private static String formatTurn(Event event) {
    Turn turn = (Turn) event;
    return "("+ turn.startNumber + " "+turn.operation.getDescription() + ") / 3 = "+turn.resultingNumber;
  }


  @FunctionalInterface
  private interface MessageFormatter {
    String formatEvent(Event event);
  }
}
