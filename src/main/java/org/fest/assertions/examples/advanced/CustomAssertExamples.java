package org.fest.assertions.examples.advanced;



import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.examples.advanced.TolkienCharacterAssert.assertThat;

import org.junit.Test;

import org.fest.assertions.examples.AbstractAssertionsExamples;
import org.fest.assertions.examples.data.TolkienCharacter;


/**
 * 
 * Shows some example of a custom assertion class: {@link TolkienCharacterAssert} that allows us to make
 * assertions specific to  {@link TolkienCharacter}.
 *
 * @author Joel Costigliola
 */
public class CustomAssertExamples extends AbstractAssertionsExamples {

  @Test
  public void succesful_custom_assertion_example() {
    // custom assertion : assertThat is resolved from TolkienCharacterAssert static import
    assertThat(frodo).hasName("Frodo");
    // Fest standard assertion : assertThat is resolved from org.fest.assertions.Assertions static import
    assertThat(frodo.getAge()).isEqualTo(33);  
    // generic assertion like 'isNotEqualTo' are available for TolkienCharacterAssert 
    assertThat(frodo).isNotEqualTo(merry);  
  }

  @Test
  public void failed_custom_assertion_example() {
    sam.setName("Sammy");
    try {
      // custom assertion : assertThat is resolved from TolkienCharacterAssert static import
      assertThat(sam).hasName("Sam");
    } catch (AssertionError e) {
      // As we are defining custom assertion, we can set meaningful assertion error message, like this one : 
      assertThat(e).hasMessage("Expected character's name to be <Sam> but was <Sammy>");
    }
  }
  
}
