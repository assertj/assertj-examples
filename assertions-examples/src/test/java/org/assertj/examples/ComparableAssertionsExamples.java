/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2016 the original author or authors.
 */
package org.assertj.examples;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Comparable assertions specific examples
 *
 * @author Joel Costigliola
 */
public class ComparableAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void comparable_assertions_examples() {

    Rating goodRating = new Rating(8);
    Rating badRating = new Rating(4);
    assertThat(goodRating).isGreaterThan(badRating);
    assertThat(goodRating).isGreaterThanOrEqualTo(badRating);
    assertThat(badRating).isLessThan(goodRating);
    assertThat(badRating).isLessThanOrEqualTo(goodRating);
    assertThat(goodRating).isEqualByComparingTo(goodRating);
    assertThat(goodRating).isNotEqualByComparingTo(badRating);
    try {
      assertThat(badRating).isGreaterThan(goodRating);
    } catch (AssertionError e) {
      logAssertionErrorMessage("isGreaterThan for generic", e);
    }
    // just to show that we can use ObjectAssert assertion:
    assertThat(goodRating).isNotEqualTo(new Rating(8));
  }

  private static class Rating implements Comparable<Rating> {

    private int note;

    public Rating(int note) {
      this.note = note;
    }

    @Override
    public int compareTo(Rating o) {
      return note - o.note;
    }

    @Override
    public String toString() {
      return String.valueOf(note);
    }
  }

}
