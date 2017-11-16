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

import org.assertj.examples.AbstractAssertionsExamples;
import org.junit.Test;

public class UsingConditionsExercises extends AbstractAssertionsExamples {

	// the data used are initialized in AbstractAssertionsExamples.

	@Test
	public void exercise_1_simple_condition() {
		// TODO step 1 write a Condition to check that a TolkienCharacter is a young Hobbit (less than < 30 years old).
		// TODO step 2 check that merry is a young hobbit but Sam is not
		// TODO step 3 adjust the condition description to get a nice error message when the assertion fails
	}

	@Test
	public void exercise_2_combine_conditions() {
		// TODO write an assertion checking that a TolkienCharacter is either an Elf or Maia, each Race cehck should be a simple Condition.
	}

	@Test
	public void exercise_3_collections_conditions() {
		// TODO : check that the fellowshipOfTheRing has at least two old hobbits and no more than two men
	}

}
