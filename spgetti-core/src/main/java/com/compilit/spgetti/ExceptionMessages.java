package com.compilit.spgetti;

final class ExceptionMessages {

  public static final String HANDLER_NOT_FOUND = "Failed to find matching handler.";
  public static final String MULTIPLE_MATCHING_HANDLERS = "A handler for this request is already registered, only Events can have multiple handlers.";

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
