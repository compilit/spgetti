package com.compilit.spgetti;

import com.compilit.spgetti.api.EventEmitter;
import com.compilit.spgetti.api.RequestDispatcher;
import com.compilit.spgetti.testutil.SideEffectContext;
import com.compilit.spgetti.testutil.TestEvent;
import com.compilit.spgetti.testutil.TestRequest1;
import com.compilit.spgetti.testutil.TestRequest2;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {TestApplicationHappy.class})
class MediatorHappyTests {

  @Autowired
  private RequestDispatcher requestDispatcher;

  @Autowired
  private EventEmitter eventEmitter;

  @BeforeEach
  void reset() {
    SideEffectContext.reset();
  }

  @Test
  void emit_multipleEvents_shouldInteractWithContextMultipleTimes() {
    eventEmitter.emit(new TestEvent(), new TestEvent(), new TestEvent());
    Assertions.assertThat(SideEffectContext.isInvoked(3)).isTrue();
  }

  @Test
  void emit_eventWithMultipleEventHandlers_shouldInteractWithContextMultipleTimes() {
    eventEmitter.emit(new TestEvent());
    AssertionsForClassTypes.assertThat(SideEffectContext.isInvoked(2)).isTrue();
  }

  @Test
  void dispatch_command_shouldDispatchCommand() {
    requestDispatcher.dispatch(new TestRequest1());
    AssertionsForClassTypes.assertThat(SideEffectContext.isInvoked).isTrue();
  }

  @Test
  void dispatch_query_shouldInteractWithContext() {
    requestDispatcher.dispatch(new TestQuery());
    AssertionsForClassTypes.assertThat(SideEffectContext.isInvoked).isTrue();
  }

  @Test
  void dispatch_multipleInvocations_shouldInteractWithContextMultipleTimes() {
    requestDispatcher.dispatch(new TestQuery());
    requestDispatcher.dispatch(new TestRequest1());
    requestDispatcher.dispatch(new TestRequest2());
    eventEmitter.emit(new TestEvent());
    AssertionsForClassTypes.assertThat(SideEffectContext.isInvoked(4)).isTrue();
  }

}
