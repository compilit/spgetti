package com.compilit.spgetti;

import com.compilit.spgetti.api.CommandHandler;
import com.compilit.spgetti.testutil.SideEffectContext;
import com.compilit.spgetti.testutil.TestObject;

public class TestCommandHandler1 implements CommandHandler<TestCommand1, TestObject> {

  @Override
  public TestObject handle(TestCommand1 command) {
    SideEffectContext.invoke();
    return null;
  }
}
