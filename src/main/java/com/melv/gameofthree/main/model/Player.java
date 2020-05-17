package com.melv.gameofthree.main.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public final class Player {
  @JsonProperty("name")
  @NotBlank
  public final String name;

  @JsonProperty("playerID")
  public final int playerID;

  @JsonCreator
  public Player(@JsonProperty("name") String name, @JsonProperty("playerID") int playerID) {
    this.name = name;
    this.playerID = playerID;
  }

  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        ", playerID=" + playerID +
        '}';
  }
}
