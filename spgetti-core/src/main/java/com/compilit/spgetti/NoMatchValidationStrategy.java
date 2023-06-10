package com.compilit.spgetti;

import java.util.List;

class NoMatchValidationStrategy implements ValidationStrategy {

  @Override
  public void validate(String classSignature, List<?> input) {
    if (input == null) {
      throw new MediatorException("Input for the validation strategy cannot be null");
    }
  }
}
