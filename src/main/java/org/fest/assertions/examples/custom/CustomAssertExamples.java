package org.fest.assertions.examples.custom;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.examples.custom.MyProjectAssertions.assertThat;

import org.junit.Test;

import org.fest.assertions.examples.AbstractAssertionsExamples;
import org.fest.assertions.examples.data.TolkienCharacter;

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
    // If you have created a class inheriting from org.fest.assertions.Assertions having an entry point to
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

}
