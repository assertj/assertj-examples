package org.fest.assertions.examples;

import static java.lang.Integer.toHexString;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Fail.fail;
import static org.fest.assertions.data.Index.atIndex;
import static org.fest.util.Arrays.array;
import static org.fest.util.Collections.list;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;

import org.junit.Test;

import org.fest.assertions.examples.comparator.AbsValueComparator;
import org.fest.assertions.examples.data.Person;
import org.fest.assertions.examples.data.TolkienCharacter;
import org.fest.assertions.internal.Failures;

public class AssertionsWithCustomComparatorExamples extends AbstractAssertionsExamples {

  @Test
  public void string_assertions_with_custom_comparison_examples() {
    // standard assertion based on equals() comparison strategy
    assertThat("Frodo").startsWith("Fro").endsWith("do");
    // now let's use a specific comparison strategy that is case iNsenSiTIve :o)
    // We see that assertions succeeds even though case is not the same (that's the point)
    assertThat("Frodo").usingComparator(caseInsensitiveStringComparator).startsWith("fr").endsWith("ODO");
    // Assertions after usingComparator(caseInsensitiveStringComparator) are based on the given comparator ... 
    assertThat("Frodo").usingComparator(caseInsensitiveStringComparator).contains("fro").doesNotContain("don");
    // ... but a new assertion is not  
    // assertThat("Frodo").startsWith("fr").endsWith("ODO"); // FAILS !!!
  }

  @Test
  public void equals_assertions_with_custom_comparator_examples() {
    // standard comparison : frodo is not equal to sam ...
    assertThat(frodo).isNotEqualTo(sam);
    // ... but if we compare only character's race frodo is equal to sam
    assertThat(frodo).usingComparator(raceComparator).isEqualTo(sam).isEqualTo(merry).isEqualTo(pippin);
    // isIn assertion should be consistent with raceComparator :
    assertThat(frodo).usingComparator(raceComparator).isIn(sam, merry, pippin);
    // chained assertions use the specified comparator, we thus can write
    assertThat(frodo).usingComparator(raceComparator).isEqualTo(sam).isIn(merry, pippin);

    // note that error message mentions the comparator used to understand the failure better.
    boolean errorHasBeenThrown = false;
    try {
      assertThat(frodo).usingComparator(raceComparator).isEqualTo(sauron);
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "Expecting actual:<Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]> to be equal to "
              + "<Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]> "
              + "according to 'CharacterRaceComparator' comparator but was not.");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();

    errorHasBeenThrown = false;
    // custom comparison by race : frodo IS equal to sam => isNotEqual must fail
    try {
      assertThat(frodo).usingComparator(raceComparator).isNotEqualTo(sam);
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "<Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]> should not be equal to:"
              + "<Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]> "
              + "according to 'CharacterRaceComparator' comparator");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();
  }

