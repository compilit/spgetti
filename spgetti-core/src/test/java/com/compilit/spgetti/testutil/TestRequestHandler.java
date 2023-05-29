package com.compilit.spgetti.testutil;

import com.compilit.spgetti.api.RequestHandler;
import java.io.Serializable;

public class TestRequestHandler implements RequestHandler<TestRequest, TestObject>, Serializable {

  @Override
  public TestObject handle(TestRequest query) {
    SideEffectContext.invoke();
    return null;
  }

}
