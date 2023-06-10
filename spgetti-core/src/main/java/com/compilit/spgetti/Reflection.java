package com.compilit.spgetti;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

final class Reflection<T> {

  private final List<String> genericInterfaceNames = new ArrayList<>();
  private final List<Class<?>> genericInterfaceClasses = new ArrayList<>();
  private final Map<Integer, Optional<String>> genericTypeParameterNames = new HashMap<>();
  private final Map<Integer, Optional<Class<?>>> genericTypeParameterClasses = new HashMap<>();
  private final T subject;
  private Class<?> extensionClass;
  private String fullName;
  private String className;
  private Class<T> subjectClass;

  private Reflection(T subject) {
    this.subject = subject;
  }

  public String getClassName() {
    if (className == null) {
      className = getSubjectClass().getTypeName();
    }
    return className;
  }

  public List<String> getInterfaceNames() {
    if (genericInterfaceNames.isEmpty()) {
      var clazz = getSubjectClass();
      var subjectInterfaces = clazz.getInterfaces();
      if (subjectInterfaces.length > 0) {
        genericInterfaceNames.addAll(Arrays.stream(subjectInterfaces)
                                           .map(Type::getTypeName)
                                           .toList());
      }
    }
    return genericInterfaceNames;
  }

  public List<Class<?>> getInterfaceClasses() {
    if (genericInterfaceNames.isEmpty()) {
      var clazz = getSubjectClass();
      var subjectInterfaces = clazz.getInterfaces();
      if (subjectInterfaces.length > 0) {
        genericInterfaceClasses.addAll(Arrays.stream(subjectInterfaces)
                                             .map(Type::getTypeName)
                                             .map(Clazz::of)
                                             .toList());
      }
    }
    return genericInterfaceClasses;
  }

  public Optional<String> getInterfaceName(int index) {
    return getInterfaceNames().size() >= index
           ? Optional.of(getInterfaceNames().get(index))
           : Optional.empty();
  }

  public Optional<Class<?>> getInterfaceClass(int index) {
    return getInterfaceClasses().size() >= index
           ? Optional.of(getInterfaceClasses().get(index))
           : Optional.empty();
  }

  public String getClassSignature() {
    if (fullName == null) {
      var clazz = getSubjectClass();
      var modifier = Modifier.toString(clazz.getModifiers());
      var genericInterfaces = getInterfaceNames();
      var base = String.format("%s %s", modifier, getClassName());
      if (extendsAnyClass()) {
        fullName = String.format("%s extends %s", base, getExtensionName());
      }
      if (implementsAnyInterface()) {
        var stringJoiner = new StringJoiner(", ");
        for (int index = 0; index < genericInterfaces.size(); index++) {
          if (index == genericInterfaces.size() - 1) {
            var interfaces = stringJoiner + genericInterfaces.get(index);
            fullName = String.format("%s implements %s", fullName, interfaces);
          }
          stringJoiner.add(genericInterfaces.get(index));
        }
      }
    }
    return fullName;
  }

  public Optional<String> getInterfaceTypeParameter(int interfaceIndex, int parameterIndex) {
    return genericTypeParameterNames.computeIfAbsent(parameterIndex, i -> getInterfaceName(interfaceIndex)
      .map(interfacesTypeName -> {
        var parameterTypePart = interfacesTypeName.split("<")[1];
        var cleanedParameters = parameterTypePart.replace("<", "").replace(">", "");
        return cleanedParameters.split(",")[parameterIndex].trim();
      }));
  }

  public Optional<Class<?>> getTypeParameterClass(int interfaceIndex, int parameterIndex) {
    return genericTypeParameterClasses.computeIfAbsent(
      parameterIndex,
      i -> getInterfaceTypeParameter(
        interfaceIndex,
        parameterIndex
      ).map(Clazz::of)
    );
  }

  public boolean implementsInterface(Class<?> interfaceClass) {
    return getInterfaceClasses().stream().anyMatch(genericInterface -> genericInterface.equals(interfaceClass));
  }

  public boolean implementsAnyInterface() {
    return !getInterfaceNames().isEmpty();
  }

  public boolean extendsAnyClass() {
    return getExtensionClass().isPresent();
  }

  public boolean extendsClass(Class<?> extensionClass) {
    return getExtensionClass().map(x -> x.equals(extensionClass)).orElse(false);
  }

  public Optional<Class<?>> getExtensionClass() {
    if (extensionClass == null) {
      extensionClass = getSubjectClass().getSuperclass();
    }
    return Optional.ofNullable(extensionClass);
  }

  public Optional<String> getExtensionName() {
    return getExtensionClass().map(Class::getTypeName);
  }

  public Class<T> getSubjectClass() {
    if (subjectClass == null) {
      subjectClass = (Class<T>) subject.getClass();
    }
    return subjectClass;
  }

  public static <T> Reflection<T> of(T subject) {
    return new Reflection<>(subject);
  }


  static class Clazz {

    private Clazz() {}

    /**
     * Get the specified class if possible.
     *
     * @param className the name of the class you wish to instantiate (package included)
     * @return an instance of the desired class
     */
    public static <T> Class<T> of(String className) {
      try {
        return (Class<T>) ClassLoader.getSystemClassLoader().loadClass(className);
      } catch (ReflectiveOperationException reflectiveOperationException) {
        throw new MediatorException(reflectiveOperationException.getMessage());
      }
    }

  }

}

