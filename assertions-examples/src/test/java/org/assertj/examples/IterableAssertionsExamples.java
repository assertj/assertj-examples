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

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.assertj.core.api.Assertions.in;
import static org.assertj.core.api.Assertions.not;
import static org.assertj.core.api.Assertions.notIn;
import static org.assertj.core.api.Assertions.setAllowExtractingPrivateFields;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.InstanceOfAssertFactories.STRING;
import static org.assertj.core.util.Lists.list;
import static org.assertj.core.util.Sets.newHashSet;
import static org.assertj.core.util.Sets.newLinkedHashSet;
import static org.assertj.examples.data.Race.ELF;
import static org.assertj.examples.data.Race.HOBBIT;
import static org.assertj.examples.data.Race.MAIA;
import static org.assertj.examples.data.Race.MAN;
import static org.assertj.examples.data.Race.ORC;
import static org.assertj.examples.data.Ring.dwarfRing;
import static org.assertj.examples.data.Ring.manRing;
import static org.assertj.examples.data.Ring.narya;
import static org.assertj.examples.data.Ring.nenya;
import static org.assertj.examples.data.Ring.oneRing;
import static org.assertj.examples.data.Ring.vilya;
import static org.assertj.examples.extractor.BasketballExtractors.teammates;
import static org.assertj.examples.extractor.TolkienCharactersExtractors.age;
import static org.assertj.examples.extractor.TolkienCharactersExtractors.ageAndRace;
import static org.assertj.examples.extractor.TolkienCharactersExtractors.race;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.api.Condition;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.StringAssert;
import org.assertj.core.api.iterable.ThrowingExtractor;
import org.assertj.core.util.introspection.IntrospectionError;
import org.assertj.examples.data.BasketBallPlayer;
import org.assertj.examples.data.Employee;
import org.assertj.examples.data.Race;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.jupiter.api.Test;

/**
 * Iterable (including Collection) assertions examples.<br>
 *
 * @author Joel Costigliola
 */
public class IterableAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void iterable_basic_assertions_examples() {

    List<String> abc = asList("a", "b", "c");
    assertThat(abc).hasSize(3)
                   .containsAnyOf("b")
                   .containsAnyOf("b", "c")
                   .containsAnyOf("a", "b", "c")
                   .containsAnyOf("a", "b", "c", "d")
                   .containsAnyOf("e", "f", "g", "b");

    assertThat(asList("a", "a", "b")).containsOnly("a", "a", "b", "b");

    // would work the same way with Iterable<Ring>,
    Iterable<Ring> elvesRings = list(vilya, nenya, narya);
    assertThat(elvesRings).isNotEmpty().hasSize(3);
    assertThat(elvesRings).hasSameSizeAs(trilogy);
    assertThat(elvesRings).contains(nenya, narya)
                          .doesNotContain(oneRing)
                          .containsAnyOf(nenya, oneRing, dwarfRing);

    // with containsOnly, all the elements must be present (but the order is not important)
    assertThat(elvesRings).containsOnly(nenya, vilya, narya)
                          .containsOnly(vilya, nenya, narya)
                          .isSubsetOf(list(nenya, narya, vilya, nenya))
                          .hasSameElementsAs(list(nenya, narya, vilya, nenya));
    assertThat(elvesRings).doesNotContainNull().doesNotHaveDuplicates();

    Iterable<Ring> duplicatedElvesRings = list(vilya, nenya, narya, vilya, nenya, narya);
    assertThat(elvesRings).hasSameElementsAs(duplicatedElvesRings)
                          .isSubsetOf(duplicatedElvesRings);

    try {
      assertThat(elvesRings)
              .isSubsetOf(list(vilya, nenya, vilya, oneRing))
              .containsOnly(nenya, vilya, oneRing);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnly", e);
    }

    // special check for null, empty collection or both
    assertThat(list(frodo, null, sam)).containsNull();
    List<Object> list = list();
    assertThat(list).isEmpty();
    assertThat(list).contains();
    assertThat(list).isNullOrEmpty();
    list = null;
    assertThat(list).isNullOrEmpty();

