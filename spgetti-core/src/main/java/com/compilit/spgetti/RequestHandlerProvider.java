package com.compilit.spgetti;

import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestHandler;
import java.util.List;

final class RequestHandlerProvider<H extends RequestHandler<C, R>, C extends Request<R>, R>
  implements HandlerProvider<H> {

  private final List<H> handlers;

  public RequestHandlerProvider(List<RequestHandler<?, ?>> handlers) {
    this.handlers = (List<H>) handlers;
  }

  @Override
  public List<H> provide(String classSignature, ValidationStrategy validationStrategy) {
    validationStrategy.validate(classSignature, handlers);
    return handlers;
  }
}
