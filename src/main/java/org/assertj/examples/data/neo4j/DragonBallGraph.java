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
 * Copyright 2012-2013 the original author or authors.
 */
package org.assertj.examples.data.neo4j;

import com.google.common.io.CharStreams;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import static com.google.common.base.Splitter.on;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Maps.newHashMap;
import static org.neo4j.helpers.collection.IteratorUtil.asIterable;

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
        try (Transaction transaction = graphDB.beginTx()) {
            ResourceIterator<Node> nodes = cypherEngine.execute(
                "MATCH (disciples:CHARACTER)-[:HAS_TRAINED_WITH]->(:MASTER {name: 'Master Roshi'}) " +
                "RETURN disciples"
            ).columnAs("disciples");

            LinkedList<Node> disciples = new LinkedList<>();
            while (nodes.hasNext()) {
                disciples.add(nodes.next());
            }
            transaction.success();
            return disciples;
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
