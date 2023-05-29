package com.compilit.spgetti;

import static org.assertj.core.api.Assertions.assertThat;

import com.compilit.spgetti.testutil.TestRequest1;
import org.junit.jupiter.api.Test;

class RequestHandlerMatcherTest {

  @Test
  void canHandle_true_shouldReturnTrue() {
    var requestHandler = new TestCommandHandler1();
    var request = new TestCommand1();
    var actual = new RequestHandlerMatcher().canHandle(requestHandler, request);
    assertThat(actual).isTrue();
  }

  @Test
  void canHandle_false_shouldReturnFalse() {
    var requestHandler = new TestCommandHandler1();
    var request = new TestRequest1();
    var actual = new RequestHandlerMatcher().canHandle(requestHandler, request);
    assertThat(actual).isFalse();
  }
}