package com.compilit.spgetti;

import com.compilit.spgetti.api.RequestHandler;
import java.util.ArrayList;
import java.util.List;

final class EventHandlerProvider<H extends RequestHandler<?, ?>> implements HandlerProvider<List<H>> {

  private final List<H> handlers = new ArrayList<>();

  public EventHandlerProvider(List<H> handlers) {
    this.handlers.addAll(handlers);
  }

  @Override
  public List<H> provide() {
    return handlers;
  }
}
