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
import static org.assertj.core.api.Assumptions.assumeThat;

import org.junit.Test;

public class AssumptionExamples extends AbstractAssertionsExamples {

	@Test
	public void should_be_skipped_as_assumption_is_not_met() {
		assumeThat(this.fellowshipOfTheRing).contains(this.sauron);
		// never executed, the whole test is skipped !
		assertThat(true).isFalse();
	}

}
