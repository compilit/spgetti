package com.compilit.spgetti.api;

public interface HandlerMatcher {
  boolean canHandle(RequestHandler<?,?> requestHandler, Request<?> request);
}
