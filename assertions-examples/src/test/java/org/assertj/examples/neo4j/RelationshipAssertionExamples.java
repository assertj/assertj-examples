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

import org.assertj.examples.data.neo4j.DragonBallGraphRepository;
import org.junit.jupiter.api.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

import java.util.Collection;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.neo4j.api.Assertions.assertThat;

public class RelationshipAssertionExamples extends Neo4jAssertionExamples {

  @Test
  public void relationship_assertion_examples() {
    try (Transaction ignored = graphDatabase().beginTx()) {
      // let us find the findFusions between Dragon Ball characters persisted in Neo4j
      DragonBallGraphRepository dragonBallGraphRepository = dragonBallGraphRepository();
      Collection<Relationship> fusions = dragonBallGraphRepository.findFusions();

      // you can enjoy the usual assertj-core assertions ;-)
      assertThat(fusions)
          .hasSize(4)
          .filteredOn(RelationshipAssertionExamples::isUselessFusion)
          .hasSize(2);

      // you can chain assertions on relationship types: their String representation
      // or the equivalent RelationshipType instance
      Iterator<Relationship> iterator = fusions.iterator();
      Relationship gogeta = iterator.next();
      assertThat(gogeta)
          .hasType("IN_FUSION_WITH")
          .hasType(RelationshipType.withName("IN_FUSION_WITH"))
          .doesNotHaveType("HAS_WORKED_FOR")
          .doesNotHaveType(RelationshipType.withName("HAS_WORKED_FOR"));

      // you can benefit from all PropertyContainer assertions
      // when you give a Relationship instance
      Relationship prillin = iterator.next();
      Node krillin = dragonBallGraphRepository.findUniqueCharacter("Krillin");
      Node piccolo = dragonBallGraphRepository.findUniqueCharacter("Piccolo");

      assertThat(prillin)
          .hasPropertyKey("fusion_character_name")
          .hasProperty("fusion_character_name", "Prilin")
          .hasProperty("useless", true)
          .doesNotHaveProperty("useless", false)
          .doesNotHavePropertyKey("something")

          // you can test relationship start/end nodes
          .startsOrEndsWithNode(krillin)
          .startsWithNode(krillin)
          .startsOrEndsWithNode(piccolo)
          .endsWithNode(piccolo);

      // and you can enjoy the same error message mechanism from assertj-core !
      try {
        assertThat(gogeta).as("[check %s's start/end node]", gogeta.getProperty("fusion_character_name"))
                          .startsOrEndsWithNode(krillin);
      } catch (AssertionError ae) {
        assertThat(ae).hasMessageContaining("[[check Gogeta's start/end node]] \n" +
                                            "Expecting relationship with ID")
                      .hasMessageContaining("and type: IN_FUSION_WITH\n" +
                                            "to either start or end with node:");
      }
    }
  }

  @Test
  public void relationship_assertion_examples_added_in_2_0_0() throws Exception {
    try (Transaction ignored = graphDatabase().beginTx()) {
      DragonBallGraphRepository dragonBallGraphRepository = dragonBallGraphRepository();
      Relationship masterRoshiTraining = dragonBallGraphRepository.findUniqueTraining("Master Roshi");
      Node chiaotzu = dragonBallGraphRepository.findUniqueCharacter("Chiaotzu");
      Node masterShen = dragonBallGraphRepository.findUniqueCharacter("Master Shen");
      // enjoy the new negation assertions!
      assertThat(masterRoshiTraining)
          .doesNotStartWithNode(chiaotzu)
          .doesNotEndWithNode(masterShen);
    }

  }

  private static boolean isUselessFusion(Relationship rel) {
    return (boolean) rel.getProperty("useless", false);
  }
}
