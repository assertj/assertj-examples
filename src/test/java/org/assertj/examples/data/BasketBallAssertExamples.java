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
 * Copyright 2012-2013 the original author or authors.
 */
package org.assertj.examples.data;

import org.assertj.examples.AbstractAssertionsExamples;
import org.assertj.examples.exception.NameException;
import org.junit.Test;

/**
 * 
 * Shows some example of a custom assertion class: {@link org.assertj.examples.custom.TolkienCharacterAssert} that
 * allows us to make assertions specific to {@link org.assertj.examples.data.TolkienCharacter}.
 * 
 * @author Joel Costigliola
 */
public class BasketBallAssertExamples extends AbstractAssertionsExamples {

  @Test
  public void basketBallPlayer_assertion_rethrow_getter_exception() throws NameException {
    // since BasketBallPlayer.getName throws NameException so does BasketBallPlayerAssert.hasName
    BasketBallPlayerAssert.assertThat(rose).hasName(new Name("Derrick", "Rose"));
  }
}
