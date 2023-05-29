package com.compilit.spgetti;

interface Identifiable {
  default Identifier getIdentifier() {
    return new Identifier() {
      @Override
      public String get() {
        return getClass().getTypeName();
      }
    };
  }
}
