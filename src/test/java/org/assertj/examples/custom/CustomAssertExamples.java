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
 * Copyright 2012-2013 the original author or authors.
 */
package org.assertj.examples.custom;

import static org.assertj.examples.custom.MyProjectAssertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.examples.AbstractAssertionsExamples;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.Test;


/**
 * 
 * Shows some example of a custom assertion class: {@link TolkienCharacterAssert} that allows us to make assertions
 * specific to {@link TolkienCharacter}.
 * 
 * @author Joel Costigliola
 */
public class CustomAssertExamples extends AbstractAssertionsExamples {

  @Test
  public void successful_custom_assertion_example() {
    // custom assertion : assertThat is resolved from TolkienCharacterAssert static import
    TolkienCharacterAssert.assertThat(frodo).hasName("Frodo");
    // If you have created a class inheriting from Assertions having an entry point to
    // TolkienCharacterAssert you can use it to access TolkienCharacterAssert and standard assertions.
    // The rationale here is to have a unique entry to all assertions : yours and the standard ones.
    // e.g. with MyProjectAssertions :
    // - hasName comes from .MyProjectAssertions.assertThat(TolkienCharacter actual)
    // - isNotEqualTo comes from .MyProjectAssertions.assertThat(TolkienCharacter actual)
    assertThat(frodo).hasName("Frodo").hasAge(33).isNotEqualTo(merry);
    // - isNotEqualTo comes from MyProjectAssertions.assertThat(TolkienCharacter actual)
    // - resolved from Assertions.assertThat(int actual) since MyProjectAssertions insherits from Assertions
    assertThat(frodo.getAge()).isEqualTo(33);
  }

  @Test
  public void failed_custom_assertion_example() {
    sam.setName("Sammy");
    try {
      // custom assertion : assertThat is resolved from MyProjectAssertions.assertThat static import
      assertThat(sam).hasName("Sam");
    } catch (AssertionError e) {
      // As we are defining custom assertion, we can set meaningful assertion error message, like this one :
      assertThat(e).hasMessage("Expected character's name to be <Sam> but was <Sammy>");
    }

    // show that the user description is honored.
    try {
      assertThat(sam).as("check name").hasName("Sam");
    } catch (AssertionError e) {
      // As we are defining custom assertion, we can set meaningful assertion error message, like this one :
      assertThat(e).hasMessage("[check name] Expected character's name to be <Sam> but was <Sammy>");
    }
  }

  @Test
  public void inherited_assertion_example() {
    Employee employee = new Employee();
    employee.jobTitle = "CEO";
    employee.name = "John Smith";
    assertThat(employee).hasJobTitle("CEO").hasName("John Smith");
    assertThat(employee).hasName("John Smith").hasJobTitle("CEO");

    Human joe = new Human();
    joe.name = "joe";
    assertThat(joe).hasName("joe");
  }

}
