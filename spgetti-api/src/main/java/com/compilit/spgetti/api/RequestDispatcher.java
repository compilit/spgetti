package com.compilit.spgetti.api;

import java.util.concurrent.Future;

public interface RequestDispatcher {

  <R, T extends Request<R>> R dispatch(T request);

  <R, T extends Request<R>> Future<R> dispatchAsync(T request);
}
