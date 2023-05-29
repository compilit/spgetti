package com.compilit.spgetti;

import com.compilit.spgetti.api.CommandDispatcher;
import com.compilit.spgetti.api.QueryDispatcher;
import com.compilit.spgetti.testutil.SideEffectContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {TestApplicationUnhappy.class})
class MediatorUnhappyTests {

  @Autowired
  private CommandDispatcher commandDispatcher;

  @Autowired
  private QueryDispatcher queryDispatcher;

  @BeforeEach
  void reset() {
    SideEffectContext.reset();
  }

  @Test
  void dispatch_multipleEqualCommands_shouldThrowException() {
    var testCommand = new IdentifiableRequest<>(new TestCommand1());
    Assertions.assertThatThrownBy(() -> commandDispatcher.dispatch(testCommand.innerRequest()))
              .isInstanceOf(MediatorException.class)
              .hasMessage(ExceptionMessages.multipleHandlersRegisteredMessage(testCommand.getIdentifier().get()));
  }

  @Test
  void dispatch_multipleEqualQueries_shouldThrowException() {
    var testQuery = new IdentifiableRequest<>(new TestQuery());
    Assertions.assertThatThrownBy(() -> queryDispatcher.dispatch(testQuery.innerRequest()))
              .isInstanceOf(MediatorException.class)
              .hasMessage(ExceptionMessages.multipleHandlersRegisteredMessage(testQuery.getIdentifier().get()));
  }

}
