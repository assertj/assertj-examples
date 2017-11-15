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

import java.time.Instant;

import javax.naming.NoPermissionException;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.assertj.examples.AbstractAssertionsExamples;
import org.junit.Test;

public class ExceptionAssertionsExercises extends AbstractAssertionsExamples {

	void alert() {
		final Exception cause = new NoPermissionException("Donald T: What is this red button for ?");
		throw new IllegalStateException("A crazy dude tried to launch nuclear missiles at " + Instant.now() + " !", cause);
	}

	@Test
	public void exercise_1_assertThatThrownBy() {
		// GIVEN
		final ThrowingCallable alertCall = () -> alert();

		// TODO : check the raised exception type and message with assertThatThrownBy
	}

	@Test
	public void exercise_2_assertThatExceptionOfType() {
		// GIVEN
		final ThrowingCallable alertCall = () -> alert();

		// TODO step 1: use assertThatExceptionOfType and check the raised exception cause

		// TODO step 2: use assertThatIllegalArgumentException to check the raised exception
	}

	@Test
	public void exercise_3_BDD_exception_assertion_with_catchThrowable() {
		// GIVEN
		final ThrowingCallable alertCall = () -> alert();
		// WHEN TODO step1: use catchThrowable on alertCall to catch the exception
		// THEN TODO step2: check the caught exception
	}

}
