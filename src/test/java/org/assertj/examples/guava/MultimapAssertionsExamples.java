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
package org.assertj.examples.guava;

import static com.google.common.collect.Lists.newArrayList;

import static org.assertj.guava.api.GUAVA.assertThat;
import static org.assertj.guava.api.GUAVA.entry;

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
    
    Multimap<String, String> actual = ArrayListMultimap.create();
    actual.putAll("Lakers", newArrayList("Kobe Bryant", "Magic Johnson", "Kareem Abdul Jabbar"));
    actual.putAll("Spurs", newArrayList("Tony Parker", "Tim Duncan", "Manu Ginobili"));

    assertThat(actual).hasSize(6);
    assertThat(actual).containsKeys("Lakers", "Spurs");
    assertThat(actual).contains(entry("Lakers", "Kobe Bryant"), entry("Spurs", "Tim Duncan"));
    assertThat(actual).containsValues("Kareem Abdul Jabbar", "Tony Parker");

    Multimap<String, String> emptyMultimap = ArrayListMultimap.create();
    assertThat(emptyMultimap).isEmpty();
  }
  
}
