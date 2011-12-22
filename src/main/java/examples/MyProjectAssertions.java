package examples;


/**
 * A single entry point for all assertions, Fest standard assertions and MyProject custom assertions.
 * <p>
 * With  MyProjectAssertions.assertThat sttaic import, you will access all possible assertions (standard and custom ones)
 */
public class MyProjectAssertions { // extends Assertions make available all standard assertions.  

  // add the custom assertions related to MyProject
  
  public static TolkienCharacterAssert assertThat(TolkienCharacter actual) {
    return new TolkienCharacterAssert(actual);
  }
  

}
