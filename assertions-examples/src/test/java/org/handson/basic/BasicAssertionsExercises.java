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
package org.handson.basic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.examples.data.Race.ELF;
import static org.hamcrest.CoreMatchers.equalTo;

import org.assertj.examples.AbstractAssertionsExamples;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public class BasicAssertionsExercises extends AbstractAssertionsExamples {

	// the data used are initialized in AbstractAssertionsExamples.

	// TODO exercise 0: configure Eclipse to be able to directly use AssertJ assertThat
	// hint: http://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html#ide-automatic-static-import

	@Test
	public void exercise_1_describe_your_assertion_with_as() {
		// this assertion obviously fails (frodo is a hobbit !) but the error message is not really clear
		// TODO make it clearer by describing the context useing .as() before the isTrue() assertion
		assertThat(this.frodo.getRace().equals(ELF)).isTrue();
	}

	@Test
	public void exercise_2_chain_assertions() {
		// GIVEN
		final String quote = "Real stupidity beats artificial intelligence all the time";
		// THEN
		// TODO check that it starts with "Real" ands with "time" and contains "stupidity"
		// bonus points if you know who said that :)
	}

	@Test
	public void exercise_3_dont_misuse_assertj() {
		final String actual = "";
		final String expected = "";
		// BAD USAGE : no assertion is performed, one must call an assertion after assertThat(expression).
		assertThat(actual.equals(expected)); // does not do anything
		assertThat(false == true); // won't fail since no assertion is performed.
		// assertThat(false).isTrue(); // this one fails as expected.
		// TODO : fix the previous assertion
		// Optional TODO : use findbugs to detect this incorrect usage (and ask your manager for a well deserved raise).

		// BAD USAGE : as() has no effect, it must be called before the assertion
		assertThat(actual).isEqualTo(expected).as("description");

		// TODO : show the proper usage of as(), verify that it works by failing the assertion
	}

	@Test
	public void exercise_4_helpful_error_messages() {
		// these dices have the same toString()
		final Dice dice = new Dice();
		final AlwaysLosingDice losingDice = new AlwaysLosingDice();
		// Hamcrest gives an unhelpful error message ...
		MatcherAssert.assertThat(dice, equalTo(losingDice));
		// TODO check assertj error message, is it better ?
		assertThat(dice).isEqualTo(losingDice);
	}

	public class Dice {

		@Override
		public String toString() {
			return "Dice";
		}
	}

	public class AlwaysLosingDice {

		@Override
		public String toString() {
			// To trick you, I pretend I'm a normal dice - AH AH AH !
			return "Dice";
		}
	}

}
