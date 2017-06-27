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
package org.assertj.examples.data.neo4j;

import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import static com.google.common.base.Splitter.on;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.String.format;

public class DragonBallGraph {

  private final GraphDatabaseService graphDB;

  public DragonBallGraph(GraphDatabaseService graphDB) {
    this.graphDB = graphDB;
  }

  public final void importGraph(InputStream dataFile) throws IOException {
    InputStreamReader reader = new InputStreamReader(dataFile);
    try (Transaction transaction = graphDB.beginTx()) {
      for (String statement : statements(reader)) {
        graphDB.execute(statement);
      }
      transaction.success();
    }
  }

  public Iterable<Node> disciplesOf(String masterName) {
    try (Transaction transaction = graphDB.beginTx();
        ResourceIterator<Node> nodes = discipleRowsOf(masterName).columnAs("disciples")) {

      Collection<Node> disciples = new LinkedList<>();
      while (nodes.hasNext()) {
        disciples.add(nodes.next());
      }
      transaction.success();
      return disciples;
    }
  }

  public Result discipleRowsOf(String masterName) {
    Map<String, Object> parameters = newHashMap();
    parameters.put("name", masterName);

    return graphDB.execute("MATCH (disciples:CHARACTER)-[:HAS_TRAINED_WITH]->(master:MASTER {name: {name}}) "
        + "RETURN disciples, master ORDER BY disciples.name", parameters);
  }

  public Iterable<Relationship> fusions() {
    try (Transaction tx = graphDB.beginTx();
        ResourceIterator<Relationship> relationships = graphDB.execute(
            "MATCH (:CHARACTER)-[fusions:IN_FUSION_WITH]-(:CHARACTER) RETURN fusions").columnAs("fusions")) {

      Collection<Relationship> fusions = new LinkedHashSet<>();
      while (relationships.hasNext()) {
        fusions.add(relationships.next());
      }
      tx.success();
      return fusions;
    }
  }

  public ResourceIterator<String> fusionCharactersIterator() {
    try (Transaction tx = graphDB.beginTx()) {
      ResourceIterator<String> relationships = graphDB.execute(
          "MATCH (:CHARACTER)-[fusions:IN_FUSION_WITH]-(:CHARACTER) RETURN DISTINCT fusions.into ORDER BY fusions.into").columnAs("fusions.into");

      tx.success();
      return relationships;
    }
  }

  public Node findCharacter(String characterName) {
    Map<String, Object> parameters = newHashMap();
    parameters.put("name", characterName);
    try (Transaction transaction = graphDB.beginTx();
        ResourceIterator<Node> relationships = graphDB.execute(
            "MATCH (character:CHARACTER {name: {name}}) RETURN character", parameters).columnAs("character")) {

      Collection<Node> characters = new LinkedHashSet<>();
      while (relationships.hasNext()) {
        characters.add(relationships.next());
      }
      if (characters.size() > 1) {
        throw new IllegalStateException(format("There should be only one character named <%s>", characterName));
      }
      transaction.success();
      return characters.iterator().next();
    }
  }

  public Path findShortestPathBetween(String characterOne, String characterTwo) {
    Map<String, Object> parameters = newHashMap();
    parameters.put("name1", characterOne);
    parameters.put("name2", characterTwo);
    try (Transaction transaction = graphDB.beginTx()) {
      Path path = graphDB
          .execute(
              "MATCH (character1:CHARACTER {name: {name1}}), " + "(character2:CHARACTER {name: {name2}}), "
                  + "path = shortestPath((character1)-[*..15]-(character2)) " + "RETURN path", parameters)
          .<Path> columnAs("path").next();
      transaction.success();
      return path;
    }
  }

  public Relationship findTrainingFrom(String characterName) {
    Map<String, Object> parameters = newHashMap();
    parameters.put("name", characterName);
    try (Transaction transaction = graphDB.beginTx();
        ResourceIterator<Relationship> relationships = graphDB.execute(
            "MATCH (:CHARACTER {name: {name}})-[training:HAS_TRAINED_WITH]->(:MASTER) RETURN training", parameters)
            .columnAs("training")) {

      LinkedList<Relationship> trainings = new LinkedList<>();
      while (relationships.hasNext()) {
        trainings.add(relationships.next());
      }

      if (trainings.size() > 1) {
        throw new IllegalStateException(format("There should be only one training involving character named <%s>",
            characterName));
      }
      transaction.success();
      return trainings.iterator().next();
    }
  }

  private Collection<String> statements(InputStreamReader reader) throws IOException {
    return from(on(';').trimResults().omitEmptyStrings().split(CharStreams.toString(reader))).toList();
  }
}
