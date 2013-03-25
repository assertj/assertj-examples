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
package org.assertj.examples.data;

import static java.lang.String.format;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;


/**
 * {@link Employee} specific assertions - Generated by CustomAssertionGenerator.
 */
public class EmployeeAssert extends AbstractAssert<EmployeeAssert, Employee> {

  /**
   * Creates a new </code>{@link EmployeeAssert}</code> to make assertions on actual Employee.
   * @param actual the Employee we want to make assertions on.
   */
  public EmployeeAssert(Employee actual) {
    super(actual, EmployeeAssert.class);
  }

  /**
   * An entry point for EmployeeAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one's can write directly : <code>assertThat(myEmployee)</code> and get specific assertion with code completion.
   * @param actual the Employee we want to make assertions on.
   * @return a new </code>{@link EmployeeAssert}</code>
   */
  public static EmployeeAssert assertThat(Employee actual) {
    return new EmployeeAssert(actual);
  }

  /**
   * Verifies that the actual Employee's age is equal to the given one.
   * @param age the given age to compare the actual Employee's age to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Employee's age is not equal to the given one.
   */
  public EmployeeAssert hasAge(int age) {
    // check that actual Employee we want to make assertions on is not null.
    isNotNull();

    // we overrides the default error message with a more explicit one
    String errorMessage = format("\nExpected <%s> age to be:\n  <%s>\n but was:\n  <%s>", actual, age, actual.getAge());
    
    // check
    if (actual.getAge() != age) { throw new AssertionError(errorMessage); }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Employee's company is equal to the given one.
   * @param company the given company to compare the actual Employee's company to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Employee's company is not equal to the given one.
   */
  public EmployeeAssert hasCompany(String company) {
    // check that actual Employee we want to make assertions on is not null.
    isNotNull();

    // we overrides the default error message with a more explicit one
    String errorMessage = format("\nExpected <%s> company to be:\n  <%s>\n but was:\n  <%s>", actual, company, actual.getCompany());
    
    // check
    if (!actual.getCompany().equals(company)) { throw new AssertionError(errorMessage); }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Employee's name is equal to the given one.
   * @param name the given name to compare the actual Employee's name to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Employee's name is not equal to the given one.
   */
  public EmployeeAssert hasName(String name) {
    // check that actual Employee we want to make assertions on is not null.
    isNotNull();

    // we overrides the default error message with a more explicit one
    String errorMessage = format("\nExpected <%s> name to be:\n  <%s>\n but was:\n  <%s>", actual, name, actual.getName());
    
    // check
    if (!actual.getName().equals(name)) { throw new AssertionError(errorMessage); }

    // return the current assertion for method chaining
    return this;
  }

}