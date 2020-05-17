package com.melv.gameofthree.main.api.game;

/**
 * API to start and play games games and access game information.
 */
public interface GameManager extends GameEventHandler {

  /**
   * attempts to start a game. does nothing if no player is connected
   */
  void startNewGame();

  String localPlayerName();
  String opponentName();

}
