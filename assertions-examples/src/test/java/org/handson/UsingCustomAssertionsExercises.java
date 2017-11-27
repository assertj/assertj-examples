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

public class UsingCustomAssertionsExercises extends AbstractAssertionsExamples {

	// the data used are initialized in AbstractAssertionsExamples.

	// looks familiar ? ;)
	public static class Identifier {

		private final String namespace;

		private final String id;

		public Identifier(final String namespace, final String id) {
			this.id = id;
			this.namespace = namespace;
		}

		public String getId() {
			return this.id;
		}

		public String getNamespace() {
			return this.namespace;
		}

		// equals and hash code omitted for brevety

		@Override
		public String toString() {
			return this.id + "/" + this.namespace;
		}

	}

	@Test
	public void exercise_1_use_Identifier_custom_assertions() {
		// TODO step 1: write a IdentifierAssert class providing Identifier custom assertions
		// Hint: use org.assertj.examples.custom.TolkienCharacterAssert as an example

		final Identifier homer = new Identifier("Springfield", "Homer Simpson");
		// TODO step 2: checks homer's id and namespace, the code should looks like:
		// assertThat(homer).hasId(...).hasNamespace(...)

		// TODO step 3: make of the assertion fails to show your a nice error message !
	}

}
