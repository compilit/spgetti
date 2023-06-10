package com.compilit.spgetti;

import java.util.List;

interface HandlerProvider<R> {

  List<R> provide(String classSignature, ValidationStrategy validationStrategy);

}
