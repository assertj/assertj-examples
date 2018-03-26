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

public class UsingComparatorsExercises extends AbstractAssertionsExamples {

	// the data used are initialized in AbstractAssertionsExamples.

	@Test
	public void exercise_1_use_a_specific_comparator() {
		// TODO when using a org.assertj.examples.data.Race comparator, check that:
		// - aragorn is equal to boromir
		// - aragorn is in (frodo, boromir, gandalf) group
	}

	@Test
	public void exercise_2_use_a_specific_comparator_on_collection_elements() {
		// TODO reuse the race comparator and check that the fellowshipOfTheRing contains sauron !
	}

	@Test
	public void optional_exercise_3_recursive_field_by_field_comparison() {
		// Optional as this exercise is not as straightforward as the others one, that's for grown up ;-)
		// TODO : play with isEqualToComparingFieldByFieldRecursively with objects !
		// TODO : play with usingRecursiveFieldByFieldElementComparator collections !
	}

	// if you struggle finding how ot use comparators, have a look at:
	// http://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html#custom-comparison-strategy
}
