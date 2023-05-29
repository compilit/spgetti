package com.compilit.spgetti;
interface Identifier {
    String get();

    default boolean matches(Identifier requestIdentifier) {
        return get().contains(requestIdentifier.get());
    }
}
