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
package org.assertj.examples.custom;

import org.assertj.core.api.SoftAssertionsProvider;
import org.assertj.examples.data.TolkienCharacter;

/**
 * An example of an interface providing custom assertions.
 * <p>
 * This is not enough to run the assertions, you need another class acting as your aggregated soft assertions entry point, see {@link UberSoftAssertions}.
 */
public interface TolkienSoftAssertionsProvider extends SoftAssertionsProvider {

  // custom assertions
  default TolkienCharacterAssert assertThat(TolkienCharacter actual) {
    return proxy(TolkienCharacterAssert.class, TolkienCharacter.class, actual);
  }

}
