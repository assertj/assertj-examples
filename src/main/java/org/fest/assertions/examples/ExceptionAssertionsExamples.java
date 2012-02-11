package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import org.fest.assertions.internal.Failures;

public class ExceptionAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void exceptions_assertions_examples() {
    assertThat(fellowshipOfTheRing).hasSize(9);
    try {
      fellowshipOfTheRing.get(9); // argggl !
    } catch (Exception e) {
      assertThat(e).isInstanceOf(IndexOutOfBoundsException.class).hasMessage("Index: 9, Size: 9")
          .hasMessageStartingWith("Index: 9").hasMessageContaining("9").hasMessageEndingWith("Size: 9").hasNoCause();
    }
  }

  @Test
  public void fail_usage() throws Exception {
    try {
      Failures.instance().failure(null);
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  @Test
  public void stack_trace_filtering() {
  
    // check the err output to see the difference between a classic and a filtered stack trace. 
    System.err.println("--------------- stack trace not filtered -----------------");
    Failures.instance().setRemoveFestRelatedElementsFromStackTrace(false);
    try {
      assertThat("Messi").isEqualTo("Ronaldo");
    } catch (AssertionError e) {
      e.printStackTrace();
    }
    
    System.err.println("\n--------------- stack trace filtered -----------------");
    Failures.instance().setRemoveFestRelatedElementsFromStackTrace(true);
    try {
      assertThat("Messi").isEqualTo("Ronaldo");
    } catch (AssertionError e) {
      e.printStackTrace();
      e.getStackTrace();
    }
  }
  
  

  

  
  
  // ------------------------------------------------------------------------------------------------------
  // methods used in our examples
  // ------------------------------------------------------------------------------------------------------


}
