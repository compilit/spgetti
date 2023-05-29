package com.compilit.spgetti;

import com.compilit.spgetti.api.EventEmitter;
import com.compilit.spgetti.api.HandlerMatcher;
import com.compilit.spgetti.api.RequestDispatcher;
import com.compilit.spgetti.api.RequestHandler;
import jakarta.inject.Provider;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MediatorConfiguration {

  @Bean
  HandlerMatcher createHandlerMatcher() {
    return new RequestHandlerMatcher();
  }

  @Bean
  RequestHandlerManager createRequestHandlerManager(Provider<List<RequestHandler<?, ?>>> requestHandlers) {
    return new RequestHandlerManager(requestHandlers::get);
  }

  @Bean
  Mediator createMediator(
    RequestHandlerManager requestHandlerManager,
    HandlerMatcher handlerMatcher
  ) {
    return new RequestMediator(requestHandlerManager, handlerMatcher);
  }

  @Bean
  RequestDispatcher createQueryDispatcher(Mediator mediator) {
    return new MediatingRequestDispatcher(mediator);
  }

  @Bean
  EventEmitter createEventEmitter(Mediator mediator) {
    return new MediatingEventEmitter(mediator);
  }

}