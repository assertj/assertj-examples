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

import com.google.common.base.Predicate;
import org.junit.Test;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import java.util.Iterator;

import static com.google.common.collect.Iterables.filter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.examples.neo4j.RelationshipAssertionExamples.UselessFusion.FUNNY_ONLY;
import static org.assertj.neo4j.api.Assertions.assertThat;

public class RelationshipAssertionExamples extends Neo4jAssertionExamples {

    @Test
    public void relationship_assertions_examples() {
        try (Transaction tx = graphDB.beginTx()) {
            // let us find the fusions between Dragon Ball characters persisted in Neo4j
            Iterable<Relationship> fusions = dragonBallGraph.fusions();

            // you can enjoy the usual assertj-core assertions ;-)
            assertThat(fusions).hasSize(4);
            Iterable<Relationship> funnyFusions = filter(fusions, FUNNY_ONLY());
            assertThat(funnyFusions).hasSize(2);

            // you can chain assertions on relationship types: their String representation
            // or the equivalent RelationshipType instance
            Iterator<Relationship> iterator = funnyFusions.iterator();
            Relationship veku = iterator.next();

            assertThat(veku)
                .hasType("IN_FUSION_WITH")
                .hasType(DynamicRelationshipType.withName("IN_FUSION_WITH"))
                .doesNotHaveType("HAS_WORKED_FOR")
                .doesNotHaveType(DynamicRelationshipType.withName("HAS_WORKED_FOR"));

            // you can benefit from all PropertyContainer assertions
            // when you give a Relationship instance
            Relationship krillinPiccoloFusion = iterator.next();
            Node krillin = dragonBallGraph.findCharacter("Krillin");
            Node piccolo = dragonBallGraph.findCharacter("Piccolo");

            assertThat(krillinPiccoloFusion)
                .hasPropertyKey("into")
                .hasProperty("into", "Prilin")
                .hasProperty("useless", true)
                .doesNotHaveProperty("useless", false)
                .doesNotHavePropertyKey("name")

            // you can test relationship start/end nodes
                .startsOrEndsWithNode(krillin)
                .startsWithNode(krillin)
                .startsOrEndsWithNode(piccolo)
                .endsWithNode(piccolo);


            // and you can enjoy the same error message mechanism from assertj-core !
            try {
                assertThat(veku).as("[check %s's start/end node]", veku.getProperty("into"))
                    .startsOrEndsWithNode(krillin);
            }
            catch (AssertionError ae) {
                assertThat(ae).hasMessage("[[check Veku's start/end node]] \n" +
                    "Expecting:\n" +
                    "  <Relationship[19]>\n" +
                    "to either start or end with node:\n" +
                    "  <Node[6]>\n"
                );
            }

            tx.close();
        }
    }

    static class UselessFusion implements Predicate<Relationship> {

        private UselessFusion() {}

        public static UselessFusion FUNNY_ONLY() {
            return new UselessFusion();
        }

        @Override
        public boolean apply(Relationship input) {
            if (input == null) {
                return false;
            }
            return (boolean) input.getProperty("useless", Boolean.FALSE);
        }
    }
}
