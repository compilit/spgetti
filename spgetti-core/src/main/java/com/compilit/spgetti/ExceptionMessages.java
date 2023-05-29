package com.compilit.spgetti;

final class ExceptionMessages {

  private ExceptionMessages() {
  }

  public static String handlerNotFoundMessage(String requestName) {
    return String.format("Failed to find handler for %s.", requestName);
  }

  public static String multipleHandlersRegisteredMessage(String requestName) {
    return String.format(
      "A handler for %s is already registered, only Events can have multiple handlers.",
      requestName
    );
  }

}