  @Test
  public void assertions_with_custom_contains_comparison_examples() {
    boolean errorHasBeenThrown = false;
    // standard comparison : the fellowshipOfTheRing does not include sauron ...
    assertThat(fellowshipOfTheRing).doesNotContain(sauron);
    // ... but if we compare only character's race sauron is in fellowshipOfTheRing because he's a maia like Gandalf.
    assertThat(fellowshipOfTheRing).contains(gandalf);
    assertThat(fellowshipOfTheRing).usingComparator(raceComparator).contains(sauron);
    assertThat(list(gandalf)).usingComparator(raceComparator).contains(sauron);
    assertThat(list(gandalf)).usingComparator(raceComparator).containsOnly(sauron);
    assertThat(list(sam, gandalf)).usingComparator(raceComparator).contains(sauron, atIndex(1));
    assertThat(list(sam, gandalf)).usingComparator(raceComparator).doesNotContain(sauron, atIndex(0));
    assertThat(list(sam, gandalf)).isSortedAccordingTo(raceComparator);
    assertThat(list(sam, gandalf)).usingComparator(raceComparator).isSorted();

    // note that error message mentions the comparator used to understand the failure better.
    try {
      assertThat(list(gandalf)).usingComparator(raceComparator).doesNotContain(sauron);
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "expecting:<[Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020]]> not to contain:"
              + "<[Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]]> but found:"
              + "<[Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]]> "
              + "according to 'CharacterRaceComparator' comparator");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();

    // note that error message mentions the comparator used to understand the failure better.
    try {
      errorHasBeenThrown = false;
      assertThat(list(sam, gandalf)).usingComparator(raceComparator).contains(sauron, atIndex(0));
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "expecting <Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]> at index <0> "
              + "but found <Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]> in "
              + "<[Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]"
              + ", Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020]]>"
              + " according to 'CharacterRaceComparator' comparator");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();

    // note that error message mentions the comparator used to understand the failure better.
    try {
      errorHasBeenThrown = false;
      assertThat(list(sam, gandalf)).usingComparator(raceComparator).doesNotContain(sauron, atIndex(1));
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "expecting <[Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38], "
              + "Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020]]> "
              + "not to contain <Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]> "
              + "at index <1> according to 'CharacterRaceComparator' comparator");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();

    // duplicates assertion honors custom comparator :
    assertThat(fellowshipOfTheRing).doesNotHaveDuplicates();
    assertThat(list(sam, gandalf)).usingComparator(raceComparator).doesNotHaveDuplicates();
    try {
      errorHasBeenThrown = false;
      assertThat(list(sam, gandalf, frodo)).usingComparator(raceComparator).doesNotHaveDuplicates();
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "found duplicate(s):<[Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]]> in:<"
              + "[Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38], "
              + "Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020], "
              + "Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]]> "
              + "according to 'CharacterRaceComparator' comparator");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();
  }

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
  public void file_assertions_examples() throws Exception {
    File xFile = writeFile("xFile", "The Truth Is Out There");
    File xFileClone = writeFile("xFileClone", "The Truth Is Out There");
    assertThat(xFile).exists().isFile().hasContentEqualTo(xFileClone).isRelative();
    File emptyFile = writeFile("emptyFile", "");
    assertThat(emptyFile.length()).isZero();
  }

  @Test
  // new in FEST 2.0
  public void stream_assertions_examples() throws Exception {
    assertThat(streamFrom("string")).hasContentEqualTo(streamFrom("string"));
  }

  private static ByteArrayInputStream streamFrom(String string) {
    return new ByteArrayInputStream(string.getBytes());
  }
  
  // new in FEST 2.0
  @Test
  public void assertion_error_with_message_differentiating_expected_double_and_actual_float() throws Exception {

    final Object expected = 42d;
    final Object actual = 42f;
    try {
      assertThat(actual).isEqualTo(expected);
    } catch (AssertionError e) {
      assertThat(expected.hashCode()).isNotEqualTo(actual.hashCode());
      assertThat(e).hasMessage(
          "expected:<'42.0 (Double@" + toHexString(expected.hashCode()) + ")'> but was:<'42.0 (Float@"
              + toHexString(actual.hashCode()) + ")'>");
      return;
    }
    fail("AssertionError expected");
  }

  // new in FEST 2.0
  @Test
  public void assertion_error_with_message_differentiating_expected_and_actual_persons() throws Exception {

    Person actual = new Person("Jake", 43);
    Person expected = new Person("Jake", 47);
    try {
      assertThat(actual).isEqualTo(expected);
    } catch (AssertionError e) {
      assertThat(expected.hashCode()).isNotEqualTo(actual.hashCode());
      assertThat(e).hasMessage(
          "expected:<'Person[name=Jake] (Person@" + toHexString(expected.hashCode())
              + ")'> but was:<'Person[name=Jake] (Person@" + toHexString(actual.hashCode()) + ")'>");
      return;
    }
    fail("AssertionError expected");
  }

  

  
  
  // ------------------------------------------------------------------------------------------------------
  // methods used in our examples
  // ------------------------------------------------------------------------------------------------------

  private File writeFile(String fileName, String fileContent) throws Exception {
    File file = new File("target/" + fileName);
    BufferedWriter out = new BufferedWriter(new FileWriter(file));
    out.write(fileContent);
    out.close();
    return file;
  }

}
