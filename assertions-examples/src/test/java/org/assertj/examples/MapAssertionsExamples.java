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
import static org.assertj.core.api.Assertions.entry;
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

import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.api.Condition;
import org.assertj.examples.data.BasketBallPlayer;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

/**
 * Map assertions example.
 *
 * @author Joel Costigliola
 */
public class MapAssertionsExamples extends AbstractAssertionsExamples {

  private final class OneRingManCondition extends Condition<Map.Entry<TolkienCharacter, Ring>> {
    @Override
    public boolean matches(Map.Entry<TolkienCharacter, Ring> entry) {
      return entry.getKey().getRace() == MAN && entry.getValue() == oneRing;
    }
  }

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

    assertThat(basketballPlayer).extracting("name", "age")
                                .contains("kawhi", 25);
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
  public void should_not_produce_warning_for_varargs_parameter() {
    Map<String, String> map = new HashMap<>();
    map.put("A", "B");
    assertThat(map.entrySet()).containsExactly(Pair.of("A", "B"));
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

    assertThat(ringBearers).hasEntrySatisfying(new OneRingManCondition());
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

  private static <K, V> Map.Entry<K, V> javaMapEntry(K key, V value) {
    return new SimpleImmutableEntry<>(key, value);
  }

  Condition<TolkienCharacter> isMan = new Condition<TolkienCharacter>("is man") {
    @Override
    public boolean matches(TolkienCharacter value) {
      return value.getRace() == MAN;
    }
  };

  Condition<Ring> oneRingBearer = new Condition<Ring>("One ring bearer") {
    @Override
    public boolean matches(Ring value) {
      return value == oneRing;
    }
  };

}
