package com.compilit.spgetti;

import com.compilit.spgetti.api.RequestDispatcher;
import com.compilit.spgetti.testutil.SideEffectContext;
import com.compilit.spgetti.testutil.TestRequest;
import com.compilit.spgetti.testutil.TestRequest1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {TestApplicationUnhappy.class})
class MediatorUnhappyTests {

  @Autowired
  private RequestDispatcher requestDispatcher;

  @BeforeEach
  void reset() {
    SideEffectContext.reset();
  }

  @Test
  void dispatch_multipleEqualCommands_shouldThrowException() {
    var testCommand = new IdentifiableRequest<>(new TestRequest1());
    Assertions.assertThatThrownBy(() -> requestDispatcher.dispatch(testCommand.innerRequest()))
              .isInstanceOf(MediatorException.class)
              .hasMessage(ExceptionMessages.multipleHandlersRegisteredMessage(testCommand.getIdentifier().get()));
  }

  @Test
  void dispatch_multipleEqualQueries_shouldThrowException() {
    var testQuery = new IdentifiableRequest<>(new TestRequest());
    Assertions.assertThatThrownBy(() -> requestDispatcher.dispatch(testQuery.innerRequest()))
              .isInstanceOf(MediatorException.class)
              .hasMessage(ExceptionMessages.multipleHandlersRegisteredMessage(testQuery.getIdentifier().get()));
  }

}
