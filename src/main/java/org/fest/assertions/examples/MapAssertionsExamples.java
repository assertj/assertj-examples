package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.*;
import static org.fest.assertions.examples.data.Ring.*;

import org.junit.Test;

/**
 * Map assertions example.
 *
 * @author Joel Costigliola
 */
public class MapAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void map_assertions_examples() {
    // ringBearers is a Map of TolkienCharacter indexed by the Ring they are wearing.
    assertThat(ringBearers).isNotEmpty().hasSize(4);
    
    // note the usage of Assertions.entry(key, value) synthetic sugar for better readability (similar to MapEntry.entry(key, value)). 
    assertThat(ringBearers).contains(entry(oneRing, frodo), entry(nenya, galadriel));
    assertThat(ringBearers).doesNotContain(entry(oneRing, aragorn));
    
    ringBearers.clear();
    assertThat(ringBearers).isEmpty();
  }

}
