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
  public <T extends Request<R>, R> R mediateRequest(Reflection<T> requestReflection) {
    var requestHandlers = requestHandlerManager.getRequestHandlers(
      requestReflection,
      handlerMatcher,
      new SingleMatchValidationStrategy()
    );
    var matchingRequestHandler = requestHandlers.get(0);
    Reflection<RequestHandler<T, R>> handlerReflection = Reflection.of(matchingRequestHandler);
    return applyHandler(requestReflection, handlerReflection, false);
  }

  @Override
  public <T extends Event> void mediateEvent(Reflection<T> event, boolean async) {
    if (async) {
      executorService.submit(() -> handleEvent(event, true));
    } else {
      handleEvent(event, false);
    }
  }

  private <T extends Event> void handleEvent(Reflection<T> eventReflection, boolean asyncNestedEvents) {
    logger.info("Emitting {}", eventReflection.getClassSignature());
    var requestHandlers = requestHandlerManager.getRequestHandlers(
      eventReflection,
      handlerMatcher,
      new NoMatchValidationStrategy()
    );
    for (var requestHandler : requestHandlers) {
      Reflection<RequestHandler<T, Void>> handlerReflection = Reflection.of(requestHandler);
      applyHandler(eventReflection, handlerReflection, asyncNestedEvents);
    }
  }

  private <T extends Request<R>, R> R applyHandler(Reflection<T> requestReflection,
                                                   Reflection<RequestHandler<T, R>> handlerReflection,
                                                   boolean asyncNestedEvents) {
    var request = requestReflection.getSubject();
    var handler = handlerReflection.getSubject();
    logger.info("Handling {} using {}", requestReflection.getClassSignature(), handlerReflection.getClassSignature());
    emit(handler.onAccepted(request), request, asyncNestedEvents);
    var result = handler.handle(request);
    var resultName = result != null ? result.getClass() : "Void";
    logger.info(
      "Handler {} returned {} for {}",
      handlerReflection.getClassSignature(),
      resultName,
      requestReflection.getClassSignature()
    );
    emit(handler.onHandled(request, result), request, asyncNestedEvents);
    return result;
  }

  private <T extends Request<?>> void emit(Event eventToEmit, T request, boolean asyncNestedEvents) {
    if (!(request instanceof Event) && eventToEmit != null) {
      mediateEvent(Reflection.of(eventToEmit), asyncNestedEvents);
    }
  }

}
