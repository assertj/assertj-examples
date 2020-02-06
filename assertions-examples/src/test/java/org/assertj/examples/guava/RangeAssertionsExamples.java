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
package org.assertj.examples.guava;

import static org.assertj.guava.api.Assertions.assertThat;

import org.assertj.examples.AbstractAssertionsExamples;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Range;

/**
 * {@link Range} assertions example.
 * 
 * @author Joel Costigliola
 */
public class RangeAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void range_assertions_examples() {

    Range<Integer> range = Range.closed(10, 12);

    assertThat(range).isNotEmpty()
                     .contains(10, 11, 12)
                     .hasClosedLowerBound()
                     .hasLowerEndpointEqualTo(10);

  }

}
