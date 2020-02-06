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
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.util.Lists.list;
import static org.assertj.examples.data.Ring.dwarfRing;
import static org.assertj.examples.data.Ring.manRing;
import static org.assertj.examples.data.Ring.narya;
import static org.assertj.examples.data.Ring.nenya;
import static org.assertj.examples.data.Ring.oneRing;
import static org.assertj.examples.data.Ring.vilya;

import java.awt.Rectangle;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.jupiter.api.Test;

/**
 * Assertions examples specific to {@link List}.
 *
 * @author Joel Costigliola
 */
public class ListSpecificAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void newArrayList_contains_at_index_assertions_examples() {
    // You can check element at a given index (we use Assertions.atIndex(int) synthetic sugar for better readability).
    List<Ring> elvesRings = list(vilya, nenya, narya);
    assertThat(elvesRings).contains(vilya, atIndex(0))
                          .contains(nenya, atIndex(1))
                          .contains(narya, atIndex(2));
  }

  @Test
  public void newArrayList_is_sorted_assertion_example() {

    // You can check that a list is sorted
    Collections.sort(fellowshipOfTheRing, ageComparator);
    assertThat(fellowshipOfTheRing).contains(frodo)
                                   .isSortedAccordingTo(ageComparator);
    assertThat(fellowshipOfTheRing).usingElementComparator(ageComparator)
                                   .isSorted();

    // You can use iterable assertion and after that list specific ones
    assertThat(fellowshipOfTheRing).contains(frodo)
                                   .contains(frodo, atIndex(1))
                                   .extracting(TolkienCharacter::getName)
                                   .contains(frodo.getName());

    // enum order = order of declaration = ring power
    assertThat(list(oneRing, vilya, nenya, narya, dwarfRing, manRing)).isSorted();

    // ring comparison by increasing power
    Comparator<Ring> increasingPowerRingComparator = new Comparator<Ring>() {
      @Override
      public int compare(Ring ring1, Ring ring2) {
        return -ring1.compareTo(ring2);
      }
    };
    assertThat(list(manRing, dwarfRing, narya, nenya, vilya, oneRing)).isSortedAccordingTo(increasingPowerRingComparator);
  }

  @Test
  public void newArrayList_element_satisfies_condition_at_index_example() {
    // You can check that a list element satisfies a condition
    assertThat(list(rose, noah)).has(doubleDoubleStats, atIndex(1));
    assertThat(list(rose, noah)).is(potentialMvp, atIndex(0));
  }

  @Test
  public void iterable_is_subset_of_assertion_example() {
    Collection<Ring> elvesRings = list(vilya, nenya, narya);
    assertThat(elvesRings).isSubsetOf(ringsOfPower);
  }

  @Test
  public void switch_to_List_assertion() {
    Object elvesRings = list(nenya, vilya, narya);
    assertThat(elvesRings).asList().contains(nenya, atIndex(0));
  }

  @Test
  public void containsOnlyOnce_assertion_should_not_require_objects_to_be_comparable() {
    // Rectangles are not Comparable.
    Rectangle r0 = new Rectangle(0, 0);
    Rectangle r1 = new Rectangle(1, 1);
    Rectangle r2 = new Rectangle(2, 2);
    assertThat(list(r1, r2, r2)).containsOnlyOnce(r1);
    try {
      assertThat(list(r1, r2, r2)).containsOnlyOnce(r0, r1, r2);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnlyOnce", e);
    }
  }

}
