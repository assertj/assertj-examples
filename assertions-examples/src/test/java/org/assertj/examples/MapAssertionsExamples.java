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
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.InstanceOfAssertFactories.STRING;
import static org.assertj.examples.data.Race.ELF;
import static org.assertj.examples.data.Race.MAIA;
import static org.assertj.examples.data.Race.MAN;
import static org.assertj.examples.data.Ring.dwarfRing;
import static org.assertj.examples.data.Ring.manRing;
import static org.assertj.examples.data.Ring.narya;
import static org.assertj.examples.data.Ring.nenya;
import static org.assertj.examples.data.Ring.oneRing;
import static org.assertj.examples.data.Ring.vilya;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.assertj.core.api.Condition;
import org.assertj.examples.data.BasketBallPlayer;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

/**
 * Map assertions example.
 *
 * @author Joel Costigliola
 */
public class MapAssertionsExamples extends AbstractAssertionsExamples {

  private Condition<Ring> oneRingBearer = new Condition<>(ring -> ring == oneRing, "One ring bearer");

  // @format:off
  private Condition<Map.Entry<TolkienCharacter, Ring>> oneRingManBearer = new Condition<>(entry -> entry.getKey().getRace() == MAN
                                                                                                   && entry.getValue() == oneRing,
                                                                                          "One ring man bearer");
  // @format:on

  @Test
  public void map_assertions_examples() {
    // ringBearers is a Map of TolkienCharacter indexed by the Ring they are wearing.
    assertThat(ringBearers).isNotEmpty().hasSize(4);

    // note the usage of Assertions.entry(key, value) synthetic sugar for better readability (similar to
    // MapEntry.entry(key, value)).
    assertThat(ringBearers).contains(entry(oneRing, frodo), entry(nenya, galadriel));
    // using java util Map.Entry
    assertThat(ringBearers).contains(javaMapEntry(oneRing, frodo), javaMapEntry(nenya, galadriel));
    // same assertion but different way of expressing it : no entry call needed but no varargs support.
    assertThat(ringBearers).containsEntry(oneRing, frodo).containsEntry(nenya, galadriel);
    // opposite of contains/containsEntry
    assertThat(ringBearers).doesNotContain(entry(oneRing, sauron), entry(nenya, aragorn));
    assertThat(ringBearers).doesNotContainEntry(oneRing, aragorn);

    Map<Ring, TolkienCharacter> ringBearersInDifferentOrder = new LinkedHashMap<>();
    ringBearersInDifferentOrder.put(Ring.oneRing, frodo);
    ringBearersInDifferentOrder.put(Ring.narya, gandalf);
    ringBearersInDifferentOrder.put(Ring.nenya, galadriel);
    ringBearersInDifferentOrder.put(Ring.vilya, elrond);
    assertThat(ringBearers).containsExactlyInAnyOrderEntriesOf(ringBearersInDifferentOrder);

    // Assertion on key
    assertThat(ringBearers).containsKey(nenya);
    assertThat(ringBearers).containsKeys(nenya, narya);
    assertThat(ringBearers).containsValues(frodo, galadriel);
    assertThat(ringBearers).containsOnlyKeys(nenya, narya, vilya, oneRing);
    assertThat(ringBearers).doesNotContainKey(manRing);
    assertThat(ringBearers).doesNotContainKeys(manRing, dwarfRing);

    try {
      assertThat(ringBearers).containsOnlyKeys(nenya, narya, dwarfRing);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnlyKeys with not found and not expected keys.", e);
    }

    // Assertion on value
    assertThat(ringBearers).containsValue(frodo);
    assertThat(ringBearers).doesNotContainValue(sam);

    assertThat(ringBearers).hasSameSizeAs(ringBearers);
    ringBearers.clear();
    assertThat(ringBearers).contains();
    assertThat(ringBearers).containsAllEntriesOf(ringBearers);
  }

