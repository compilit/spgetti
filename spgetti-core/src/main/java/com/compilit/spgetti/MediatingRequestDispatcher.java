package com.compilit.spgetti;

import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestDispatcher;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class MediatingRequestDispatcher implements RequestDispatcher {

  private final Mediator mediator;

  public MediatingRequestDispatcher(Mediator mediator) {
    this.mediator = mediator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <R, T extends Request<R>> R dispatch(T request) {
    var requestReflection = Reflection.of(request);
    return mediator.mediateRequest(requestReflection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <R, T extends Request<R>> Future<R> dispatchAsync(T request) {
    return CompletableFuture.supplyAsync(() -> dispatch(request));
  }
}
