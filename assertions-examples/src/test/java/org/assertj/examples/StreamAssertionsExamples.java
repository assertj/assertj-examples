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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.extractProperty;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.assertj.core.api.Assertions.setAllowExtractingPrivateFields;
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
import static org.assertj.examples.extractor.BasketballExtractors.teammates;
import static org.assertj.examples.extractor.TolkienCharactersExtractors.ageAndRace;
import static org.assertj.examples.extractor.TolkienCharactersExtractors.race;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.assertj.core.util.introspection.IntrospectionError;
import org.assertj.examples.data.Employee;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.jupiter.api.Test;

public class StreamAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void stream_basic_assertions_examples() {

    // would work the same way with Stream<Ring>,
    assertThat(Stream.of(vilya, nenya, narya)).isNotEmpty().hasSize(3);
    assertThat(Stream.of(vilya, nenya, narya)).hasSameSizeAs(trilogy);
    assertThat(Stream.of(vilya, nenya, narya)).contains(nenya).doesNotContain(oneRing);

    // with containsOnly, all the elements must be present (but the order is not important)
    assertThat(Stream.of(vilya, nenya, narya)).containsOnly(nenya, vilya, narya)
                                              .containsOnly(vilya, nenya, narya)
                                              .isSubsetOf(newArrayList(nenya, narya, vilya, nenya))
                                              .hasSameElementsAs(newArrayList(nenya, narya, vilya, nenya));
    assertThat(Stream.of(vilya, nenya, narya)).doesNotContainNull().doesNotHaveDuplicates();

    List<Ring> duplicatedElvesRings = newArrayList(vilya, nenya, narya, vilya, nenya, narya);
    assertThat(Stream.of(vilya, nenya, narya)).hasSameElementsAs(duplicatedElvesRings)
                                              .isSubsetOf(duplicatedElvesRings);

    try {
      assertThat(Stream.of(vilya, nenya, narya)).isSubsetOf(newArrayList(vilya, nenya, vilya, oneRing));
      assertThat(Stream.of(vilya, nenya, narya)).containsOnly(nenya, vilya, oneRing);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnly", e);
    }

    // special check for null, empty collection or both
    assertThat(Stream.of(frodo, null, sam)).containsNull();
    assertThat(Stream.empty()).isEmpty();
    assertThat(Stream.empty()).contains();
    assertThat(Stream.empty()).isNullOrEmpty();
    Stream<String> nullStream = null;
    assertThat(nullStream).isNullOrEmpty();

    // you can also check the start or end of your collection/iterable
    assertThat(Stream.of(oneRing, vilya, nenya, narya, dwarfRing, manRing)).startsWith(oneRing, vilya)
                                                                           .endsWith(dwarfRing, manRing)
                                                                           .containsSequence(nenya, narya, dwarfRing)
                                                                           .containsAll(newArrayList(vilya, nenya,
                                                                                                     narya));

    // to show an error message
    try {
      assertThat(Stream.of(vilya, nenya, narya)).containsAll(newArrayList(oneRing, vilya, nenya, narya, dwarfRing,
                                                                          manRing));
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsAll", e);
    }

    Stream<Integer> testedList = Stream.of(1);
    Stream<Integer> referenceList = Stream.of(1, 2, 3);
    assertThat(referenceList).containsSequence(testedList.toArray(Integer[]::new));

