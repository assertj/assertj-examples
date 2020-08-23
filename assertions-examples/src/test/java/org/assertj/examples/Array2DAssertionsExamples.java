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
import static org.assertj.core.data.Index.atIndex;

import org.junit.jupiter.api.Test;

public class Array2DAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void array2D_assertions_examples() {
    String[][] abc = { { "a", "b", "c" }, { "1", "2", "3" } };
    int[][] numbers = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
    // 2D generic array
    assertThat(abc).hasDimensions(2, 3)
                   .isNotEmpty()
                   .contains(new String[] { "1", "2", "3" }, atIndex(1))
                   .isDeepEqualTo(new String[][] { { "a", "b", "c" }, { "1", "2", "3" } })
                   .hasSameDimensionsAs(numbers);
    // 2D primtive arrays
    assertThat(numbers).hasDimensions(2, 3)
                       .isNotEmpty()
                       .contains(new int[] { 1, 2, 3 }, atIndex(0))
                       .hasSameDimensionsAs(abc)
                       .isDeepEqualTo(new int[][] { { 1, 2, 3 }, { 4, 5, 6 } });

  }
}
