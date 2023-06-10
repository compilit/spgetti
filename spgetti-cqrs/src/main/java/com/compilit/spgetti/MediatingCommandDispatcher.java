package com.compilit.spgetti;

import com.compilit.narcissus.Reflection;
import com.compilit.spgetti.api.Command;
import com.compilit.spgetti.api.CommandDispatcher;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * {@inheritDoc}
 */
public class MediatingCommandDispatcher implements CommandDispatcher {

  private final Mediator mediator;

  public MediatingCommandDispatcher(Mediator mediator) {
    this.mediator = mediator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T dispatch(Command<T> command) {
    var commandReflection = Reflection.of(command);
    return mediator.mediateRequest(commandReflection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> Future<T> dispatchAsync(Command<T> command) {
    return CompletableFuture.supplyAsync(() -> dispatch(command));
  }

}
