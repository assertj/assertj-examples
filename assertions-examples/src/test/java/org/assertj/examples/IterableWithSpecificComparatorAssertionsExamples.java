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
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.examples.data.Race.HOBBIT;

import java.util.Comparator;
import java.util.List;

import org.assertj.examples.comparator.AtPrecisionComparator;
import org.assertj.examples.data.Employee;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.Test;

/**
 * Iterable (including Collection) assertions examples.<br>
 *
 * @author Joel Costigliola
 */
public class IterableWithSpecificComparatorAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void iterable_assertions_with_element_comparator_examples() {

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
    assertThat(newArrayList(sam, gandalf)).usingElementComparator(raceNameComparator)
                                          .doesNotHaveDuplicates()
                                          .isEqualTo(newArrayList(frodo, gandalf));
    try {
      assertThat(newArrayList(sam, gandalf, frodo)).usingElementComparator(raceNameComparator).doesNotHaveDuplicates();
    } catch (AssertionError e) {
      logAssertionErrorMessage("doesNotHaveDuplicates with custom element comparator", e);
    }
  }

  public static class Dude {
    String name;
    double height;
    Dude friend;

    Dude(String name, double height) {
      this.name = name;
      this.height = height;
    }

    @Override
    public String toString() {
      return String.format("Dude [name=%s, height=%s]", name, height);
    }

  }

  @Test
  public void iterable_assertions_with_specific_comparator_by_element_field_or_type_examples() {

    Dude dude = new Dude("Frodo", 1.2);
    Dude tallerDude = new Dude("Frodo", 1.3);

    Comparator<Double> closeEnough = new AtPrecisionComparator<>(0.5);

    Dude[] hobbits = new Dude[] { dude };

    // assertions will pass
    assertThat(hobbits).usingComparatorForElementFieldsWithNames(closeEnough, "height")
                       .usingFieldByFieldElementComparator()
                       .contains(tallerDude);

    assertThat(hobbits).usingComparatorForElementFieldsWithNames(closeEnough, "height")
                       .usingElementComparatorOnFields("height")
                       .contains(tallerDude);

    assertThat(hobbits).usingComparatorForElementFieldsWithNames(closeEnough, "height")
                       .usingElementComparatorIgnoringFields("name")
                       .contains(tallerDude);

    assertThat(hobbits).usingComparatorForElementFieldsWithNames(closeEnough, "height")
                       .usingRecursiveFieldByFieldElementComparator()
                       .contains(tallerDude);

    assertThat(asList(dude)).usingComparatorForElementFieldsWithNames(closeEnough, "height")
                            .usingFieldByFieldElementComparator()
                            .contains(tallerDude);

    assertThat(asList(dude)).usingComparatorForElementFieldsWithNames(closeEnough, "height")
                            .usingElementComparatorOnFields("height")
                            .contains(tallerDude);

    assertThat(asList(dude)).usingComparatorForElementFieldsWithNames(closeEnough, "height")
                            .usingElementComparatorIgnoringFields("name")
                            .contains(tallerDude);

    assertThat(asList(dude)).usingComparatorForElementFieldsWithNames(closeEnough, "height")
                            .usingRecursiveFieldByFieldElementComparator()
                            .contains(tallerDude);

    assertThat(hobbits).usingComparatorForElementFieldsWithType(closeEnough, Double.class)
                       .usingFieldByFieldElementComparator()
                       .contains(tallerDude);

    assertThat(hobbits).usingComparatorForElementFieldsWithType(closeEnough, Double.class)
                       .usingElementComparatorOnFields("height")
                       .contains(tallerDude);

    assertThat(hobbits).usingComparatorForElementFieldsWithType(closeEnough, Double.class)
                       .usingElementComparatorIgnoringFields("name")
                       .contains(tallerDude);

    assertThat(hobbits).usingComparatorForElementFieldsWithType(closeEnough, Double.class)
                       .usingRecursiveFieldByFieldElementComparator()
                       .contains(tallerDude);

    assertThat(asList(dude)).usingComparatorForElementFieldsWithType(closeEnough, Double.class)
                            .usingFieldByFieldElementComparator()
                            .contains(tallerDude);

    assertThat(asList(dude)).usingComparatorForElementFieldsWithType(closeEnough, Double.class)
                            .usingElementComparatorOnFields("height")
                            .contains(tallerDude);

    assertThat(asList(dude)).usingComparatorForElementFieldsWithType(closeEnough, Double.class)
                            .usingElementComparatorIgnoringFields("name")
                            .contains(tallerDude);

    assertThat(asList(dude)).usingComparatorForElementFieldsWithType(closeEnough, Double.class)
                            .usingRecursiveFieldByFieldElementComparator()
                            .contains(tallerDude);

    Dude reallyTallDude = new Dude("Frodo", 1.9);
    try {
      assertThat(asList(dude)).usingComparatorForElementFieldsWithNames(closeEnough, "height")
                              .usingFieldByFieldElementComparator()
                              .contains(reallyTallDude);
    } catch (AssertionError e) {
      logAssertionErrorMessage("contains for Iterable usingComparatorForElementFieldsWithNames", e);
    }
  }

  @Test
  public void iterable_assertions_with_recursive_field_by_field_element_comparator_examples() {
    Dude jon = new Dude("Jon", 1.2);
    Dude sam = new Dude("Sam", 1.3);
    jon.friend = sam;
    sam.friend = jon;

    Dude jonClone = new Dude("Jon", 1.2);
    Dude samClone = new Dude("Sam", 1.3);
    jonClone.friend = samClone;
    samClone.friend = jonClone;

    assertThat(asList(jon, sam)).usingRecursiveFieldByFieldElementComparator()
                                .contains(jonClone, samClone);

  }

  @Test
  public void iterable_assertions_comparing_elements_field_by_field_example() {
    // this is useful if elements don't have a good equals method implementation.
    Employee bill = new Employee("Bill", 60, "Micro$oft");
    final List<Employee> micro$oftEmployees = newArrayList(bill);
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
    assertThat(newArrayList(frodo)).usingElementComparatorOnFields("race").contains(sam);
    assertThat(newArrayList(frodo)).usingElementComparatorOnFields("race").isEqualTo(newArrayList(sam));
    // ... but not when comparing both name and race
    try {
      assertThat(newArrayList(frodo)).usingElementComparatorOnFields("name", "race").contains(sam);
    } catch (AssertionError e) {
      logAssertionErrorMessage("contains for Iterable usingElementComparatorOnFields", e);
    }
  }

}
