package org.assertj.examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.filter.Filters.filter;

import org.assertj.examples.data.Player;
import org.junit.Test;

import org.assertj.core.api.Condition;

/**
 * Iterable (including Collection) assertions examples.<br>
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
