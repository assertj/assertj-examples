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
import static org.assertj.core.api.Assertions.withMarginOf;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class DurationAssertionsExamples {

  @Test
  public void duration_assertions_examples() {
    assertThat(Duration.ofDays(5)).hasDays(5);
    assertThat(Duration.ofHours(15)).hasHours(15);

    assertThat(Duration.ofMinutes(65)).hasMinutes(65);
    assertThat(Duration.ofSeconds(250)).hasSeconds(250);

    assertThat(Duration.ofMillis(250)).hasMillis(250);
    assertThat(Duration.ofNanos(145)).hasNanos(145);

    assertThat(Duration.ofHours(5)).isPositive();
    assertThat(Duration.ofMinutes(-15)).isNegative();
    assertThat(Duration.ZERO).isZero();

    assertThat(Duration.ofMinutes(15)).isCloseTo(Duration.ofMinutes(10), withMarginOf(Duration.ofMinutes(5)));
  }

}
