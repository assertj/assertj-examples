package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.atIndex;
import static org.fest.assertions.examples.data.Ring.dwarfRing;
import static org.fest.assertions.examples.data.Ring.manRing;
import static org.fest.assertions.examples.data.Ring.narya;
import static org.fest.assertions.examples.data.Ring.nenya;
import static org.fest.assertions.examples.data.Ring.oneRing;
import static org.fest.assertions.examples.data.Ring.vilya;
import static org.fest.util.Collections.list;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import org.fest.assertions.examples.data.Ring;

/**
 * Assertions examples specific to {@link List}.
 * 
 * @author Joel Costigliola
 */
public class ListSpecificAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void list_contains_at_index_assertions_examples() {
    // You can check element at a given index (we use Assertions.atIndex(int) synthetic sugar for better readability).
    List<Ring> elvesRings = list(vilya, nenya, narya);
    assertThat(elvesRings).contains(vilya, atIndex(0)).contains(nenya, atIndex(1)).contains(narya, atIndex(2));
  }

  // new in FEST 2.0
  @Test
  public void list_is_sorted_assertion_example() {

    // You can check that a list is sorted (new in FEST 2.0)
    Collections.sort(fellowshipOfTheRing, ageComparator);
    assertThat(fellowshipOfTheRing).isSortedAccordingTo(ageComparator);
    assertThat(fellowshipOfTheRing).usingElementComparator(ageComparator).isSorted();

    // enum order = order of declaration = ring power
    assertThat(list(oneRing, vilya, nenya, narya, dwarfRing, manRing)).isSorted();

    // ring comparison by increasing power
    Comparator<Ring> increasingPowerRingComparator = new Comparator<Ring>() {
      public int compare(Ring ring1, Ring ring2) {
        return -ring1.compareTo(ring2);
      }
    };
    assertThat(list(manRing, dwarfRing, narya, nenya, vilya, oneRing)).isSortedAccordingTo(
        increasingPowerRingComparator);
  }

  @Test
  public void list_element_satisfies_condition_at_index_example() {
    // You can check that a list element satisfies a condition
    assertThat(list(rose, noah)).has(doubleDoubleStats, atIndex(1));
    assertThat(list(rose, noah)).is(potentialMvp, atIndex(0));
  }

  // new in FEST 2.0
  @Test
  public void iterable_is_subset_of_assertion_example() {
    Collection<Ring> elvesRings = list(vilya, nenya, narya);
    assertThat(elvesRings).isSubsetOf(ringsOfPower);
  }

}
