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
package org.assertj.examples.neo4j;

import org.junit.jupiter.api.Test;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.neo4j.api.Assertions.assertThat;

public class NodeAssertionExamples extends Neo4jAssertionExamples {

  @Test
  public void node_assertion_examples() {
    try (Transaction ignored = graphDatabase().beginTx()) {
      // let us find the disciples of Master Roshi persisted in Neo4j
      Iterable<Node> disciples = dragonBallGraphRepository().findDisciplesOfMaster("Master Roshi");

      // you can enjoy the usual assertj-core assertions ;-)
      assertThat(disciples).hasSize(3);

      // you can benefit from all PropertyContainer assertions
      // when you give a Node instance
      Node firstDisciple = disciples.iterator().next();
      assertThat(firstDisciple)
          .hasPropertyKey("name")
          .hasProperty("name", "Krillin")
          .doesNotHavePropertyKey("firstName")
          .doesNotHaveProperty("name", "Bulma")

          // you can test against node labels: their String representation or
          // the equivalent Label object
          .hasLabel("Character")
          .hasLabel(Label.label("Hero"))
          .doesNotHaveLabel("Villain")
          .doesNotHaveLabel(Label.label("Master"));

      // and you can enjoy the same error message mechanism from assertj-core !
      try {
        assertThat(firstDisciple)
            .as("[check %s's name]", firstDisciple.getProperty("name"))
            .doesNotHaveProperty("name", "Krillin");
      } catch (AssertionError ae) {
        assertThat(ae).hasMessageContaining(
            "not to have property with key:\n" +
            "  <\"name\">\n" +
            "and value:\n" +
            "  <\"Krillin\">\n" +
            "but actually found such property ");
      }
    }

    // just check that we can use standard assertions along with Neo4j ones
    assertThat("hello world").startsWith("hello");
  }
}
