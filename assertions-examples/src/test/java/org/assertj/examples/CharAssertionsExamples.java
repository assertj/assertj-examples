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

import org.junit.jupiter.api.Test;

/**
 * char assertions examples.<br>
 *
 * @author Joel Costigliola
 */
public class CharAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void char_assertions_examples() throws Exception {

    // equals / no equals assertions
    assertThat('a').isNotEqualTo('A');

    // <= < > >= assertions
    assertThat('a').isGreaterThan('A').isGreaterThanOrEqualTo('a');
    assertThat('A').isLessThan('a').isLessThanOrEqualTo('a');

    // case assertions
    assertThat('A').isUpperCase();
    assertThat('a').isLowerCase();
  }

  @Test
  public void char_assertions_with_custom_comparison_examples() {
    assertThat('a').usingComparator(caseInsensitiveComparator).isEqualTo('A');
    assertThat(Character.valueOf('a')).usingComparator(caseInsensitiveComparator).isEqualTo(Character.valueOf('A'));
  }

}
