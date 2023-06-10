package com.compilit.spgetti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.compilit.spgetti.testutil.TestInterface;
import com.compilit.spgetti.testutil.TestObject;
import com.compilit.spgetti.testutil.TestSubject;
import org.junit.jupiter.api.Test;

class ReflectionTest {

  private final Reflection<TestSubject<String>> reflection = Reflection.of(new TestSubject<>());

  @Test
  void getClassName() {
    assertEquals("com.compilit.narcissus.testutil.TestSubject", reflection.getClassName());
  }

  @Test
  void getGenericInterfaceNames() {
    var firstEntry = reflection.getInterfaceNames().get(0);
    assertEquals(
      "com.compilit.narcissus.api.RequestHandler<com.compilit.narcissus.testutil.TestRequest, com.compilit.narcissus.testutil.TestObject>",
      firstEntry
    );
  }

  @Test
  void getGenericInterfaceName() {
    assertEquals(
      "com.compilit.narcissus.api.RequestHandler<com.compilit.narcissus.testutil.TestRequest, com.compilit.narcissus.testutil.TestObject>",
      reflection.getInterfaceName(0).get()
    );
  }

  @Test
  void getClassSignatureName() {
    assertEquals(
      "public com.compilit.narcissus.testutil.TestSubject extends com.compilit.narcissus.testutil.TestObject implements com.compilit.narcissus.api.RequestHandler<com.compilit.narcissus.testutil.TestRequest, com.compilit.narcissus.testutil.TestObject>",
      reflection.getClassSignature()
    );
  }

  @Test
  void getGenericTypeParameter() {
    assertEquals("com.compilit.narcissus.testutil.TestRequest", reflection.getInterfaceTypeParameter(0, 0).get());
    assertEquals("com.compilit.narcissus.testutil.TestObject", reflection.getInterfaceTypeParameter(0, 1).get());
  }

  @Test
  void implementsInterface() {
    assertTrue(reflection.implementsInterface(TestInterface.class));
  }

  @Test
  void implementsAnyInterface() {
    assertTrue(reflection.implementsAnyInterface());
  }

  @Test
  void extendsAny() {
    assertTrue(reflection.extendsAnyClass());
  }

  @Test
  void extendsClass() {
    assertTrue(reflection.extendsClass(TestObject.class));
  }

  @Test
  void getExtensionClass() {
    assertEquals(TestObject.class, reflection.getExtensionClass().get());
  }

  @Test
  void getExtensionName() {
    assertEquals("com.compilit.narcissus.testutil.TestObject", reflection.getExtensionName().get());
  }

}
