package com.compilit.spgetti;

import com.compilit.spgetti.testutil.TestRequest;
import com.compilit.spgetti.testutil.TestRequest1;
import com.compilit.spgetti.testutil.TestRequestHandler1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RequestHandlerMatcherTest {

  @Test
  void canHandle_true_shouldReturnTrue() {
    var requestHandler = new TestRequestHandler1();
    var request = new TestRequest1();
    var actual = new RequestHandlerMatcher().canHandle(requestHandler, request);
    Assertions.assertThat(actual).isTrue();
  }

  @Test
  void canHandle_false_shouldReturnFalse() {
    var requestHandler = new TestRequestHandler1();
    var request = new TestRequest();
    var actual = new RequestHandlerMatcher().canHandle(requestHandler, request);
    Assertions.assertThat(actual).isFalse();
  }
}