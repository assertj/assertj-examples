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
 * Copyright 2012-2013 the original author or authors.
 */
package org.assertj.examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;
import static org.assertj.core.api.Assertions.filter;
import static org.assertj.core.data.Index.atIndex;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.examples.data.Alignment.EVIL;
import static org.assertj.examples.data.Race.ELF;
import static org.assertj.examples.data.Race.HOBBIT;
import static org.assertj.examples.data.Race.ORC;
import static org.assertj.examples.data.Ring.dwarfRing;
import static org.assertj.examples.data.Ring.manRing;
import static org.assertj.examples.data.Ring.narya;
import static org.assertj.examples.data.Ring.nenya;
import static org.assertj.examples.data.Ring.oneRing;
import static org.assertj.examples.data.Ring.vilya;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

import org.assertj.examples.data.Race;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;
import org.assertj.examples.data.movie.Movie;

/**
 * Array assertions examples.
 * 
 * @author Joel Costigliola
 */
public class ArrayAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void array_assertions_examples() {
    // array assertion are very similar to newArrayList assertions
    Ring[] elvesRings = array(vilya, nenya, narya);
    Movie[] trilogy = array(theFellowshipOfTheRing, theTwoTowers, theReturnOfTheKing);
    assertThat(elvesRings).isNotEmpty().hasSize(3);
    assertThat(elvesRings).hasSameSizeAs(trilogy);
    assertThat(elvesRings).hasSameSizeAs(newArrayList(trilogy));
    assertThat(elvesRings).contains(nenya).doesNotContain(oneRing);

    // you can check element at a given index (we use Index.atIndex(int) synthetic sugar for better readability).
    assertThat(elvesRings).contains(vilya, atIndex(0)).contains(nenya, atIndex(1)).contains(narya, atIndex(2));
    // with containsOnly, all the elements must be present (but the order is not important)
    assertThat(elvesRings).containsOnly(nenya, vilya, narya);
    assertThat(elvesRings).doesNotContainNull().doesNotHaveDuplicates();
    // special check for null, empty collection or both
    assertThat(newArrayList(frodo, null, sam)).containsNull();
    Object[] array = array();
    assertThat(array).isEmpty();
    assertThat(array).contains();
    assertThat(array).isNullOrEmpty();
    array = null;
    assertThat(array).isNullOrEmpty();
    // you can also check the start or end of your collection/iterable
    Ring[] allRings = array(oneRing, vilya, nenya, narya, dwarfRing, manRing);
    assertThat(allRings).startsWith(oneRing, vilya).endsWith(dwarfRing, manRing);
    assertThat(allRings).containsSequence(nenya, narya, dwarfRing);
    // you can check that an array is sorted
    TolkienCharacter[] fellowshipOfTheRingArray = fellowshipOfTheRing.toArray(new TolkienCharacter[0]);
    Arrays.sort(fellowshipOfTheRingArray, ageComparator);
    assertThat(fellowshipOfTheRingArray).isSortedAccordingTo(ageComparator);
    assertThat(fellowshipOfTheRingArray).usingElementComparator(ageComparator).isSorted();

    // Uncomment when #131 is fixed
    String[] arr = { "a", "b", "c" };
    assertThat(arr).containsExactly("a", "b", "c");

    array = new String[] { "--option", "a=b", "--option", "c=d" };
    assertThat(array).containsSequence("--option", "c=d");
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
  public void arra_assertions_on_extracted_values_example() {
    TolkienCharacter[] fellowshipOfTheRingArray = fellowshipOfTheRing.toArray(new TolkienCharacter[0]);

    // extract simple property value (having a java standard type)
    assertThat(extractProperty("name").from(fellowshipOfTheRingArray)).contains("Boromir", "Gandalf", "Frodo",
                                                                                "Legolas")
                                                                      .doesNotContain("Sauron", "Elrond");

    // extracting property works also with user's types (here Race)
    assertThat(extractProperty("race").from(fellowshipOfTheRingArray)).contains(HOBBIT, ELF).doesNotContain(ORC);

    // same assertion but specifying the type of the extracted values (here Race)
    assertThat(fellowshipOfTheRingArray).extracting("race", Race.class)
                                        .contains(HOBBIT, ELF)
                                        .doesNotContain(ORC);

    // extract nested property on Race
    assertThat(extractProperty("race.name").from(fellowshipOfTheRingArray)).contains("Hobbit", "Elf")
                                                                           .doesNotContain("Orc");

    // same assertions but written with extracting(), it has the advantage of being able to extract field values as well
    // as property values

    // extract 'name' property values.
    assertThat(fellowshipOfTheRing).extracting("name")
                                   .contains("Boromir", "Gandalf", "Frodo", "Legolas")
                                   .doesNotContain("Sauron", "Elrond");

    // extract 'age' field values, it works because 'age' is public in TolkienCharacter class.
    assertThat(fellowshipOfTheRing).extracting("age")
                                   .contains(33, 38, 36);

    // extracting works also with user's types (here Race),
    assertThat(fellowshipOfTheRing).extracting("race")
                                   .contains(HOBBIT, ELF)
                                   .doesNotContain(ORC);

    // extract nested property values on Race
    assertThat(fellowshipOfTheRing).extracting("race.name")
                                   .contains("Hobbit", "Elf")
                                   .doesNotContain("Orc");
  }

