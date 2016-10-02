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
import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * {@link Table} assertions example.
 * 
 * @author Joel Costigliola
 */
public class TableAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void table_assertions_examples() {

    Table<Integer, String, String> bestMovies = HashBasedTable.create();

    bestMovies.put(1970, "Palme d'Or", "M.A.S.H");
    bestMovies.put(1994, "Palme d'Or", "Pulp Fiction");
    bestMovies.put(2008, "Palme d'Or", "Entre les murs");
    bestMovies.put(2000, "Best picture Oscar", "American Beauty");
    bestMovies.put(2011, "Goldene Bär", "A Separation");

    assertThat(bestMovies).hasRowCount(5).hasColumnCount(3).hasSize(5)
                          .containsValues("American Beauty", "A Separation", "Pulp Fiction")
                          .containsCell(1994, "Palme d'Or", "Pulp Fiction")
                          .containsColumns("Palme d'Or", "Best picture Oscar", "Goldene Bär")
                          .containsRows(1970, 1994, 2000, 2008, 2011);

    try {
      assertThat(bestMovies).containsValues("toto");
    } catch (AssertionError e) {
      logAssertionErrorMessage("hasRowCount", e);
    }

  }

}
