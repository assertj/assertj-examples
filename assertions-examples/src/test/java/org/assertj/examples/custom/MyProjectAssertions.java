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

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.assertj.examples.data.TolkienCharacter;


/**
 * A single entry point for all assertions, AssertJ standard assertions and MyProject custom assertions.
 * <p>
 * With  MyProjectAssertions.assertThat static import, you will access all possible assertions (standard and custom ones)
 */
public class MyProjectAssertions extends Assertions { // extending make all standard AssertJ assertions available.

  // add the custom assertions related to MyProject

  public static TolkienCharacterAssert assertThat(TolkienCharacter actual) {
    return new TolkienCharacterAssert(actual);
  }

  public static HumanAssert assertThat(Human actual) {
    return new HumanAssert(actual);
  }

  public static EmployeeAssert assertThat(Employee actual) {
    return new EmployeeAssert(actual);
  }

  public static EmployeeOfTheMonthAssert assertThat(EmployeeOfTheMonth actual) {
    return new EmployeeOfTheMonthAssert(actual);
  }
  
  public static MyBigDecimalAssert assertThat(BigDecimal actual) {
    return new MyBigDecimalAssert(actual);
  }
  
}
