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

public class UsingAssumptionsExercises extends AbstractAssertionsExamples {

	// the data used are initialized in AbstractAssertionsExamples.

	@Test
	public void exercise_1_run_test_if_in_Pacific_Auckland_timezone_only() {
		// TODO step 1: write an assumption verifying that we are in the Pacific/Auckland timezone
		// hint: start writing assumptions with Assumptions.assumeThat(...
		// hint: use ZoneId.systemDefault().getId(); to get the timezone
		// TODO step 2: write some code proving the test is executed (e.g. failing assertion or a log)
		// TODO step 3: change the assumption so that it is not met anymore, run the test again, what is the result ?
	}

	@Test
	public void exercise_2_use_a_complex_assumption() {
		// TODO only run the following code if fellowshipOfTheRing is composed of a hobbit named frodo.
		System.out.println("We wants it. We needs it. Must have the precious. They stole it from us. Sneaky little Hobbitses");
	}

	// assumptions doc: http://joel-costigliola.github.io/assertj/assertj-core-news.html#assertj-core-2.9.0-assumptions
}
