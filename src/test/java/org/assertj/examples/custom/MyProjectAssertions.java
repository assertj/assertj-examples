package org.assertj.examples.custom;

import org.assertj.examples.data.TolkienCharacter;

import org.assertj.core.api.Assertions;


/**
 * A single entry point for all assertions, AssertJ standard assertions and MyProject custom assertions.
 * <p>
 * With  MyProjectAssertions.assertThat sttaic import, you will access all possible assertions (standard and custom ones)
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

}
