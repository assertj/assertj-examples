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
package org.assertj.examples.custom;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.util.Objects;
import org.assertj.examples.data.Race;
import org.assertj.examples.data.TolkienCharacter;

/**
 * A simple class to illustrate how to extend AssertJ assertions with custom ones to make assertions speicific to
 * {@link TolkienCharacter}.
 * 
 * @author Joel Costigliola
 */
// 1 - Remember to inherit from AbstractAssert !
public class TolkienCharacterAssert extends AbstractAssert<TolkienCharacterAssert, TolkienCharacter> {

  // 2 - Write a constructor to build your assertion class from the object you want make assertions on.
  /**
   * Creates a new </code>{@link TolkienCharacterAssert}</code> to make assertions on actual TolkienCharacter.
   * 
   * @param actual the TolkienCharacter we want to make assertions on.
   */
  public TolkienCharacterAssert(TolkienCharacter actual) {
    super(actual, TolkienCharacterAssert.class);
  }

  // 3 - A fluent entry point to your specific assertion class
  // the other option here is to gather all your specific assertions entry point to a class inehriting from Assertions
  // thus this class will be your oonly entry poiny to all AssertJ assertions and YOURS.
  // see MyProjectAssertions for an example.
  /**
   * An entry point for TolkienCharacterAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one's can write directly : <code>assertThat(frodo).hasName("Frodo");</code>
   * 
   * @param actual the TolkienCharacter we want to make assertions on.
   * @return a new </code>{@link TolkienCharacterAssert}</code>
   */
  public static TolkienCharacterAssert assertThat(TolkienCharacter actual) {
    return new TolkienCharacterAssert(actual);
  }

  // 4 - a specific assertion
  /**
   * Verifies that the actual TolkienCharacter's name is equal to the given one.
   * 
   * @param name the given name to compare the actual TolkienCharacter's name to.
   * @return this assertion object.
   * @throws AssertionError - if the actual TolkienCharacter's name is not equal to the given one.
   */
  public TolkienCharacterAssert hasName(String name) {
    // check that actual TolkienCharacter we want to make assertions on is not null.
    isNotNull();

    // check condition
    if (!actual.getName().equals(name)) {
      failWithMessage("Expected character's name to be <%s> but was <%s>", name, actual.getName());
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual TolkienCharacter's race is equal to the given one.
   * @param race the given race to compare the actual TolkienCharacter's race to.
   * @return this assertion object.
   * @throws AssertionError - if the actual TolkienCharacter's race is not equal to the given one.
   */
  public TolkienCharacterAssert hasRace(Race race) {
    // check that actual TolkienCharacter we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpected race of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";
    
    // null safe check
    Race actualRace = actual.getRace();
    if (!Objects.areEqual(actualRace, race)) {
      failWithMessage(assertjErrorMessage, actual, race, actualRace);
    }

    // return the current assertion for method chaining
    return this;
  }

 
  // another specific assertion
  /**
   * Verifies that the actual TolkienCharacter's age is equal to the given one.
   * 
   * @param age the given age to compare the actual TolkienCharacter's age to.
   * @return this assertion object.
   * @throws AssertionError - if the actual TolkienCharacter's age is not equal to the given one.
   */
  public TolkienCharacterAssert hasAge(int age) {
    // check that actual TolkienCharacter we want to make assertions on is not null.
    isNotNull();

    // check condition
    if (actual.age != age) {
      failWithMessage("Expected character's age to be <%s> but was <%s>", age, actual.age);
    }
    
    // return the current assertion for method chaining
    return this;
  }

}
