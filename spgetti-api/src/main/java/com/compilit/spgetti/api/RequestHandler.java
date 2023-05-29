package com.compilit.spgetti.api;

/**
 * A generic request for all request handlers.
 *
 * @param <T> The type of request.
 * @param <R> The return type of the request.
 * @see EventHandler
 */
public interface RequestHandler<T extends Request<R>, R> {

  /**
   * Handle the given request
   *
   * @param request The specific implementation of either a Command, Query or Event.
   * @return R The return type of the request.
   */
  R handle(T request);

  /**
   * A factory method to create the event which will be emitted upon starting the handling of the request. Overriding
   * this method will allow you to specify custom events to be emitted for certain life cycle hooks.
   *
   * @param request the request that is going to be handled
   * @return the event to emit
   */
  default Event onAccepted(T request) {
    return null;
  }

  /**
   * A factory method to create the event which will be emitted upon handling of the request. Overriding this
   * method will allow you to specify custom events to be emitted for certain life cycle hooks.
   *
   * @param request the request that was handled
   * @param result  the returned value of the handler, or null
   * @return the event to emit
   */
  default Event onHandled(T request, R result) {
    return null;
  }

  /**
   * Overriding this method will allow you to customize the handler matching. Note that you will have the responsibility
   * of making sure that the request can in fact be handled by the handler. This means that you need to cast the request
   * explicitly to the desired request type.
   *
   * @param request the request to test against this handler instance
   * @param <Q>     the request type
   * @return true if this handler can handle the provided request
   */
  default <Q extends Request<?>> boolean canHandle(Q request) {
    return false;
  }
}
