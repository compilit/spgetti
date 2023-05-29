package com.compilit.spgetti;

import com.compilit.spgetti.api.EventHandler;
import com.compilit.spgetti.api.RequestHandler;
import com.compilit.spgetti.testutil.TestEvent;
import com.compilit.spgetti.testutil.TestEventHandler;
import com.compilit.spgetti.testutil.TestObject;
import com.compilit.spgetti.testutil.TestRequest;
import com.compilit.spgetti.testutil.TestRequest1;
import com.compilit.spgetti.testutil.TestRequestHandler;
import com.compilit.spgetti.testutil.TestRequestHandler1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableMediator
@SpringBootApplication(scanBasePackages = "com.compilit.spgetti.testutil")
public class TestApplicationUnhappy {

  public static void main(String[] args) {
    SpringApplication.run(TestApplicationUnhappy.class);
  }

  @Bean
  public RequestHandler<TestRequest1, TestObject> createTestCommandHandler1() {
    return new TestRequestHandler1();
  }

  @Bean
  public RequestHandler<TestRequest1, TestObject> createTestCommandHandler2() {
    return new TestRequestHandler1();
  }

  @Bean
  public RequestHandler<TestRequest, TestObject> createTestQueryHandler1() {
    return new TestRequestHandler();
  }

  @Bean
  public RequestHandler<TestRequest, TestObject> createTestQueryHandler2() {
    return new TestRequestHandler();
  }

  @Bean
  public EventHandler<TestEvent> createTestEventHandler1() {
    return new TestEventHandler();
  }

  @Bean
  public EventHandler<TestEvent> createTestEventHandler2() {
    return new TestEventHandler();
  }
}
