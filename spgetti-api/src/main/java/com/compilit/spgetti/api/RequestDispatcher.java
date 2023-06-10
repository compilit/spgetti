package com.compilit.spgetti.api;

import java.util.concurrent.Future;

/**
 * A RequestDispatcher is a dedicated interface for sending Queries to the Mediator. A Mediator should never be interacted
 * with directly because you could never truly know that your code complies with CQRS.
 *
 * @see Request
 */
public interface RequestDispatcher {

  /**
   * Send the query into the mediator. If a matching handler is found, return the result of this handler.
   *
   * @param request The specific Request to send to the Mediator.
   * @param <T>   The request type.
   * @param <R>   The return type.
   * @return R The return type value.
   */
  <R, T extends Request<R>> R dispatch(T request);

  /**
   * Send the query into the mediator. If a matching handler is found, return the result of this handler.
   *
   * @param request The specific Request to send to the Mediator.
   * @param <T>   The request type.
   * @param <R>   The return type.
   * @return R The return type value wrapped in a Future.
   */
  <R, T extends Request<R>> Future<R> dispatchAsync(T request);
}
