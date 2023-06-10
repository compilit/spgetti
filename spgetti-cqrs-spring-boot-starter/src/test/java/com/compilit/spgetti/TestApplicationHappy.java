package com.compilit.spgetti;

import com.compilit.spgetti.api.CommandHandler;
import com.compilit.spgetti.api.EventHandler;
import com.compilit.spgetti.api.QueryHandler;
import com.compilit.spgetti.testutil.TestEvent;
import com.compilit.spgetti.testutil.TestEventHandler;
import com.compilit.spgetti.testutil.TestObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableCqrsMediator
@SpringBootApplication(scanBasePackages = "com.compilit.spgetti.testutil")
public class TestApplicationHappy {

  @Bean
  public CommandHandler<TestCommand1, TestObject> createTestCommandHandler() {
    return new TestCommandHandler1();
  }

  @Bean
  public CommandHandler<TestCommand2, TestObject> createTestCommandHandler2() {
    return new TestCommandHandler2();
  }

  @Bean
  public QueryHandler<TestQuery, TestObject> createTestQueryHandler() {
    return new TestQueryHandler();
  }

  @Bean
  public EventHandler<TestEvent> createTestEventHandler1() {
    return new TestEventHandler();
  }

  @Bean
  public EventHandler<TestEvent> createTestEventHandler2() {
    return new TestEventHandler();
  }

  public static void main(String[] args) {
    SpringApplication.run(TestApplicationHappy.class);
  }
}
