package com.compilit.spgetti;

import com.compilit.spgetti.api.RequestHandler;
import java.lang.reflect.Type;
import java.util.Arrays;

final class HandlerIdentifier implements Identifier {

  private final String identifier;

  public HandlerIdentifier(Class<? extends RequestHandler> handlerClass) {
    var genericInterfaces = handlerClass.getGenericInterfaces();
    var handler = handlerClass.getTypeName();
    var handlerInterfaceName = Arrays.stream(genericInterfaces)
                                     .map(Type::getTypeName)
                                     .findFirst()
                                     .orElse("");
    this.identifier = String.format("%s implements %s", handler, handlerInterfaceName);
  }

  @Override
  public String get() {
    return identifier;
  }
}
