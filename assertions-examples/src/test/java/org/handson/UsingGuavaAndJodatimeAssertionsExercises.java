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

import org.joda.time.DateTime;
import org.junit.Test;

import com.google.common.collect.Range;

public class UsingGuavaAndJodatimeAssertionsExercises {

	@Test
	public void exercise_1_guava_range_assertions() {
		// GIVEN
		final Range<Integer> range = Range.closed(10, 12);
		// THEN
		// TODO : check that the range content and lower endpoint
		// Hint: you need to import org.assertj.guava.api.Assertions.assertThat
	}

	@Test
	public void exercise_2_joda_assertions() {
		// GIVEN
		final DateTime firstOfJune2000 = new DateTime("2000-01-01");
		// THEN
		// TODO : check that firstOfJune2000 is before today, use the convenient isEqualTo(String)
		// Hint: you need to import org.assertj.jodatime.api.Assertions.assertThat
	}

	@Test
	public void exercise_3_mix_joda_assertions() {
		// GIVEN
		final String dateTimeAsString = "2000-01-01";
		final DateTime firstOfJune2000 = new DateTime(dateTimeAsString);
		// THEN
		// TODO : check that dateTimeAsString is not empty
		// TODO : check that firstOfJune2000 is before 2011-11-11
	}

}
