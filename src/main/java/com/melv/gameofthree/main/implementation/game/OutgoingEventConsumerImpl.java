package com.melv.gameofthree.main.implementation.game;

import com.melv.gameofthree.main.config.ServiceConfig;
import com.melv.gameofthree.main.model.event.game.*;
import com.melv.gameofthree.main.api.game.GameEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Consumer;

@Service
public class OutgoingEventConsumerImpl implements GameEventHandler, Consumer<Event> {

  private final ServiceConfig config;

  private WebClient client;

  public OutgoingEventConsumerImpl(@Autowired ServiceConfig config) {
    this.config = config;
  }

  @Override
  @JmsListener(destination = "outbox", containerFactory = "messageContainerFactory")
  public void accept(Event event) {
    if (event instanceof GameStarted) {
      gameStarted((GameStarted) event);
    } else if (event instanceof Turn) {
      handleTurn((Turn) event);
    } else if (event instanceof GameOver) {
      gameEnded((GameOver) event);
    } else if (event instanceof ReadyToPlay) {
      playerReady((ReadyToPlay) event);
    }
  }

  private WebClient lazyClient() {
    if (client == null) {
      client = WebClient
          .builder()
          .baseUrl(config.getRemote())
          .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .build();
    }
    return client;
  }

  public void gameStarted(GameStarted event) {
    lazyClient()
        .post()
        .uri("/gameStarted")
        .body(BodyInserters.fromValue(event))
        .exchange().block();
  }


  public void playerReady(ReadyToPlay event) {
    lazyClient()
        .post()
        .uri("/playerReady")
        .body(BodyInserters.fromValue(event))
        .exchange().block();
  }


  public void handleTurn(Turn event) {
    lazyClient()
        .post()
        .uri("/handleTurn")
        .body(BodyInserters.fromValue(event))
        .exchange().block();
  }

  public void gameEnded(GameOver event) {
    lazyClient()
        .post()
        .uri("/gameEnded")
        .body(BodyInserters.fromValue(event))
        .exchange().block();
  }

}
