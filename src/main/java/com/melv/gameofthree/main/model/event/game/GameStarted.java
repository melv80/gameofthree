package com.melv.gameofthree.main.model.event.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melv.gameofthree.main.model.Player;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * occurs when a game has been started.
 */
public final class GameStarted extends Event {

  @JsonProperty("firstPlayer")
  @NotNull
  public final Player first;

  @JsonProperty("secondPlayer")
  @NotNull
  public final Player second;

  @Min(3)
  @JsonProperty("startingNumer")
  public final long startNumber;

  @JsonCreator
  public GameStarted(@JsonProperty("firstPlayer")Player first, @JsonProperty("secondPlayer") Player second, @JsonProperty("startingNumer") long startNumber) {
    this.first = first;
    this.second = second;
    this.startNumber = startNumber;
  }


  @Override
  public String toString() {
    return "GameStarted{" +
        "first=" + first +
        ", second=" + second +
        ", startNumber=" + startNumber +
        ", senderID=" + senderID +
        '}';
  }
}
