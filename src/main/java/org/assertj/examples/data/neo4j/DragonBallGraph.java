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
package org.assertj.examples.data.neo4j;

import com.google.common.io.CharStreams;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

import static com.google.common.base.Splitter.on;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.String.format;

public class DragonBallGraph {

    private final GraphDatabaseService graphDB;
    private final ExecutionEngine cypherEngine;

    public DragonBallGraph(GraphDatabaseService graphDB) {
        this.graphDB = graphDB;
        cypherEngine = new ExecutionEngine(this.graphDB);
    }

    public final void importGraph(InputStream dataFile) throws IOException {
        InputStreamReader reader = new InputStreamReader(dataFile);
        try (Transaction transaction = graphDB.beginTx()) {
            for (String statement : statements(reader)) {
                cypherEngine.execute(statement);
            }
            transaction.success();
        }
    }

    public Iterable<Node> disciplesOf(String masterName) {
        Map<String,Object> parameters = newHashMap();
        parameters.put("name", masterName);
        try (Transaction transaction = graphDB.beginTx();
            ResourceIterator<Node> nodes = cypherEngine.execute(
                "MATCH (disciples:CHARACTER)-[:HAS_TRAINED_WITH]->(:MASTER {name: {name}}) " +
                "RETURN disciples",
                parameters
            ).columnAs("disciples")) {

            Collection<Node> disciples = new LinkedList<>();
            while (nodes.hasNext()) {
                disciples.add(nodes.next());
            }
            transaction.success();
            return disciples;
        }
    }

    public Iterable<Relationship> fusions() {
        try (Transaction transaction = graphDB.beginTx();
             ResourceIterator<Relationship> relationships = cypherEngine.execute(
                 "MATCH (:CHARACTER)-[fusions:IN_FUSION_WITH]-(:CHARACTER) RETURN fusions"
             ).columnAs("fusions")) {

            Collection<Relationship> fusions = new LinkedHashSet<>();
            while (relationships.hasNext()) {
                fusions.add(relationships.next());
            }
            transaction.success();
            return fusions;
        }
    }

    public Node findCharacter(String characterName) {
        Map<String,Object> parameters = newHashMap();
        parameters.put("name", characterName);
        try (Transaction transaction = graphDB.beginTx();
             ResourceIterator<Node> relationships = cypherEngine.execute(
                 "MATCH (character:CHARACTER {name: {name}}) RETURN character",
                 parameters
             ).columnAs("character")) {

            Collection<Node> characters = new LinkedHashSet<>();
            while (relationships.hasNext()) {
                characters.add(relationships.next());
            }
            if (characters.size() > 1) {
                throw new IllegalStateException(format(
                    "There should be only one character named <%s>",
                    characterName
                ));
            }
            transaction.success();
            return characters.iterator().next();
        }
    }

    public GraphDatabaseService getGraphDB() {
        return graphDB;
    }

    private Collection<String> statements(InputStreamReader reader) throws IOException {
        return from(on(';')
            .trimResults()
            .omitEmptyStrings()
            .split(CharStreams.toString(reader)))
            .toList();
    }
}
