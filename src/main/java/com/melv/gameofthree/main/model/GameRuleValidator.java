package com.melv.gameofthree.main.model;

import com.melv.gameofthree.main.model.event.game.Turn;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public final class GameRuleValidator {
  /**
   * validates a number to start the game with
   * @param input > 1
   * @throws IllegalArgumentException if input is invalid
   */
  public void validateStartGameNumber(long input) {
    if (input <=1) throw new IllegalArgumentException("invalid start number: "+input);
  }

  /**
   * validates a number to start the game with
   * @param input > 1
   * @throws IllegalArgumentException if input is invalid
   */
  public void validateInput(long input) {
    if (input <=0) throw new IllegalArgumentException("invalid input: "+input);
  }


  public void validateTurn(Turn turn) {
    if ((turn.operation.apply(turn.startNumber)  / 3) != turn.resultingNumber)
      throw new IllegalArgumentException("illegal turn: "+turn);

    validateInput(turn.startNumber);
    validatePlayerID(turn.senderID);
    validatePlayerID(turn.receiverID);
  }

  public void validatePlayerID(int playerID) {
    if (playerID ==0) throw new IllegalArgumentException("invalid playerID: "+playerID);
  }


  /**
   * @param turn turn to check
   * @return true if the given turn is a winning turn
   */
  public final boolean testWin(@NotNull Turn turn) {
    validateTurn(turn);
    return turn.resultingNumber == 1;
  }
}
