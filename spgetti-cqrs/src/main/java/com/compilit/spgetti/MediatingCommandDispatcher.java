package com.compilit.spgetti;

import com.compilit.spgetti.api.Command;
import com.compilit.spgetti.api.CommandDispatcher;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

final class MediatingCommandDispatcher implements CommandDispatcher {

  private final Mediator mediator;

  public MediatingCommandDispatcher(Mediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public <T> T dispatch(Command<T> command) {
    return mediator.mediateRequest(command);
  }

  @Override
  public <T> Future<T> dispatchAsync(Command<T> command) {
    return CompletableFuture.supplyAsync(() -> dispatch(command));
  }

}