    // you can also check the start or end of your collection/iterable
    Iterable<Ring> allRings = list(oneRing, vilya, nenya, narya, dwarfRing, manRing);
    assertThat(allRings).startsWith(oneRing, vilya)
                        .endsWith(dwarfRing, manRing);
    assertThat(allRings).containsSequence(nenya, narya, dwarfRing)
                        .containsSequence(list(nenya, narya, dwarfRing))
                        .containsSubsequence(oneRing, nenya, dwarfRing)
                        .containsSubsequence(newLinkedHashSet(oneRing, nenya, dwarfRing))
                        .doesNotContainSequence(vilya, nenya, oneRing, narya)
                        .doesNotContainSequence(list(vilya, nenya, oneRing, narya));
    assertThat(allRings).containsAll(elvesRings);
    assertThat(elvesRings).containsAnyElementsOf(allRings)
                          .containsAnyOf(oneRing, nenya);

    assertThat(fellowshipOfTheRing).contains(frodo, sam);

    // to show an error message
    try {
      assertThat(elvesRings).containsAll(allRings);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsAll", e);
    }

    List<Integer> testedList = list(1);
    List<Integer> referenceList = list(1, 2, 3);
    assertThat(referenceList).containsSequence(testedList.toArray(new Integer[testedList.size()]));

