package com.compilit.spgetti.testutil;

import com.compilit.spgetti.api.RequestHandler;

public class TestRequestHandler2 implements RequestHandler<TestRequest2, TestObject> {

  @Override
  public TestObject handle(TestRequest2 command) {
    SideEffectContext.invoke();
    return null;
  }
}
