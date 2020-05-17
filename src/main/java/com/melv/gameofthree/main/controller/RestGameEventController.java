package com.melv.gameofthree.main.controller;


import com.melv.gameofthree.main.model.event.game.GameOver;
import com.melv.gameofthree.main.model.event.game.GameStarted;
import com.melv.gameofthree.main.model.event.game.ReadyToPlay;
import com.melv.gameofthree.main.model.event.game.Turn;
import com.melv.gameofthree.main.api.game.GameEventHandler;
import com.melv.gameofthree.main.api.game.GameManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1")
/*
 * responsible rest controller handles game events that occur via a rest api.
 */
public class RestGameEventController implements GameEventHandler {
  private Logger logger = LoggerFactory.getLogger(getClass().getName());


  @Autowired
  private GameManager gameManager;

  public RestGameEventController(@Autowired GameManager gameManager) {
    this.gameManager = gameManager;
  }

  @RequestMapping(
      value = "gameStarted",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.CREATED)
  public void gameStarted(@RequestBody @NotNull @Valid GameStarted gameEvent) {
    logger.info("received: "+gameEvent);
    gameManager.gameStarted(gameEvent);
  }

  @RequestMapping(
      value = "handleTurn",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.CREATED)
  public void handleTurn(@RequestBody @NotNull Turn gameEvent) {
    logger.info("received: "+ gameEvent);
    gameManager.handleTurn(gameEvent);
  }

  @RequestMapping(
      value = "gameEnded",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.CREATED)
  public void gameEnded(@RequestBody @NotNull GameOver gameEvent) {
    logger.info("received: "+ gameEvent);
    gameManager.gameEnded(gameEvent);
  }

  @RequestMapping(
      value = "playerReady",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public void playerReady(@RequestBody @NotNull ReadyToPlay gameEvent) {
    logger.info("received: "+ gameEvent);
    gameManager.playerReady(gameEvent);
  }

}
