package org.assertj.examples;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * char assertions examples.<br>
 * 
 * @author Joel Costigliola
 */
public class CharAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void char_assertions_examples() throws Exception {

    // equals / no equals assertions
    assertThat('a').isNotEqualTo('A');

    // <= < > >= assertions
    assertThat('a').isGreaterThan('A').isGreaterThanOrEqualTo('a');
    assertThat('A').isLessThan('a').isLessThanOrEqualTo('a');

    // case assertions
    assertThat('A').isUpperCase();
    assertThat('a').isLowerCase();
  }

  @Test
  public void char_assertions_with_custom_comparison_examples() {
    assertThat('a').usingComparator(caseInsensitiveComparator).isEqualTo('A');
    assertThat(new Character('a')).usingComparator(caseInsensitiveComparator).isEqualTo(new Character('A'));
  }

}
