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
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.DateUtil.monthOf;
import static org.assertj.core.util.DateUtil.tomorrow;
import static org.assertj.core.util.DateUtil.yesterday;
import static org.assertj.core.util.Lists.newArrayList;

import java.math.BigDecimal;
import java.util.Date;

import org.assertj.examples.comparator.AbsValueComparator;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.jupiter.api.Test;

/**
 * Examples of assertions using a specific comparator.
 *
 * @author Joel Costigliola
 */
public class AssertionsWithCustomComparatorExamples extends AbstractAssertionsExamples {

  @Test
  public void string_assertions_with_custom_comparison_examples() {

    // standard assertion based on equals() comparison strategy
    assertThat("Frodo").startsWith("Fro").endsWith("do");

    // now let's use a specific comparison strategy that is case iNsenSiTIve :o)
    // We see that assertions succeeds even though case is not the same (that's the point)
    assertThat("Frodo").usingComparator(caseInsensitiveStringComparator).startsWith("fr").endsWith("ODO");

    // All assertions called after usingComparator(caseInsensitiveStringComparator) are based on the given comparator
    // ...
    assertThat("Frodo").usingComparator(caseInsensitiveStringComparator).contains("fro").doesNotContain("don");
    // ... but a new assertion is not
    // assertThat("Frodo").startsWith("fr").endsWith("ODO"); // FAILS !!!

    // usingComparator is honored for Comparable assertions
    assertThat("abc").usingComparator(caseInsensitiveStringComparator)
                     .isLessThanOrEqualTo("ABC")
                     .usingDefaultComparator()
                     .isGreaterThan("ABC");
  }

  @Test
  public void equals_assertions_with_custom_comparator_examples() {

    // standard comparison : frodo is not equal to sam ...
    assertThat(frodo).isNotEqualTo(sam);
    // ... but if we compare only character's race frodo is equal to sam
    assertThat(frodo).usingComparator(raceNameComparator).isEqualTo(sam).isEqualTo(merry).isEqualTo(pippin);

    // isIn assertion should be consistent with raceComparator :
    assertThat(frodo).usingComparator(raceNameComparator).isIn(sam, merry, pippin);

    // chained assertions use the specified comparator, we thus can write
    assertThat(frodo).usingComparator(raceNameComparator).isEqualTo(sam).isIn(merry, pippin);

    // note that error message mentions the comparator used to understand the failure better.
    try {
      assertThat(frodo).usingComparator(raceNameComparator).isEqualTo(sauron);
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualTo with custom comparator", e);
    }

    // custom comparison by race : frodo IS equal to sam => isNotEqual must fail
    try {
      assertThat(frodo).usingComparator(raceNameComparator).isNotEqualTo(sam);
    } catch (AssertionError e) {
      logAssertionErrorMessage("isNotEqualTo with custom comparator", e);
    }
  }

  @Test
  public void collection_assertions_with_custom_comparator_examples() {

    // standard comparison : the fellowshipOfTheRing includes Gandalf but not Sauron ...
    assertThat(fellowshipOfTheRing).contains(gandalf).doesNotContain(sauron);
    // ... but if we compare only race name Sauron is in fellowshipOfTheRing because he's a Maia like Gandalf.
    assertThat(fellowshipOfTheRing).usingElementComparator(raceNameComparator).contains(sauron);

    // note that error message mentions the comparator used to better understand the failure
    // the message indicates that Sauron were found because he is a Maia like Gandalf.
    try {
      assertThat(newArrayList(gandalf)).usingElementComparator(raceNameComparator).doesNotContain(sauron);
    } catch (AssertionError e) {
      logAssertionErrorMessage("doesNotContain with custom element comparator", e);
    }

    // duplicates assertion honors custom comparator
    assertThat(fellowshipOfTheRing).doesNotHaveDuplicates();
    assertThat(newArrayList(sam, gandalf)).usingElementComparator(raceNameComparator).doesNotHaveDuplicates();
    try {
      assertThat(newArrayList(sam, gandalf, frodo)).usingElementComparator(raceNameComparator).doesNotHaveDuplicates();
    } catch (AssertionError e) {
      logAssertionErrorMessage("doesNotHaveDuplicates with custom element comparator", e);
    }
  }

