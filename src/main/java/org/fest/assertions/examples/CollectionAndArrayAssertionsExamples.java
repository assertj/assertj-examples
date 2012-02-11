package org.fest.assertions.examples;

import static java.util.Collections.sort;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.data.Index.atIndex;
import static org.fest.assertions.data.MapEntry.entry;
import static org.fest.assertions.examples.data.Ring.*;
import static org.fest.util.Arrays.array;
import static org.fest.util.Collections.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import org.fest.assertions.examples.data.Ring;
import org.fest.assertions.examples.data.TolkienCharacter;
import org.fest.assertions.internal.PropertySupport;

public class CollectionAndArrayAssertionsExamples extends AbstractAssertionsExamples {

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
  public void assertions_on_arrays_with_custom_comparison_examples() {
    boolean errorHasBeenThrown = false;
    TolkienCharacter[] fellowshipOfTheRingCharacters = fellowshipOfTheRing.toArray(new TolkienCharacter[0]);
    // standard comparison : the fellowshipOfTheRing does not include sauron ...
    assertThat(fellowshipOfTheRingCharacters).doesNotContain(sauron);
    // ... but if we compare only character's race sauron is in fellowshipOfTheRing because he's a maia like Gandalf.
    assertThat(fellowshipOfTheRingCharacters).contains(gandalf);
    assertThat(fellowshipOfTheRingCharacters).usingComparator(raceComparator).contains(sauron);
    assertThat(array(gandalf, sam)).usingComparator(raceComparator).startsWith(sauron);
    assertThat(array(sam, gandalf)).usingComparator(raceComparator).endsWith(sauron);
    assertThat(array(gandalf)).usingComparator(raceComparator).contains(sauron);
    assertThat(array(gandalf)).usingComparator(raceComparator).containsOnly(sauron);

    assertThat(array(sam, gandalf)).isSortedAccordingTo(raceComparator);
    assertThat(array(sam, gandalf)).usingComparator(raceComparator).isSorted();
    errorHasBeenThrown = false;
    try {
      assertThat(array(gandalf, sam)).usingComparator(raceComparator).isSorted();
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "group is not sorted according to 'CharacterRaceComparator' comparator because "
              + "element 0:<Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020]> "
              + "is not less or equal than "
              + "element 1:<Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]>, group was:<["
              + "Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020], "
              + "Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]]>");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();
    //

    // note that error message mentions the comparator used to understand the failure better.
    try {
      assertThat(array(gandalf)).usingComparator(raceComparator).doesNotContain(sauron);
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "expecting:<[Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020]]> not to contain:"
              + "<[Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]]> but found:"
              + "<[Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]]> "
              + "according to 'CharacterRaceComparator' comparator");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();

    // duplicates assertion honors custom comparator :
    assertThat(fellowshipOfTheRingCharacters).doesNotHaveDuplicates();
    assertThat(array(sam, gandalf)).usingComparator(raceComparator).doesNotHaveDuplicates();
    errorHasBeenThrown = false;
    try {
      assertThat(array(sam, gandalf, frodo)).usingComparator(raceComparator).doesNotHaveDuplicates();
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
  public void list_assertions_examples() {
    assertThat(fellowshipOfTheRing).hasSize(9).contains(frodo, sam).doesNotContain(sauron);
    // isOrdered example
    List<TolkienCharacter> fellowshipOfTheRingSortedByAge = new ArrayList<TolkienCharacter>();
    fellowshipOfTheRingSortedByAge.addAll(fellowshipOfTheRing);
    sort(fellowshipOfTheRingSortedByAge, ageComparator);
    assertThat(fellowshipOfTheRingSortedByAge).isSortedAccordingTo(ageComparator);
    // is empty
    fellowshipOfTheRing.clear();
    assertThat(fellowshipOfTheRing).isEmpty();
  }

  @Test
  public void onProperty_usage() { // CHANGED IN FEST 2.x
    // with simple property
    assertThat(PropertySupport.instance().propertyValues("name", fellowshipOfTheRing)).contains("Boromir", "Gandalf",
        "Frodo", "Legolas").doesNotContain("Sauron", "Elrond");
    // with simple property on Race
    assertThat(PropertySupport.instance().propertyValues("race", fellowshipOfTheRing)).contains(HOBBIT, MAN, ELF)
        .doesNotContain(ORC);
    // nested property introspection on Race
    assertThat(PropertySupport.instance().propertyValues("race.name", fellowshipOfTheRing)).contains("Hobbit", "Man",
        "Elf").doesNotContain("Orc");
  }

  @Test
  public void map_assertions_examples() {
    assertThat(ringBearers).hasSize(4).contains(entry(Ring.oneRing, frodo), entry(Ring.nenya, galadriel))
        .doesNotContain(entry(Ring.oneRing, aragorn));
  }

  // new in FEST 2.0
  @Test
  public void is_sorted_assertion() {
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

  // new in FEST 2.0
  @Test
  public void iterables_assertion() {
    Iterable<Ring> elvesRings = list(vilya, nenya, narya);
    assertThat(elvesRings).hasSize(3).contains(nenya).doesNotContain(oneRing);
  }
  
}
