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

import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.examples.data.Ring.narya;
import static org.assertj.examples.data.Ring.nenya;
import static org.assertj.examples.data.Ring.vilya;

import java.util.List;

import org.assertj.examples.AbstractAssertionsExamples;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.Test;

public class IterableAssertionsExercises extends AbstractAssertionsExamples {

	@Test
	public void exercise_1_basic_collection_assertions() {

		// we are going to use this.fellowshipOfTheRing, a List initialized with famous TokienCharacter

		// TODO Step 1: check that this.fellowshipOfTheRing contains frodo, gandalf but not sauron !

		// TODO Step 2: check this.fellowshipOfTheRing is not empty and has a size of ... 9 (but you knew that, right ?)

		final Iterable<Ring> elvesRings = newArrayList(vilya, nenya, narya);
		// TODO Step 3: use containsOnly and containsExactly to check elvesRings content,
		// what happens when you give containsExactly the correct content in a different order
	}

	@Test
	public void exercise_2_using_the_extracting_and_filter_features_when_asserting_collections() {
		// TODO Step 1: extract the names of all TokienCharacter in fellowshipOfTheRing, check that it contains "Boromir" and "Frodo"
		// hint: there are different ways of doing this ;-)

		// TODO Step 2: filter fellowshipOfTheRing to only keep Hobbits, check the content of the filtered collection

		// TODO Step 3: extract the names and the race of all TokienCharacter in fellowshipOfTheRing, check the content
		// hint: use tuple to group the expected values, shoulld look like: tuple("Sam", 38)

		// Optional TODO Step 4: extract the names and the race's name of all non Hobbit TokienCharacter in fellowshipOfTheRing
		// check the content
	}

	@Test
	public void exercise_3_lambdas_and_collection_assertions() {

		final List<TolkienCharacter> hobbits = newArrayList(this.frodo, this.sam, this.pippin);
		// TODO Step 1: use allMatch to verify that hobbits contains only Hobbit characters.

		// TODO Step 2: use allSatisfy to verify that hobbits contains only Hobbit characters and none ot the names contains "alf".
		// What is the advantage of allSatisfy over allMatch ? (hint: fail the assertions)

		// TODO Step 3: use anySatisfy to check that hobbits contains at least one Hobbit whose age is < 30
	}

}
