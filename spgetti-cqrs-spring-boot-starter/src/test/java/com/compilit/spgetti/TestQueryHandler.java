package com.compilit.spgetti;

import com.compilit.spgetti.api.QueryHandler;
import com.compilit.spgetti.testutil.SideEffectContext;
import com.compilit.spgetti.testutil.TestObject;

public class TestQueryHandler implements QueryHandler<TestQuery, TestObject> {

  @Override
  public TestObject handle(TestQuery query) {
    SideEffectContext.invoke();
    return null;
  }
}
