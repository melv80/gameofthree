package com.melv.gameofthree.main.model.event.game;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Abstract event class that may implement basic features to all events.
 *
 */
public abstract class Event {

  /**
   * ID of the player that created this event.
   * This is important for event handling in order to dispatch the events correctly.
   */
  @JsonProperty("senderID")
  public int senderID;

  /**
   * ID of the player that created this event.
   * This is important for event handling in order to dispatch the events correctly.
   */
  @JsonProperty("receiverID")
  public int receiverID;

  public Event() {}

}