  @Test
  public void array_assertions_with_custom_comparison_examples() {
    TolkienCharacter[] fellowshipOfTheRingCharacters = fellowshipOfTheRing.toArray(new TolkienCharacter[0]);

    // standard comparison : the fellowshipOfTheRing includes Gandalf but not Sauron ...
    assertThat(fellowshipOfTheRingCharacters).contains(gandalf).doesNotContain(sauron);
    // ... but if we compare only race name Sauron is in fellowshipOfTheRing because he's a Maia like Gandalf.
    assertThat(fellowshipOfTheRingCharacters).usingElementComparator(raceNameComparator).contains(sauron);

    // isSorted assertion honors custom comparator
    assertThat(array(sam, gandalf)).isSortedAccordingTo(ageComparator);
    assertThat(array(sam, gandalf)).usingElementComparator(ageComparator).isSorted();

    // note that error message mentions the comparator used to better understand the failure
    try {
      assertThat(array(gandalf, sam)).usingElementComparator(ageComparator).isSorted();
    } catch (AssertionError e) {
      logAssertionErrorMessage("isSorted with custom element comparator", e);
    }

    // duplicates assertion honors custom comparator :
    assertThat(fellowshipOfTheRingCharacters).doesNotHaveDuplicates();
    assertThat(array(sam, gandalf)).usingElementComparator(raceNameComparator).doesNotHaveDuplicates();
    try {
      assertThat(array(sam, gandalf, frodo)).usingElementComparator(raceNameComparator).doesNotHaveDuplicates();
    } catch (AssertionError e) {
      logAssertionErrorMessage("doesNotHaveDuplicates with custom element comparator", e);
    }
  }

  @Test
  public void number_assertions_with_custom_comparison_examples() {

    // with absolute values comparator : |-8| == |8|
    assertThat(-8).usingComparator(absValueComparator).isEqualTo(8);
    assertThat(-8.0).usingComparator(new AbsValueComparator<Double>()).isEqualTo(8.0);
    assertThat((byte) -8).usingComparator(new AbsValueComparator<Byte>()).isEqualTo((byte) 8);
    assertThat(new BigDecimal("-8")).usingComparator(new AbsValueComparator<BigDecimal>()).isEqualTo(
                                                                                                     new BigDecimal("8"));

    // works with arrays !
    assertThat(new int[] { -1, 2, 3 }).usingElementComparator(absValueComparator).contains(1, 2, -3);
  }

  @Test
  public void char_assertions_with_custom_comparison_examples() {
    assertThat('a').usingComparator(caseInsensitiveComparator).isEqualTo('A');
    assertThat(Character.valueOf('a')).usingComparator(caseInsensitiveComparator).isEqualTo(Character.valueOf('A'));
  }

  @Test
  public void date_assertions_with_custom_comparison_examples() {

    // theTwoTowers.getReleaseDate() : 2002-12-18
    assertThat(theTwoTowers.getReleaseDate()).usingComparator(yearAndMonthComparator)
                                             .isEqualTo("2002-12-01").isEqualTo("2002-12-02") // same year and month
                                             .isNotEqualTo("2002-11-18") // same year but different month
                                             .isBetween("2002-12-01", "2002-12-10", true, true)
                                             .isNotBetween("2002-12-01", "2002-12-10") // second date is excluded !
                                             .isIn("2002-12-01") // ok same year and month
                                             .isNotIn("2002-11-01", "2002-10-01"); // same year but different month

    // build date away from today by one day (if we are at the end of the month we subtract one day, otherwise we add one)
    Date oneDayFromTodayInSameMonth = monthOf(tomorrow()) == monthOf(new Date()) ? tomorrow() : yesterday();
    assertThat(oneDayFromTodayInSameMonth).usingComparator(yearAndMonthComparator).isToday();
  }

}
