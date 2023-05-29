package com.compilit.spgetti.testutil;

import com.compilit.spgetti.api.EventHandler;

public class TestSameEventHandler implements EventHandler<TestEvent> {

  @Override
  public Void handle(TestEvent command) {
    SideEffectContext.invoke();
    return null;
  }
}
