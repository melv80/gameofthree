package com.melv.gameofthree.main.api.game;

import com.melv.gameofthree.main.model.event.game.GameOver;
import com.melv.gameofthree.main.model.event.game.GameStarted;
import com.melv.gameofthree.main.model.event.game.ReadyToPlay;
import com.melv.gameofthree.main.model.event.game.Turn;

/**
 * Handles game events.
 */
public interface GameEventHandler {

  /**
   * @param event a game has been started
   */
  void gameStarted(GameStarted event);

  /**
   * @param event  a player changed its ready state. Game can be started when both players are ready
   */
  void playerReady(ReadyToPlay event);

  /**
   * @param event a turn happened
   */
  void handleTurn(Turn event);

  /**
   * @param event a game is over
   */
  void gameEnded(GameOver event);
}
