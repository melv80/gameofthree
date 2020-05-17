package com.melv.gameofthree.main.model.event.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.function.ToLongFunction;

/**
 * this event occurs when one player makes a turn
 */
public final class Turn extends Event {

  @JsonProperty("operation")
  public final Operation operation;

  @JsonProperty("startingNumber")
  public final long startNumber;

  @JsonProperty("resultingNumber")
  public final long resultingNumber;

  @JsonCreator
  public Turn(@JsonProperty("operation") Operation operation,
              @JsonProperty("startingNumber") long startNumber,
              @JsonProperty("resultingNumber") long resultingNumber) {
    this.operation = operation;
    this.startNumber = startNumber;
    this.resultingNumber = resultingNumber;
  }

  @Override
  public String toString() {
    return "Turn{" +
        "operation=" + operation +
        ", startNumber=" + startNumber +
        ", resultingNumber=" + resultingNumber +
        ", sender=" + senderID +
        '}';
  }

  public enum Operation {
    DECREMENT_ONE((number) -> number - 1, "- 1") ,
    NOTHING((number) -> number, "+ 0"),
    INCREMENT_ONE((number) -> number +1, "+ 1");


    private final String description;
    private ToLongFunction<Long> operation;

    Operation(ToLongFunction<Long> op, String description) {
      this.operation = op;
      this.description = description;
    }

    public long apply(long input) {
      return operation.applyAsLong(input);
    }

    public String getDescription() {
      return description;
    }
  }
}
