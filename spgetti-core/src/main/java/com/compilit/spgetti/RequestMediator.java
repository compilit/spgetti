package com.compilit.spgetti;

import com.compilit.spgetti.api.Event;
import com.compilit.spgetti.api.HandlerMatcher;
import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class RequestMediator implements Mediator {

  private final ExecutorService executorService = Executors.newSingleThreadExecutor();
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final RequestHandlerManager requestHandlerManager;
  private final HandlerMatcher handlerMatcher;

  public RequestMediator(RequestHandlerManager requestHandlerManager) {
    this(requestHandlerManager, new CoreHandlerMatcher());
  }

  public RequestMediator(RequestHandlerManager requestHandlerManager,
                         HandlerMatcher handlerMatcher) {
    this.requestHandlerManager = requestHandlerManager;
    this.handlerMatcher = handlerMatcher;
  }

  @Override
  public <T extends Request<R>, R> R mediateRequest(T request) {
    RequestHandler<Request<R>, R> handler = requestHandlerManager.getRequestHandler(request, handlerMatcher);
    return applyHandler(request, handler, false);
  }

  @Override
  public <T extends Event> void mediateEvent(T event, boolean async) {
    if (async) {
      executorService.submit(() -> handleEvent(event, true));
    } else {
      handleEvent(event, false);
    }
  }

  private <T extends Event> void handleEvent(T event, boolean asyncNestedEvents) {
    var requestIdentifier = new IdentifiableRequest<>(event).getIdentifier();
    logger.info("Emitting {}", requestIdentifier);
    requestHandlerManager.getEventHandlers(event, handlerMatcher)
                       .forEach(handler -> applyHandler((Request<?>) event, (RequestHandler) handler, asyncNestedEvents));
  }

  private <T extends Request<R>, R> R applyHandler(T request, RequestHandler<T, R> handler, boolean asyncNestedEvents) {
    var requestIdentifier = new IdentifiableRequest<>(request).getIdentifier();
    var handleIdentifier = new IdentifiableRequestHandler<>(handler).getIdentifier();
    logger.info("Handling {} using {}", requestIdentifier, handleIdentifier);
    emit(handler.onAccepted(request), request, asyncNestedEvents);
    var result = handler.handle(request);
    var resultName = result != null ? result.getClass() : "Void";
    logger.info("Handler {} returned {} for {}", handleIdentifier, resultName, requestIdentifier);
    emit(handler.onHandled(request, result), request, asyncNestedEvents);
    return result;
  }

  private <T extends Request<?>> void emit(Event eventToEmit, T request, boolean asyncNestedEvents) {
    if (!(request instanceof Event) && eventToEmit != null) {
      mediateEvent(eventToEmit, asyncNestedEvents);
    }
  }

}
