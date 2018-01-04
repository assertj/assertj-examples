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
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.examples.data.Race.ELF;
import static org.assertj.examples.data.Race.HOBBIT;
import static org.assertj.examples.data.Ring.narya;
import static org.assertj.examples.data.Ring.nenya;
import static org.assertj.examples.data.Ring.oneRing;
import static org.assertj.examples.data.Ring.vilya;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.BigDecimalComparator;
import org.assertj.core.util.introspection.FieldSupport;
import org.assertj.core.util.introspection.IntrospectionError;
import org.assertj.examples.comparator.AtPrecisionComparator;
import org.assertj.examples.data.Person;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;
import org.assertj.examples.data.movie.Movie;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

/**
 * Assertions available for all objects.
 *
 * @author Joel Costigliola
 */
public class BasicAssertionsExamples extends AbstractAssertionsExamples {

  // the data used are initialized in AbstractAssertionsExamples.

  @Test
  public void isEqualTo_isNotEqualTo_assertions_examples() {
    // the most simple assertion
    assertThat(frodo.age).isEqualTo(33);
    assertThat(frodo.getName()).isEqualTo("Frodo").isNotEqualTo("Frodon");
    // shows that we are no more limited by generics, if we had defined isEqualTo to take only the type of actual
    // (TolkienCharacter) the assertion below would not have compiled
    Object frodoAsObject = frodo;
    assertThat(frodo).isEqualTo(frodoAsObject);
  }

  @Test
  public void meaningful_error_with_test_description_example() {
    try {
      // set a bad age to Mr Frodo, just to see how nice is the assertion error message
      frodo.setAge(50);
      // you can specify a test description with as() method or describedAs(), it supports String format args
      assertThat(frodo.age).as("check %s's age", frodo.getName()).isEqualTo(33);
    } catch (AssertionError e) {
      assertThat(e).hasMessage("[check Frodo's age] expected:<[33]> but was:<[50]>");
    }
    // but you still can override the error message if you have a better one :
    final String frodon = "Frodon";
    try {
      assertThat(frodo.getName()).as("check Frodo's name")
                                 .overridingErrorMessage("Hey my name is Frodo not %s", frodon).isEqualTo(frodon);
    } catch (AssertionError e) {
      assertThat(e).hasMessage("[check Frodo's name] Hey my name is Frodo not Frodon");
    }
    // if you still can override the error message if you have a better one :
    try {
      assertThat(frodo.getName()).overridingErrorMessage("Hey my name is Frodo not (%)").isEqualTo(frodon);
    } catch (AssertionError e) {
      assertThat(e).hasMessage("Hey my name is Frodo not (%)");
    }
  }

  @Test
  public void isSame_isNotSame_assertions_examples() {
    // isSame compares objects reference
    Object jake = new Person("Jake", 43);
    Object sameJake = jake;
    Object jakeClone = new Person("Jake", 43); // equals to jake but not the same
    assertThat(jake).isSameAs(sameJake).isNotSameAs(jakeClone);
  }

  @Test
  public void isIn_isNotIn_assertions_examples() {
    assertThat(frodo).isIn(fellowshipOfTheRing);
    assertThat(frodo).isIn(sam, frodo, pippin);
    assertThat((TolkienCharacter) null).isIn(sam, frodo, pippin, null);
    assertThat(sauron).isNotIn(fellowshipOfTheRing);
    assertThat((TolkienCharacter) null).isNotIn(fellowshipOfTheRing);
  }

  @Test
  public void isNull_isNotNull_assertions_examples() {
    Object nullObject = null;
    assertThat(nullObject).isNull();
    Object nonNullObject = new Object();
    assertThat(nonNullObject).isNotNull();
  }

  @Test
  public void matches_assertions_examples() {
    assertThat(frodo).matches(p -> p.age > 30 && p.getRace() == HOBBIT);
    assertThat(frodo.age).matches(p -> p > 30);
  }

  @Test
  public void isInstanceOf_assertions_examples() {
    assertThat(gandalf).isInstanceOf(TolkienCharacter.class).isInstanceOfAny(Object.class, TolkienCharacter.class);
    assertThat(gandalf).isNotInstanceOf(Movie.class).isNotInstanceOfAny(Movie.class, Ring.class);
  }

