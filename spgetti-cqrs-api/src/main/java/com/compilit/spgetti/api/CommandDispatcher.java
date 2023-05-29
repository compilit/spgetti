package com.compilit.spgetti.api;

import java.util.concurrent.Future;

/**
 * A CommandDispatcher is a dedicated interface for sending Commands to the Mediator. A Mediator should never be
 * interacted with directly because you could never truly know that your code complies with CQERS.
 *
 * @see Command
 */
public interface CommandDispatcher {

  /**
   * Send the command into the mediator. If a matching handler is found, return the result of this handler.
   *
   * @param command The specific Command you wish to send to the Mediator.
   * @param <T>     The return type of the Command.
   * @return The return type value.
   */
  <T> T dispatch(Command<T> command);


  /**
   * Send the command into the mediator. If a matching handler is found, return the result of this handler.
   *
   * @param command The specific Command you wish to send to the Mediator.
   * @param <T>     The return type of the Command.
   * @return The return type value wrapped in a Future.
   */
  <T> Future<T> dispatchAsync(Command<T> command);

}
