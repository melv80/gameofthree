package com.melv.gameofthree.main.model.event.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.melv.gameofthree.main.model.Player;

/**
 * exchanged between services in order to show that the player is ready.
 */
public final class ReadyToPlay extends Event {

  @JsonProperty("player")
  public final Player player;

  @JsonProperty("sendResponseTo")
  public final String responseEndpoint;

  public ReadyToPlay(@JsonProperty("player") Player player,
                     @JsonProperty("sendResponseTo") String responseEndpoint) {
    this.player = player;
    this.responseEndpoint = responseEndpoint;
  }

  @Override
  public String toString() {
    return "ReadyToPlay{" +
        "player=" + player +
        ", senderID=" + senderID +
        '}';
  }
}
