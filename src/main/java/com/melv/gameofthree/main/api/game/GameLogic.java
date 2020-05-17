package com.melv.gameofthree.main.api.game;

import com.melv.gameofthree.main.model.Player;
import com.melv.gameofthree.main.model.event.game.Turn;

import javax.validation.constraints.NotNull;

/**
 * class that implements game logic and moves.
 */
public abstract class GameLogic {

  /**
   *
   * @param player1
   * @param player2
   * @param startNumber must be > 1
   */
  public abstract Turn startGame(@NotNull Player player1, @NotNull Player player2, long startNumber);

  /**
   * @param activePlayer  player whose turn it is
   * @param previousTurn  previous turn
   * @return a valid turn
   */
  public abstract Turn doTurn(Player activePlayer, Turn previousTurn);

  /**
   * @return a random whole number that is sent to the other player as opening turn
   */
  public abstract long getStartingNumber();

}
