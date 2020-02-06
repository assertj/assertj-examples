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
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import org.junit.jupiter.api.Test;

/**
 * Fail usage examples.
 *
 * @author Joel Costigliola
 */
public class FailUsageExamples extends AbstractAssertionsExamples {

  @Test
  public void fail_usage_examples() {

    assertThat(fellowshipOfTheRing).hasSize(9);

    // here's the typical pattern to use Fail :
    try {
      fellowshipOfTheRing.get(9); // argggl !
      // we should not arrive here => use fail to expresses that
      // if IndexOutOfBoundsException was not thrown, test would fail the specified message
      fail("IndexOutOfBoundsException expected because fellowshipOfTheRing has only 9 elements");
    } catch (IndexOutOfBoundsException e) {
      assertThat(e).hasMessageContaining("Index")
                   .hasMessageContaining("9");
    }

    // Warning : don't catch Throwable in catch clause as it would also catch AssertionError thrown by fail method

    // another way to do the same thing
    try {
      fellowshipOfTheRing.get(9); // argggl !
      // if IndexOutOfBoundsException was not thrown, test would fail with message :
      // "Expected IndexOutOfBoundsException to be thrown"
      failBecauseExceptionWasNotThrown(IndexOutOfBoundsException.class);
    } catch (IndexOutOfBoundsException e) {
      assertThat(e).hasMessageContaining("Index")
                   .hasMessageContaining("9");
    }
  }
}
