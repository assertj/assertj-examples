package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.*;
import static org.fest.assertions.examples.data.Ring.*;
import static org.fest.util.Collections.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import org.fest.assertions.examples.data.Employee;
import org.fest.assertions.examples.data.Movie;
import org.fest.assertions.examples.data.Person;
import org.fest.assertions.examples.data.Ring;

/**
 * Iterable (including Collection) assertions examples.<br>
 * Iterable has been introduces in <b>FEST 2.0</b> (before 2.0 version, only Collection assertions were available).
 * 
 * @author Joel Costigliola
 */
public class CollectionAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void collection_and_iterable_assertion_examples() {

    // would work the same way with Iterable<Ring>,
    Collection<Ring> elvesRings = list(vilya, nenya, narya);
    Collection<Movie> trilogy = list(theFellowshipOfTheRing, theTwoTowers, theReturnOfTheKing);
    assertThat(elvesRings).isNotEmpty().hasSize(3);
    assertThat(elvesRings).hasSameSizeAs(trilogy);
    assertThat(elvesRings).hasSameSizeAs(trilogy.toArray());
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

    // to show an error message
    // assertThat(elvesRings).containsAll(allRings);

    List<Integer> testedList = list(1);
    List<Integer> referenceList = list(1, 2, 3);
    assertThat(referenceList).containsSequence(testedList.toArray(new Integer[0]));
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

  @Test
  public void list_specific_assertions_examples() {
    // list assertions inherits from collection/iterable assertions but offers more.

    // You can check that a list is sorted (new in FEST 2.0)
    Collections.sort(fellowshipOfTheRing, ageComparator);
    assertThat(fellowshipOfTheRing).isSortedAccordingTo(ageComparator);
    assertThat(fellowshipOfTheRing).usingElementComparator(ageComparator).isSorted();

    // You can check element at a given index (we use Assertions.atIndex(int) synthetic sugar for better readability).
    List<Ring> elvesRings = list(vilya, nenya, narya);
    assertThat(elvesRings).contains(vilya, atIndex(0)).contains(nenya, atIndex(1)).contains(narya, atIndex(2));
  }

  // CHANGED IN FEST 2.x, was assertThat(myCollection).onProperty(propertyName).contains...
  @Test
  public void collection_assertions_on_extracted_property_values_example() {

    // extract simple property values having a java standard type
    assertThat(extractProperty("name", String.class).from(fellowshipOfTheRing))
        .contains("Boromir", "Gandalf", "Frodo", "Legolas")
        .doesNotContain("Sauron", "Elrond");
    // in Fest 1.x, this would have been written :
    // assertThat(fellowshipOfTheRing).onProperty("name").contains("Boromir", "Gandalf", "Frodo", "Legolas");

    // extracting property works also with user's types (here Race)
    assertThat(extractProperty("race").from(fellowshipOfTheRing)).contains(HOBBIT, ELF).doesNotContain(ORC);

    // extract nested property on Race
    assertThat(extractProperty("race.name").from(fellowshipOfTheRing)).contains("Hobbit", "Elf").doesNotContain("Orc");
  }

  // new in FEST 2.0
  @Test
  public void collection_is_sorted_assertion_example() {

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

  // new in FEST 2.0
  @Test
  public void is_subset_of_assertion_example() {
    Collection<Ring> elvesRings = list(vilya, nenya, narya);
    assertThat(elvesRings).isSubsetOf(ringsOfPower);
  }

  @Test
  public void type_safe_assertion_example() {
    // just to show that containsAll can accept subtype of is not bounded to Object only
    Collection<Ring> elvesRings = list(vilya, nenya, narya);
    Collection<Object> powerfulRings = new ArrayList<Object>();
    powerfulRings.add(oneRing);
    powerfulRings.add(vilya);
    powerfulRings.add(nenya);
    powerfulRings.add(narya);
    assertThat(powerfulRings).containsAll(elvesRings);
    
    
    Person luke = new Employee("Luke", 30, "Jedi"); 
    Employee yoda = new Employee("Yoda", 500, "Jedi Master");
    Collection<Person> persons = new ArrayList<Person>();
    persons.add(luke);
    persons.add(yoda);
    Collection<Employee> employees = new ArrayList<Employee>();
    employees.add(yoda);
    assertThat(persons).containsAll(employees);
    
  }
  
}
