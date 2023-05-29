package com.compilit.spgetti;

import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestHandler;

final class RequestHandlerProvider<H extends RequestHandler<C, R>, C extends Request<R>, R>
  implements HandlerProvider<H> {

  private final H handler;

  public RequestHandlerProvider(H handler) {
    this.handler = handler;
  }

  @Override
  public H provide() {
    return handler;
  }
}
