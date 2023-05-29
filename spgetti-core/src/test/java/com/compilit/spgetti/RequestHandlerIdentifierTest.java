package com.compilit.spgetti;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.compilit.spgetti.api.RequestHandler;
import com.compilit.spgetti.testutil.TestRequestHandler;
import com.compilit.spgetti.testutil.TestRequestHandler1;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RequestHandlerIdentifierTest {

  public static Stream<Arguments> testCases() {
    return Stream.of(
      arguments(
        new TestRequestHandler(),
        "com.compilit.spgetti.testutil.TestRequestHandler implements com.compilit.spgetti.api.RequestHandler<com.compilit.spgetti.testutil.TestRequest, com.compilit.spgetti.testutil.TestObject>"
      ),
      arguments(
        new TestRequestHandler1(),
        "com.compilit.spgetti.testutil.TestRequestHandler1 implements com.compilit.spgetti.api.RequestHandler<com.compilit.spgetti.testutil.TestRequest1, com.compilit.spgetti.testutil.TestObject>"
      )
    );
  }

  @ParameterizedTest
  @MethodSource("testCases")
  void ctor_shouldReturnCorrectId(RequestHandler identifierProvider, String expected) {
    Assertions.assertThat(Identifiers.from(identifierProvider).get()).isEqualTo(expected);
  }
}
