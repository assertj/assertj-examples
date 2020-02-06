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

import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

public class ResultAssertionExamples extends Neo4jAssertionExamples {

  @Test
  @Disabled // https://github.com/joel-costigliola/assertj-neo4j/issues/17
  public void result_assertion_examples() throws Exception {
    try (Transaction ignored = graphDatabase().beginTx()) {
      Result result = graphDatabase().execute(
          "MATCH (character:Character) WHERE character.name =~ 'Master.*' RETURN character.name ORDER BY character.name ASC");
      // Result is just an iterator of Map<String,Object> so you will directly use the standard Neo4j assertions!
      assertThat(result)
          .hasSize(3)
          .doesNotContainNull()
          .containsExactly(
              Maps.newHashMap("character.name", "Master Mutaito"),
              Maps.newHashMap("character.name", "Master Roshi"),
              Maps.newHashMap("character.name", "Master Shen")
          );
    }
  }
}
