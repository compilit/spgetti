package com.compilit.spgetti;

import com.compilit.narcissus.Reflection;
import com.compilit.spgetti.api.Query;
import com.compilit.spgetti.api.QueryDispatcher;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * {@inheritDoc}
 */
public class MediatingQueryDispatcher implements QueryDispatcher {

  private final Mediator mediator;

  public MediatingQueryDispatcher(Mediator mediator) {
    this.mediator = mediator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T dispatch(Query<T> query) {
    var queryReflection = Reflection.of(query);
    return mediator.mediateRequest(queryReflection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> Future<T> dispatchAsync(Query<T> query) {
    return CompletableFuture.supplyAsync(() -> dispatch(query));
  }

}
