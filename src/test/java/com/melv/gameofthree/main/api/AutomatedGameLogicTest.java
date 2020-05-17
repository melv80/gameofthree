package com.melv.gameofthree.main.api;

import com.melv.gameofthree.main.implementation.game.AutomatedGameLogicImpl;
import com.melv.gameofthree.main.model.GameRuleValidator;
import com.melv.gameofthree.main.model.Player;
import com.melv.gameofthree.main.model.event.game.Turn;
import com.melv.gameofthree.main.api.game.GameLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotNull;

/**
 * make sure automated game logic play by standard rule set
 */
class AutomatedGameLogicTest {


  private GameLogic logic = new AutomatedGameLogicImpl(new GameRuleValidator());

  private static Player player1 = new Player("Player1", 1);
  private static Player player2 = new Player("Player2", 2);


  private Turn createTurn(long number) {
    // make a play
    final Turn turn = new Turn(Turn.Operation.NOTHING, number*3, number);
    turn.senderID = player1.playerID;
    turn.receiverID = player2.playerID;
    return turn;
  }

  @Test
  // warning this test is randomized
  void testRandomPlay() {
    // generate opening
    long openingNumber = logic.getStartingNumber();

    // make a play
    logic.doTurn(player1, createTurn(openingNumber));

  }


  @Test
  void testInvalid() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> logic.doTurn(player1, createTurn(-31)), "input was negative, hence invalid");
    Assertions.assertThrows(IllegalArgumentException.class, () -> logic.doTurn(player1, createTurn(0)), "input was 0, hence invalid");
  }

  @Test
  void testInvalidStartingNumber() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> logic.startGame(player1, player2, 1), "input was 1, hence invalid");
    Assertions.assertThrows(IllegalArgumentException.class, () -> logic.startGame(player1, player2, 0), "input was 0, hence invalid");
    Assertions.assertThrows(IllegalArgumentException.class, () -> logic.startGame(player1, player2, -1), "input was 0, hence invalid");
  }

  @Test
  void testValidTurn() {

    @NotNull Turn turn = logic.doTurn(player1, createTurn(33));
    Assertions.assertEquals(Turn.Operation.NOTHING, turn.operation, "input was already dividable by 3 ");
    Assertions.assertEquals(11, turn.resultingNumber);


    turn = logic.doTurn(player1, createTurn(32));
    Assertions.assertEquals(Turn.Operation.INCREMENT_ONE, turn.operation, "input needs to be incremented by one");
    Assertions.assertEquals(11, turn.resultingNumber);


    turn = logic.doTurn(player1, createTurn(31));
    Assertions.assertEquals(Turn.Operation.DECREMENT_ONE, turn.operation, "input needs to be decremented by one");
    Assertions.assertEquals(10, turn.resultingNumber);
  }



}