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
import static org.assertj.core.api.Assertions.in;
import static org.assertj.core.api.Assertions.not;
import static org.assertj.core.api.Assertions.notIn;
import static org.assertj.core.api.filter.Filters.filter;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.examples.data.Race.HOBBIT;
import static org.assertj.examples.data.Race.MAIA;
import static org.assertj.examples.data.Race.MAN;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.examples.data.BasketBallPlayer;
import org.junit.Before;
import org.junit.Test;

/**
 * Iterable (including Collection) assertions examples.<br>
 * 
 * @author Joel Costigliola
 */
public class FilterExamples extends AbstractAssertionsExamples {
  protected Employee yoda;
  protected Employee obiwan;
  protected Employee luke;
  protected Employee noname;
  protected List<Employee> employees;

  @Before
  public void setUp() {
    yoda = new Employee(1L, new Name("Yoda"), 800);
    obiwan = new Employee(2L, new Name("Obi"), 800);
    luke = new Employee(3L, new Name("Luke", "Skywalker"), 26);
    noname = new Employee(4L, null, 10);
    employees = newArrayList(yoda, luke, obiwan, noname);
  }

  @Test
  public void filter_with_examples() {
    // with(property).equalsTo(someValue) works by instrospection on specified property
    assertThat(filter(fellowshipOfTheRing).with("race").equalsTo(HOBBIT).get()).containsOnly(sam, frodo, pippin, merry);
    // same thing - shorter way
    assertThat(filter(fellowshipOfTheRing).with("race", HOBBIT).get()).containsOnly(sam, frodo, pippin, merry);
    // same thing - even shorter way
    assertThat(fellowshipOfTheRing).filteredOn("race", HOBBIT)
                                   .containsOnly(sam, frodo, pippin, merry);

    // nested property are supported
    assertThat(filter(fellowshipOfTheRing).with("race.name").equalsTo("Man").get()).containsOnly(aragorn, boromir);
    assertThat(fellowshipOfTheRing).filteredOn("race.name", "Man")
                                   .containsOnly(aragorn, boromir);

    // you can apply different comparison
    assertThat(filter(fellowshipOfTheRing).with("race").notIn(HOBBIT, MAN).get()).containsOnly(gandalf, gimli, legolas);
    assertThat(fellowshipOfTheRing).filteredOn("race", notIn(HOBBIT, MAN))
                                   .containsOnly(gandalf, gimli, legolas);

    assertThat(filter(fellowshipOfTheRing).with("race").in(MAIA, MAN).get()).containsOnly(gandalf, boromir, aragorn);
    assertThat(fellowshipOfTheRing).filteredOn("race", in(MAIA, MAN))
                                   .containsOnly(gandalf, boromir, aragorn);

    assertThat(filter(fellowshipOfTheRing).with("race").notEqualsTo(HOBBIT).get()).contains(gandalf, boromir, aragorn,
                                                                                            gimli, legolas);
    assertThat(fellowshipOfTheRing).filteredOn("race", not(HOBBIT))
                                   .containsOnly(gandalf, boromir, aragorn, gimli, legolas);

    // you can chain multiple filter criteria
    assertThat(filter(fellowshipOfTheRing).with("race").equalsTo(MAN).and("name").notEqualsTo("Boromir").get()).contains(aragorn);
    assertThat(fellowshipOfTheRing).filteredOn("race", MAN)
                                   .filteredOn("name", not("Boromir"))
                                   .containsOnly(aragorn);
  }

  @Test
  public void filter_on_condition_examples() {
    // having(condition) example
    Condition<BasketBallPlayer> mvpStats = new Condition<BasketBallPlayer>() {
      @Override
      public boolean matches(BasketBallPlayer player) {
        return player.getPointsPerGame() > 20 && (player.getAssistsPerGame() >= 8 || player.getReboundsPerGame() >= 8);
      }
    };
    assertThat(filter(basketBallPlayers).having(mvpStats).get()).containsOnly(rose, james, wade);
    assertThat(basketBallPlayers).filteredOn(mvpStats).containsOnly(rose, james, wade);

    // being(condition) example : same condition can be applied but is renamed to be more readable
    Condition<BasketBallPlayer> potentialMvp = mvpStats;
    assertThat(filter(basketBallPlayers).being(potentialMvp).get()).containsOnly(rose, james, wade);
    assertThat(basketBallPlayers).filteredOn(potentialMvp).containsOnly(rose, james, wade);
  }

  @Test
  public void iterable_fluent_filter_with_examples() {
    assertThat(fellowshipOfTheRing).filteredOn("race", HOBBIT)
                                   .containsOnly(sam, frodo, pippin, merry);

    Assertions.setAllowExtractingPrivateFields(true);
    assertThat(fellowshipOfTheRing).filteredOn("notAccessibleField", notIn(0L))
                                   .contains(sam, frodo, pippin, merry);

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

    assertThat(fellowshipOfTheRing).filteredOn(character -> character.getName().contains("o"))
                                   .containsOnly(aragorn, frodo, legolas, boromir);

    assertThat(fellowshipOfTheRing).filteredOn(character -> character.getName().contains("o"))
                                   .containsOnly(aragorn, frodo, legolas, boromir)
                                   .extracting(character -> character.getRace().getName())
                                   .contains("Hobbit", "Elf", "Man");

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
  public void should_filter_iterable_under_test_on_private_field_values() {
    assertThat(employees).filteredOn("city", notIn("Paris")).containsOnly(yoda, obiwan, luke, noname);
    assertThat(employees).filteredOn("city", notIn("New York")).isEmpty();
    assertThat(employees).filteredOn("city", notIn("New York", "Paris")).isEmpty();
  }

}
