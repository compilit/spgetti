package com.compilit.spgetti;

import com.compilit.spgetti.api.Event;
import com.compilit.spgetti.api.EventEmitter;


public class MediatingEventEmitter implements EventEmitter {

  private final Mediator mediator;

  public MediatingEventEmitter(Mediator mediator) {
    this.mediator = mediator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void emit(Event event, Event... events) {
    emit(false, event, events);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void emitAsync(Event event, Event... events) {
    emit(true, event, events);
  }

  private void emit(boolean async, Event event, Event[] events) {
    var eventReflection = Reflection.of(event);
    mediator.mediateEvent(eventReflection, async);
    if (event != null) {
      for (var e : events) {
        var eReflection = Reflection.of(e);
        mediator.mediateEvent(eReflection, async);
      }
    }
  }

}
