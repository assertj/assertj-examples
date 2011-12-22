package examples;


import static examples.TolkienCharacterAssert.assertThat;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;



public class CustomFestAssertExamples {

  private final Race hobbit = new Race("Hobbit", false);

  @Test
  public void succesful_custom_assertion_example() {
    TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, hobbit);
    TolkienCharacter merry = new TolkienCharacter("Merry", 36, hobbit);
    // custom assertion : assertThat is resolved from CharacterAssert static import
    assertThat(frodo).hasName("Frodo");  // 
    // Fest standard assertion : assertThat is resolved from org.fest.assertions.Assertions static import
    assertThat(frodo.getAge()).isEqualTo(33);  
    // generic assertion like 'isNotEqualTo' are available for CharacterAssert 
    assertThat(frodo).isNotEqualTo(merry);  
  }

  @Test
  public void failed_custom_assertion_example() {
    TolkienCharacter sam = new TolkienCharacter("Merry", 38, hobbit); // oops wrong name !
    try {
      // custom assertion : assertThat is resolved from CharacterAssert static import
      assertThat(sam).hasName("Sam");
    } catch (AssertionError e) {
      // see that error message is explicit 
      // assertThat(e).hasMessage("Expected character's name to be <Sam> but was <Merry>");
      assertThat(e.getMessage()).isEqualTo("Expected character's name to be <Sam> but was <Merry>");
    }
  }
  
}
