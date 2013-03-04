package org.fest.assertions.examples.guava;

import static org.fest.assertions.api.GUAVA.assertThat;

import org.junit.Test;

import com.google.common.base.Optional;

import org.fest.assertions.examples.AbstractAssertionsExamples;

/**
 * {@link Optional} assertions example.
 * 
 * @author Joel Costigliola
 */
public class OptionalAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void optional_assertions_examples() {
    final Optional<String> optional = Optional.of("Test");
    assertThat(optional).isPresent().contains("Test");

    Optional<Object> absentOptional = Optional.fromNullable(null);
    assertThat(absentOptional).isAbsent();
    
    // log some error messages to have a look at them
    try {
      assertThat(absentOptional).isPresent();
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.isPresent", e);
    }
    try {
      assertThat(absentOptional).contains("Test");
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.contains", e);
    }
    try {
      assertThat(optional).contains("Foo");
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.contains", e);
    }
    try {
      assertThat(optional).isAbsent();
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.isAbsent", e);
    }
  }
  
}
