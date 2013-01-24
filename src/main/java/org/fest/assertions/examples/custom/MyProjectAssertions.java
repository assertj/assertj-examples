package org.fest.assertions.examples.custom;

import org.fest.assertions.api.Assertions;
import org.fest.assertions.examples.data.TolkienCharacter;


/**
 * A single entry point for all assertions, Fest standard assertions and MyProject custom assertions.
 * <p>
 * With  MyProjectAssertions.assertThat sttaic import, you will access all possible assertions (standard and custom ones)
 */
public class MyProjectAssertions extends Assertions { // extending make all standard Fest assertions available.  

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
