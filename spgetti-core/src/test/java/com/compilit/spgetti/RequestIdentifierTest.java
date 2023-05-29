package com.compilit.spgetti;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.compilit.spgetti.api.Request;
import com.compilit.spgetti.testutil.TestRequest;
import com.compilit.spgetti.testutil.TestRequest1;
import com.compilit.spgetti.testutil.TestEvent;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RequestIdentifierTest {

  public static Stream<Arguments> testCases() {
    return Stream.of(
      arguments(new TestEvent(), "com.compilit.spgetti.testutil.TestEvent"),
      arguments(new TestRequest1(), "com.compilit.spgetti.testutil.TestRequest1"),
      arguments(new TestRequest(), "com.compilit.spgetti.testutil.TestRequest")
    );
  }

  @ParameterizedTest
  @MethodSource("testCases")
  void ctor_shouldReturnCorrectId(Request identifierProvider, String expected) {
    Assertions.assertThat(Identifiers.from(identifierProvider).get()).isEqualTo(expected);
  }
}
