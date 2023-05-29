package com.compilit.spgetti;

import com.compilit.spgetti.api.CommandHandler;
import com.compilit.spgetti.testutil.SideEffectContext;
import com.compilit.spgetti.testutil.TestObject;

public class TestCommandHandler2 implements CommandHandler<TestCommand2, TestObject> {

  @Override
  public TestObject handle(TestCommand2 command) {
    SideEffectContext.invoke();
    return null;
  }
}
