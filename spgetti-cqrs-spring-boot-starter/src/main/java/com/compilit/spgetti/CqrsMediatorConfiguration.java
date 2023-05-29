package com.compilit.spgetti;

import com.compilit.spgetti.api.CommandDispatcher;
import com.compilit.spgetti.api.EventEmitter;
import com.compilit.spgetti.api.QueryDispatcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CqrsMediatorConfiguration {


  @Bean
  CommandDispatcher createCommandDispatcher(Mediator mediator) {
    return new MediatingCommandDispatcher(mediator);
  }

  @Bean
  QueryDispatcher createQueryDispatcher(Mediator mediator) {
    return new MediatingQueryDispatcher(mediator);
  }

  @Bean
  InitializingBean createInstanceProvider(CommandDispatcher commandDispatcher,
                                          QueryDispatcher queryDispatcher,
                                          EventEmitter eventEmitter) {
    return new Dispatchers(commandDispatcher, queryDispatcher, eventEmitter);
  }


}