  @Test
  public void assertion_error_message_differentiates_expected_and_actual_persons() {
    // Assertion error message is built with toString description of involved objects.
    // Sometimes, objects differs but not their toString description, in that case the error message would be
    // confusing because, if toString returns "Jake" for different objects, isEqualTo would return :
    // "expected:<'Jake'> but was:<'Jake'> ... How confusing !
    // In that case, AssertJ is smart enough and differentiates objects description by adding their class and hashCode.
    Person actual = new Person("Jake", 43);
    Person expected = new Person("Jake", 47);
    try {
      assertThat(actual).isEqualTo(expected);
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualTo differentiating expected and actual having same toString representation", e);
    }
  }

  @Test
  public void basic_assertions_with_custom_comparator_examples() {

    // standard comparison : frodo is not equal to sam ...
    assertThat(frodo).isNotEqualTo(sam);
    // ... but if we compare only character's race frodo is equal to sam
    assertThat(frodo).usingComparator(raceNameComparator).isEqualTo(sam).isEqualTo(merry).isEqualTo(pippin);

    // isIn assertion should be consistent with raceComparator :
    assertThat(frodo).usingComparator(raceNameComparator).isIn(sam, merry, pippin);

    // chained assertions use the specified comparator, we thus can write
    assertThat(frodo).usingComparator(raceNameComparator).isEqualTo(sam).isIn(merry, pippin);

    // note that error message mentions the comparator used to understand the failure better.
    try {
      assertThat(frodo).usingComparator(raceNameComparator).isEqualTo(sauron);
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualTo with custom comparator", e);
    }

    // custom comparison by race : frodo IS equal to sam => isNotEqual must fail
    try {
      assertThat(frodo).usingComparator(raceNameComparator).isNotEqualTo(sam);
    } catch (AssertionError e) {
      logAssertionErrorMessage("isNotEqualTo with custom comparator", e);
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
  public void extracting_object_values() {
    assertThat(frodo).extracting(TolkienCharacter::getName,
                                 character -> character.age,
                                 character -> character.getRace().getName())
                     .containsExactly("Frodo", 33, "Hobbit");

    assertThat(frodo).extracting("name", "age", "race.name")
                     .containsExactly("Frodo", 33, "Hobbit");

    System.out.println("FieldSupport.extraction().fieldValue(\"age\", Integer.class, frodo)"
                       + FieldSupport.extraction().fieldValue("age", Integer.class, frodo));
    System.out.println("FieldSupport.extraction().fieldValue(\"age\", Integer.class, frodo)"
                       + FieldSupport.extraction().fieldValue("age", Object.class, frodo));
  }

  @Test
  public void should_be_able_to_extract_primitive_values() {
    assertThat(FieldSupport.extraction().fieldValue("age", Integer.class, frodo)).isEqualTo(33);
    assertThat(FieldSupport.extraction().fieldValue("age", int.class, frodo)).isEqualTo(33);
  }

  @Test
  public void has_field_or_property_examples() {
    assertThat(frodo).hasFieldOrProperty("age");
    // private field are found unless Assertions.setAllowExtractingPrivateFields(false);
    assertThat(frodo).hasFieldOrProperty("notAccessibleField");
    assertThat(frodo).hasFieldOrPropertyWithValue("age", 33);
    assertThat(frodo).hasFieldOrProperty("race.name");
    assertThat(frodo).hasFieldOrPropertyWithValue("race.name", "Hobbit");
  }

  @Test
  public void hasNoNullFieldsOrProperties_examples() {
    assertThat(frodo).hasNoNullFieldsOrProperties();
    TolkienCharacter sam = new TolkienCharacter(null, 38, HOBBIT);
    assertThat(sam).hasNoNullFieldsOrPropertiesExcept("name");
  }

  @Test
  public void extracting_field_or_property_examples() {
    assertThat(frodo).extracting("name", "age", "race.name")
                     .containsExactly("Frodo", 33, "Hobbit");

    assertThat(frodo).extracting("name", "age", "race.name")
                     .usingComparatorForType(caseInsensitiveStringComparator, String.class)
                     .containsExactly("FRODO", 33, "hoBBit");

    assertThat(frodo).extracting("name", "age", "race.name")
                     .usingComparatorForType(caseInsensitiveStringComparator, String.class)
                     .usingComparatorForType(absValueComparator, Integer.class)
                     .containsExactly("FRODO", -33, "hoBBit");
  }

  @SuppressWarnings("unchecked")
  @Test
  public void as_and_list_or_map() {
    List<Ring> elvesRings = newArrayList(vilya, nenya, narya);
    assertThat(elvesRings).as("abc").isNotEmpty();
    assertThat(elvesRings).as("abc").contains(vilya, atIndex(0));
    assertThat(elvesRings).as("abc %d %d", 1, 2).isNotEmpty();

    assertThat(ringBearers).as("map").hasSize(4);
    assertThat(ringBearers).as("map %s", "size").hasSize(4);
    assertThat(ringBearers).as("map %s", "keys").containsOnlyKeys(vilya, nenya, narya, oneRing);
    assertThat(ringBearers).as("map").containsOnlyKeys(vilya, nenya, narya, oneRing);

    Map<String, String> map = ImmutableMap.of("Key1", "Value1", "Key2", "Value2");
    assertThat(map).as("").containsOnlyKeys("Key1", "Key2");

    @SuppressWarnings("rawtypes")
    Map map1 = new java.util.HashMap<>();
    map1.put("Key1", "Value1");
    map1.put("Key2", "Value2");
    Assertions.assertThat(map1).containsOnlyKeys("Key1", "Key2");
  }

  @Test
  public void field_by_field_comparison_with_specific_comparator_by_type_or_field_examples() {

    TolkienCharacter olderFrodo = new TolkienCharacter("Frodo", 35, HOBBIT);

    Assertions.setAllowComparingPrivateFields(false); // ignore notAccessibleField in comparison

    // specify a comparator for a single field : age
    assertThat(frodo).usingComparatorForFields(new AtPrecisionComparator<>(2), "age")
                     .isEqualToComparingFieldByField(olderFrodo)
                     .isEqualToComparingOnlyGivenFields(olderFrodo, "age");

    // specify a comparator for a field type : Integer
    assertThat(frodo).usingComparatorForType(new AtPrecisionComparator<>(2), Integer.class)
                     .isEqualToComparingFieldByField(olderFrodo)
                     .isEqualToComparingOnlyGivenFields(olderFrodo, "age");

    // field comparators take precendence over field type comparators
    assertThat(frodo).usingComparatorForFields(new AtPrecisionComparator<>(2), "age")
                     .usingComparatorForType(new AtPrecisionComparator<>(1), Integer.class)
                     .isEqualToComparingFieldByField(olderFrodo);

    TolkienCharacter elfFrodo = new TolkienCharacter("Frodo", 33, ELF);
    assertThat(frodo).usingComparatorForFields(new Comparator<String>() {

      @Override
      public int compare(String o1, String o2) {
        return 0;
      }
    }, "race.name").isEqualToComparingOnlyGivenFields(elfFrodo);

    Assertions.setAllowComparingPrivateFields(true);
  }

  @Test
  public void satisfies_examples() {
    assertThat(fellowshipOfTheRing.get(0)).satisfies(character -> {
      assertThat(character.getRace()).isEqualTo(HOBBIT);
      assertThat(character.age).isLessThan(200);
    });
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

  @Test
  @Ignore
  public void use_BigDecimal_comparator_with_extracting() {
    // GIVEN
    Person joe = new Person("Joe", 25);
    joe.setHeight(new BigDecimal("1.80"));

    System.out.println("ddd " + new BigDecimalComparator().compare(new BigDecimal("1.8"), new BigDecimal("1.80")));;

    // THEN
    assertThat(joe).matches(p -> p.getName().equals("Joe") && p.getHeight().compareTo(new BigDecimal("1.8")) == 0);
    assertThat(joe).extracting("name", "height")
                   .usingComparatorForElementFieldsWithType(new BigDecimalComparator(), BigDecimal.class)
                   .containsExactly("Joe", new BigDecimal("1.8"));

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