  @Test
  public void array_is_sorted_assertion_example() {

    // enum order = order of declaration = ring power
    assertThat(new Ring[] { oneRing, vilya, nenya, narya, dwarfRing, manRing }).isSorted();

    // ring comparison by increasing power
    Comparator<Ring> increasingPowerRingComparator = new Comparator<Ring>() {
      public int compare(Ring ring1, Ring ring2) {
        return -ring1.compareTo(ring2);
      }
    };
    assertThat(new Ring[] { manRing, dwarfRing, narya, nenya, vilya, oneRing }).isSortedAccordingTo(
                                                                                                    increasingPowerRingComparator);
  }

  @Test
  public void filter_then_extract_assertion_example() {
    Iterable<TolkienCharacter> badBadGuys = filter(orcsWithHobbitPrisoners).with("race.alignment", EVIL).get();
    assertThat(badBadGuys).extracting("name").containsOnly("Guruk");

  }

  @Test
  public void contains_exactly_for_basic_types_assertion_examples() {
    // int
    assertThat(new int[] { 1, 2, 3 }).containsExactly(1, 2, 3);
    try {
      assertThat(new int[] { 1, 2, 3 }).containsExactly(2, 1, 3);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsExactly for int array", e);
    }

    // long
    assertThat(new long[] { 1, 2, 3 }).containsExactly(1, 2, 3);
    try {
      assertThat(new long[] { 1, 2, 3 }).containsExactly(2, 1, 3);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsExactly for long array", e);
    }

    // short
    assertThat(new short[] { 1, 2, 3 }).containsExactly((short) 1, (short) 2, (short) 3);
    try {
      assertThat(new short[] { 1, 2, 3 }).containsExactly((short) 2, (short) 1, (short) 3);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsExactly for long array", e);
    }

    // byte
    assertThat(new byte[] { 1, 2, 3 }).containsExactly((byte) 1, (byte) 2, (byte) 3);
    try {
      assertThat(new byte[] { 1, 2, 3 }).containsExactly((byte) 2, (byte) 1, (byte) 3);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsExactly for long array", e);
    }

    // float
    assertThat(new float[] { 1, 2.0f, 3 }).containsExactly(1.0f, 2, 3);
    try {
      assertThat(new float[] { 1.0f, 2, 3 }).containsExactly(2.0f, 1, 3);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsExactly for float array", e);
    }

    // double
    assertThat(new double[] { 1.0, 2, 3 }).containsExactly(1.0, 2, 3);
    try {
      assertThat(new double[] { 1.0, 2, 3 }).containsExactly(2.0, 1, 3);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsExactly for double array", e);
    }
  }

  @Test
  public void containsOnlyOnce_for_basic_types_assertion_examples() {
    // int
    assertThat(new int[] { 1, 2, 3 }).containsOnlyOnce(1);
    assertThat(new int[] { 1, 2, 3 }).containsOnlyOnce(1, 2);
    assertThat(new int[] { 1, 2, 3 }).containsOnlyOnce(1, 2, 3);
    assertThat(new int[] { 1, 2, 3 }).containsOnlyOnce(3, 2, 3);
    try {
      assertThat(new int[] { 1, 2, 1 }).containsOnlyOnce(1);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnlyOnce for int array", e);
    }

    try {
      assertThat(new int[] { 1, 2, 3 }).containsOnlyOnce(4);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnlyOnce for int array", e);
    }

    try {
      assertThat(new int[] { 1, 2, 3, 3, 1 }).containsOnlyOnce(0, 1, 2, 3, 4, 5);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnlyOnce for int array", e);
    }

    assertThat(new char[] { 'a', 'b', 'c' }).containsOnlyOnce('a', 'b');
  }

}
