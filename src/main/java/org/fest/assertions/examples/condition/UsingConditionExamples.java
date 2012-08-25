package org.fest.assertions.examples.condition;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.condition.AllOf.allOf;
import static org.fest.assertions.condition.AnyOf.anyOf;
import static org.fest.assertions.condition.Not.not;
import static org.fest.util.Sets.newLinkedHashSet;

import java.util.Set;

import org.junit.Test;

import org.fest.assertions.core.Condition;
import org.fest.assertions.examples.AbstractAssertionsExamples;

public class UsingConditionExamples extends AbstractAssertionsExamples {

  @Test
  public void is_condition_example() {
    assertThat("Yoda").is(jedi);
    assertThat("Vador").isNot(jedi);
    assertThat(rose).is(potentialMvp);
    assertThat(james).is(potentialMvp);
    assertThat(noah).isNot(potentialMvp);
  }

  @Test
  public void has_condition_example() {
    assertThat("Yoda").has(jediPower);
    assertThat("Solo").doesNotHave(jediPower);
    assertThat(noah).has(doubleDoubleStats);
    assertThat(durant).doesNotHave(doubleDoubleStats);
    try {
      assertThat(rose).has(doubleDoubleStats);
    } catch (AssertionError e) {
      assertThat(e).hasMessage("expecting:<Player[Derrick Rose, team=Chicago Bulls]> to have:<double double stats>");
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void anyOf_condition_example() {
    assertThat("Vader").is(anyOf(jedi, sith));
  }

  @Test
  public void condition_example_on_multiple_elements() {
    // are & areNot
    assertThat(newLinkedHashSet("Luke", "Yoda")).are(jedi);
    assertThat(newLinkedHashSet("Leia", "Solo")).areNot(jedi);
    assertThat(newLinkedHashSet(rose, james)).are(potentialMvp);
    try {
      // noah is not a potential MVP !
      assertThat(newLinkedHashSet(rose, noah)).are(potentialMvp);
    } catch (AssertionError e) {
      assertThat(e).hasMessage("expecting elements:\n" +
          "<[Player[Joachim Noah, team=Chicago Bulls]]>\n" +
          " of \n" +
          "<[Player[Derrick Rose, team=Chicago Bulls], Player[Joachim Noah, team=Chicago Bulls]]>\n" +
          " to be <a potential MVP>");
    }

    // have & doNotHave
    assertThat(newLinkedHashSet("Luke", "Yoda")).have(jediPower);
    assertThat(newLinkedHashSet("Leia", "Solo")).doNotHave(jediPower);

    // areAtLeast & areNotAtLeast
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).areAtLeast(2, jedi);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Obiwan")).areAtLeast(2, jedi);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).areNotAtLeast(1, jedi);
    assertThat(newLinkedHashSet("Luke", "Solo", "Leia")).areNotAtLeast(1, jedi);

    // haveAtLeast & doNotHaveAtLeast
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).haveAtLeast(2, jediPower);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Obiwan")).haveAtLeast(2, jediPower);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).doNotHaveAtLeast(1, jediPower);
    assertThat(newLinkedHashSet("Luke", "Solo", "Leia")).doNotHaveAtLeast(1, jediPower);

    // areAtMost & areNotAtMost
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).areAtMost(2, jedi);
    assertThat(newLinkedHashSet("Luke", "Solo", "Leia")).areAtMost(2, jedi);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).areNotAtMost(1, jedi);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Obiwan")).areNotAtMost(1, jedi);

    // haveAtMost & doNotHaveAtMost
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).haveAtMost(2, jediPower);
    assertThat(newLinkedHashSet("Luke", "Solo", "Leia")).haveAtMost(2, jediPower);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).doNotHaveAtMost(1, jediPower);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Obiwan")).doNotHaveAtMost(1, jediPower);

    // areExactly & areNotExactly
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).areExactly(2, jedi);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).areNotExactly(1, jedi);

    // haveExactly & haveNotExactly
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).haveExactly(2, jediPower);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).doNotHaveExactly(1, jediPower);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void has_not_condition_example() {
    assertThat("Yoda").has(jediPower);
    assertThat("Yoda").has(allOf(jediPower, not(sithPower)));
    assertThat("Solo").has(not(jediPower));
    assertThat("Solo").doesNotHave(jediPower);
    assertThat("Solo").is(allOf(not(jedi), not(sith)));
  }

  private final Condition<String> jedi = new Condition<String>("jedi") {
    private final Set<String> jedis = newLinkedHashSet("Luke", "Yoda", "Obiwan");

    @Override
    public boolean matches(String value) {
      return jedis.contains(value);
    };
  };

  private final Condition<String> jediPower = new Condition<String>("jedi power") {
    private final Set<String> jedis = newLinkedHashSet("Luke", "Yoda", "Obiwan");

    @Override
    public boolean matches(String value) {
      return jedis.contains(value);
    };
  };

  private final Condition<String> sith = new Condition<String>("sith") {
    private final Set<String> siths = newLinkedHashSet("Sidious", "Vader", "Plagueis");

    @Override
    public boolean matches(String value) {
      return siths.contains(value);
    };
  };

  private final Condition<String> sithPower = new Condition<String>("sith power") {
    private final Set<String> siths = newLinkedHashSet("Sidious", "Vader", "Plagueis");

    @Override
    public boolean matches(String value) {
      return siths.contains(value);
    };
  };

}
