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

import org.assertj.core.api.JUnitSoftAssertions;
import org.assertj.examples.data.TolkienCharacter;


/**
 * A single entry point for all soft assertions, AssertJ standard assertions and MyProject custom assertions.
 */
// extending make all standard AssertJ assertions available
public class MyProjectJUnitSoftAssertions extends JUnitSoftAssertions {

  // add the custom assertions
  public TolkienCharacterAssert assertThat(TolkienCharacter actual) {
    return proxy(TolkienCharacterAssert.class, TolkienCharacter.class, actual);
  }

  public HumanAssert assertThat(Human actual) {
    return proxy(HumanAssert.class, Human.class, actual);
  }

  public EmployeeAssert assertThat(Employee actual) {
    return proxy(EmployeeAssert.class, Employee.class, actual);
  }

  public EmployeeOfTheMonthAssert assertThat(EmployeeOfTheMonth actual) {
    return proxy(EmployeeOfTheMonthAssert.class, EmployeeOfTheMonth.class, actual);
  }
  
}
