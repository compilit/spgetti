package com.compilit.spgetti;

import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestDispatcher;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


final class MediatingRequestDispatcher implements RequestDispatcher {

  private final Mediator mediator;

  public MediatingRequestDispatcher(Mediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public <R, T extends Request<R>> R dispatch(T request) {
    return mediator.mediateRequest(request);
  }

  @Override
  public <R, T extends Request<R>> Future<R> dispatchAsync(T request) {
    return CompletableFuture.supplyAsync(() -> mediator.mediateRequest(request));
  }
}
