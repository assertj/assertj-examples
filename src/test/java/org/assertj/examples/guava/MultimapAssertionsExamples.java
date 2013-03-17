package org.assertj.examples.guava;

import static com.google.common.collect.Lists.newArrayList;

import static org.fest.assertions.api.GUAVA.assertThat;
import static org.fest.assertions.api.GUAVA.entry;

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
