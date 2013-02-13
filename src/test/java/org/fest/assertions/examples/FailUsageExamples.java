package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;

/**
 * Fail usage examples.
 * 
 * @author Joel Costigliola
 */
public class FailUsageExamples extends AbstractAssertionsExamples {

  @Test
  public void fail_usage_examples() {

    assertThat(fellowshipOfTheRing).hasSize(9);

    // here's the typical pattern to use Fail :
    try {
      fellowshipOfTheRing.get(9); // argggl !
      // we should not arrive here => use fail to expresses that
      // if IndexOutOfBoundsException was not thrown, test would fail the specified message 
      fail("IndexOutOfBoundsException expected because fellowshipOfTheRing has only 9 elements");
    } catch (IndexOutOfBoundsException e) {
      assertThat(e).hasMessage("Index: 9, Size: 9");
    }

    // Warning : don't catch Throwable in catch clause as it would also catch AssertionError thrown by fail method

    // another way to do the same thing 
    try {
      fellowshipOfTheRing.get(9); // argggl !
      // if IndexOutOfBoundsException was not thrown, test would fail with message : 
      // "Expected IndexOutOfBoundsException to be thrown"
      failBecauseExceptionWasNotThrown(IndexOutOfBoundsException.class);
    } catch (IndexOutOfBoundsException e) {
      assertThat(e).hasMessage("Index: 9, Size: 9");
    }
  }
}
