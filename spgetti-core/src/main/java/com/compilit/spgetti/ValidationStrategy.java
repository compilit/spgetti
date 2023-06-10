package com.compilit.spgetti;

import java.util.List;

interface ValidationStrategy {

  void validate(String classSignature, List<?> input);
}
