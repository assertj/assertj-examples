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
		assertThat(this.frodo.age).isEqualTo(33);
		assertThat(this.frodo.getName()).isEqualTo("Frodo").isNotEqualTo("Frodon");
		// shows that we are no more limited by generics, if we had defined isEqualTo to take only the type of actual
		// (TolkienCharacter) the assertion below would not have compiled
		final Object frodoAsObject = this.frodo;
		assertThat(this.frodo).isEqualTo(frodoAsObject);
	}

	@Test
	public void meaningful_error_with_test_description_example() {
		try {
			// set a bad age to Mr Frodo, just to see how nice is the assertion error message
			this.frodo.setAge(50);
			// you can specify a test description with as() method or describedAs(), it supports String format args
			assertThat(this.frodo.age).as("check %s's age", this.frodo.getName()).isEqualTo(33);
		} catch (final AssertionError e) {
			assertThat(e).hasMessage("[check Frodo's age] expected:<[33]> but was:<[50]>");
		}
		// but you still can override the error message if you have a better one :
		final String frodon = "Frodon";
		try {
			assertThat(this.frodo.getName()).as("check Frodo's name")
					.overridingErrorMessage("Hey my name is Frodo not %s", frodon).isEqualTo(frodon);
		} catch (final AssertionError e) {
			assertThat(e).hasMessage("[check Frodo's name] Hey my name is Frodo not Frodon");
		}
		// if you still can override the error message if you have a better one :
		try {
			assertThat(this.frodo.getName()).overridingErrorMessage("Hey my name is Frodo not (%)").isEqualTo(frodon);
		} catch (final AssertionError e) {
			assertThat(e).hasMessage("Hey my name is Frodo not (%)");
		}
	}

	@Test
	public void isSame_isNotSame_assertions_examples() {
		// isSame compares objects reference
		final Object jake = new Person("Jake", 43);
		final Object sameJake = jake;
		final Object jakeClone = new Person("Jake", 43); // equals to jake but not the same
		assertThat(jake).isSameAs(sameJake).isNotSameAs(jakeClone);
	}

	@Test
	public void isIn_isNotIn_assertions_examples() {
		assertThat(this.frodo).isIn(this.fellowshipOfTheRing);
		assertThat(this.frodo).isIn(this.sam, this.frodo, this.pippin);
		assertThat((TolkienCharacter) null).isIn(this.sam, this.frodo, this.pippin, null);
		assertThat(this.sauron).isNotIn(this.fellowshipOfTheRing);
		assertThat((TolkienCharacter) null).isNotIn(this.fellowshipOfTheRing);
	}

	@Test
	public void isNull_isNotNull_assertions_examples() {
		final Object nullObject = null;
		assertThat(nullObject).isNull();
		final Object nonNullObject = new Object();
		assertThat(nonNullObject).isNotNull();
	}

	@Test
	public void matches_assertions_examples() {
		assertThat(this.frodo).matches(p -> p.age > 30 && p.getRace() == HOBBIT);
		assertThat(this.frodo.age).matches(p -> p > 30);
	}

	@Test
	public void isInstanceOf_assertions_examples() {
		assertThat(this.gandalf).isInstanceOf(TolkienCharacter.class).isInstanceOfAny(Object.class, TolkienCharacter.class);
		assertThat(this.gandalf).isNotInstanceOf(Movie.class).isNotInstanceOfAny(Movie.class, Ring.class);
	}

	@Test
	public void assertion_error_message_differentiates_expected_and_actual_persons() {
		// Assertion error message is built with toString description of involved objects.
		// Sometimes, objects differs but not their toString description, in that case the error message would be
		// confusing because, if toString returns "Jake" for different objects, isEqualTo would return :
		// "expected:<'Jake'> but was:<'Jake'> ... How confusing !
		// In that case, AssertJ is smart enough and differentiates objects description by adding their class and hashCode.
		final Person actual = new Person("Jake", 43);
		final Person expected = new Person("Jake", 47);
		try {
			assertThat(actual).isEqualTo(expected);
		} catch (final AssertionError e) {
			logAssertionErrorMessage("isEqualTo differentiating expected and actual having same toString representation", e);
		}
	}

	@Test
	public void basic_assertions_with_custom_comparator_examples() {

		// standard comparison : frodo is not equal to sam ...
		assertThat(this.frodo).isNotEqualTo(this.sam);
		// ... but if we compare only character's race frodo is equal to sam
		assertThat(this.frodo).usingComparator(this.raceNameComparator).isEqualTo(this.sam).isEqualTo(this.merry).isEqualTo(this.pippin);

		// isIn assertion should be consistent with raceComparator :
		assertThat(this.frodo).usingComparator(this.raceNameComparator).isIn(this.sam, this.merry, this.pippin);

		// chained assertions use the specified comparator, we thus can write
		assertThat(this.frodo).usingComparator(this.raceNameComparator).isEqualTo(this.sam).isIn(this.merry, this.pippin);

		// note that error message mentions the comparator used to understand the failure better.
		try {
			assertThat(this.frodo).usingComparator(this.raceNameComparator).isEqualTo(this.sauron);
		} catch (final AssertionError e) {
			logAssertionErrorMessage("isEqualTo with custom comparator", e);
		}

		// custom comparison by race : frodo IS equal to sam => isNotEqual must fail
		try {
			assertThat(this.frodo).usingComparator(this.raceNameComparator).isNotEqualTo(this.sam);
		} catch (final AssertionError e) {
			logAssertionErrorMessage("isNotEqualTo with custom comparator", e);
		}
	}

	@Test
	public void basic_assertions_with_field_by_field_comparison_examples() {

		final TolkienCharacter mysteriousHobbit = new TolkienCharacter(null, 33, HOBBIT);

		// ------------------------------------------------------------------------------------
		// Lenient equality with field by field comparison
		// ------------------------------------------------------------------------------------

		// Frodo is still Frodo ...
		assertThat(this.frodo).isEqualToComparingFieldByField(this.frodo);

		final TolkienCharacter frodoClone = new TolkienCharacter("Frodo", 33, HOBBIT);

		// Frodo and his clone are equals by comparing fields (if we ignore private fields without getter)
		Assertions.setAllowComparingPrivateFields(false);
		assertThat(this.frodo).isEqualToComparingFieldByField(frodoClone);

		// ------------------------------------------------------------------------------------
		// Lenient equality when ignoring null fields of other object
		// ------------------------------------------------------------------------------------

		// Frodo is still Frodo ...
		assertThat(this.frodo).isEqualToIgnoringNullFields(this.frodo);

		// Null fields in expected object are ignored, the mysteriousHobbit has null name
		assertThat(this.frodo).isEqualToIgnoringNullFields(mysteriousHobbit);
		// ... But the lenient equality is not reversible !
		try {
			assertThat(mysteriousHobbit).isEqualToIgnoringNullFields(this.frodo);
		} catch (final AssertionError e) {
			logAssertionErrorMessage("isEqualToIgnoringNullFields", e);
		}

		// ------------------------------------------------------------------------------------
		// Lenient equality with field by field comparison expect specified fields
		// ------------------------------------------------------------------------------------

		// Except name and age, frodo and sam both are hobbits, so they are lenient equals ignoring name and age
		assertThat(this.frodo).isEqualToIgnoringGivenFields(this.sam, "name", "age");

		// But not when just age is ignored
		try {
			assertThat(this.frodo).isEqualToIgnoringGivenFields(this.sam, "age");
		} catch (final AssertionError e) {
			logAssertionErrorMessage("isEqualToIgnoringGivenFields", e);
		}

		// Null fields are not ignored, so when expected has null field, actual must have too
		assertThat(mysteriousHobbit).isEqualToIgnoringGivenFields(mysteriousHobbit, "age");

		// ------------------------------------------------------------------------------------
		// Lenient equality with field by field comparison on given fields only
		// ------------------------------------------------------------------------------------

		// frodo and sam both are hobbits, so they are lenient equals on race
		assertThat(this.frodo).isEqualToComparingOnlyGivenFields(this.sam, "race");
		// nested fields are supported
		assertThat(this.frodo).isEqualToComparingOnlyGivenFields(this.sam, "race.name");

		// but not when accepting name and race
		try {
			assertThat(this.frodo).isEqualToComparingOnlyGivenFields(this.sam, "name", "race", "age");
		} catch (final AssertionError e) {
			logAssertionErrorMessage("isEqualToComparingOnlyGivenFields", e);
		}

		// Null fields are not ignored, so when expected has null field, actual must have too
		assertThat(mysteriousHobbit).isEqualToComparingOnlyGivenFields(mysteriousHobbit, "name");

		// Specified fields must exist
		try {
			assertThat(this.frodo).isEqualToComparingOnlyGivenFields(this.sam, "hairColor");
		} catch (final IntrospectionError e) {
			logger.info("isEqualToComparingOnlyGivenFields InstrospectionError message : {}", e.getMessage());
		}
		Assertions.setAllowComparingPrivateFields(true);
	}

	@Test
	public void extracting_object_values() {
		assertThat(this.frodo).extracting(TolkienCharacter::getName,
				character -> character.age,
				character -> character.getRace().getName())
				.containsExactly("Frodo", 33, "Hobbit");
	}

	@Test
	public void has_field_or_property_examples() {
		assertThat(this.frodo).hasFieldOrProperty("age");
		// private field are found unless Assertions.setAllowExtractingPrivateFields(false);
		assertThat(this.frodo).hasFieldOrProperty("notAccessibleField");
		assertThat(this.frodo).hasFieldOrPropertyWithValue("age", 33);
		assertThat(this.frodo).hasFieldOrProperty("race.name");
		assertThat(this.frodo).hasFieldOrPropertyWithValue("race.name", "Hobbit");
	}

	@Test
	public void hasNoNullFieldsOrProperties_examples() {
		assertThat(this.frodo).hasNoNullFieldsOrProperties();
		final TolkienCharacter sam = new TolkienCharacter(null, 38, HOBBIT);
		assertThat(sam).hasNoNullFieldsOrPropertiesExcept("name");
	}

	@Test
	public void extracting_field_or_property_examples() {
		assertThat(this.frodo).extracting("name", "age", "race.name")
				.containsExactly("Frodo", 33, "Hobbit");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void as_and_list_or_map() {
		final List<Ring> elvesRings = newArrayList(vilya, nenya, narya);
		assertThat(elvesRings).as("abc").isNotEmpty();
		assertThat(elvesRings).as("abc").contains(vilya, atIndex(0));
		assertThat(elvesRings).as("abc %d %d", 1, 2).isNotEmpty();

		assertThat(this.ringBearers).as("map").hasSize(4);
		assertThat(this.ringBearers).as("map %s", "size").hasSize(4);
		assertThat(this.ringBearers).as("map %s", "keys").containsOnlyKeys(vilya, nenya, narya, oneRing);
		assertThat(this.ringBearers).as("map").containsOnlyKeys(vilya, nenya, narya, oneRing);

		final Map<String, String> map = ImmutableMap.of("Key1", "Value1", "Key2", "Value2");
		assertThat(map).as("").containsOnlyKeys("Key1", "Key2");

		@SuppressWarnings("rawtypes")
		final Map map1 = new java.util.HashMap<>();
		map1.put("Key1", "Value1");
		map1.put("Key2", "Value2");
		Assertions.assertThat(map1).containsOnlyKeys("Key1", "Key2");
	}

	@Test
	public void field_by_field_comparison_with_specific_comparator_by_type_or_field_examples() {

		final TolkienCharacter olderFrodo = new TolkienCharacter("Frodo", 35, HOBBIT);

		Assertions.setAllowComparingPrivateFields(false); // ignore notAccessibleField in comparison

		// specify a comparator for a single field : age
		assertThat(this.frodo).usingComparatorForFields(new AtPrecisionComparator<>(2), "age")
				.isEqualToComparingFieldByField(olderFrodo)
				.isEqualToComparingOnlyGivenFields(olderFrodo, "age");

		// specify a comparator for a field type : Integer
		assertThat(this.frodo).usingComparatorForType(new AtPrecisionComparator<>(2), Integer.class)
				.isEqualToComparingFieldByField(olderFrodo)
				.isEqualToComparingOnlyGivenFields(olderFrodo, "age");

		// field comparators take precendence over field type comparators
		assertThat(this.frodo).usingComparatorForFields(new AtPrecisionComparator<>(2), "age")
				.usingComparatorForType(new AtPrecisionComparator<>(1), Integer.class)
				.isEqualToComparingFieldByField(olderFrodo);

		final TolkienCharacter elfFrodo = new TolkienCharacter("Frodo", 33, ELF);
		assertThat(this.frodo).usingComparatorForFields(new Comparator<String>() {

			@Override
			public int compare(final String o1, final String o2) {
				return 0;
			}
		}, "race.name").isEqualToComparingOnlyGivenFields(elfFrodo);

		Assertions.setAllowComparingPrivateFields(true);
	}

	@Test
	public void satisfies_examples() {
		assertThat(this.fellowshipOfTheRing.get(0)).satisfies(character -> {
			assertThat(character.getRace()).isEqualTo(HOBBIT);
			assertThat(character.age).isLessThan(200);
		});
	}

	@Test
	public void usingFieldByFieldElementComparatorTest() throws Exception {
		final List<Animal> animals = new ArrayList<>();
		final Bird bird = new Bird("White");
		final Snake snake = new Snake(15);
		animals.add(bird);
		animals.add(snake);

		assertThat(animals).usingFieldByFieldElementComparator()
				.containsExactly(bird, snake);
	}

	@Test
	@Ignore
	public void use_BigDecimal_comparator_with_extracting() {
		// GIVEN
		final Person joe = new Person("Joe", 25);
		joe.setHeight(new BigDecimal("1.80"));

		System.out.println("ddd " + new BigDecimalComparator().compare(new BigDecimal("1.8"), new BigDecimal("1.80")));

		// THEN
		assertThat(joe).matches(p -> p.getName().equals("Joe") && p.getHeight().compareTo(new BigDecimal("1.8")) == 0);
		assertThat(joe).extracting("name", "height")
				.usingComparatorForElementFieldsWithType(new BigDecimalComparator(), BigDecimal.class)
				.containsExactly("Joe", new BigDecimal("1.8"));

	}

	private class Animal {

		private final String name;

		private Animal(final String name) {
			this.name = name;
		}

		@SuppressWarnings("unused")
		public String getName() {
			return this.name;
		}
	}

	private class Bird extends Animal {

		private final String color;

		private Bird(final String color) {
			super("Bird");
			this.color = color;
		}

		@SuppressWarnings("unused")
		public String getColor() {
			return this.color;
		}
	}

	private class Snake extends Animal {

		private final int length;

		private Snake(final int length) {
			super("Snake");
			this.length = length;
		}

		@SuppressWarnings("unused")
		public int getLength() {
			return this.length;
		}
	}
}
