package com.compilit.spgetti;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * Using this annotation you can easily bootstrap all the necessary beans for the mediator.
 */
@Import(MediatorConfiguration.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE})
public @interface EnableMediator {
}
