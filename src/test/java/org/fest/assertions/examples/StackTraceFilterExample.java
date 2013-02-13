package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.assertThat;

import org.fest.assertions.internal.Failures;

public class StackTraceFilterExample {

  public static void main(String[] args) {

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
    }
  }
}
