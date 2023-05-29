package com.compilit.spgetti;

import static com.compilit.spgetti.ExceptionMessages.handlerNotFoundMessage;
import static com.compilit.spgetti.ExceptionMessages.multipleHandlersRegisteredMessage;

import com.compilit.spgetti.api.HandlerMatcher;
import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

abstract class HandlerManager {

  protected static final int FIRST_ENTRY = 0;
  protected static final int EXPECTED_NON_EVENT_HANDLERS = 1;
  protected final Map<Identifier, HandlerProvider<?>> handlerCache = new HashMap<>();

  protected <H extends RequestHandler<?, ?>, T extends Request> List<H> findMatchingHandlers(T request,
                                                                                             List<H> requestHandlers,
                                                                                             HandlerMatcher handlerMatcher) {
    return requestHandlers.stream()
                          .filter(requestHandler -> requestHandler.canHandle(request)
                            || handlerMatcher.canHandle(requestHandler, request)
                          )
                          .collect(Collectors.toList());
  }

  protected void validateResult(List<?> list, String requestName) {
    if (list.isEmpty()) {
      throw new MediatorException(handlerNotFoundMessage(requestName));
    }
    if (list.size() > EXPECTED_NON_EVENT_HANDLERS) {
      throw new MediatorException(multipleHandlersRegisteredMessage(requestName));
    }
  }

}
