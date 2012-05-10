package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.*;
import static org.fest.assertions.data.Index.atIndex;
import static org.fest.assertions.examples.data.Ring.*;
import static org.fest.util.Arrays.array;
import static org.fest.util.Collections.list;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

import org.fest.assertions.examples.data.Movie;
import org.fest.assertions.examples.data.Ring;
import org.fest.assertions.examples.data.TolkienCharacter;

/**
 * Array assertions examples.
 * 
 * @author Joel Costigliola
 */
public class ArrayAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void array_assertions_examples() {
    // array assertion are very similar to list assertions
    Ring[] elvesRings = array(vilya, nenya, narya);
    Movie[] trilogy = array(theFellowshipOfTheRing, theTwoTowers, theReturnOfTheKing);
    assertThat(elvesRings).isNotEmpty().hasSize(3);
    assertThat(elvesRings).hasSameSizeAs(trilogy);
    assertThat(elvesRings).hasSameSizeAs(list(trilogy));
    assertThat(elvesRings).contains(nenya).doesNotContain(oneRing);
    // you can check element at a given index (we use Index.atIndex(int) synthetic sugar for better readability).
    assertThat(elvesRings).contains(vilya, atIndex(0)).contains(nenya, atIndex(1)).contains(narya, atIndex(2));
    // with containsOnly, all the elements must be present (but the order is not important)
    assertThat(elvesRings).containsOnly(nenya, vilya, narya);
    assertThat(elvesRings).doesNotContainNull().doesNotHaveDuplicates();
    // special check for null, empty collection or both
    assertThat(list(frodo, null, sam)).containsNull();
    Object[] array = array();
    assertThat(array).isEmpty();
    assertThat(array).isNullOrEmpty();
    array = null;
    assertThat(array).isNullOrEmpty();
    // you can also check the start or end of your collection/iterable
    Ring[] allRings = array(oneRing, vilya, nenya, narya, dwarfRing, manRing);
    assertThat(allRings).startsWith(oneRing, vilya).endsWith(dwarfRing, manRing);
    assertThat(allRings).containsSequence(nenya, narya, dwarfRing);
    // you can check that an array is sorted (new in FEST 2.0)
    TolkienCharacter[] fellowshipOfTheRingArray = fellowshipOfTheRing.toArray(new TolkienCharacter[0]);
    Arrays.sort(fellowshipOfTheRingArray, ageComparator);
    assertThat(fellowshipOfTheRingArray).isSortedAccordingTo(ageComparator);
    assertThat(fellowshipOfTheRingArray).usingComparator(ageComparator).isSorted();
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
              + "element 1:<Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]>.\n"
              + "group was:\n"
              + "<[Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020], "
              + "Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]]>");
    }

    // duplicates assertion honors custom comparator :
    assertThat(fellowshipOfTheRingCharacters).doesNotHaveDuplicates();
    assertThat(array(sam, gandalf)).usingComparator(raceNameComparator).doesNotHaveDuplicates();
    try {
      assertThat(array(sam, gandalf, frodo)).usingComparator(raceNameComparator).doesNotHaveDuplicates();
    } catch (AssertionError e) {
      assertThat(e)
          .hasMessage(
              "found duplicate(s)\n"
                  + "<[Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]]>\n"
                  + " in\n"
                  + "<[Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38], Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020], Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]]>\n"
                  + " according to 'TolkienCharacterRaceNameComparator' comparator");
    }
  }

  // CHANGED IN FEST 2.x, was assertThat(myCollection).onProperty(propertyName).contains...
  @Test
  public void assertions_on_collection_extracted_property_values_example() {
    TolkienCharacter[] fellowshipOfTheRingArray = fellowshipOfTheRing.toArray(new TolkienCharacter[0]);

    // extract simple property value (having a java standard type)
    assertThat(extractProperty("name").from(fellowshipOfTheRingArray)).contains("Boromir", "Gandalf", "Frodo", "Legolas")
        .doesNotContain("Sauron", "Elrond");
    // in Fest 1.x, this would have been written :
    // assertThat(fellowshipOfTheRingArray).onProperty("name").contains("Boromir", "Gandalf", "Frodo", "Legolas");

    // extracting property works also with user's types (here Race)
    assertThat(extractProperty("race").from(fellowshipOfTheRingArray)).contains(HOBBIT, ELF).doesNotContain(ORC);

    // extract nested property on Race
    assertThat(extractProperty("race.name").from(fellowshipOfTheRingArray)).contains("Hobbit", "Elf").doesNotContain("Orc");
  }

  // new in FEST 2.0
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

}
