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
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.assertj.core.api.Assumptions.assumeThatCode;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.assertj.examples.data.Race.HOBBIT;
import static org.assertj.examples.data.Race.ORC;

import org.assertj.examples.data.TolkienCharacter;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;

public class AssumptionExamples extends AbstractAssertionsExamples {

  // the data used are initialized in AbstractAssertionsExamples.

  private static int ranTests = 0;

  @AfterClass
  public static void afterClass() {
    assertThat(ranTests).as("number of tests run").isEqualTo(2);
  }

  @Test
  public void when_assumption_is_not_met_the_test_should_be_ignored() {
    // since this assumption is obviously false ...
    assumeThat(frodo.getRace()).isEqualTo(ORC);
    // ... this assertion should not be performed.
    assertThat(fellowshipOfTheRing).contains(sauron);
    fail("should not arrive here");
  }

  @Test
  public void given_assumption_is_not_met_the_test_should_be_ignored() {
    // BDD style assumptions
    // since this assumption is obviously false ...
    given(frodo.getRace()).isEqualTo(ORC);
    // ... this assertion should not be performed.
    assertThat(fellowshipOfTheRing).contains(sauron);
    fail("should not arrive here");
  }

  @Test
  public void when_string_comparable_assumption_is_not_met_the_test_should_be_ignored() {
    // since this assumption is obviously false ...
    assumeThat(frodo.getName()).isGreaterThan("Gandalf");
    // ... this assertion should not be performed.
    assertThat(fellowshipOfTheRing).contains(sauron);
    fail("should not arrive here");
  }

  @Test
  public void when_assumption_is_met_the_test_should_be_run() {
    // since this assumption is true ...
    assumeThat(frodo.getRace()).isEqualTo(HOBBIT);
    // ... this assertion should be performed.
    assertThat(fellowshipOfTheRing).doesNotContain(sauron);
    ranTests++;
  }

  @Test
  public void when_all_assumptions_are_met_the_test_should_be_run() {
    // since this assumption is true ...
    assumeThat(frodo.getRace()).isEqualTo(HOBBIT)
                               .isNotEqualTo(ORC);
    // ... this assertion should be performed.
    assertThat(fellowshipOfTheRing).doesNotContain(sauron);
    ranTests++;
  }

  @Test
  public void can_use_extracting_feature_in_assumptions() {
    // since this assumption is obviously false ...
    assumeThat(fellowshipOfTheRing).extracting("race")
                                   .contains(ORC);
    // ... this assertion should not be performed.
    assertThat(fellowshipOfTheRing).contains(sauron);
    fail("should not arrive here");
  }

  @Test
  public void assumeThatCode_assumption_not_met_example() {
    // since this assumption is obviously false ...
    assumeThatCode(() -> fellowshipOfTheRing.get(1000)).doesNotThrowAnyException();
    // ... this assertion should not be performed.
    assertThat(fellowshipOfTheRing).contains(sauron);
    fail("should not arrive here");
  }

  @Test
  public void assumeThatCode_assumption_met_example() {
    // since the given code throws an ArrayIndexOutOfBoundsException ...
    assumeThatCode(() -> fellowshipOfTheRing.get(1000)).isInstanceOf(IndexOutOfBoundsException.class);
    // ... this assertion should be performed.
    assertThat(fellowshipOfTheRing).contains(frodo);
  }

  @Test
  public void all_assumptions_must_be_met_otherwise_the_test_is_ignored() {
    // since one of the assumptions is obviously false ...
    assumeThat(frodo.getRace()).isEqualTo(HOBBIT);
    assumeThat(fellowshipOfTheRing).extracting("race")
                                   .contains(ORC);
    // ... this assertion should not be performed.
    assertThat(fellowshipOfTheRing).contains(sauron);
    fail("should not arrive here");
  }

  @Test
  public void assumptions_still_work_when_switching_the_object_under_test() {
    assumeThat(fellowshipOfTheRing).size()
                                   .isLessThan(100)
                                   .returnToIterable()
                                   .filteredOn("race", HOBBIT)
                                   .containsOnly(sam, frodo, pippin, merry)
                                   .extracting(TolkienCharacter::getName)
                                   .contains("Frodo")
                                   .contains("Elrond"); // fail
    fail("should not arrive here");
  }

}
