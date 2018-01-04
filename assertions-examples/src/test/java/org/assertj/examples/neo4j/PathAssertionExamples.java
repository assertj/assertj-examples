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

import static org.assertj.neo4j.api.Assertions.assertThat;

import org.assertj.examples.data.neo4j.DragonBallGraphRepository;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public class PathAssertionExamples extends Neo4jAssertionExamples {

  @Test
  public void path_assertion_examples() {
    try (Transaction ignored = graphDatabase().beginTx()) {
      // let us find the shortest path between Bulma and Master Roshi
      DragonBallGraphRepository dragonBallGraphRepository = dragonBallGraphRepository();
      Path bulmaToMasterRoshiPath = dragonBallGraphRepository.findShortestPathBetween("Bulma", "Master Roshi");

      // you can test several Path properties such as length,
      // start/end node and last relationship
      final Node bulmaNode = dragonBallGraphRepository.findUniqueCharacter("Bulma");
      final Node masterRoshiNode = dragonBallGraphRepository.findUniqueCharacter("Master Roshi");
      final Relationship sonGokuTraining = dragonBallGraphRepository.findUniqueTraining("Son Goku");
      assertThat(bulmaToMasterRoshiPath)
        .hasLength(3)
        .startsWithNode(bulmaNode)
        .endsWithNode(masterRoshiNode)
        .endsWithRelationship(sonGokuTraining)

          // you can enjoy the usual assertj-core assertions (Path is an Iterable) ;-)
        .doesNotContainNull();
    }
  }

  @Test
  public void path_assertion_examples_introduced_in_2_0_0_and_2_0_1() throws Exception {
    try (Transaction ignored = graphDatabase().beginTx()) {
      // let us find the shortest path between Son Goku and Dr. Flappe
      DragonBallGraphRepository dragonBallGraphRepository = dragonBallGraphRepository();
      Path sonGokuToDoctorFlappe = dragonBallGraphRepository.findShortestPathBetween("Son Goku", "Dr. Flappe");
      Node doctorGero = dragonBallGraphRepository.findUniqueCharacter("Dr. Gero");
      Node tienShinhan = dragonBallGraphRepository.findUniqueCharacter("Tien Shinhan");
      final Relationship masterShenTraining = dragonBallGraphRepository.findUniqueTraining("Master Shen");
      // enjoy the negation assertions!
      assertThat(sonGokuToDoctorFlappe)
          .doesNotStartWithNode(doctorGero)
          .doesNotEndWithNode(tienShinhan)
          .doesNotEndWithRelationship(masterShenTraining);
    }
  }
}
