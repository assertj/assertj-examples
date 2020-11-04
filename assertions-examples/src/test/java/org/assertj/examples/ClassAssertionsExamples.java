/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2016 the original author or authors.
 */
package org.assertj.examples;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.examples.data.Employee;
import org.assertj.examples.data.Magical;
import org.assertj.examples.data.Person;
import org.assertj.examples.data.Powerful;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.jupiter.api.Test;

/**
 * Class assertions specific examples
 *
 * @author Joel Costigliola
 */
public class ClassAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void class_assertions_examples() {
    assertThat(Magical.class).isAnnotation();
    assertThat(Ring.class).isNotAnnotation()
                          .hasAnnotation(Magical.class);

    try {
      assertThat(Ring.class).isAnnotation();
    } catch (AssertionError e) {
      logAssertionErrorMessage("isAnnotation", e);
    }

    try {
      assertThat(TolkienCharacter.class).hasAnnotation(Magical.class);
    } catch (AssertionError e) {
      logAssertionErrorMessage("hasAnnotation", e);
    }

    assertThat(TolkienCharacter.class).isNotInterface()
                                      .hasPackage("org.assertj.examples.data")
                                      .hasPackage(Package.getPackage("org.assertj.examples.data"));
    assertThat(Person.class).isAssignableFrom(Employee.class);
    assertThat(Employee.class).hasSuperclass(Person.class);
    assertThat(Cloneable.class).hasNoSuperclass();
  }

  @Test
  public void class_visibility_examples() {
    assertThat(TolkienCharacter.class).isPublic();
    assertThat(String.class).isPublic();
    assertThat(MyClass.class).isProtected();
    assertThat(MySuperClass.class).isPackagePrivate();

    try {
      assertThat(TolkienCharacter.class).isProtected();
    } catch (AssertionError e) {
      logAssertionErrorMessage("isProtected", e);
    }
  }

  @Test
  public void method_visibility_examples() {
    assertThat(TolkienCharacter.class).hasPublicMethods("getName", "getRace");

    try {
      assertThat(TolkienCharacter.class).hasPublicMethods("getAliases");
    } catch (AssertionError e) {
      logAssertionErrorMessage("hasPublicMethods", e);
    }
  }

  @Test
  public void class_fields_assertions_examples() {
    assertThat(MyClass.class).hasPublicFields("fieldOne")
                             .hasPublicFields("fieldOne", "fieldTwo")
                             .hasPublicFields("fieldTwo", "fieldOne")
                             .hasDeclaredFields("fieldThree")
                             .hasDeclaredFields("fieldThree", "fieldTwo", "fieldOne")
                             .hasOnlyPublicFields("fieldOne", "fieldTwo")
                             .hasOnlyPublicFields("fieldTwo", "fieldOne")
                             .hasOnlyDeclaredFields("fieldThree", "fieldTwo", "fieldOne");
  }

  @Test
  public void class_methods_assertions_examples() {
    assertThat(MyClass.class).hasMethods("methodOne", "methodTwo", "superMethod", "privateSuperMethod")
                             .hasPublicMethods("methodOne", "superMethod")
                             .hasDeclaredMethods("methodTwo", "methodOne");
  }

  @Test
  public void should_not_produce_warning_for_varargs_parameter() {
    assertThat(Ring.class).hasAnnotations(Magical.class, Powerful.class);
  }

  @SuppressWarnings("unused")
  class MySuperClass {
    public void superMethod() {}

    private void privateSuperMethod() {}
  }

  @SuppressWarnings("unused")
  protected class MyClass extends MySuperClass {
    public String fieldOne;
    public String fieldTwo;
    private String fieldThree;

    public void methodOne() {}

    private void methodTwo() {}
  }

}
