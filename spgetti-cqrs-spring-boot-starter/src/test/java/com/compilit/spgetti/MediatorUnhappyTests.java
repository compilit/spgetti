package com.compilit.spgetti;

import com.compilit.narcissus.Reflection;
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
    var testCommand = new TestCommand1();
    var testCommandReflection = Reflection.of(testCommand);
    Assertions.assertThatThrownBy(() -> commandDispatcher.dispatch(testCommand))
              .isInstanceOf(MediatorException.class)
              .hasMessage(ExceptionMessages.multipleHandlersRegisteredMessage(testCommandReflection.getClassName()));
  }

  @Test
  void dispatch_multipleEqualQueries_shouldThrowException() {
    var testQuery = new TestQuery();
    var testQueryReflection = Reflection.of(testQuery);
    Assertions.assertThatThrownBy(() -> queryDispatcher.dispatch(testQuery))
              .isInstanceOf(MediatorException.class)
              .hasMessage(ExceptionMessages.multipleHandlersRegisteredMessage(testQueryReflection.getClassName()));
  }

}
