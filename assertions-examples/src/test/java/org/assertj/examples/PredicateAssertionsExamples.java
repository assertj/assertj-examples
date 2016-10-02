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
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.examples.data.Race.HOBBIT;

import java.util.Map;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.Test;

public class PredicateAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void predicate_assertions_examples() {
    Predicate<TolkienCharacter> hobbitPredicate = character -> character.getRace() == HOBBIT;

    assertThat(hobbitPredicate).accepts(frodo)
                               .accepts(frodo, sam, pippin)
                               .rejects(sauron)
                               .rejects(sauron, aragorn)
                               .acceptsAll(newArrayList(frodo, sam, pippin))
                               .rejectsAll(newArrayList(sauron, gandalf, aragorn));

    Predicate<String> ballSportPredicate = sport -> sport.contains("ball");

    // assertion succeeds:
    assertThat(ballSportPredicate).accepts("football")
                                  .accepts("football", "basketball", "handball");
  }

  @Test
  public void primitives_predicate_assertions_examples() {
    IntPredicate evenNumber = n -> n % 2 == 0;

    assertThat(evenNumber).accepts(4)
                          .rejects(3)
                          .accepts(2, 4, 6)
                          .rejects(1, 3, 5);

    DoublePredicate tallSize = size -> size > 1.90;
    assertThat(tallSize).accepts(1.95, 2.00, 2.05);
  }

  @Test
  public void should_not_produce_warning_for_varargs_parameter() {
    Predicate<Map.Entry<String, String>> predicate = entry -> entry.getKey().equals("A");
    assertThat(predicate).accepts(Pair.of("A", "B"))
                         .rejects(Pair.of("C", "D"));
  }

}
