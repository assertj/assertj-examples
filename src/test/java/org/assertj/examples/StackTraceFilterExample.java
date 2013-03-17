package org.assertj.examples;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.internal.Failures;

public class StackTraceFilterExample {

  public static void main(String[] args) {

    System.err.println("--------------- stack trace not filtered -----------------");
    Failures.instance().setRemoveAssertJRelatedElementsFromStackTrace(false);
    try {
      assertThat("Messi").isEqualTo("Ronaldo");
    } catch (AssertionError e) {
      e.printStackTrace();
    }
    
    System.err.println("\n--------------- stack trace filtered -----------------");
    Failures.instance().setRemoveAssertJRelatedElementsFromStackTrace(true);
    try {
      assertThat("Messi").isEqualTo("Ronaldo");
    } catch (AssertionError e) {
      e.printStackTrace();
    }
  }
}
