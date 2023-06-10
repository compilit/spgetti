package com.compilit.spgetti.api;

/**
 * Through a HandlerMatcher you are able to externalize the matching of Requests to RequestHandlers and all of their
 * subclasses. Externalizing means you can use other frameworks to do the matching.
 */
public interface HandlerMatcher {

  /** Find out if the given Request can be handled by the given RequestHandler.
   *
   * @param requestHandler the RequestHandler you want to match against the Request.
   * @param request the Request you want to match against the RequestHandler.
   * @return true if the RequestHandler can in fact handle the given Request.
   */
  boolean canHandle(RequestHandler<?, ?> requestHandler, Request<?> request);
}
