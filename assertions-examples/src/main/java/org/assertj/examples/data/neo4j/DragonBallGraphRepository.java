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

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class DragonBallGraphRepository {

  private final GraphDatabaseService graphDatabase;

  public DragonBallGraphRepository(GraphDatabaseService graphDatabase) {
    this.graphDatabase = graphDatabase;
  }

  public Iterable<Node> findDisciplesOfMaster(String masterName) {
    try (Transaction ignored = graphDatabase.beginTx();
        ResourceIterator<Node> nodes = graphDatabase
            .execute("MATCH (disciple:Character)-[:HAS_TRAINED_WITH]->(master:Master {name: {name}}) "
                     + "RETURN disciple, master ORDER BY disciple.name ASC", map("name", masterName))
            .columnAs("disciple")) {

      return nodes.stream().collect(Collectors.toList());
    }
  }

  public Collection<Relationship> findFusions() {
    try (Transaction ignored = graphDatabase.beginTx();
        ResourceIterator<Relationship> relationships = graphDatabase.execute(
            "MATCH (:Character)-[fusion:IN_FUSION_WITH]->(:Character) RETURN fusion ORDER BY fusion.fusion_character_name ASC")
                                                                    .columnAs("fusion")) {

      return relationships.stream().collect(Collectors.toList());
    }
  }

  public Node findUniqueCharacter(String characterName) {
    Map<String, Object> parameters = map("name", characterName);
    try (Transaction ignored = graphDatabase.beginTx();
        ResourceIterator<Node> nodes = graphDatabase.execute(
            "MATCH (character:Character {name: {name}}) RETURN character", parameters).columnAs("character")) {

      return getUniqueOrFail(nodes, format("There should be only one character named <%s>", characterName));
    }
  }

  public Path findShortestPathBetween(String character1, String character2) {
    try (Transaction ignored = graphDatabase.beginTx();
        ResourceIterator<Path> shortestPaths = graphDatabase.execute(
            "MATCH (character1:Character {name: {name1}}), (character2:Character {name: {name2}}), "
            + "path = shortestPath((character1)-[*..15]-(character2)) RETURN path", map("name1", character1,
                                                                                        "name2", character2))
                                                            .columnAs("path")) {
      return getUniqueOrFail(shortestPaths,
                             format("Expected only 1 shortest path between character %s and character %s",
                                    character1,
                                    character2));
    }
  }

  public Relationship findUniqueTraining(String characterName) {
    try (Transaction ignored = graphDatabase.beginTx();
        ResourceIterator<Relationship> relationships = graphDatabase.execute(
            "MATCH (:Character {name: {name}})-[training:HAS_TRAINED_WITH]->(:Master) RETURN training",
            map("name", characterName)).columnAs("training")) {

      return getUniqueOrFail(relationships,
                             format("There should be only one training involving character named <%s>", characterName));
    }
  }

  private static <K, V> Map<K, V> map(K key, V value) {
    Map<K, V> parameters = new HashMap<>((int) Math.ceil(1 / 0.75f));
    parameters.put(key, value);
    return parameters;
  }

  private static <K, V> Map<K, V> map(K key, V value, K key2, V value2) {
    Map<K, V> parameters = new HashMap<>((int) Math.ceil(2 / 0.75f));
    parameters.put(key, value);
    parameters.put(key2, value2);
    return parameters;
  }

  private static <T> T getUniqueOrFail(ResourceIterator<T> relationships, String errorMessage) {
    List<T> characters = relationships.stream().collect(Collectors.toList());
    if (characters.size() > 1) {
      throw new IllegalArgumentException(errorMessage);
    }
    return characters.iterator().next();
  }
}
