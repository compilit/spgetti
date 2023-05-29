package com.compilit.spgetti.testutil;

import com.compilit.spgetti.api.EventHandler;


public class TestEventHandler implements EventHandler<TestEvent> {

  @Override
  public Void handle(TestEvent event) {
    SideEffectContext.invoke();
    return null;
  }
}
