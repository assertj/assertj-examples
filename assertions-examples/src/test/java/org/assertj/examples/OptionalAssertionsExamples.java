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

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.STRING;
import static org.assertj.examples.condition.UsingConditionExamples.JEDI;
import static org.assertj.examples.data.Race.ELF;
import static org.assertj.examples.data.Race.HOBBIT;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Function;

import org.assertj.core.api.Condition;
import org.assertj.examples.data.Race;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.jupiter.api.Test;

/**
 * {@link Optional} assertions example.
 * 
 * @author Joel Costigliola
 */
public class OptionalAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void optional_assertions_examples() {
    Optional<String> optional = Optional.of("Test");
    assertThat(optional).isPresent()
                        .containsInstanceOf(String.class)
                        .contains("Test");

    Optional<Object> emptyOptional = Optional.empty();
    assertThat(emptyOptional).isEmpty();

    String someString = "something";
    assertThat(Optional.of(someString)).containsSame(someString);
    assertThat(Optional.of(someString)).hasValueSatisfying(s -> {
      assertThat(s).isEqualTo("something");
      assertThat(s).startsWith("some");
      assertThat(s).endsWith("thing");
    });

    Condition<TolkienCharacter> isAnElf = new Condition<>(character -> character.getRace() == Race.ELF, "an elf");

    assertThat(Optional.of(legolas)).hasValueSatisfying(isAnElf);

    // log some error messages to have a look at them
    try {
      assertThat(emptyOptional).isPresent();
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.isPresent", e);
    }
    try {
      assertThat(emptyOptional).contains("Test");
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.contains", e);
    }
    try {
      assertThat(optional).contains("Foo");
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.contains", e);
    }
    try {
      assertThat(optional).isEmpty();
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.isEmpty", e);
    }
    try {
      assertThat(optional).hasValueSatisfying(s -> {
        assertThat(s).isEqualTo("Not Test");
      });
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.containing", e);
    }
    try {
      assertThat(emptyOptional).hasValueSatisfying(o -> {});
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.containing", e);
    }
  }

  @Test
  public void primitives_optional_assertions_examples() {
    OptionalInt optional = OptionalInt.of(12);
    assertThat(optional).isPresent()
                        .hasValue(12);

    OptionalDouble emptyOptional = OptionalDouble.empty();
    assertThat(emptyOptional).isEmpty();
  }

  @Test
  public void optional_comparator_assertions_examples() {
    Optional<String> optional = Optional.of("YODA");
    assertThat(optional).usingValueComparator(caseInsensitiveStringComparator)
                        .hasValue("yoda")
                        .contains("yoda");
  }

  @Test
  public void map_optional_assertion_example() {
    Optional<String> optional = Optional.of("YODA");
    assertThat(optional).map(String::length)
                        .hasValue(4);
  }

  @Test
  public void hasValueSatisfying_with_condition_assertion_example() {
    assertThat(Optional.of("Yoda")).hasValueSatisfying(JEDI);

    Condition<TolkienCharacter> isAnElf = new Condition<>(character -> character.getRace() == ELF, "an elf");

    TolkienCharacter legolas = new TolkienCharacter("Legolas", 1000, ELF);
    TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);

    // assertion succeeds
    assertThat(Optional.of(legolas)).hasValueSatisfying(isAnElf);
    try {
      assertThat(Optional.of(frodo)).hasValueSatisfying(isAnElf);
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.hasValueSatisfying with condition", e);
    }
  }

  @Test
  public void flatMap_optional_assertion_example() {
    Function<String, Optional<String>> UPPER_CASE_OPTIONAL_STRING = s -> s == null ? Optional.empty()
        : Optional.of(s.toUpperCase());

    // assertions succeed
    assertThat(Optional.of("something")).contains("something")
                                        .flatMap(UPPER_CASE_OPTIONAL_STRING)
                                        .contains("SOMETHING");

    assertThat(Optional.<String> empty()).flatMap(UPPER_CASE_OPTIONAL_STRING)
                                         .isEmpty();

    assertThat(Optional.<String> ofNullable(null)).flatMap(UPPER_CASE_OPTIONAL_STRING)
                                                  .isEmpty();
  }

  @Test
  public void get_optional_assertion_example() {
    TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);

    // Use get() to navigate to perform assertions on frodo.
    assertThat(Optional.of(frodo)).get().isNotNull();

    Optional<String> optional = Optional.of("Frodo");

    // The following assertion will succeed:
    assertThat(optional).get(as(STRING))
                        .startsWith("Fro");
  }

}