    List<String> options = list("--option", "a=b", "--option", "c=d");
    assertThat(options).containsSequence("--option", "c=d");
  }

  @Test
  public void single_element_iterable_assertions_examples() {
    List<String> babySimpsons = list("Maggie");

    // assertion succeeds, only Object assertions are available after singleElement()
    assertThat(babySimpsons).singleElement()
                            .isEqualTo("Maggie");

    // String assertion succeeds, String assertions as we have passed InstanceOfAssertFactories.STRING
    assertThat(babySimpsons).singleElement(as(STRING))
                            .startsWith("Mag");
  }

  @Test
  public void iterable_basic_contains_exactly_assertions_examples() {
    Iterable<Ring> elvesRings = list(vilya, nenya, narya);
    assertThat(elvesRings).containsExactly(vilya, nenya, narya)
                          .containsExactlyInAnyOrder(vilya, nenya, narya)
                          .containsExactlyInAnyOrder(nenya, vilya, narya);

    // It works with collections that have a consistent iteration order
    SortedSet<Ring> elvesRingsSet = new TreeSet<>();
    elvesRingsSet.add(vilya);
    elvesRingsSet.add(nenya);
    elvesRingsSet.add(narya);
    assertThat(elvesRingsSet).containsExactly(vilya, nenya, narya);

    // Expected values can be given by another Iterable.
    assertThat(elvesRings).containsExactlyElementsOf(elvesRingsSet);

    try {
      // putting a different order would make the assertion fail :
      assertThat(elvesRings).containsExactly(nenya, vilya, narya);
    } catch (AssertionError e) {
      logger.info(e.getMessage());
      logAssertionErrorMessage("containsExactly", e);
    }

    try {
      List<String> z = list("a", "a", "a");
      assertThat(z).containsExactly("a", "a");
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsExactly with same elements appearing several time", e);
    }

    try {
      // putting a different order would make the assertion fail :
      assertThat(list(narya, vilya, nenya)).containsExactlyElementsOf(elvesRingsSet);
    } catch (AssertionError e) {
      logger.info(e.getMessage());
      logAssertionErrorMessage("containsExactlyElementsOf with elements in different order", e);
    }
  }

  @Test
  public void iterable_assertions_on_extracted_values_example() {

    // extract 'name' property values
    assertThat(fellowshipOfTheRing).extracting("name")
                                   .contains("Boromir", "Gandalf", "Frodo", "Legolas")
                                   .doesNotContain("Sauron", "Elrond");

    // extract 'surname' property values not backed by a field
    assertThat(fellowshipOfTheRing).extracting("surname").contains("Sam the Hobbit");

    // extract 'age' field values
    assertThat(fellowshipOfTheRing).extracting("age").contains(33, 38, 36);

    // extracting works also with user's types (here Race)
    assertThat(fellowshipOfTheRing).extracting("race").contains(HOBBIT, ELF).doesNotContain(ORC);

    // extract nested property values on Race
    assertThat(fellowshipOfTheRing).as("foo")
                                   .extracting("race.name")
                                   .contains("Hobbit", "Elf")
                                   .doesNotContain("Orc");

    // to have type safe extracting, use the second parameter to pass the expected property type:
    assertThat(fellowshipOfTheRing).extracting("name", String.class)
                                   .contains("Boromir", "Gandalf", "Frodo", "Legolas")
                                   .doesNotContain("Sauron", "Elrond");
  }

  public void iterable_assertions_with_external_extracted_values_example() {

    // extract simple property values having a java standard type
    assertThat(extractProperty("name", String.class).from(fellowshipOfTheRing)).contains("Boromir", "Gandalf", "Frodo")
                                                                               .doesNotContain("Sauron", "Elrond");

    // same extraction with an alternate syntax
    assertThat(extractProperty("name").ofType(String.class).from(fellowshipOfTheRing)).contains("Boromir", "Gandalf",
                                                                                                "Frodo", "Legolas")
                                                                                      .doesNotContain("Sauron",
                                                                                                      "Elrond");

    // extracting property works also with user's types (here Race)
    assertThat(extractProperty("race").from(fellowshipOfTheRing)).contains(HOBBIT, ELF).doesNotContain(ORC);

    // extract nested property on Race
    assertThat(extractProperty("race.name").from(fellowshipOfTheRing)).contains("Hobbit", "Elf").doesNotContain("Orc");
  }

  @Test
  public void iterable_assertions_on_extracted_private_fields_values_example() {

    // extract private fields
    assertThat(trilogy).extracting("duration").containsExactly("178 min", "179 min", "201 min");

    // disable private field extraction
    setAllowExtractingPrivateFields(false);

    try {
      assertThat(trilogy).extracting("duration").isNotEmpty();
      failBecauseExceptionWasNotThrown(IntrospectionError.class);
    } catch (Exception ignore) {} finally {
      // back to default value
      setAllowExtractingPrivateFields(true);
    }
  }

  @Test
  public void iterable_assertions_on_several_extracted_values() {

    // extract 'name' and 'age' values.
    assertThat(fellowshipOfTheRing).extracting("name", "age").contains(tuple("Boromir", 37),
                                                                       tuple("Sam", 38),
                                                                       tuple("Legolas", 1000));

    // extract 'name', 'age' and Race name values.
    assertThat(fellowshipOfTheRing).extracting("name", "age", "race.name")
                                   .contains(tuple("Boromir", 37, "Man"),
                                             tuple("Sam", 38, "Hobbit"),
                                             tuple("Legolas", 1000, "Elf"));
    assertThat(fellowshipOfTheRing).extracting(TolkienCharacter::getName,
                                               tolkienCharacter -> tolkienCharacter.age,
                                               tolkienCharacter -> tolkienCharacter.getRace().getName())
                                   .contains(tuple("Boromir", 37, "Man"),
                                             tuple("Sam", 38, "Hobbit"),
                                             tuple("Legolas", 1000, "Elf"));
    // same thing but flatten the extraction
    assertThat(fellowshipOfTheRing).flatExtracting("name", "age", "race.name")
                                   .contains("Boromir", 37, "Man",
                                             "Sam", 38, "Hobbit",
                                             "Legolas", 1000, "Elf");

    // extract 'name', 'age' and Race name values.
    TolkienCharacter unknown = new TolkienCharacter("unknown", 100, null);
    assertThat(list(sam, unknown)).extracting("name", "age", "race.name").contains(tuple("Sam", 38, "Hobbit"),
                                                                                   tuple("unknown", 100, null));

  }

  @Test
  public void narrow_assertions_type_with_asInstanceOf_example() {

    Object hobbits = list(frodo, pippin, merry, sam);

    // As we specify the TolkienCharacter class, the following chained assertion expect to be given TolkienCharacters.
    // This means that method like extracting or filteredOn are given a TolkienCharacter
    assertThat(hobbits).asInstanceOf(InstanceOfAssertFactories.list(TolkienCharacter.class))
                       .contains(frodo, sam)
                       .extracting(TolkienCharacter::getName)
                       .contains("Frodo", "Sam");

    // Use LIST if the elements type is not important but note that the chained assertions
    // will be given Object not TolkienCharacter
    assertThat(hobbits).asInstanceOf(InstanceOfAssertFactories.LIST)
                       // .extracting(TolkienCharacter::getName) does not work as extracting is given an Object
                       .contains(frodo);
  }

  @Test
  public void iterable_is_subset_of_assertion_example() {
    Collection<Ring> elvesRings = list(vilya, nenya, narya);
    assertThat(elvesRings)
            .isSubsetOf(ringsOfPower)
            .isSubsetOf(vilya, nenya, narya);
    try {
      assertThat(elvesRings).isSubsetOf(list(nenya, narya));
    } catch (AssertionError e) {
      logAssertionErrorMessage("isSubsetOf", e);
    }
  }

  @Test
  public void iterable_type_safe_assertion_example() {
    // just to show that containsAll can accept subtype of is not bounded to Object only
    Collection<Ring> elvesRings = list(vilya, nenya, narya);
    Collection<Object> powerfulRings = new ArrayList<>();
    powerfulRings.add(oneRing);
    powerfulRings.add(vilya);
    powerfulRings.add(nenya);
    powerfulRings.add(narya);
    assertThat(powerfulRings).containsAll(elvesRings);
  }

  @Test
  public void iterator_assertion_example() {
    // Iterable assertions also works if you give an Iterator as input.
    Iterator<Ring> elvesRingsIterator = list(vilya, nenya, narya).iterator();
    // elvesRingsIterator is only consumed when needed which is not the case with null/notNull assertion
    assertThat(elvesRingsIterator).isNotNull()
                                  .hasNext();
    // elvesRingsIterator is consumed, to chain assertion we must convert it an Iterable first
    assertThat(elvesRingsIterator).toIterable()
                                  .isSubsetOf(ringsOfPower)
                                  .contains(nenya, narya);
    // elvesRingsIterator is consumed since it was converted to an Iterable
    assertThat(elvesRingsIterator).isExhausted();
  }

  @Test
  public void doesNotContainAnyElementsOf_assertion_example() {
    // this assertion succeed :
    List<String> actual = list("GIT", "CVS", "SOURCESAFE");
    List<String> values = list("git", "cvs", "subversion");
    assertThat(actual).doesNotContainAnyElementsOf(values);

    // This one failed :
    actual = list("GIT", "cvs", "SOURCESAFE");
    values = list("git", "cvs", "subversion");
    try {
      assertThat(actual).doesNotContainAnyElementsOf(values);
    } catch (AssertionError e) {
      logAssertionErrorMessage("doesNotContainAnyElementsOf", e);
    }
  }

  @Test
  public void containsOnlyOnce_assertion_examples() {
    // int
    assertThat(list("Winter", "is", "coming")).containsOnlyOnce("Winter");
    assertThat(list("Winter", "is", "coming")).containsOnlyOnce("coming", "Winter");
    try {
      assertThat(list("Aria", "Stark", "daughter", "of", "Ned", "Stark")).containsOnlyOnce("Stark");
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnlyOnce for Iterable", e);
    }

    try {
      assertThat(list("winter", "is", "coming")).containsOnlyOnce("Lannister");
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnlyOnce for Iterable", e);
    }

    try {
      assertThat(list("Aria", "Stark", "daughter", "of", "Ned", "Stark")).containsOnlyOnce("Stark",
                                                                                           "Lannister", "Aria");
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnlyOnce for Iterable", e);
    }
  }

  @Test
  public void subSequence_assertion_examples() {
    List<String> list = list("Batman", "is", "weaker", "than", "Superman", "but", "he", "is", "less",
                             "annoying");
    assertThat(list).containsSubsequence("Superman", "is", "annoying")
                    .containsSubsequence(list("Superman", "is", "annoying"));

    assertThat(list("Breaking", "objects", "is", "pretty", "bad")).containsSubsequence("Breaking", "bad");
    try {
      assertThat(list("A", "B", "C", "D")).containsSubsequence("B", "A", "C");
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsSubsequence for Iterable", e);
    }

    assertThat(list).containsSubsequence(list("Superman", "is", "annoying"));
    assertThat(list).doesNotContainSubsequence("Superman", "is", "great")
                    .doesNotContainSubsequence(list("Superman", "is", "great"));

    List<String> title = list("A", " ", "Game", " ", "of", " ", "Thrones", " ");
    assertThat(title).containsSubsequence("Game", "Thrones");
  }

  @Test
  public void iterable_assertions_on_extracted_method_result_example() {
    // extract results of calls to 'toString' method
    assertThat(fellowshipOfTheRing).extractingResultOf("toString").contains("Frodo 33 years old Hobbit",
                                                                            "Aragorn 87 years old Man");
  }

  @Test
  public void iterable_assertion_match_example() {
    List<TolkienCharacter> hobbits = list(frodo, sam, pippin, merry);
    assertThat(hobbits).allMatch(character -> character.getRace() == HOBBIT, "hobbits")
                       .anyMatch(character -> character.getName().contains("pp"))
                       .noneMatch(character -> character.getRace() == ORC);
  }

  @Test
  public void iterable_satisfy_assertion_example() {
    List<TolkienCharacter> hobbits = list(frodo, sam, pippin);
    assertThat(hobbits)
            .allSatisfy(character -> {
      assertThat(character.getRace()).isEqualTo(HOBBIT);
      assertThat(character.getName()).isNotEqualTo("Sauron");
    })
            .anySatisfy(character -> {
      assertThat(character.getRace()).isEqualTo(HOBBIT);
      assertThat(character.age).isLessThan(30);
    })
            .noneSatisfy(character -> assertThat(character.getRace()).isEqualTo(ELF));

    // order is important, so frodo must satisfy the first lambda requirements, sam the second and pippin the last
    assertThat(hobbits).satisfiesExactly(character -> assertThat(character.getName()).isEqualTo("Frodo"),
                                         character -> assertThat(character.getName()).isEqualTo("Sam"),
                                         character -> assertThat(character.getName()).startsWith("Pip"))
                       // order is not but all requirements must be met
                       .satisfiesExactlyInAnyOrder(character -> assertThat(character.getName()).isEqualTo("Sam"),
                                                   character -> assertThat(character.getName()).startsWith("Pip"),
                                                   character -> assertThat(character.getName()).isEqualTo("Frodo"));
  }

  @Test
  public void iterable_assertions_comparing_elements_field_by_field_example() {
    // this is useful if elements don't have a good equals method implementation.
    Employee bill = new Employee("Bill", 60, "Micro$oft");
    final List<Employee> micro$oftEmployees = list(bill);
    Employee appleBill = new Employee("Bill", 60, "Apple");

    // this assertion should fail as the company differs but it passes since Employee equals ignores company fields.
    assertThat(micro$oftEmployees).contains(appleBill);

    // let's make the assertion fails by comparing all Employee's fields instead of using equals.
    try {
      assertThat(micro$oftEmployees).usingFieldByFieldElementComparator().contains(appleBill);
    } catch (AssertionError e) {
      logAssertionErrorMessage("contains for Iterable using field by field element comparator", e);
    }
    // if we don't compare company, appleBill is equivalent to bill.
    assertThat(micro$oftEmployees).usingElementComparatorIgnoringFields("company").contains(appleBill);

    // if we compare only name and company, youngBill is equivalent to bill ...
    Employee youngBill = new Employee("Bill", 25, "Micro$oft");
    assertThat(micro$oftEmployees).usingElementComparatorOnFields("company").contains(youngBill);
    // ... but not if we compare only age.
    try {
      assertThat(micro$oftEmployees).usingElementComparatorOnFields("age").contains(youngBill);
    } catch (AssertionError e) {
      logAssertionErrorMessage("contains for Iterable usingElementComparatorOnFields", e);
    }

    // another example with usingElementComparatorOnFields
    TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
    TolkienCharacter sam = new TolkienCharacter("Sam", 38, HOBBIT);

    // frodo and sam both are hobbits, so they are equals when comparing only race ...
    assertThat(list(frodo)).usingElementComparatorOnFields("race").contains(sam);
    assertThat(list(frodo)).usingElementComparatorOnFields("race").isEqualTo(list(sam));
    // ... but not when comparing both name and race
    try {
      assertThat(list(frodo)).usingElementComparatorOnFields("name", "race").contains(sam);
    } catch (AssertionError e) {
      logAssertionErrorMessage("contains for Iterable usingElementComparatorOnFields", e);
    }
  }

  @Test
  public void use_hexadecimal_representation_in_error_messages() {
    final List<Byte> bytes = list((byte) 0x10, (byte) 0x20);
    try {
      assertThat(bytes).inHexadecimal().contains((byte) 0x30);
    } catch (AssertionError e) {
      logAssertionErrorMessage("asHexadecimal for byte list", e);
    }
  }

  @Test
  public void use_binary_representation_in_error_messages() {
    final List<Byte> bytes = list((byte) 0x10, (byte) 0x20);
    try {
      assertThat(bytes).inBinary().contains((byte) 0x30);
    } catch (AssertionError e) {
      logAssertionErrorMessage("asBinary for byte list", e);
    }
  }

  @Test
  public void iterable_assertions_on_extracted_value_examples() throws Exception {
    // extract 'age' field values
    assertThat(fellowshipOfTheRing).extracting(age()).contains(33, 38, 36);

    Object hobbits = list(frodo, pippin, merry, sam);

    assertThat(hobbits).asInstanceOf(InstanceOfAssertFactories.list(TolkienCharacter.class))
                       .contains(frodo, sam)
                       .extracting(TolkienCharacter::getName)
                       .contains("Frodo", "Sam");
    assertThat(hobbits).asInstanceOf(InstanceOfAssertFactories.LIST)
                       .contains(frodo);

    // extracting works also with user's types (here Race)
    assertThat(fellowshipOfTheRing).extracting(race())
                                   .contains(HOBBIT, ELF)
                                   .doesNotContain(ORC);
    assertThat(fellowshipOfTheRing).extracting(TolkienCharacter::getRace)
                                   .contains(HOBBIT, ELF)
                                   .doesNotContain(ORC);
    assertThat(fellowshipOfTheRing).extracting(tc -> tc.getRace().getName())
                                   .contains("Hobbit", "Elf")
                                   .doesNotContain("Orc");

    // extract 'name' and 'age' values.
    assertThat(fellowshipOfTheRing).extracting(ageAndRace()).contains(tuple(33, HOBBIT), tuple(38, HOBBIT),
                                                                      tuple(1000, ELF));
  }

  @Test
  public void iterable_assertions_on_extracted_nested_values_combining_field_and_property() throws Exception {
    // extract 'age' field values
    assertThat(fellowshipOfTheRing).extracting("race.name", "race.immortal")
                                   .contains(tuple("Hobbit", false),
                                             tuple("Maia", true),
                                             tuple("Man", false));
    assertThat(fellowshipOfTheRing).extracting("race.fullname", "race.immortal")
                                   .contains(tuple("Hobbit", false),
                                             tuple("immortal Maia", true),
                                             tuple("Man", false));

  }

  @Test
  public void iterable_assertions_on_flat_extracted_values_examples() throws Exception {
    assertThat(list(noah, james)).flatExtracting(BasketBallPlayer::getTeamMates).contains(wade, rose);
    assertThat(list(noah, james)).flatExtracting(teammates()).contains(wade, rose);
    assertThat(list(noah, james)).flatExtracting("teamMates").contains(wade, rose);

    // extract a list of values, flatten them and use contains assertion
    assertThat(fellowshipOfTheRing).flatExtracting(c -> asList(c.getName(), c.getRace().getName()))
                                   .contains("Hobbit", "Frodo", "Elf", "Legolas");

    // same goal but instead of extracting a list of values, give the list properties/fields to extract :
    assertThat(fellowshipOfTheRing).flatExtracting("name", "race.name")
                                   .contains("Hobbit", "Frodo", "Elf", "Legolas");

    // same goal but specify a list of single value extractors instead of a list extractor :
    assertThat(fellowshipOfTheRing).flatExtracting(TolkienCharacter::getName,
                                                   tc -> tc.getRace().getName())
                                   .contains("Hobbit", "Frodo", "Elf", "Legolas");
    assertThat(fellowshipOfTheRing).flatExtracting(TolkienCharacter::getName,
                                                   tc -> tc.getRace().getName())
                                   .containsSubsequence("Frodo", "Hobbit", "Legolas", "Elf");
  }

  @Test
  public void iterable_assertions_testing_elements_type() throws Exception {
    List<Long> numbers = list(1L, 2L);

    assertThat(numbers)
            .hasOnlyElementsOfType(Number.class)
            .hasOnlyElementsOfType(Long.class);

    List<? extends Object> mixed = list("string", 1L);
    assertThat(mixed)
            .hasAtLeastOneElementOfType(String.class)
            .hasOnlyElementsOfTypes(Long.class, String.class);
  }

  @Test
  public void iterable_fluent_filter_with_examples() {

    assertThat(fellowshipOfTheRing).filteredOn("race", HOBBIT)
                                   .containsOnly(sam, frodo, pippin, merry);
    // same assertion but type safe
    assertThat(fellowshipOfTheRing).filteredOn(TolkienCharacter::getRace, HOBBIT)
                                   .containsOnly(sam, frodo, pippin, merry);

    assertThat(newHashSet(fellowshipOfTheRing)).filteredOn(p -> p.getRace() == HOBBIT)
                                               .containsOnly(sam, frodo, pippin, merry);

    // nested property are supported
    assertThat(fellowshipOfTheRing).filteredOn("race.name", "Man")
                                   .containsOnly(aragorn, boromir);

    // you can apply different comparison
    assertThat(fellowshipOfTheRing).filteredOn("race", notIn(HOBBIT, MAN))
                                   .containsOnly(gandalf, gimli, legolas);

    assertThat(fellowshipOfTheRing).filteredOn("race", in(MAIA, MAN))
                                   .containsOnly(gandalf, boromir, aragorn);

    assertThat(fellowshipOfTheRing).filteredOn("race", not(HOBBIT))
                                   .containsOnly(gandalf, boromir, aragorn, gimli, legolas);

    // you can chain multiple filter criteria
    assertThat(fellowshipOfTheRing).filteredOn("race", MAN)
                                   .filteredOn("name", not("Boromir"))
                                   .containsOnly(aragorn);

    // having(condition) example
    Condition<BasketBallPlayer> potentialMvp = new Condition<BasketBallPlayer>() {
      @Override
      public boolean matches(BasketBallPlayer player) {
        return player.getPointsPerGame() > 20 && (player.getAssistsPerGame() >= 8 || player.getReboundsPerGame() >= 8);
      }
    };
    assertThat(basketBallPlayers).filteredOn(potentialMvp).containsOnly(rose, james, wade);
  }

  @Test
  public void test_issue_245() throws Exception {
    Foo foo1 = new Foo("id", 1);
    foo1._f2 = "foo1";
    Foo foo2 = new Foo("id", 2);
    foo2._f2 = "foo1";
    List<Foo> list1 = list(foo1);
    List<Foo> list2 = list(foo2);
    assertThat(list1).usingElementComparatorOnFields("_f2").isEqualTo(list2);
    assertThat(list1).usingElementComparatorOnFields("id").isEqualTo(list2);
    assertThat(list1).usingElementComparatorIgnoringFields("bar").isEqualTo(list2);
    try {
      assertThat(list1).usingFieldByFieldElementComparator().isEqualTo(list2);
    } catch (AssertionError e) {
      logAssertionErrorMessage("asBinary for byte list", e);
    }
  }

  @Test
  public void test_issue_236() throws Exception {
    List<Foo> list1 = list(new Foo("id", 1));
    List<Foo> list2 = list(new Foo("id", 2));
    assertThat(list1).usingElementComparatorOnFields("id").isEqualTo(list2);
    assertThat(list1).usingElementComparatorIgnoringFields("bar").isEqualTo(list2);
    try {
      assertThat(list1).usingFieldByFieldElementComparator().isEqualTo(list2);
    } catch (AssertionError e) {
      logAssertionErrorMessage("asBinary for byte list", e);
    }
  }

  @Test
  public void display_collection_with_one_element_per_line() throws Exception {
    try {
      assertThat(fellowshipOfTheRing).contains(sauron);
    } catch (AssertionError e) {
      logAssertionErrorMessage("display collection with one element per line", e);
    }
  }

  @Test
  public void test_issue_656() {
    Iterator<String> iterator = new ArrayList<String>().iterator();
    assertThat(iterator).isSameAs(iterator);
  }

  @Test
  public void navigation_with_iterable_examples() {
    Iterable<TolkienCharacter> hobbits = list(frodo, sam, pippin);
    assertThat(hobbits).first().isEqualTo(frodo);
    assertThat(hobbits).element(1).isEqualTo(sam);
    assertThat(hobbits).last().isEqualTo(pippin);

    Iterable<String> hobbitsName = list("frodo", "sam", "pippin");

    assertThat(hobbitsName).first(as(STRING))
                           .startsWith("fro")
                           .endsWith("do");
    assertThat(hobbitsName).element(1, as(STRING))
                           .startsWith("sa")
                           .endsWith("am");
    assertThat(hobbitsName).last(as(STRING))
                           .startsWith("pip")
                           .endsWith("pin");

    assertThat(hobbitsName, StringAssert.class).first()
                                               .startsWith("fro")
                                               .endsWith("do");
    assertThat(hobbitsName, StringAssert.class).element(1).contains("a");
    assertThat(hobbitsName, StringAssert.class).last().endsWith("in");
  }

  @Test
  public void test_navigation_with_list() {
    List<TolkienCharacter> hobbits = list(frodo, sam, pippin);
    assertThat(hobbits).first().isEqualTo(frodo);
    assertThat(hobbits).element(1).isEqualTo(sam);
    assertThat(hobbits).last().isEqualTo(pippin);
  }

  @Test
  public void test_navigable_size_assertions() {
    Iterable<Ring> elvesRings = list(vilya, nenya, narya);

    // assertion will pass:
    assertThat(elvesRings).size()
                          .isGreaterThan(1)
                          .isLessThanOrEqualTo(3)
                          .returnToIterable()
                          .contains(narya)
                          .doesNotContain(oneRing);
  }

  @Test
  public void singleElement_example() {
    Iterable<String> babySimpsons = list("Maggie");

    // only object assertions available
    assertThat(babySimpsons).singleElement()
                            .isEqualTo("Maggie");

    assertThat(babySimpsons, StringAssert.class).singleElement()
                                                .startsWith("Mag");

    assertThat(babySimpsons).singleElement(as(STRING))
                            .endsWith("gie");
  }

  @Test
  public void zipStatisfy_example() {
    Iterable<Ring> elvesRings = list(vilya, nenya, narya);
    Iterable<String> elvesRingsToString = list("vilya", "nenya", "narya");

    assertThat(elvesRings).zipSatisfy(elvesRingsToString,
                                      (ring, desc) -> assertThat(ring).hasToString(desc));
  }

  @Test
  public void should_not_produce_warning_for_varargs_parameter() {
    List<Entry<String, String>> list = new ArrayList<>();
    list.add(Pair.of("A", "B"));
    assertThat(list)
            .containsAnyOf(Pair.of("A", "B"), Pair.of("C", "D"))
            .containsExactly(Pair.of("A", "B"))
            .contains(Pair.of("A", "B"));
  }

  @Test
  public void should_not_forget_assertion_description() {
    try {
      assertThat(fellowshipOfTheRing).as("check hobbits")
                                     .extracting("name")
                                     .contains(sauron);
    } catch (AssertionError error) {
      assertThat(error).hasMessageContaining("check hobbits");
    }
  }

  @Test
  public void doesNotHaveAnyElementsOfTypes_example() {
    List<Number> numbers = new ArrayList<>();
    numbers.add(1);
    numbers.add(2);
    numbers.add(3.0);
    numbers.add(4.1);
    numbers.add(BigDecimal.ONE);

    assertThat(numbers).doesNotHaveAnyElementsOfTypes(Long.class, Float.class);
  }

  public static class Foo {
    private String id;
    private int bar;
    public String _f2;

    public String getId() {
      return id;
    }

    public int getBar() {
      return bar;
    }

    public Foo(String id, int bar) {
      super();
      this.id = id;
      this.bar = bar;
    }

    @Override
    public String toString() {
      return "Foo [id=" + id + ", bar=" + bar + "]";
    }
  }

  @Test
  public void should_extract_properties_from_default_method() {
    // GIVEN
    List<Person> people = asList(new Person());
    // THEN
    assertThat(people).extracting("name").containsOnly("John Doe");
  }

  @Test
  public void noneMatch_example() {
    // GIVEN
    Iterable<String> abcc = list("a", "b", "cc");
    // THEN
    assertThat(abcc).noneMatch(String::isEmpty);
  }

  @Test
  public void anyMatch_example() {
    // GIVEN
    Iterable<String> abcc = list("a", "b", "cc");
    // THEN
    assertThat(abcc).anyMatch(s -> s.length() == 2);
  }

  @Test
  public void filteredOnAssertions_example() {
    // GIVEN
    TolkienCharacter pippin = new TolkienCharacter("Pippin", 28, HOBBIT);
    TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
    TolkienCharacter merry = new TolkienCharacter("Merry", 36, HOBBIT);
    TolkienCharacter sam = new TolkienCharacter("Sam", 38, HOBBIT);

    List<TolkienCharacter> hobbits = list(frodo, sam, merry, pippin);
    // THEN
    assertThat(hobbits).filteredOnAssertions(hobbit -> assertThat(hobbit.age).isLessThan(34))
                       .containsOnly(frodo, pippin);
  }

  @Test
  public void should_allow_extractor_throwing_checked_exceptions() {
    ThrowingExtractor<TolkienCharacter, Race, Exception> nonHobbitRace = tolkienCharacter -> {
      if (tolkienCharacter.getRace() == HOBBIT) throw new Exception("Filthy little hobbites. They stole it from us.");
      return tolkienCharacter.getRace();
    };

    assertThat(list(elrond, aragorn)).extracting(nonHobbitRace)
                                     .containsOnly(ELF, MAN)
                                     .doesNotContain(HOBBIT);

    ThrowingExtractor<TolkienCharacter, Collection<String>, Exception> nameAndRaceExtractor = tolkienCharacter -> {
      if (tolkienCharacter == null) {
        throw new Exception("can't accept null TolkienCharacter");
      }
      return asList(tolkienCharacter.getName(), tolkienCharacter.getRace().getName());
    };

    assertThat(fellowshipOfTheRing).flatExtracting(nameAndRaceExtractor)
                                   .contains("Frodo", "Hobbit", "Elf", "Legolas");

    // raise an Exception as fellowshipOfTheRing contains Mr Frodo !
    // assertThat(fellowshipOfTheRing).extracting(raceButNoHobbit)
    // .contains(HOBBIT, ELF)
    // .doesNotContain(ORC);
  }

  class Person implements DefaultName {

  }

  interface DefaultName {
    default String getName() {
      return "John Doe";
    }
  }

}
