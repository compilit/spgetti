package com.compilit.spgetti.api;

import java.util.concurrent.Future;

/**
 * A QueryDispatcher is a dedicated interface for sending Queries to the Mediator. A Mediator should never be interacted
 * with directly because you could never truly know that your code complies with CQERS.
 *
 * @see Query
 */
public interface QueryDispatcher {

  /**
   * Send the query into the mediator. If a matching handler is found, return the result of this handler.
   *
   * @param query The specific Query to send to the Mediator.
   * @param <T>   The return type.
   * @return T The return type value.
   */
  <T> T dispatch(Query<T> query);

  /**
   * Send the query into the mediator. If a matching handler is found, return the result of this handler.
   *
   * @param query The specific Query to send to the Mediator.
   * @param <T>   The return type.
   * @return T The return type value wrapped in a Future.
   */
  <T> Future<T> dispatchAsync(Query<T> query);
}
