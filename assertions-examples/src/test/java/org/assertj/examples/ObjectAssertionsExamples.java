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

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.Assertions.from;
import static org.assertj.core.api.InstanceOfAssertFactories.STRING;
import static org.assertj.examples.data.Race.HOBBIT;
import static org.assertj.examples.data.Ring.oneRing;

import java.math.BigDecimal;
import java.util.Set;

import org.assertj.core.util.BigDecimalComparator;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.jupiter.api.Test;

public class ObjectAssertionsExamples extends AbstractAssertionsExamples {

  private static final BigDecimalComparator BIG_DECIMAL_COMPARATOR = new BigDecimalComparator();

  @Test
  public void object_extracting_example() {
    // single value extracted
    assertThat(frodo).extracting(TolkienCharacter::getName)
                     .isEqualTo("Frodo");
    assertThat(frodo).extracting("name")
                     .isEqualTo("Frodo");
    assertThat(frodo).extracting("name")
                     .asInstanceOf(STRING)
                     .startsWith("Fro");

    // multiple values extracted
    assertThat(frodo).extracting(TolkienCharacter::getName, TolkienCharacter::getRace)
                     .containsExactly("Frodo", HOBBIT);

    // To get String assertions use asInstanceOf:
    assertThat(frodo).extracting("name")
                     .asInstanceOf(STRING)
                     .startsWith("Fro");
    // To get String assertions use asInstanceOf with as syntactic sugar:
    assertThat(frodo).extracting("name")
                     .asInstanceOf(as(STRING))
                     .startsWith("Fro");
    // shorter version of the above assertions:
    assertThat(frodo).extracting("name", as(STRING))
                     .startsWith("Fro");

    // extracting propagates the type parameter of the extracted value
    assertThat(frodo).extracting(TolkienCharacter::getName)
                     .extracting(String::length)
                     .isEqualTo(5);
  }

  @Test
  public void narrow_assertions_type_with_asInstanceOf_example() {

    // Given a String declared as an Object
    Object value = "Once upon a time in the west";
    // We would like to call String assertions but this is not possible since value is declared as an Object
    // assertThat(value).startsWith("ab"); // this does not compile !

    // Thanks to asInstanceOf we can now tell AssertJ to consider value as a String in order to call String assertions. To do so
    // we need to pass an InstanceOfAssertFactory that can build a StringAssert, fortunately you don’t have to write it, it is
    // already available in InstanceOfAssertFactories!

    // With asInstanceOf, we switch to specific String assertion by specifying the InstanceOfAssertFactory for String
    assertThat(value).asInstanceOf(STRING).startsWith("Once");
  }

  @Test
  public void returns_assertion() {
    assertThat(frodo).returns("Frodo", from(TolkienCharacter::getName))
                     .returns(HOBBIT, from(TolkienCharacter::getRace))
                     .returns(HOBBIT, TolkienCharacter::getRace);
  }

  @Test
  public void should_not_produce_warning_for_varargs_parameter() {
    assertThat(entry(oneRing, frodo)).extracting(entry -> entry.key, entry -> entry.value)
                                     .containsExactly(oneRing, frodo);
  }

  @Test
  public void issue_841() {
    BigDecimal value1 = BigDecimal.valueOf(100);
    BigDecimal value2 = value1.setScale(2);

    assertThat(value1).isNotEqualTo(value2);
    assertThat(value1.hashCode()).isNotEqualTo(value2.hashCode());
    assertThat(value1).isEqualByComparingTo(value2);

    class Foo {
      private final BigDecimal value;

      Foo(BigDecimal value) {
        this.value = value;
      }

      @SuppressWarnings("unused")
      BigDecimal getValue() {
        return value;
      }
    }

    class Bar {
      private final Set<Foo> foos;

      Bar(Set<Foo> foos) {
        this.foos = foos;
      }

      @SuppressWarnings("unused")
      Set<Foo> getFoos() {
        return foos;
      }
    }

    Foo foo1 = new Foo(value1);
    Foo foo2 = new Foo(value2);

    assertThat(foo1).usingRecursiveComparison()
                    .withComparatorForType(BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                    .isEqualTo(foo2);

    assertThat(new Bar(singleton(foo1))).usingRecursiveComparison()
                                        .withComparatorForType(BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                                        .isEqualTo(new Bar(singleton(foo2)));
  }

}
