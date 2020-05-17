package com.melv.gameofthree.main.model.event.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melv.gameofthree.main.model.Player;

/**
 * occurs when a game is over.
 */
public final class GameOver extends Event {
  @JsonProperty("winner")
  public final Player winner;

  @JsonProperty("winningTurn")
  public final Turn winningTurn;

  @JsonCreator
  public GameOver(@JsonProperty("winner") Player winner, @JsonProperty("winningTurn") Turn winningTurn) {
    this.winner = winner;
    this.winningTurn = winningTurn;
  }

  @Override
  public String toString() {
    return "GameOver{" +
        "winner=" + winner +
        ", winningTurn=" + winningTurn +
        ", sender=" + senderID +
        '}';
  }
}
