package com.melv.gameofthree.main.model.event.ui;

import javax.validation.constraints.NotNull;

/**
 * sent to the web client to display game
 */
public final class UIEvent {

  public final String player;

  public final String message;

  public UIEvent(@NotNull String player, @NotNull String message) {
    this.player = player;
    this.message = message;
  }

}
