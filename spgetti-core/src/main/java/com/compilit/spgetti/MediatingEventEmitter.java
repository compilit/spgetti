package com.compilit.spgetti;

import com.compilit.spgetti.api.Event;
import com.compilit.spgetti.api.EventEmitter;


final class MediatingEventEmitter implements EventEmitter {

  private final Mediator mediator;

  public MediatingEventEmitter(Mediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public void emit(Event event, Event... events) {
    mediator.mediateEvent(event, false);
    if (event != null) {
      for (var e : events) {
        mediator.mediateEvent(e, false);
      }
    }
  }

  @Override
  public void emitAsync(Event event, Event... events) {
    mediator.mediateEvent(event, true);
    if (event != null) {
      for (var e : events) {
        mediator.mediateEvent(e, true);
      }
    }
  }

}
