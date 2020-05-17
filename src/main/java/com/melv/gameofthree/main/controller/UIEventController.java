package com.melv.gameofthree.main.controller;

import com.melv.gameofthree.main.implementation.ui.UIEventEmitter;
import com.melv.gameofthree.main.model.event.ui.UIEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
public class UIEventController {

  @Autowired
  private UIEventEmitter uiEventEmitter;

  @GetMapping(path = "/ui/eventstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<UIEvent> eventstream() {
    return this.uiEventEmitter.uiEventsStream();
  }

}
