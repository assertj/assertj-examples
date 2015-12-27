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
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.examples.condition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.condition.AllOf.allOf;
import static org.assertj.core.condition.AnyOf.anyOf;
import static org.assertj.core.condition.Not.not;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Sets.newLinkedHashSet;

import java.util.List;
import java.util.Set;

import org.assertj.core.api.Condition;
import org.assertj.examples.AbstractAssertionsExamples;
import org.assertj.examples.data.BasketBallPlayer;
import org.junit.Test;

public class UsingConditionExamples extends AbstractAssertionsExamples {

  @Test
  public void is_condition_example() {
    assertThat("Yoda").is(jedi);
    assertThat("Vador").isNot(jedi);
    assertThat(rose).is(potentialMvp);
    assertThat(james).is(potentialMvp);
    assertThat(noah).isNot(potentialMvp);
    List<BasketBallPlayer> bullsPlayers = newArrayList(noah, rose);
    assertThat(bullsPlayers).haveAtLeastOne(potentialMvp);
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
      logAssertionErrorMessage("has_condition_example", e);
    }
  }

  @Test
  public void anyOf_condition_example() {
    assertThat("Vader").is(anyOf(jedi, sith));
  }

  @Test
  public void condition_with_supertype_example() {
    assertThat("string").is(new Condition<Object>() {

      @Override
      public boolean matches(Object value) {
        return value instanceof String;
      }
    });
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
      logAssertionErrorMessage("condition_example_on_multiple_elements", e);
    }

    // have & doNotHave
    assertThat(newLinkedHashSet("Luke", "Yoda")).have(jediPower);
    assertThat(newLinkedHashSet("Leia", "Solo")).doNotHave(jediPower);

    // areAtLeast & areNotAtLeast
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).areAtLeastOne(jedi);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).areAtLeast(2, jedi);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Obiwan")).areAtLeast(2, jedi);

    // haveAtLeast & doNotHaveAtLeast
    assertThat(newLinkedHashSet("Luke", "Leia")).haveAtLeastOne(jediPower);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).haveAtLeast(2, jediPower);
    assertThat(newLinkedHashSet("Luke", "Yoda", "Obiwan")).haveAtLeast(2, jediPower);

    // areAtMost
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).areAtMost(2, jedi);
    assertThat(newLinkedHashSet("Luke", "Solo", "Leia")).areAtMost(2, jedi);

    // haveAtMost
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).haveAtMost(2, jediPower);
    assertThat(newLinkedHashSet("Luke", "Solo", "Leia")).haveAtMost(2, jediPower);

    // areExactly
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).areExactly(2, jedi);

    // haveExactly
    assertThat(newLinkedHashSet("Luke", "Yoda", "Leia")).haveExactly(2, jediPower);
  }

  @Test
  public void combined_condition_example() {
    assertThat("Yoda").has(jediPower);
    assertThat("Yoda").has(allOf(jediPower, not(sithPower)));
    assertThat("Solo").has(not(jediPower));
    assertThat("Solo").doesNotHave(jediPower);
    assertThat("Solo").is(allOf(not(jedi), not(sith)));
    assertThat("Solo").isNot(anyOf(jedi, sith));
    assertThat("Solo").doesNotHave(anyOf(jediPower, sithPower));
  }

  private final Condition<String> jedi = new Condition<String>("jedi") {
    private final Set<String> jedis = newLinkedHashSet("Luke", "Yoda", "Obiwan");

    @Override
    public boolean matches(String actual) {
      return jedis.contains(actual);
    }
  };

  private final Condition<String> jediPower = jedi;

  private final Condition<String> sith = new Condition<String>("sith") {
    private final Set<String> siths = newLinkedHashSet("Sidious", "Vader", "Plagueis");

    @Override
    public boolean matches(String value) {
      return siths.contains(value);
    }
  };

  private final Condition<String> sithPower = new Condition<String>("sith power") {
    private final Set<String> siths = newLinkedHashSet("Sidious", "Vader", "Plagueis");

    @Override
    public boolean matches(String value) {
      return siths.contains(value);
    }
  };

}
