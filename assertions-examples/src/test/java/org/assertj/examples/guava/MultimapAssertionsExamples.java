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
package org.assertj.examples.guava;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.guava.api.Assertions.assertThat;
import static org.assertj.guava.api.Assertions.entry;

import org.assertj.examples.AbstractAssertionsExamples;
import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


/**
 * {@link Multimap} assertions example.
 * 
 * @author Joel Costigliola
 */
public class MultimapAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void multimap_assertions_examples() {
    
    Multimap<String, String> nbaTeams = ArrayListMultimap.create();
    nbaTeams.putAll("Lakers", newArrayList("Kobe Bryant", "Magic Johnson", "Kareem Abdul Jabbar"));
    nbaTeams.putAll("Spurs", newArrayList("Tony Parker", "Tim Duncan", "Manu Ginobili"));

    assertThat(nbaTeams).hasSize(6);
    assertThat(nbaTeams).containsKeys("Lakers", "Spurs");
    assertThat(nbaTeams).contains(entry("Lakers", "Kobe Bryant"), entry("Spurs", "Tim Duncan"));
    assertThat(nbaTeams).containsValues("Kareem Abdul Jabbar", "Tony Parker");
    assertThat(nbaTeams).hasSameEntriesAs(nbaTeams);
    assertThat(nbaTeams).containsAllEntriesOf(nbaTeams);

    Multimap<String, String> emptyMultimap = ArrayListMultimap.create();
    assertThat(emptyMultimap).isEmpty();
  }
  
}
