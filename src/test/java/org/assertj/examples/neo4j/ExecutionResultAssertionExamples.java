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
package org.assertj.examples.neo4j;

import org.junit.Test;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Transaction;

import static org.assertj.neo4j.api.Assertions.assertThat;

public class ExecutionResultAssertionExamples extends Neo4jAssertionExamples {

  @Test
  public void execution_result_assertion_examples() {
    try (Transaction tx = graphDB.beginTx()) {
      // as in NodeAssertionExamples, let us find disciples of Master Roshi
      // this time, however, the raw results will be returned
      ExecutionResult result = dragonBallGraph.discipleRowsOf("Master Roshi");

      // this consumes the entire iterator wrapped by ExecutionResult
      // therefore, you cannot chain this assertion with others
      assertThat(result).hasSize(3);

      // let us now find disciples of Master Shen
      result = dragonBallGraph.discipleRowsOf("Master Shen");

      // the following assertions can be chained
      // *so long as* the specified index increases
      assertThat(result)
        .containsColumnNamesAtRow(0, "disciples", "master")
        .containsColumnNamesAtRow(1, "disciples", "master");

      tx.close();
    }
  }
}
