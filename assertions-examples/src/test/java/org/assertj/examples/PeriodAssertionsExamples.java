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

import java.time.Period;

import org.junit.jupiter.api.Test;

public class PeriodAssertionsExamples {

  @Test
  public void duration_assertions_examples() {

    assertThat(Period.ofDays(5)).hasDays(5);
    assertThat(Period.ofMonths(15)).hasMonths(15);
    assertThat(Period.ofYears(100)).hasYears(100);

    assertThat(Period.of(1, 2, 3)).hasYears(1)
                                  .hasMonths(2)
                                  .hasDays(3)
                                  .isPositive();

    assertThat(Period.ofDays(-5)).hasDays(-5)
                                 .isNegative();
  }

}
