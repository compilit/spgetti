package com.compilit.spgetti;

import com.compilit.spgetti.api.HandlerMatcher;
import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestHandler;
import org.springframework.core.GenericTypeResolver;

class RequestHandlerMatcher implements HandlerMatcher {

  @Override
  public boolean canHandle(RequestHandler<?, ?> requestHandler, Request<?> request) {
    var requestType = request.getClass();
    var handlerType = requestHandler.getClass();
    var requestClassArray = GenericTypeResolver.resolveTypeArguments(handlerType, RequestHandler.class);
    return requestClassArray != null && requestClassArray[0].isAssignableFrom(requestType);
  }
}
