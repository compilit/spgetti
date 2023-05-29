package com.compilit.spgetti;

import com.compilit.spgetti.api.Query;
import com.compilit.spgetti.api.QueryDispatcher;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


final class MediatingQueryDispatcher implements QueryDispatcher {

  private final Mediator mediator;

  public MediatingQueryDispatcher(Mediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public <T> T dispatch(Query<T> query) {
    return mediator.mediateRequest(query);
  }

  @Override
  public <T> Future<T> dispatchAsync(Query<T> query) {
    return CompletableFuture.supplyAsync(() -> dispatch(query));
  }

}
