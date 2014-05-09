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
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.examples;

import static org.assertj.examples.data.Assertions.assertThat;

import org.junit.Test;

import org.assertj.examples.data.Name;
import org.assertj.examples.exception.NameException;

public class GeneratedStandardAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void generated_bdd_assertions_example() throws NameException {
    // use the generated assertions
    assertThat(rose).hasName(new Name("Derrick", "Rose")).hasNoTeamMates();
  }

}
