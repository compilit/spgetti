package com.compilit.spgetti.testutil;

import com.compilit.spgetti.api.RequestHandler;

public class TestSameRequestHandler implements RequestHandler<TestRequest1, TestObject> {

  @Override
  public TestObject handle(TestRequest1 command) {
    SideEffectContext.invoke();
    return null;
  }
}