  @Test
  public void containsAllEntriesOf_example() {
    Map<Ring, TolkienCharacter> elvesRingBearer = new HashMap<>();
    elvesRingBearer.put(nenya, galadriel);
    elvesRingBearer.put(narya, gandalf);
    elvesRingBearer.put(vilya, elrond);

    assertThat(ringBearers).containsAllEntriesOf(elvesRingBearer);
  }

  @Test
  public void map_contains_entries_examples() throws Exception {
    Map<String, TolkienCharacter> characters = new LinkedHashMap<>();
    characters.put(frodo.getName(), frodo);
    characters.put(galadriel.getName(), galadriel);
    characters.put(gandalf.getName(), gandalf);
    characters.put(sam.getName(), sam);

    assertThat(characters).containsOnly(entry(sam.getName(), sam),
                                        entry(frodo.getName(), frodo),
                                        entry(gandalf.getName(), gandalf),
                                        entry(galadriel.getName(), galadriel));

    try {
      assertThat(characters).containsOnly(entry(sam.getName(), sam),
                                          entry(frodo.getName(), frodo),
                                          entry(aragorn.getName(), aragorn));
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnly with not found and not expected elements.", e);
    }

    assertThat(characters).containsExactly(entry(frodo.getName(), frodo),
                                           entry(galadriel.getName(), galadriel),
                                           entry(gandalf.getName(), gandalf),
                                           entry(sam.getName(), sam));

    try {
      assertThat(characters).containsExactly(entry(frodo.getName(), frodo),
                                             entry(sam.getName(), sam),
                                             entry(gandalf.getName(), gandalf),
                                             entry(galadriel.getName(), galadriel));
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsExactly is disorder.", e);
    }
  }

  @Test
  public void map_extracting_example() {
    Map<String, Object> basketballPlayer = new HashMap<>();
    basketballPlayer.put("name", "kawhi");
    basketballPlayer.put("age", 25);

    // extracting parameters are used as keys to the map under test
    // single value:
    assertThat(basketballPlayer).extracting("name")
                                .isEqualTo("kawhi");
    // multiple values
    assertThat(basketballPlayer).extracting("name", "age")
                                .contains("kawhi", 25);

    assertThat(basketballPlayer).extractingByKey("name", as(STRING))
                                .startsWith("kaw");

  }

