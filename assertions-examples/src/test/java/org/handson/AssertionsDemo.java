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
package org.handson;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.condition.AnyOf.anyOf;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.examples.data.Race.HOBBIT;
import static org.assertj.examples.data.Ring.dwarfRing;
import static org.assertj.examples.data.Ring.manRing;
import static org.assertj.examples.data.Ring.narya;
import static org.assertj.examples.data.Ring.nenya;
import static org.assertj.examples.data.Ring.oneRing;
import static org.assertj.examples.data.Ring.vilya;

import java.util.Comparator;
import java.util.List;

import org.assertj.core.api.Condition;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.assertj.examples.AbstractAssertionsExamples;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.Test;

public class AssertionsDemo extends AbstractAssertionsExamples {

	// the data used are initialized in AbstractAssertionsExamples.

	@Test
	public void basic_assertions_examples() {

		assertThat(this.frodo.age).isEqualTo(33);

		assertThat(this.frodo.getName())
				.as("Checking Mr Frodo's name")
				.isNotNull()
				.isEqualTo("Frodo")
				.isNotEqualTo("Frodon");

		assertThat(this.frodo).isInstanceOf(TolkienCharacter.class)
				.isIn(this.fellowshipOfTheRing)
				.isIn(this.sam, this.frodo, this.pippin);

		assertThat(this.sauron).isNotIn(this.fellowshipOfTheRing);
	}

	@Test
	public void collection_assertions_examples() {

		final Iterable<Ring> elvesRings = newArrayList(vilya, nenya, narya);

		assertThat(elvesRings)
				.isNotEmpty()
				.hasSize(3)
				.contains(nenya)
				.doesNotContain(oneRing)
				// order does not matters
				.containsOnly(nenya, vilya, narya)
				// order matters
				.containsExactly(vilya, nenya, narya);

		// you can also check the start or end of your collection/iterable
		final Iterable<Ring> allRings = newArrayList(oneRing, vilya, nenya, narya, dwarfRing, manRing);
		assertThat(allRings)
				.startsWith(oneRing, vilya)
				.endsWith(dwarfRing, manRing)
				.containsAll(elvesRings)
				.containsSequence(nenya, narya, dwarfRing)
				.containsSubsequence(oneRing, nenya, dwarfRing)
				.doesNotContainSequence(vilya, nenya, oneRing, narya);

		// uncomment to show how a collection with many items is displayed
		// assertThat(this.fellowshipOfTheRing).contains(this.sauron);
	}

	@Test
	public void collection_filter_and_extracting_examples() {

		assertThat(this.fellowshipOfTheRing)
				.filteredOn(tc -> tc.getRace().equals(HOBBIT))
				.containsOnly(this.sam, this.frodo, this.pippin, this.merry);

		assertThat(this.fellowshipOfTheRing)
				.filteredOn(tc -> tc.getRace().equals(HOBBIT))
				.extracting(tc -> tc.getName())
				.containsOnly("Sam", "Frodo", "Pippin", "Merry");

	}

	@Test
	public void exceptions_assertions_examples() {

		// basic testing way
		try {
			boom();
		} catch (final Exception exception) {
			// you can check exception type
			assertThat(exception)
					.isInstanceOf(IllegalStateException.class)
					.hasMessageStartingWith("boo")
					.hasMessageContaining("oo")
					.hasMessageEndingWith("!")
					.hasMessage("boom!")
					.hasMessage("%sm!", "boo")
					.hasMessageMatching("boo..")
					.hasCauseInstanceOf(RuntimeException.class)
					.hasStackTraceContaining("let's push that red button");
		}

		final ThrowingCallable callable = () -> boom();
		assertThatThrownBy(callable)
				.isInstanceOf(IllegalStateException.class)
				.hasMessageStartingWith("boo");

		// assertThatExceptionOfType
		assertThatExceptionOfType(IllegalStateException.class)
				.isThrownBy(callable)
				.withMessage("boom!")
				.withCauseInstanceOf(RuntimeException.class)
				.withMessageContaining("boo");

		// use predefined falvour for common exception
		assertThatIllegalStateException()
				.isThrownBy(callable)
				.withMessage("boom!")
				.withMessageContaining("boo");

		// BDD testing way, separating the assertions from the code

		// WHEN
		final Throwable thrown = catchThrowable(callable);

		// THEN
		assertThat(thrown)
				.isInstanceOf(IllegalStateException.class)
				.hasMessageStartingWith("boo")
				.hasMessageContaining("oo")
				.hasMessageEndingWith("!")
				.hasMessage("boom!")
				.hasNoSuppressedExceptions()
				.hasCauseInstanceOf(RuntimeException.class)
				.hasStackTraceContaining("let's push that red button");

		// check that code does not throw anything
		// @formatter:off
		assertThatCode(() -> { }).doesNotThrowAnyException();
		// @formatter:on
	}

	void boom() {
		final RuntimeException cause = new RuntimeException("let's push that red button");
		throw new IllegalStateException("boom!", cause);
	}

	@Test
	public void conditions_example() {
		final List<String> jedis = asList("Luke", "Yoda", "Obiwan");
		final List<String> siths = asList("Sidious", "Vader", "Plagueis");

		final Condition<String> jedi = new Condition<>(jedis::contains, "jedi");
		final Condition<String> sith = new Condition<>(siths::contains, "sith");
		assertThat("Yoda").is(jedi);
		assertThat("Vader").isNot(jedi).is(sith).is(anyOf(jedi, sith));

		// alias
		final Condition<String> jediPowers = new Condition<>(jedis::contains, "jedi powers");
		assertThat("Yoda").has(jediPowers);
		assertThat("Sponge Bob").doesNotHave(jediPowers);

		// collections

		assertThat(asList("Luke", "Yoda")).are(jedi);
		assertThat(asList("Luke", "Yoda")).have(jediPowers);

		assertThat(asList("Chewbacca", "Solo")).areNot(jedi);
		assertThat(asList("Chewbacca", "Solo")).doNotHave(jediPowers);

		final List<String> characters = asList("Luke", "Yoda", "Chewbacca");
		assertThat(characters).areAtLeast(2, jedi);
		assertThat(characters).haveAtLeast(2, jediPowers);

		assertThat(characters).areAtMost(2, jedi);
		assertThat(characters).haveAtMost(2, jediPowers);

		assertThat(characters).areExactly(2, jedi);
		assertThat(characters).haveExactly(2, jediPowers);

	}

	@Test
	public void using_comparators() {
		// standard comparison : frodo is not equal to sam ...
		assertThat(this.frodo).isNotEqualTo(this.sam);
		// ... but if we compare only character's race frodo is equal to sam
		final Comparator<TolkienCharacter> raceComparator = (tc1, tc2) -> tc1.getRace().compareTo(tc2.getRace());
		assertThat(this.frodo)
				.usingComparator(raceComparator)
				.isEqualTo(this.sam)
				.isIn(this.merry, this.pippin, this.sam);

		// comparators on collection
		assertThat(this.fellowshipOfTheRing)
				.usingElementComparator(raceComparator)
				// Succeeds because Sauron is a Maia like Gandalf
				.contains(this.gandalf, this.sauron);
	}

}
