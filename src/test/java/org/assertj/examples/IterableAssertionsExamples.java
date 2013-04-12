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
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.examples.data.Race.ELF;
import static org.assertj.examples.data.Race.HOBBIT;
import static org.assertj.examples.data.Race.ORC;
import static org.assertj.examples.data.Ring.dwarfRing;
import static org.assertj.examples.data.Ring.manRing;
import static org.assertj.examples.data.Ring.narya;
import static org.assertj.examples.data.Ring.nenya;
import static org.assertj.examples.data.Ring.oneRing;
import static org.assertj.examples.data.Ring.vilya;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import org.assertj.examples.data.Ring;
import org.assertj.examples.data.movie.Movie;

/**
 * Iterable (including Collection) assertions examples.<br>
 * 
 * @author Joel Costigliola
 */
public class IterableAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void iterable_basic_assertions_examples() {

    // would work the same way with Iterable<Ring>,
    Iterable<Ring> elvesRings = newArrayList(vilya, nenya, narya);
    Iterable<Movie> trilogy = newArrayList(theFellowshipOfTheRing, theTwoTowers, theReturnOfTheKing);
    assertThat(elvesRings).isNotEmpty().hasSize(3);
    assertThat(elvesRings).hasSameSizeAs(trilogy);
    assertThat(elvesRings).contains(nenya).doesNotContain(oneRing);

    // with containsOnly, all the elements must be present (but the order is not important)
    assertThat(elvesRings).containsOnly(nenya, vilya, narya);
    assertThat(elvesRings).doesNotContainNull().doesNotHaveDuplicates();

    // special check for null, empty collection or both
    assertThat(newArrayList(frodo, null, sam)).containsNull();
    List<Object> newArrayList = newArrayList();
    assertThat(newArrayList).isEmpty();
    assertThat(newArrayList).contains();
    assertThat(newArrayList).isNullOrEmpty();
    newArrayList = null;
    assertThat(newArrayList).isNullOrEmpty();

    // you can also check the start or end of your collection/iterable
    Iterable<Ring> allRings = newArrayList(oneRing, vilya, nenya, narya, dwarfRing, manRing);
    assertThat(allRings).startsWith(oneRing, vilya).endsWith(dwarfRing, manRing);
    assertThat(allRings).containsSequence(nenya, narya, dwarfRing);
    assertThat(allRings).containsAll(elvesRings);

    // to show an error message
    // assertThat(elvesRings).containsAll(allRings);

    List<Integer> testedList = newArrayList(1);
    List<Integer> referenceList = newArrayList(1, 2, 3);
    assertThat(referenceList).containsSequence(testedList.toArray(new Integer[0]));
  }

  @Test
  public void iterable_basic_contains_exactly_assertions_examples() {
    Iterable<Ring> elvesRings = newArrayList(vilya, nenya, narya);
    assertThat(elvesRings).containsExactly(vilya, nenya, narya);

    // It works with collections that have a consistent iteration order
    SortedSet<Ring> elvesRingsSet = new TreeSet<Ring>();
    elvesRingsSet.add(vilya);
    elvesRingsSet.add(nenya);
    elvesRingsSet.add(narya);
    assertThat(elvesRingsSet).containsExactly(vilya, nenya, narya);

    try {
      // putting a different order would make the assertion fail :
      assertThat(elvesRings).containsExactly(nenya, vilya, narya);
    } catch (AssertionError e) {
      logger.info(e.getMessage());
      assertThat(e).hasMessage(
                               "actual and expected have the same elements but not in the same order, at index 0 actual element was :\n<vilya>\n whereas expected element was :\n<nenya>\n");
    }

    try {
      List<String> z = newArrayList("a", "a", "a");
      assertThat(z).containsExactly("a", "a");
    } catch (AssertionError e) {
      logger.info(e.getMessage());
    }

  }

  @Test
  public void iterable_assertions_with_custom_comparator_examples() {

    // standard comparison : the fellowshipOfTheRing includes Gandalf but not Sauron ...
    assertThat(fellowshipOfTheRing).contains(gandalf).doesNotContain(sauron);
    // ... but if we compare only race name Sauron is in fellowshipOfTheRing because he's a Maia like Gandalf.
    assertThat(fellowshipOfTheRing).usingElementComparator(raceNameComparator).contains(sauron);

    // note that error message mentions the comparator used to better understand the failure
    // the message indicates that Sauron were found because he is a Maia like Gandalf.
    try {
      assertThat(newArrayList(gandalf, sam)).usingElementComparator(raceNameComparator).doesNotContain(sauron);
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
  public void iterable_assertions_on_extracted_values_example() {

    // extract simple property values having a java standard type
    assertThat(extractProperty("name", String.class).from(fellowshipOfTheRing)).contains("Boromir", "Gandalf", "Frodo")
                                                                               .doesNotContain("Sauron", "Elrond");

    // same extraction with an alternate syntax
    assertThat(extractProperty("name").ofType(String.class).from(fellowshipOfTheRing)).contains("Boromir", "Gandalf",
                                                                                                "Frodo", "Legolas")
                                                                                      .doesNotContain("Sauron",
                                                                                                      "Elrond");

    // extracting property works also with user's types (here Race)
    assertThat(extractProperty("race").from(fellowshipOfTheRing)).contains(HOBBIT, ELF)
                                                                 .doesNotContain(ORC);

    // extract nested property on Race
    assertThat(extractProperty("race.name").from(fellowshipOfTheRing)).contains("Hobbit", "Elf")
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
  public void iterable_assertions_on_several_extracted_values() {

    // extract 'name' and 'age' values.
    assertThat(fellowshipOfTheRing).extracting("name", "age")
                                   .contains(tuple("Boromir", 37),
                                             tuple("Sam", 38),
                                             tuple("Legolas", 1000));

    // extract 'name', 'age' and Race name values.
    assertThat(fellowshipOfTheRing).extracting("name", "age", "race.name")
                                   .contains(tuple("Boromir", 37, "Man"),
                                             tuple("Sam", 38, "Hobbit"),
                                             tuple("Legolas", 1000, "Elf"));
  }

  @Test
  public void iterable_is_subset_of_assertion_example() {
    Collection<Ring> elvesRings = newArrayList(vilya, nenya, narya);
    assertThat(elvesRings).isSubsetOf(ringsOfPower);
  }

  @Test
  public void iterable_type_safe_assertion_example() {
    // just to show that containsAll can accept subtype of is not bounded to Object only
    Collection<Ring> elvesRings = newArrayList(vilya, nenya, narya);
    Collection<Object> powerfulRings = new ArrayList<Object>();
    powerfulRings.add(oneRing);
    powerfulRings.add(vilya);
    powerfulRings.add(nenya);
    powerfulRings.add(narya);
    assertThat(powerfulRings).containsAll(elvesRings);

  }

  @Test
  public void iterator_assertion_example() {
    // Iterable assertions also works if you give an Iterator as input.
    Iterator<Ring> elvesRings = newArrayList(vilya, nenya, narya).iterator();
    assertThat(elvesRings).isSubsetOf(ringsOfPower);
  }

}