  @Test
  public void display_sorted_maps_in_error_message() {
    try {
      Map<String, Integer> expected = ImmutableMap.<String, Integer> builder().put("a", 1).put("b", 2).build();
      Map<String, Integer> actual = ImmutableMap.<String, Integer> builder().put("b", 1).put("a", 1).build();
      assertThat(expected).isEqualTo(actual);
    } catch (AssertionError e) {
      logAssertionErrorMessage("display sorted maps in error message", e);
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Test
  public void test_bug_485() {
    // https://github.com/joel-costigliola/assertj-core/issues/485
    Map map1 = new HashMap();
    map1.put("Key1", "Value1");
    map1.put("Key2", "Value2");

    assertThat(map1).as("").containsOnlyKeys("Key1", "Key2");
  }

  @Test
  public void test_navigable_size_assertions() throws Exception {
    Map<Ring, TolkienCharacter> ringBearers = new HashMap<>();
    ringBearers.put(nenya, galadriel);
    ringBearers.put(narya, gandalf);
    ringBearers.put(oneRing, frodo);

    // assertion will pass:
    assertThat(ringBearers).size().isGreaterThan(1)
                           .isLessThanOrEqualTo(3)
                           .returnToMap()
                           .containsKeys(oneRing, nenya, narya)
                           .containsEntry(oneRing, frodo);
  }

  @Test
  public void containsAnyOf_example() {
    assertThat(ringBearers).containsAnyOf(entry(oneRing, frodo), entry(oneRing, sauron));
    ringBearers.clear();
    assertThat(ringBearers).containsAnyOf();
  }

  @Test
  public void map_with_condition_examples() {
    Map<TolkienCharacter, Ring> ringBearers = new HashMap<>();
    ringBearers.put(galadriel, nenya);
    ringBearers.put(gandalf, narya);
    ringBearers.put(elrond, vilya);
    ringBearers.put(frodo, oneRing);
    ringBearers.put(isildur, oneRing);

    assertThat(ringBearers).hasEntrySatisfying(oneRingManBearer);
    assertThat(ringBearers).hasEntrySatisfying(isMan, oneRingBearer);
    assertThat(ringBearers).hasKeySatisfying(isMan);
    assertThat(ringBearers).hasValueSatisfying(oneRingBearer);
  }

  @Test
  public void allSatisfy_example() {
    Map<TolkienCharacter, Ring> elvesRingBearers = new HashMap<>();
    elvesRingBearers.put(galadriel, nenya);
    elvesRingBearers.put(gandalf, narya);
    elvesRingBearers.put(elrond, vilya);

    assertThat(elvesRingBearers).allSatisfy((character, ring) -> {
      assertThat(character.getRace()).isIn(ELF, MAIA);
      assertThat(ring).isIn(nenya, narya, vilya);
    });
  }

  public void map_flatExtracting_examples() {
    Map<String, List<BasketBallPlayer>> teams = new HashMap<>();
    teams.put("spurs", asList(parker));
    teams.put("cavs", asList(james, wade));

    assertThat(teams).flatExtracting("spurs", "cavs")
                     .containsExactly(parker, james, wade);
  }

  @Test
  public void bug_1146() {
    // GIVEN
    Map<String, String> data = new HashMap<>();
    data.put("one", "1");
    data.put("two", "2");
    data.put("three", "3");
    // THEN
    try (final AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
      softly.assertThat(data).extracting("one").isEqualTo("1");
    }
  }

  @Test
  public void should_not_produce_warning_for_varargs_parameter() {
    assertThat(ringBearers).contains(entry(oneRing, frodo),
                                     entry(nenya, galadriel))
                           .containsAnyOf(entry(oneRing, frodo),
                                          entry(nenya, aragorn))
                           .containsExactly(entry(nenya, galadriel),
                                            entry(narya, gandalf),
                                            entry(vilya, elrond),
                                            entry(oneRing, frodo))
                           .containsOnly(entry(nenya, galadriel),
                                         entry(narya, gandalf),
                                         entry(vilya, elrond),
                                         entry(oneRing, frodo))
                           .doesNotContain(entry(oneRing, galadriel),
                                           entry(nenya, aragorn));
    // build a map with generic keys
    Map<Map.Entry<Ring, TolkienCharacter>, Ring> map = new HashMap<>();
    map.put(entry(nenya, galadriel), nenya);
    map.put(entry(oneRing, frodo), oneRing);
    assertThat(map).containsKeys(entry(nenya, galadriel),
                                 entry(oneRing, frodo))
                   .containsOnlyKeys(entry(nenya, galadriel),
                                     entry(oneRing, frodo))
                   .doesNotContainKeys(entry(oneRing, gandalf),
                                       entry(oneRing, galadriel));

    Map<String, Object> nba = new HashMap<>();
    nba.put("name", "kawhi");
    nba.put("age", 25);

    assertThat(nba).extracting("name", "age")
                   .contains("kawhi", 25);

    assertThat(nba).extracting(m -> m.get("name"), m -> m.get("age"))
                   .contains("kawhi", 25);

  }

  private static <K, V> Map.Entry<K, V> javaMapEntry(K key, V value) {
    return new SimpleImmutableEntry<>(key, value);
  }

  Condition<TolkienCharacter> isMan = new Condition<TolkienCharacter>("is man") {
    @Override
    public boolean matches(TolkienCharacter value) {
      return value.getRace() == MAN;
    }
  };

}
