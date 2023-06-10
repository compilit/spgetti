package com.compilit.spgetti;

import com.compilit.spgetti.api.HandlerMatcher;
import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestHandler;
import java.util.List;
import java.util.function.Supplier;

final class RequestHandlerManager extends HandlerManager {

  private final Supplier<List<RequestHandler<?, ?>>> requestHandlerProvider;

  RequestHandlerManager(Supplier<List<RequestHandler<?, ?>>> requestHandlerSupplier) {
    this.requestHandlerProvider = requestHandlerSupplier;
  }

  <T extends Request<R>, R> List<RequestHandler<T, R>> getRequestHandlers(Reflection<T> requestReflection,
                                                                          HandlerMatcher handlerMatcher,
                                                                          ValidationStrategy validationStrategy) {
    var provider = handlerCache.computeIfAbsent(requestReflection.getClassSignature(), x -> {
      List<RequestHandler<?, ?>> handlers = findMatchingHandlers(
        requestReflection,
        requestHandlerProvider.get(),
        handlerMatcher
      );
      return new RequestHandlerProvider<>(handlers);
    });
    return (List<RequestHandler<T, R>>) provider.provide(requestReflection.getClassName(), validationStrategy);
  }

}
