package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;
import static org.fest.assertions.examples.data.Ring.dwarfRing;
import static org.fest.assertions.examples.data.Ring.manRing;
import static org.fest.assertions.examples.data.Ring.narya;
import static org.fest.assertions.examples.data.Ring.nenya;
import static org.fest.assertions.examples.data.Ring.oneRing;
import static org.fest.assertions.examples.data.Ring.vilya;
import static org.fest.util.Collections.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import org.fest.assertions.examples.data.Movie;
import org.fest.assertions.examples.data.Ring;

/**
 * Iterable (including Collection) assertions examples.<br>
 * Iterable has been introduces in <b>FEST 2.0</b> (before 2.0 version, only Collection assertions were available).
 * 
 * @author Joel Costigliola
 */
public class IterableAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void iterable_basic_assertions_examples() {

    // would work the same way with Iterable<Ring>,
    Iterable<Ring> elvesRings = list(vilya, nenya, narya);
    Iterable<Movie> trilogy = list(theFellowshipOfTheRing, theTwoTowers, theReturnOfTheKing);
    assertThat(elvesRings).isNotEmpty().hasSize(3);
    assertThat(elvesRings).hasSameSizeAs(trilogy);
    assertThat(elvesRings).contains(nenya).doesNotContain(oneRing);

    // with containsOnly, all the elements must be present (but the order is not important)
    assertThat(elvesRings).containsOnly(nenya, vilya, narya);
    assertThat(elvesRings).doesNotContainNull().doesNotHaveDuplicates();

    // special check for null, empty collection or both
    assertThat(list(frodo, null, sam)).containsNull();
    List<Object> list = list();
    assertThat(list).isEmpty();
    assertThat(list).isNullOrEmpty();
    list = null;
    assertThat(list).isNullOrEmpty();

    // you can also check the start or end of your collection/iterable
    Iterable<Ring> allRings = list(oneRing, vilya, nenya, narya, dwarfRing, manRing);
    assertThat(allRings).startsWith(oneRing, vilya).endsWith(dwarfRing, manRing);
    assertThat(allRings).containsSequence(nenya, narya, dwarfRing);
    assertThat(allRings).containsAll(elvesRings);
    
    // You can check exactly what list contains, i.e. what elements and in which order.
    assertThat(elvesRings).containsExactly(vilya, nenya, narya);
    try {
      // putting a different would make the assertion fail :
      assertThat(elvesRings).containsExactly(nenya, vilya, narya);
    } catch (AssertionError e) {
      assertThat(e).hasMessage("expected:<[[nenya, vil]ya, narya]> but was:<[[vilya, nen]ya, narya]>");
    }

    // to show an error message
    // assertThat(elvesRings).containsAll(allRings);

    List<Integer> testedList = list(1);
    List<Integer> referenceList = list(1, 2, 3);
    assertThat(referenceList).containsSequence(testedList.toArray(new Integer[0]));
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
      assertThat(list(gandalf, sam)).usingElementComparator(raceNameComparator).doesNotContain(sauron);
    } catch (AssertionError e) {
      assertThat(e)
          .hasMessage(
              "expecting\n"
                  + "<[Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020], Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]]>\n"
                  + " not to contain\n"
                  + "<[Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]]>\n"
                  + " but found\n"
                  + "<[Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]]>\n"
                  + " according to 'TolkienCharacterRaceNameComparator' comparator");
    }

    // duplicates assertion honors custom comparator
    assertThat(fellowshipOfTheRing).doesNotHaveDuplicates();
    assertThat(list(sam, gandalf)).usingElementComparator(raceNameComparator).doesNotHaveDuplicates();
    try {
      assertThat(list(sam, gandalf, frodo)).usingElementComparator(raceNameComparator).doesNotHaveDuplicates();
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
  public void iterable_assertions_on_extracted_property_values_example() {

    // extract simple property values having a java standard type
    assertThat(extractProperty("name", String.class).from(fellowshipOfTheRing))
        .contains("Boromir", "Gandalf", "Frodo", "Legolas")
        .doesNotContain("Sauron", "Elrond");
    // in Fest 1.x, this would have been written :
    // assertThat(fellowshipOfTheRing).onProperty("name").contains("Boromir", "Gandalf", "Frodo", "Legolas");
    
    // same extraction with an alternate syntax
    assertThat(extractProperty("name").ofType(String.class).from(fellowshipOfTheRing))
        .contains("Boromir", "Gandalf", "Frodo", "Legolas")
        .doesNotContain("Sauron", "Elrond");

    // extracting property works also with user's types (here Race)
    assertThat(extractProperty("race").from(fellowshipOfTheRing)).contains(HOBBIT, ELF).doesNotContain(ORC);

    // extract nested property on Race
    assertThat(extractProperty("race.name").from(fellowshipOfTheRing)).contains("Hobbit", "Elf").doesNotContain("Orc");
  }

  // new in FEST 2.0
  @Test
  public void iterable_is_subset_of_assertion_example() {
    Collection<Ring> elvesRings = list(vilya, nenya, narya);
    assertThat(elvesRings).isSubsetOf(ringsOfPower);
  }

  @Test
  public void iterable_type_safe_assertion_example() {
    // just to show that containsAll can accept subtype of is not bounded to Object only
    Collection<Ring> elvesRings = list(vilya, nenya, narya);
    Collection<Object> powerfulRings = new ArrayList<Object>();
    powerfulRings.add(oneRing);
    powerfulRings.add(vilya);
    powerfulRings.add(nenya);
    powerfulRings.add(narya);
    assertThat(powerfulRings).containsAll(elvesRings);

  }

}
