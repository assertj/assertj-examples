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
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.Lists.list;
import static org.assertj.examples.data.Race.ELF;
import static org.assertj.examples.data.Race.HOBBIT;
import static org.assertj.examples.data.Race.MAIA;
import static org.assertj.examples.data.Ring.narya;
import static org.assertj.examples.data.Ring.nenya;
import static org.assertj.examples.data.Ring.oneRing;
import static org.assertj.examples.data.Ring.vilya;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.jupiter.api.Test;

/**
 * Assertions available for all objects.
 *
 * @author Joel Costigliola
 */
public class RecursiveAssertionsExamples extends AbstractAssertionsExamples {

  // the data used are initialized in AbstractAssertionsExamples.

  @Test
  public void recursive_field_by_field_comparison_assertions_examples() {
    assertThat(frodo).usingRecursiveComparison()
                     .ignoringFields("name", "age")
                     .ignoringCollectionOrder()
                     .withStrictTypeChecking()
                     .isEqualTo(sam);

    TolkienCharacter frodoClone = new TolkienCharacter("Frodo", 33, HOBBIT);
    TolkienCharacter samClone = new TolkienCharacter("Sam", 38, HOBBIT);
    TolkienCharacter merryClone = new TolkienCharacter("Merry", 36, HOBBIT);
    TolkienCharacter pippinClone = new TolkienCharacter("Pippin", 28, HOBBIT);
    // usingRecursiveComparison is available for iterables
    List<TolkienCharacter> hobbits = list(frodo, sam, pippin, merry);
    List<TolkienCharacter> hobbitsClone = list(frodoClone, samClone, pippinClone, merryClone);
    assertThat(hobbits).usingRecursiveComparison()
                       .isEqualTo(hobbitsClone);
    // to execute any iterable assertions, use usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration)
    // the drawback is that error messages won't show a detailed field differences.
    RecursiveComparisonConfiguration configuration = RecursiveComparisonConfiguration.builder()
                                                                                     .withIgnoredFields("age")
                                                                                     .build();
    TolkienCharacter olderFrodo = new TolkienCharacter("Frodo", 55, HOBBIT);
    assertThat(hobbits).usingRecursiveFieldByFieldElementComparator(configuration)
                       .contains(olderFrodo, samClone, pippinClone, merryClone)
                       .doesNotContain(gandalf);

    // ... and arrays
    TolkienCharacter[] hobbitsArray = array(frodo, sam, pippin, merry);
    TolkienCharacter[] hobbitsArrayClone = array(frodoClone, samClone, pippinClone, merryClone);
    assertThat(hobbitsArray).usingRecursiveComparison()
                            .isEqualTo(hobbitsArrayClone);
    // ... and maps
    Map<Ring, TolkienCharacter> ringBearersClone = new LinkedHashMap<>();
    ringBearersClone.put(nenya, new TolkienCharacter("Galadriel", 3000, ELF));
    ringBearersClone.put(narya, new TolkienCharacter("Gandalf", 2020, MAIA));
    ringBearersClone.put(vilya, new TolkienCharacter("Elrond", 3000, ELF));
    ringBearersClone.put(oneRing, frodoClone);
    assertThat(ringBearers).usingRecursiveComparison()
                           .isEqualTo(ringBearersClone);
    // ... and optionals
    assertThat(Optional.of(frodo)).usingRecursiveComparison()
                                  .isEqualTo(Optional.of(frodoClone));
    // isNotEqualTo is available too
    assertThat(frodo).usingRecursiveComparison()
                     .isNotEqualTo(sam);
  }

  @Test
  public void recursive_field_by_field_comparison_assertions_with_specific_type_or_fields_equals() {
    // GIVEN
    TolkienCharacter olderFrodo = new TolkienCharacter("Frodo", frodo.age + 5, HOBBIT);
    // THEN
    assertThat(frodo).usingRecursiveComparison()
                     .withEqualsForType((int1, int2) -> Math.abs(int1 - int2) <= 10, Integer.class)
                     .isEqualTo(olderFrodo);
    assertThat(frodo).usingRecursiveComparison()
                     .withEqualsForFields((Integer int1, Integer int2) -> Math.abs(int1 - int2) <= 10, "age")
                     .isEqualTo(olderFrodo);
  }

  @Test
  public void recursive_field_by_field_comparison_assertions_with_optional_examples() {
    // GIVEN
    Song song = new Song("I Can't Get No Satisfaction", new Author("Mick Jagger"), new Author("Keith Richards"));
    Song expectedSong = new Song("I Can't Get No Satisfaction", new Author("Mick Jagger"), new Author("Keith Richards"));
    // FAIL
    assertThat(song).usingRecursiveComparison()
                    .isEqualTo(expectedSong);
  }

  @Test
  public void recursive_field_by_field_comparison_assertions_ignoring_collection_order_examples() {
    // GIVEN
    Song song = new Song("I Can't Get No Satisfaction", new Author("Mick Jagger"), new Author("Keith Richards"));
    Song expectedSong = new Song("I Can't Get No Satisfaction", new Author("Mick Jagger"), new Author("Keith Richards"));
    // FAIL
    assertThat(song).usingRecursiveComparison()
                    .isEqualTo(expectedSong);
  }

  static class Song {

    public Author author;
    public Optional<Author> coAuthor;
    public String song;

    public Song(String song, Author author, Author coAuthor) {
      this.song = song;
      this.author = author;
      this.coAuthor = Optional.ofNullable(coAuthor);
    }

    @Override
    public String toString() {
      return String.format("Song [author=%s, coAuthor=%s, song=%s]", author, coAuthor, song);
    }

  }

  class Author {

    String name;

    public Author(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return name;
    }

  }

  @Test
  public void usingFieldByFieldElementComparatorTest() throws Exception {
    List<Animal> animals = new ArrayList<>();
    Bird bird = new Bird("White");
    Snake snake = new Snake(15);
    animals.add(bird);
    animals.add(snake);

    assertThat(animals).usingFieldByFieldElementComparator()
                       .containsExactly(bird, snake);
  }

  private class Animal {
    private final String name;

    private Animal(String name) {
      this.name = name;
    }

    @SuppressWarnings("unused")
    public String getName() {
      return name;
    }
  }

  private class Bird extends Animal {
    private final String color;

    private Bird(String color) {
      super("Bird");
      this.color = color;
    }

    @SuppressWarnings("unused")
    public String getColor() {
      return color;
    }
  }

  private class Snake extends Animal {
    private final int length;

    private Snake(int length) {
      super("Snake");
      this.length = length;
    }

    @SuppressWarnings("unused")
    public int getLength() {
      return length;
    }
  }
}
