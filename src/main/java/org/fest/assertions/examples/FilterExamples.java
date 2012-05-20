package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.filter.Filters.filter;

import org.junit.Test;

import org.fest.assertions.core.Condition;
import org.fest.assertions.examples.data.Player;

/**
 * Iterable (including Collection) assertions examples.<br>
 * Iterable has been introduced in <b>FEST 2.0</b> (before 2.0 version, only Collection assertions were available).
 * 
 * @author Joel Costigliola
 */
public class FilterExamples extends AbstractAssertionsExamples {

  @Test
  public void filter_with_examples() {
    // with(property).equalsTo(someValue) works by instrospection on specified property
    assertThat(filter(fellowshipOfTheRing).with("race").equalsTo(HOBBIT).get()).containsOnly(sam, frodo, pippin, merry);
    // same thing - shorter way
    assertThat(filter(fellowshipOfTheRing).with("race", HOBBIT).get()).containsOnly(sam, frodo, pippin, merry);

    // nested property are supported
    assertThat(filter(fellowshipOfTheRing).with("race.name").equalsTo("Man").get()).containsOnly(aragorn, boromir);

    // you can apply different comparison
    assertThat(filter(fellowshipOfTheRing).with("race").notIn(HOBBIT, MAN).get()).containsOnly(gandalf, gimli, legolas);
    assertThat(filter(fellowshipOfTheRing).with("race").in(MAIA, MAN).get()).containsOnly(gandalf, boromir, aragorn);
    assertThat(filter(fellowshipOfTheRing).with("race").notEqualsTo(HOBBIT).get()).contains(gandalf, boromir, aragorn, gimli,
                                                                                            legolas);
    // you can chain multiple filter criteria 
    assertThat(filter(fellowshipOfTheRing).with("race").equalsTo(MAN).and("name").notEqualsTo("Boromir").get()).contains(aragorn);
  }

  @Test
  public void filter_on_condition_examples() {
    // having(condition) example
    Condition<Player> mvpStats= new Condition<Player>() {
      @Override
      public boolean matches(Player player) {
        return player.getPointsPerGame() > 20 && (player.getAssistsPerGame() >= 8 || player.getReboundsPerGame() >= 8);
      }
    };
    assertThat(filter(players).having(mvpStats).get()).containsOnly(rose, james);
    
    // being(condition) example : same condition can be applied but is renamed to be more readable
    Condition<Player> potentialMvp= mvpStats;
    assertThat(filter(players).being(potentialMvp).get()).containsOnly(rose, james);
  }

}
