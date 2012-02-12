package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.util.Arrays.array;
import static org.fest.util.Collections.list;
import static org.fest.util.Dates.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import org.fest.assertions.examples.comparator.AbsValueComparator;
import org.fest.assertions.examples.data.TolkienCharacter;

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
    
    // All assertions called after usingComparator(caseInsensitiveStringComparator) are based on the given comparator ... 
    assertThat("Frodo").usingComparator(caseInsensitiveStringComparator).contains("fro").doesNotContain("don");
    // ... but a new assertion is not  
    // assertThat("Frodo").startsWith("fr").endsWith("ODO"); // FAILS !!!
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
      assertThat(e).hasMessage(
          "Expecting actual:<Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]> to be equal to "
              + "<Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]> "
              + "according to 'TolkienCharacterRaceNameComparator' comparator but was not.");
    }

    // custom comparison by race : frodo IS equal to sam => isNotEqual must fail
    try {
      assertThat(frodo).usingComparator(raceNameComparator).isNotEqualTo(sam);
    } catch (AssertionError e) {
      assertThat(e).hasMessage(
          "<Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]> should not be equal to:"
              + "<Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]> "
              + "according to 'TolkienCharacterRaceNameComparator' comparator");
    }
  }

  @Test
  public void collection_assertions_with_custom_comparator_examples() {

    // standard comparison : the fellowshipOfTheRing includes Gandalf but not Sauron ...
    assertThat(fellowshipOfTheRing).contains(gandalf).doesNotContain(sauron);
    // ... but if we compare only race name Sauron is in fellowshipOfTheRing because he's a Maia like Gandalf.
    assertThat(fellowshipOfTheRing).usingComparator(raceNameComparator).contains(sauron);

    // note that error message mentions the comparator used to better understand the failure
    // the message indicates that Sauron were found because he is a Maia like Gandalf.
    try {
      assertThat(list(gandalf)).usingComparator(raceNameComparator).doesNotContain(sauron);
    } catch (AssertionError e) {
      assertThat(e).hasMessage(
          "expecting:<[Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020]]> not to contain:"
              + "<[Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]]> but found:"
              + "<[Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]]> "
              + "according to 'TolkienCharacterRaceNameComparator' comparator");
    }

    // duplicates assertion honors custom comparator
    assertThat(fellowshipOfTheRing).doesNotHaveDuplicates();
    assertThat(list(sam, gandalf)).usingComparator(raceNameComparator).doesNotHaveDuplicates();
    try {
      assertThat(list(sam, gandalf, frodo)).usingComparator(raceNameComparator).doesNotHaveDuplicates();
    } catch (AssertionError e) {
      assertThat(e).hasMessage(
          "found duplicate(s):<[Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]]> in:<"
              + "[Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38], "
              + "Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020], "
              + "Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]]> "
              + "according to 'TolkienCharacterRaceNameComparator' comparator");
    }
  }

  @Test
  public void array_assertions_with_custom_comparison_examples() {
    TolkienCharacter[] fellowshipOfTheRingCharacters = fellowshipOfTheRing.toArray(new TolkienCharacter[0]);

    // standard comparison : the fellowshipOfTheRing includes Gandalf but not Sauron ...
    assertThat(fellowshipOfTheRingCharacters).contains(gandalf).doesNotContain(sauron);
    // ... but if we compare only race name Sauron is in fellowshipOfTheRing because he's a Maia like Gandalf.
    assertThat(fellowshipOfTheRingCharacters).usingComparator(raceNameComparator).contains(sauron);

    // isSorted assertion honors custom comparator (new in FEST 2.0)
    assertThat(array(sam, gandalf)).isSortedAccordingTo(ageComparator);
    assertThat(array(sam, gandalf)).usingComparator(ageComparator).isSorted();

    // note that error message mentions the comparator used to better understand the failure
    try {
      assertThat(array(gandalf, sam)).usingComparator(ageComparator).isSorted();
    } catch (AssertionError e) {
      assertThat(e).hasMessage(
          "group is not sorted according to 'AgeComparator' comparator because "
              + "element 0:<Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020]> "
              + "is not less or equal than "
              + "element 1:<Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]>, " + "group was:<["
              + "Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020], "
              + "Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]]>");
    }

    // duplicates assertion honors custom comparator :
    assertThat(fellowshipOfTheRingCharacters).doesNotHaveDuplicates();
    assertThat(array(sam, gandalf)).usingComparator(raceNameComparator).doesNotHaveDuplicates();
    try {
      assertThat(array(sam, gandalf, frodo)).usingComparator(raceNameComparator).doesNotHaveDuplicates();
    } catch (AssertionError e) {
      assertThat(e).hasMessage(
          "found duplicate(s):<[Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]]> in:<"
              + "[Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38], "
              + "Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020], "
              + "Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]]> "
              + "according to 'TolkienCharacterRaceNameComparator' comparator");
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
    assertThat(new int[] { -1, 2, 3 }).usingComparator(absValueComparator).contains(1, 2, -3);
  }

  @Test
  public void char_assertions_with_custom_comparison_examples() {
    assertThat('a').usingComparator(caseInsensitiveComparator).isEqualTo('A');
    assertThat(new Character('a')).usingComparator(caseInsensitiveComparator).isEqualTo(new Character('A'));
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
