package com.compilit.spgetti;

import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestHandler;

final class Identifiers {

  private Identifiers() {}

  static Identifier from(RequestHandler<?, ?> requestHandler) {
    return new HandlerIdentifier(requestHandler.getClass());
  }

  static Identifier from(Request<?> request) {
    if (request instanceof IdentifiableRequest<?>) {
      IdentifiableRequest<?> identifiableRequest = (IdentifiableRequest<?>) request;
      return from(identifiableRequest.innerRequest());
    }
    return new RequestIdentifier(request);
  }

}
