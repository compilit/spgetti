package com.compilit.spgetti;

import com.compilit.spgetti.api.HandlerMatcher;
import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestHandler;
import java.util.Objects;

class CoreHandlerMatcher implements HandlerMatcher {

  @Override
  public boolean canHandle(RequestHandler<?, ?> requestHandler, Request<?> request) {
    var requestReflection = Reflection.of(request);
    var handlerReflection = Reflection.of(requestHandler);
    return handlerReflection.getInterfaceTypeParameter(0, 0)
                            .map(typeName -> Objects.equals(requestReflection.getClassName(), typeName))
                            .orElse(false);
  }
}
