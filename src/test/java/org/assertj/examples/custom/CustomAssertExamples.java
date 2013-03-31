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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.examples.custom.MyProjectAssertions.assertThat;

import org.junit.Test;

import org.assertj.examples.AbstractAssertionsExamples;
import org.assertj.examples.data.TolkienCharacter;

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

    // To have a unique entry point for your custom assertions and the core ones, create a class inheriting from
    // Assertions and add an assertThat(TolkienCharacter) that build a TolkienCharacterAssert, then import statically
    // MyProjectAssertions.assertThat to have a unique entry point to all assertions : yours and the core ones.

    // For exmaple, the assertions below are accesed from MyProjectAssertions :
    // - hasName comes from .MyProjectAssertions.assertThat(TolkienCharacter actual)
    assertThat(frodo).hasName("Frodo").hasAge(33).isNotEqualTo(merry);
    // - isEqualTo is accessible since MyProjectAssertions inherits from Assertions which provides Integer assertions.
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
      assertThat(e).hasMessage("Expected character's name to be <'Sam'> but was <'Sammy'>");
    }

    // show that the user description is honored.
    try {
      assertThat(sam).as("check name").hasName("Sam");
    } catch (AssertionError e) {
      // As we are defining custom assertion, we can set meaningful assertion error message, like this one :
      assertThat(e).hasMessage("[check name] Expected character's name to be <'Sam'> but was <'Sammy'>");
    }
  }

  @Test
  public void inherited_assertion_example() {
    Employee employee = new Employee();
    employee.jobTitle = "CEO";
    employee.name = "John Smith";
    assertThat(employee).hasJobTitle("CEO").hasName("John Smith").isEqualTo(employee);
    //assertThat(employee).hasName("John Smith").hasJobTitle("CEO");

    Human joe = new Human();
    joe.name = "joe";
    assertThat(joe).hasName("joe");

    EmployeeOfTheMonth employeeOfTheMonth = new EmployeeOfTheMonth();
    employeeOfTheMonth.setName("John Doe");
    employeeOfTheMonth.setMonth(1);
    employeeOfTheMonth.jobTitle = "Guru";
    EmployeeOfTheMonthAssert.assertThat(employeeOfTheMonth).forMonth(1).isEqualTo(employeeOfTheMonth);
    EmployeeOfTheMonthAssert.assertThat(employeeOfTheMonth).isEqualTo(employeeOfTheMonth).as("").hasName("John Doe").forMonth(1).hasJobTitle("Guru");



  }

}
