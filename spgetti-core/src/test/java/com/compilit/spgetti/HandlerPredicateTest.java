package com.compilit.spgetti;

import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.api.RequestHandler;
import com.compilit.spgetti.testutil.TestEvent;
import com.compilit.spgetti.testutil.TestEventHandler;
import com.compilit.spgetti.testutil.TestRequest;
import com.compilit.spgetti.testutil.TestRequest1;
import com.compilit.spgetti.testutil.TestRequestHandler;
import com.compilit.spgetti.testutil.TestRequestHandler1;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HandlerPredicateTest {

  public static Stream<Arguments> validTestCases() {
    return Stream.of(
      Arguments.arguments(new TestRequest(), new TestRequestHandler()),
      Arguments.arguments(new TestRequest1(), new TestRequestHandler1()),
      Arguments.arguments(new TestEvent(), new TestEventHandler())
    );
  }

  public static Stream<Arguments> invalidTestCases() {
    return Stream.of(
      Arguments.arguments(new TestRequest(), new TestRequestHandler1()),
      Arguments.arguments(new TestRequest1(), new TestRequestHandler()),
      Arguments.arguments(new TestEvent(), new TestRequestHandler1())
    );
  }

  @ParameterizedTest
  @MethodSource("validTestCases")
  void handlersMatchingRequest_validMatch_shouldReturnTrue(Request request, RequestHandler<?, ?> requestHandler) {
    Assertions.assertThat(new IdentifiableRequestHandler<>(requestHandler).canHandle(new IdentifiableRequest<>(request)))
              .isTrue();
  }

  @ParameterizedTest
  @MethodSource("invalidTestCases")
  void handlersMatchingRequest_invalidMatch_shouldReturnFalse(Request request, RequestHandler<?, ?> requestHandler) {
    Assertions.assertThat(new IdentifiableRequestHandler<>(requestHandler).canHandle(new IdentifiableRequest<>(request)))
              .isFalse();
  }
}