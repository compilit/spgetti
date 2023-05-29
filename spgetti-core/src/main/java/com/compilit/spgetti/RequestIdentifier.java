package com.compilit.spgetti;

import com.compilit.spgetti.api.Request;

class RequestIdentifier implements Identifier {

  private final String identifier;

  RequestIdentifier(Request request) {
    var clazz = request.getClass();
    this.identifier = clazz.getTypeName();
  }

  @Override
  public String get() {
    return identifier;
  }

}
