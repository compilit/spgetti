package com.compilit.spgetti.api;

/**
 * A QueryHandler is a managed bean that is dedicated to handling a specific Query, represented by T.
 *
 * @param <T> The specific Query implementation.
 * @param <R> The return type of the Query.
 */
public interface QueryHandler<T extends Query<R>, R> extends RequestHandler<T, R> {

  @Override
  R handle(T query);
}
