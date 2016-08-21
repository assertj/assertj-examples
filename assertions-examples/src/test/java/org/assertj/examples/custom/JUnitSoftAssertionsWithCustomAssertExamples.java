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

import org.assertj.examples.AbstractAssertionsExamples;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class JUnitSoftAssertionsWithCustomAssertExamples extends AbstractAssertionsExamples {

  @Rule
  public final MyProjectJUnitSoftAssertions softly = new MyProjectJUnitSoftAssertions();

  @Test
  public void successful_junit_soft_custom_assertion_example() {
    // For example, the assertions below are accessed from MyProjectAssertions :
    // - hasName comes from .MyProjectAssertions.assertThat(TolkienCharacter actual)
    softly.assertThat(frodo).hasName("Frodo").hasAge(33).isNotEqualTo(merry);
    // - isEqualTo is accessible since MyProjectAssertions inherits from Assertions which provides Integer assertions.
    softly.assertThat(frodo.age).isEqualTo(33);
  }

  // comment the @Ignore to see the test failing with all the assertion error and not only the first one.
  @Test
  @Ignore
  public void failing_junit_soft_custom_assertions_example() {
    // basic object to test
    String name = "Michael Jordan - Bulls";
    // custom object to test
    Employee kent = new Employee("Kent Beck"); 
    kent.jobTitle = "TDD evangelist";

    // use our own soft assertions based on JUnit rule
    softly.assertThat(name).startsWith("Mike").contains("Lakers").endsWith("Chicago");
    softly.assertThat(kent).hasName("Wes Anderson").hasJobTitle("Director");
  }

}