    Stream<String> stream = Stream.of("--option", "a=b", "--option", "c=d");
    assertThat(stream).containsSequence("--option", "c=d");

  }

  @Test
  public void primitive_stream_assertions_examples() {
    assertThat(IntStream.of(1, 2, 3)).isNotNull()
                                     .contains(1)
                                     .anySatisfy(i -> assertThat(i).isLessThan(2));

    assertThat(LongStream.of(0, 1, 2, 3, 4)).hasSize(5)
                                            .containsSequence(1L, 2L, 3L);

    assertThat(DoubleStream.of(1, 2, 3)).isNotNull()
                                        .contains(1.0, 2.0, 3.0)
                                        .allMatch(Double::isFinite);
  }

  @Test
  public void stream_assertions_limitations() {
    // this assertion does not pass as the actual Stream is converted to a List to compare it to the expected value.
    assertThat(catchThrowable(() -> assertThat(Stream.of(1, 2, 3)).isEqualTo(Stream.of(1, 2, 3)))).as("isEqualTo").isNotNull();
    // these succeeds as isEqualTo and isSameAs checks if the instances to compare are the same and there is no need to convert
    // the Stream to a List for that
    // the actual Stream is only converted to a List when the assertions requires to inspect its content!
    Stream<Integer> stream = Stream.of(1, 2, 3);
    assertThat(stream).isEqualTo(stream)
                      .isSameAs(stream);

    LongStream stream2 = LongStream.of(1, 2, 3);
    assertThat(stream2).isEqualTo(stream2)
                       .isSameAs(stream2);
  }

  @Test
  public void stream_basic_contains_exactly_assertions_examples() {
    assertThat(Stream.of(vilya, nenya, narya)).containsExactly(vilya, nenya, narya);

    // It works with collections that have a consistent iteration order
    SortedSet<Ring> elvesRingsSet = new TreeSet<>();
    elvesRingsSet.add(vilya);
    elvesRingsSet.add(nenya);
    elvesRingsSet.add(narya);
    assertThat(elvesRingsSet).containsExactly(vilya, nenya, narya);

    // Expected values can be given by another Stream.
    assertThat(Stream.of(vilya, nenya, narya)).containsExactlyElementsOf(elvesRingsSet);

    try {
      // putting a different order would make the assertion fail :
      assertThat(Stream.of(vilya, nenya, narya)).containsExactly(nenya, vilya, narya);
    } catch (AssertionError e) {
      logger.info(e.getMessage());
      logAssertionErrorMessage("containsExactly", e);
    }

    try {
      Stream<String> z = Stream.of("a", "a", "a");
      assertThat(z).containsExactly("a", "a");
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsExactly with same elements appearing several time", e);
    }

    try {
      // putting a different order would make the assertion fail :
      assertThat(Stream.of(narya, vilya, nenya)).containsExactlyElementsOf(elvesRingsSet);
    } catch (AssertionError e) {
      logger.info(e.getMessage());
      logAssertionErrorMessage("containsExactlyElementsOf with elements in different order", e);
    }
  }

  @Test
  public void stream_assertions_with_custom_comparator_examples() {

    // standard comparison : the fellowshipOfTheRing includes Gandalf but not Sauron ...
    assertThat(fellowshipOfTheRing).contains(gandalf).doesNotContain(sauron);
    // ... but if we compare only race name Sauron is in fellowshipOfTheRing because he's a Maia like Gandalf.
    assertThat(fellowshipOfTheRing).usingElementComparator(raceNameComparator).contains(sauron);

    // note that error message mentions the comparator used to better understand the failure
    // the message indicates that Sauron were found because he is a Maia like Gandalf.
    try {
      assertThat(Stream.of(gandalf, sam)).usingElementComparator(raceNameComparator).doesNotContain(sauron);
    } catch (AssertionError e) {
      logAssertionErrorMessage("doesNotContain with custom element comparator", e);
    }

    // duplicates assertion honors custom comparator
    assertThat(fellowshipOfTheRing).doesNotHaveDuplicates();
    assertThat(Stream.of(sam, gandalf)).usingElementComparator(raceNameComparator)
                                       .doesNotHaveDuplicates()
                                       .isEqualTo(newArrayList(frodo, gandalf));
    try {
      assertThat(Stream.of(sam, gandalf, frodo)).usingElementComparator(raceNameComparator).doesNotHaveDuplicates();
    } catch (AssertionError e) {
      logAssertionErrorMessage("doesNotHaveDuplicates with custom element comparator", e);
    }
  }

  @Test
  public void stream_assertions_on_extracted_values_example() {

    // extract 'name' property values
    assertThat(fellowshipOfTheRing).extracting("name").contains("Boromir", "Gandalf", "Frodo", "Legolas")
                                   .doesNotContain("Sauron", "Elrond");

    // extract 'age' field values
    assertThat(fellowshipOfTheRing).extracting("age").contains(33, 38, 36);

    // extracting works also with user's types (here Race)
    assertThat(fellowshipOfTheRing).extracting("race").contains(HOBBIT, ELF).doesNotContain(ORC);

    // extract nested property values on Race
    assertThat(fellowshipOfTheRing).extracting("race.name").contains("Hobbit", "Elf").doesNotContain("Orc");

    // same assertions but extracting properties fluently done outside assertions

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
  public void stream_assertions_on_extracted_private_fields_values_example() {

    // extract private fields
    assertThat(trilogy).extracting("duration").containsExactly("178 min", "179 min", "201 min");

    // disable private field extraction
    setAllowExtractingPrivateFields(false);

    try {
      assertThat(trilogy).extracting("duration");
      failBecauseExceptionWasNotThrown(IntrospectionError.class);
    } catch (Exception ignore) {} finally {
      // back to default value
      setAllowExtractingPrivateFields(true);
    }
  }

  @Test
  public void stream_assertions_on_several_extracted_values() {

    // extract 'name' and 'age' values.
    assertThat(fellowshipOfTheRing).extracting("name", "age").contains(tuple("Boromir", 37), tuple("Sam", 38),
                                                                       tuple("Legolas", 1000));

    // extract 'name', 'age' and Race name values.
    assertThat(fellowshipOfTheRing).extracting("name", "age", "race.name").contains(tuple("Boromir", 37, "Man"),
                                                                                    tuple("Sam", 38, "Hobbit"),
                                                                                    tuple("Legolas", 1000, "Elf"));
    // extract 'name', 'age' and Race name values.
    TolkienCharacter unknown = new TolkienCharacter("unknown", 100, null);
    assertThat(Stream.of(sam, unknown)).extracting("name", "age", "race.name").contains(tuple("Sam", 38, "Hobbit"),
                                                                                        tuple("unknown", 100, null));
  }

  @Test
  public void stream_is_subset_of_assertion_example() {
    assertThat(Stream.of(vilya, nenya, narya)).isSubsetOf(ringsOfPower);
    try {
      assertThat(Stream.of(vilya, nenya, narya)).isSubsetOf(newArrayList(nenya, narya));
    } catch (AssertionError e) {
      logAssertionErrorMessage("isSubsetOf", e);
    }
  }

  @Test
  public void stream_type_safe_assertion_example() {
    // just to show that containsAll can accept subtype of is not bounded to Object only
    List<Ring> elvesRings = newArrayList(vilya, nenya, narya);
    Stream<Object> powerfulRings = Stream.of(oneRing, vilya, nenya, narya);
    assertThat(powerfulRings).containsAll(elvesRings);
  }

  @Test
  public void iterator_assertion_example() {
    // Stream assertions also works if you give an Iterator as input.
    Iterator<Ring> elvesRingsIterator = Stream.of(vilya, nenya, narya).iterator();
    // elvesRingsIterator is only consumed when needed which is not the case with null/notNull assertion
    assertThat(elvesRingsIterator).isNotNull();
    assertThat(elvesRingsIterator.hasNext()).as("iterator is not consumed").isTrue();
    // elvesRingsIterator is consumed when needed but only once, you can then chain assertion
    assertThat(elvesRingsIterator).toIterable().isSubsetOf(ringsOfPower).contains(nenya, narya);
    // elvesRingsIterator is consumed ...
    assertThat(elvesRingsIterator.hasNext()).as("iterator is consumed").isFalse();
  }

  @Test
  public void doesNotContainAnyElementsOf_assertion_example() {
    // this assertion succeed :
    Stream<String> actual = Stream.of("GIT", "CVS", "SOURCESAFE");
    List<String> values = newArrayList("git", "cvs", "subversion");
    assertThat(actual).doesNotContainAnyElementsOf(values);

    // This one failed :
    actual = Stream.of("GIT", "cvs", "SOURCESAFE");
    values = newArrayList("git", "cvs", "subversion");
    try {
      assertThat(actual).doesNotContainAnyElementsOf(values);
    } catch (AssertionError e) {
      logAssertionErrorMessage("doesNotContainAnyElementsOf", e);
    }
  }

  @Test
  public void containsOnlyOnce_assertion_examples() {
    // int
    assertThat(Stream.of("Winter", "is", "coming")).containsOnlyOnce("Winter");
    assertThat(Stream.of("Winter", "is", "coming")).containsOnlyOnce("coming", "Winter");
    try {
      assertThat(Stream.of("Aria", "Stark", "daughter", "of", "Ned", "Stark")).containsOnlyOnce("Stark");
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnlyOnce for Stream", e);
    }

    try {
      assertThat(Stream.of("winter", "is", "coming")).containsOnlyOnce("Lannister");
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnlyOnce for Stream", e);
    }

    try {
      assertThat(Stream.of("Aria", "Stark", "daughter", "of", "Ned", "Stark")).containsOnlyOnce("Stark",
                                                                                                "Lannister",
                                                                                                "Aria");
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnlyOnce for Stream", e);
    }
  }

  @Test
  public void containsSubSequence_assertion_examples() {
    assertThat(Stream.of("Batman", "is", "weaker", "than", "Superman", "but", "he", "is", "less",
                         "annoying")).containsSubsequence("Superman", "is", "annoying");
    assertThat(Stream.of("Breaking", "objects", "is", "pretty", "bad")).containsSubsequence("Breaking", "bad");
    try {
      assertThat(Stream.of("A", "B", "C", "D")).containsSubsequence("B", "A", "C");
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsSubsequence for Stream", e);
    }
  }

  @Test
  public void stream_assertions_on_extracted_method_result_example() {
    // extract results of calls to 'toString' method
    assertThat(fellowshipOfTheRing).extractingResultOf("toString").contains("Frodo 33 years old Hobbit",
                                                                            "Aragorn 87 years old Man");
  }

  @Test
  public void stream_assertions_comparing_elements_field_by_field_example() {
    // this is useful if elements don't have a good equals method implementation.
    Employee bill = new Employee("Bill", 60, "Micro$oft");
    Employee appleBill = new Employee("Bill", 60, "Apple");

    // this assertion should fail as the company differs but it passes since Employee equals ignores company fields.
    assertThat(Stream.of(bill)).contains(appleBill);

    // let's make the assertion fails by comparing all Employee's fields instead of using equals.
    try {
      assertThat(Stream.of(bill)).usingFieldByFieldElementComparator().contains(appleBill);
    } catch (AssertionError e) {
      logAssertionErrorMessage("contains for Stream using field by field element comparator", e);
    }
    // if we don't compare company, appleBill is equivalent to bill.
    assertThat(Stream.of(bill)).usingElementComparatorIgnoringFields("company").contains(appleBill);

    // if we compare only name and company, youngBill is equivalent to bill ...
    Employee youngBill = new Employee("Bill", 25, "Micro$oft");
    assertThat(Stream.of(bill)).usingElementComparatorOnFields("company").contains(youngBill);
    // ... but not if we compare only age.
    try {
      assertThat(Stream.of(bill)).usingElementComparatorOnFields("age").contains(youngBill);
    } catch (AssertionError e) {
      logAssertionErrorMessage("contains for Stream usingElementComparatorOnFields", e);
    }

    // another example with usingElementComparatorOnFields
    TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
    TolkienCharacter sam = new TolkienCharacter("Sam", 38, HOBBIT);

    // frodo and sam both are hobbits, so they are equals when comparing only race ...
    assertThat(Stream.of(frodo)).usingElementComparatorOnFields("race").contains(sam);
    // ... but not when comparing both name and race
    try {
      assertThat(Stream.of(frodo)).usingElementComparatorOnFields("name", "race").contains(sam);
    } catch (AssertionError e) {
      logAssertionErrorMessage("contains for Stream usingElementComparatorOnFields", e);
    }
  }

  @Test
  public void use_hexadecimal_representation_in_error_messages() {
    final Stream<Byte> bytes = Stream.of((byte) 0x10, (byte) 0x20);
    try {
      assertThat(bytes).inHexadecimal().contains((byte) 0x30);
    } catch (AssertionError e) {
      logAssertionErrorMessage("asHexadecimal for byte stream", e);
    }
  }

  @Test
  public void use_binary_representation_in_error_messages() {
    final Stream<Byte> bytes = Stream.of((byte) 0x10, (byte) 0x20);
    try {
      assertThat(bytes).inBinary().contains((byte) 0x30);
    } catch (AssertionError e) {
      logAssertionErrorMessage("asBinary for byte stream", e);
    }
  }

  @Test
  public void stream_assertions_on_extracted_value_examples() throws Exception {
    // extract 'age' field values
    assertThat(fellowshipOfTheRing.stream()).contains(frodo)
                                            .doesNotContain(sauron);

    // extracting works also with user's types (here Race)
    assertThat(fellowshipOfTheRing.stream()).extracting(race())
                                            .contains(HOBBIT, ELF).doesNotContain(ORC);
    assertThat(fellowshipOfTheRing.stream()).extracting(TolkienCharacter::getRace)
                                            .contains(HOBBIT, ELF).doesNotContain(ORC);
    assertThat(fellowshipOfTheRing.stream()).extracting(tc -> tc.getRace().getName())
                                            .contains("Hobbit", "Elf")
                                            .doesNotContain("Orc");

    // extract 'name' and 'age' values.
    assertThat(fellowshipOfTheRing).extracting(ageAndRace()).contains(tuple(33, HOBBIT), tuple(38, HOBBIT),
                                                                      tuple(1000, ELF));
  }

  @Test
  public void stream_assertions_on_flat_extracted_values_examples() throws Exception {
    assertThat(Stream.of(noah, james)).flatExtracting(teammates()).contains(wade, rose);
  }

  @Test
  public void stream_assertions_testing_elements_type() throws Exception {
    assertThat(Stream.of(1L, 2L)).hasOnlyElementsOfType(Number.class);
    assertThat(Stream.of(1L, 2L)).hasOnlyElementsOfType(Long.class);

    Stream<Object> mixed = Stream.of("string", 1L);
    assertThat(mixed).hasAtLeastOneElementOfType(String.class);
  }

  @Test
  public void test_issue_245() throws Exception {
    Foo foo1 = new Foo("id", 1);
    foo1._f2 = "foo1";
    Foo foo2 = new Foo("id", 2);
    foo2._f2 = "foo1";
    List<Foo> stream2 = newArrayList(foo2);
    assertThat(Stream.of(foo1)).usingElementComparatorOnFields("_f2").isEqualTo(stream2);
    assertThat(Stream.of(foo1)).usingElementComparatorOnFields("id").isEqualTo(stream2);
    assertThat(Stream.of(foo1)).usingElementComparatorIgnoringFields("bar").isEqualTo(stream2);
    try {
      assertThat(Stream.of(foo1)).usingFieldByFieldElementComparator().isEqualTo(stream2);
    } catch (AssertionError e) {
      logAssertionErrorMessage("asBinary for byte stream", e);
    }
  }

  @Test
  public void test_issue_236() throws Exception {
    List<Foo> stream2 = newArrayList(new Foo("id", 2));
    assertThat(Stream.of(new Foo("id", 1))).usingElementComparatorOnFields("id").isEqualTo(stream2);
    assertThat(Stream.of(new Foo("id", 1))).usingElementComparatorIgnoringFields("bar").isEqualTo(stream2);
    try {
      assertThat(Stream.of(new Foo("id", 1))).usingFieldByFieldElementComparator().isEqualTo(stream2);
    } catch (AssertionError e) {
      logAssertionErrorMessage("asBinary for byte stream", e);
    }
  }

  @Test
  public void iterable_assertions_on_flat_extracted_values_examples() throws Exception {
    assertThat(newArrayList(noah, james).stream()).flatExtracting(teammates()).contains(wade, rose);
    assertThat(newArrayList(noah, james).stream()).flatExtracting("teamMates").contains(wade, rose);

    // extract a list of values, flatten them and use contains assertion
    assertThat(fellowshipOfTheRing.stream()).flatExtracting(c -> asList(c.getName(), c.getRace().getName()))
                                            .contains("Hobbit", "Frodo", "Elf", "Legolas");

    // same goal but instead of extracting a list of values, give the list properties/fields to extract :
    assertThat(fellowshipOfTheRing.stream()).flatExtracting("name", "race.name")
                                            .contains("Hobbit", "Frodo", "Elf", "Legolas");

    // same goal but specify a list of single value extractors instead of a list extractor :
    assertThat(fellowshipOfTheRing.stream()).flatExtracting(TolkienCharacter::getName,
                                                            tc -> tc.getRace().getName())
                                            .contains("Hobbit", "Frodo", "Elf", "Legolas");

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

}
