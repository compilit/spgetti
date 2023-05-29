package com.compilit.spgetti;

import com.compilit.spgetti.api.Event;
import com.compilit.spgetti.api.EventHandler;
import com.compilit.spgetti.api.HandlerMatcher;
import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestHandler;
import java.util.List;
import java.util.function.Supplier;

final class RequestHandlerManager extends HandlerManager {

  private final Supplier<List<RequestHandler<?, ?>>> requestHandlerProvider;

  RequestHandlerManager(Supplier<List<RequestHandler<?, ?>>> requestHandlerProvider) {
    this.requestHandlerProvider = requestHandlerProvider;
  }

  List<? extends EventHandler<? extends Event>> getEventHandlers(Event event, HandlerMatcher handlerMatcher) {
    var identifiableRequest = new IdentifiableRequest<>(event);
    var provider = handlerCache.computeIfAbsent(identifiableRequest.getIdentifier(), i -> {
      var matchingEventHandlers = findMatchingHandlers(
        event,
        requestHandlerProvider.get(),
        handlerMatcher
      );
      return new EventHandlerProvider<>(matchingEventHandlers);
    });
    return (List<? extends EventHandler<? extends Event>>) provider.provide();
  }

  <R> RequestHandler<Request<R>, R> getRequestHandler(Request<R> request, HandlerMatcher handlerMatcher) {
    var identifiableRequest = new IdentifiableRequest<>(request);
    var provider = handlerCache.computeIfAbsent(identifiableRequest.getIdentifier(), x -> {
      RequestHandler<Request<R>, R> handler = findRequestHandler(identifiableRequest, handlerMatcher);
      return new RequestHandlerProvider<>(handler);
    });
    return (RequestHandler<Request<R>, R>) provider.provide();
  }

  private <T extends Request<R>, R> RequestHandler<Request<R>, R> findRequestHandler(IdentifiableRequest<T> identifiableRequest,
                                                                                     HandlerMatcher handlerMatcher) {
    var requestName = identifiableRequest.getIdentifier().get();
    var requestHandlers = requestHandlerProvider.get();
    List<RequestHandler<?, ?>> handlers = findMatchingHandlers(identifiableRequest.innerRequest(), requestHandlers, handlerMatcher);
    validateResult(handlers, requestName);
    return (RequestHandler<Request<R>, R>) handlers.get(FIRST_ENTRY);
  }
}
