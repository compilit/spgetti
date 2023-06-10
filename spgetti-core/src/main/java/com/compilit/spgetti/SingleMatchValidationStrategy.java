package com.compilit.spgetti;

import java.util.List;

class SingleMatchValidationStrategy implements ValidationStrategy {

  @Override
  public void validate(String classSignature, List<?> input) {
    if (input.isEmpty()) {
      throw new MediatorException(ExceptionMessages.handlerNotFoundMessage(classSignature));
    }
    if (input.size() != 1) {
      throw new MediatorException(ExceptionMessages.multipleHandlersRegisteredMessage(classSignature));
    }
  }
}
