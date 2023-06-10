package com.compilit.spgetti;

import com.compilit.spgetti.api.HandlerMatcher;
import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

abstract class HandlerManager {

  protected final Map<String, HandlerProvider<?>> handlerCache = new HashMap<>();

  protected <H extends RequestHandler<?, ?>, T extends Request> List<H> findMatchingHandlers(Reflection<T> requestReflection,
                                                                                             List<H> requestHandlers,
                                                                                             HandlerMatcher handlerMatcher) {
    return requestHandlers.stream()
                          .filter(requestHandler -> requestHandler.canHandle(requestReflection.getSubject())
                            || handlerMatcher.canHandle(requestHandler, requestReflection.getSubject()))
                          .collect(Collectors.toList());
  }

}
