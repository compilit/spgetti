package com.compilit.spgetti;

import com.compilit.spgetti.api.HandlerMatcher;
import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestHandler;

class CoreHandlerMatcher implements HandlerMatcher {

  @Override
  public boolean canHandle(RequestHandler<?,?> requestHandler, Request<?> request) {
    return new IdentifiableRequestHandler<>(requestHandler).canHandle(new IdentifiableRequest<>(request));
  }
}
