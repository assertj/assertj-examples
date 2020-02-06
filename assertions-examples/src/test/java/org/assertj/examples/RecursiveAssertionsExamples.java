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

import org.assertj.core.api.Assertions;
import org.assertj.core.util.introspection.IntrospectionError;
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
  public void basic_assertions_with_field_by_field_comparison_examples() {

    TolkienCharacter mysteriousHobbit = new TolkienCharacter(null, 33, HOBBIT);

    // ------------------------------------------------------------------------------------
    // Lenient equality with field by field comparison
    // ------------------------------------------------------------------------------------

    // Frodo is still Frodo ...
    assertThat(frodo).isEqualToComparingFieldByField(frodo);

    TolkienCharacter frodoClone = new TolkienCharacter("Frodo", 33, HOBBIT);

    // Frodo and his clone are equals by comparing fields (if we ignore private fields without getter)
    Assertions.setAllowComparingPrivateFields(false);
    assertThat(frodo).isEqualToComparingFieldByField(frodoClone);

    // ------------------------------------------------------------------------------------
    // Lenient equality when ignoring null fields of other object
    // ------------------------------------------------------------------------------------

    // Frodo is still Frodo ...
    assertThat(frodo).isEqualToIgnoringNullFields(frodo);

    // Null fields in expected object are ignored, the mysteriousHobbit has null name
    assertThat(frodo).isEqualToIgnoringNullFields(mysteriousHobbit);
    // ... But the lenient equality is not reversible !
    try {
      assertThat(mysteriousHobbit).isEqualToIgnoringNullFields(frodo);
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualToIgnoringNullFields", e);
    }

    // ------------------------------------------------------------------------------------
    // Lenient equality with field by field comparison expect specified fields
    // ------------------------------------------------------------------------------------

    // Except name and age, frodo and sam both are hobbits, so they are lenient equals ignoring name and age
    assertThat(frodo).isEqualToIgnoringGivenFields(sam, "name", "age");

    // But not when just age is ignored
    try {
      assertThat(frodo).isEqualToIgnoringGivenFields(sam, "age");
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualToIgnoringGivenFields", e);
    }

    // Null fields are not ignored, so when expected has null field, actual must have too
    assertThat(mysteriousHobbit).isEqualToIgnoringGivenFields(mysteriousHobbit, "age");

    // ------------------------------------------------------------------------------------
    // Lenient equality with field by field comparison on given fields only
    // ------------------------------------------------------------------------------------

    // frodo and sam both are hobbits, so they are lenient equals on race
    assertThat(frodo).isEqualToComparingOnlyGivenFields(sam, "race");
    // nested fields are supported
    assertThat(frodo).isEqualToComparingOnlyGivenFields(sam, "race.name");

    // but not when accepting name and race
    try {
      assertThat(frodo).isEqualToComparingOnlyGivenFields(sam, "name", "race", "age");
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualToComparingOnlyGivenFields", e);
    }

    // Null fields are not ignored, so when expected has null field, actual must have too
    assertThat(mysteriousHobbit).isEqualToComparingOnlyGivenFields(mysteriousHobbit, "name");

    // Specified fields must exist
    try {
      assertThat(frodo).isEqualToComparingOnlyGivenFields(sam, "hairColor");
    } catch (IntrospectionError e) {
      logger.info("isEqualToComparingOnlyGivenFields InstrospectionError message : {}", e.getMessage());
    }
    Assertions.setAllowComparingPrivateFields(true);
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
