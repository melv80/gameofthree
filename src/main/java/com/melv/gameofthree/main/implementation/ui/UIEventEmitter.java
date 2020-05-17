package com.melv.gameofthree.main.implementation.ui;

import com.melv.gameofthree.main.config.ServiceConfig;
import com.melv.gameofthree.main.model.Player;
import com.melv.gameofthree.main.model.event.game.Event;
import com.melv.gameofthree.main.model.event.ui.UIEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public final class UIEventEmitter {

  @Autowired
  private UIEventCreator messageCreator;

  @Autowired
  private ServiceConfig config;

  private BlockingQueue<UIEvent> events = new LinkedBlockingQueue<>();

  public void emitUIEvent(Player sender, Event event) {
    events.add(messageCreator.fromGameEvent(sender, event));
  }

  public Flux<UIEvent> uiEventsStream() {
    return Flux.interval(Duration.ofMillis(config.getEmitterDelay()))
        .onBackpressureDrop()
        .map(this::drainEvents)
        .flatMapIterable(x -> x)
        .delayElements(Duration.ofMillis(config.getEmitterDelay()));
  }


  private List<UIEvent> drainEvents(Long __) {
    List<UIEvent> res = new ArrayList<>();
    events.drainTo(res);
    return res;
  }

}
