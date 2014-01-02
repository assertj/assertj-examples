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
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Transaction;

import static org.assertj.neo4j.api.Assertions.assertThat;

public class PathAssertionExamples extends Neo4jAssertionExamples {

   @Test
   public void path_assertion_examples() {
      try (Transaction tx = graphDB.beginTx()) {
          // let us find the shortest path between Bulma and Master Roshi
          Path bulmaToMasterRoshiPath = dragonBallGraph.findShortestPathBetween("Bulma", "Master Roshi");

          // you can test several Path properties such as length,
          // start/end node and last relationship
          assertThat(bulmaToMasterRoshiPath)
              .hasLength(3)
              .startsWithNode(dragonBallGraph.findCharacter("Bulma"))
              .endsWithNode(dragonBallGraph.findCharacter("Master Roshi"))
              .endsWithRelationship(dragonBallGraph.findTrainingFrom("Son Goku"))

          // you can enjoy the usual assertj-core assertions (Path is an Iterable) ;-)
              .doesNotContainNull();

          tx.close();
      }
   }
}
