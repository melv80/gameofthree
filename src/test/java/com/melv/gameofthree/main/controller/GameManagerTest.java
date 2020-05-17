package com.melv.gameofthree.main.controller;

import com.melv.gameofthree.main.MainApplication;
import com.melv.gameofthree.main.implementation.game.GameManagerImpl;
import com.melv.gameofthree.main.implementation.game.OutgoingEventConsumerImpl;
import com.melv.gameofthree.main.model.Player;
import com.melv.gameofthree.main.model.event.game.GameOver;
import com.melv.gameofthree.main.model.event.game.GameStarted;
import com.melv.gameofthree.main.model.event.game.ReadyToPlay;
import com.melv.gameofthree.main.model.event.game.Turn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;


@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class GameManagerTest {

  @Mock
  JmsTemplate jmsTemplate;

  @Mock
  OutgoingEventConsumerImpl out;

  @InjectMocks
  @Autowired
  GameManagerImpl gameManager;

  private static final Player player = new Player("Player1", 1);
  private static final Player player2 = new Player("Player2", 2);
  private static final Turn validTurn = new Turn(Turn.Operation.NOTHING, 12, 4);
  private static final Turn invalidTurn = new Turn(Turn.Operation.NOTHING, 12, 2);


  @BeforeAll
  static void setup() {
    validTurn.senderID = player.playerID;
    validTurn.receiverID = player2.playerID;

    invalidTurn.senderID = player.playerID;
    invalidTurn.receiverID = player2.playerID;
  }

  @BeforeEach
  void readyManager() {
    ReadyToPlay ready = new ReadyToPlay(player2, "/api/v1");
    gameManager.playerReady(ready);
  }

  @Test
  void validTurn() {
    gameManager.handleTurn(validTurn);
  }

  @Test
  void inValidTurn() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> gameManager.handleTurn(invalidTurn));
  }

  @Test
  void testStartGame() {
    GameStarted gameStarted = new GameStarted(player, player2, 67);
    gameManager.gameStarted(gameStarted);
  }

  @Test
  void testGameEndedINCREMENT() {
    Turn winningTurn = new Turn(Turn.Operation.INCREMENT_ONE, 2, 1);
    winningTurn.senderID = player.playerID;
    winningTurn.receiverID = player2.playerID;

    GameOver gameOver = new GameOver(player, winningTurn);
    gameManager.gameEnded(gameOver);
  }

  @Test
  void testGameEndedNOTHING() {
    Turn winningTurn = new Turn(Turn.Operation.NOTHING, 3, 1);
    winningTurn.senderID = player.playerID;
    winningTurn.receiverID = player2.playerID;

    GameOver gameOver = new GameOver(player, winningTurn);
    gameManager.gameEnded(gameOver);
  }

  @Test
  void testGameEndedDecrement() {
    Turn winningTurn = new Turn(Turn.Operation.DECREMENT_ONE, 4, 1);
    winningTurn.senderID = player.playerID;
    winningTurn.receiverID = player2.playerID;

    GameOver gameOver = new GameOver(player, winningTurn);
    gameManager.gameEnded(gameOver);
  }

}