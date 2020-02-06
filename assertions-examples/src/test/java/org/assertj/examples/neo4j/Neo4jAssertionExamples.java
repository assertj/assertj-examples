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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.assertj.examples.data.neo4j.DragonBallGraphRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.test.TestGraphDatabaseFactory;

public class Neo4jAssertionExamples {

  private static GraphDatabaseService graphDatabase;
  private static DragonBallGraphRepository dragonBallGraphRepository;

  @BeforeAll
  public static void prepare_graph() {
    graphDatabase = new TestGraphDatabaseFactory().newImpermanentDatabase();
    importGraph(graphDatabase);
    dragonBallGraphRepository = new DragonBallGraphRepository(graphDatabase);
  }

  @AfterAll
  public static void cleanUp() {
    graphDatabase.shutdown();
  }

  static GraphDatabaseService graphDatabase() {
    return graphDatabase;
  }

  static DragonBallGraphRepository dragonBallGraphRepository() {
    return dragonBallGraphRepository;
  }

  private static void importGraph(GraphDatabaseService graphDatabase) {
    try (InputStream dumpFile = Neo4jAssertionExamples.class.getResourceAsStream("/dragonBall.cypher");
        Transaction transaction = graphDatabase.beginTx()) {

      cypherStatements(dumpFile).forEach(graphDatabase::execute);
      transaction.success();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  private static Collection<String> cypherStatements(InputStream inputStream) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      Predicate<String> isEmpty = String::isEmpty;
      String statements = reader.lines().filter(isEmpty.negate()).collect(Collectors.joining("\n"));
      return Arrays.asList(statements.split(";"));
    }
  }
}
