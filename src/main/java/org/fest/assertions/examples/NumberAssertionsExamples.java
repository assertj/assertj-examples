package org.fest.assertions.examples;

import static java.lang.Integer.toHexString;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Fail.fail;
import static org.fest.assertions.data.Index.atIndex;
import static org.fest.util.Arrays.array;
import static org.fest.util.Collections.list;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;

import org.junit.Test;

import org.fest.assertions.examples.comparator.AbsValueComparator;
import org.fest.assertions.examples.data.Person;
import org.fest.assertions.examples.data.TolkienCharacter;

public class NumberAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void number_assertions_examples() throws Exception {
    assertThat(gandalf.getAge()).isGreaterThan(frodo.getAge());
  }


  @Test
  public void number_assertions_with_custom_comparison_examples() {
    assertThat(-8).usingComparator(absValueComparator).isEqualTo(8);
    assertThat(new Integer("-8")).usingComparator(new AbsValueComparator<Integer>()).isEqualTo(new Integer("8"));

    assertThat(new Long("-8")).usingComparator(new AbsValueComparator<Long>()).isEqualTo(new Long("8"));
    assertThat(-8l).usingComparator(absValueComparator).isEqualTo(8l);

    assertThat(new Short("-8")).usingComparator(new AbsValueComparator<Short>()).isEqualTo(new Short("8"));
    assertThat((short) -8).usingComparator(absValueComparator).isEqualTo((short) 8);

    assertThat(new Float("-8")).usingComparator(new AbsValueComparator<Float>()).isEqualTo(new Float("8"));
    assertThat(-8.0f).usingComparator(absValueComparator).isEqualTo(8.0f);

    assertThat(new Double("-8")).usingComparator(new AbsValueComparator<Double>()).isEqualTo(new Double("8"));
    assertThat(-8.0).usingComparator(absValueComparator).isEqualTo(8.0);

    assertThat('a').usingComparator(caseInsensitiveComparator).isEqualTo('A');
    assertThat(new Character('a')).usingComparator(caseInsensitiveComparator).isEqualTo(new Character('A'));

    assertThat(new Byte("-8")).usingComparator(new AbsValueComparator<Byte>()).isEqualTo(new Byte("8"));
    assertThat((byte) -8).usingComparator(absValueComparator).isEqualTo((byte) 8);

    assertThat(new BigDecimal("-8")).usingComparator(new AbsValueComparator<BigDecimal>()).isEqualTo(
        new BigDecimal("8"));
    // assertThat(-8).usingComparator(absValueComparator).isEqualTo(9);
    // assertThat(new int[] { -1, 2, 3 }).contains(1, 2, 3);
    assertThat(new int[] { -1, 2, 3 }).usingComparator(absValueComparator).contains(1, 2, -3);
    assertThat(new long[] { -1, 2, 3 }).usingComparator(new AbsValueComparator<Long>()).contains(1, 2, -3);
    assertThat(new short[] { -1, 2, 3 }).usingComparator(new AbsValueComparator<Short>()).contains(
        new short[] { 1, 2, -3 });
    assertThat(new float[] { -1, 2, 3 }).usingComparator(new AbsValueComparator<Float>()).contains(1, 2, -3);
    assertThat(new double[] { -1, 2, 3 }).usingComparator(new AbsValueComparator<Double>()).contains(1, 2, -3);
    assertThat(new byte[] { -1, 2, 3 }).usingComparator(new AbsValueComparator<Byte>()).contains(
        new byte[] { 1, 2, -3 });
    assertThat(new char[] { 'a', 'B', 'c' }).usingComparator(caseInsensitiveComparator).contains('A', 'B', 'C');
  }

  // new in FEST 2.0
  @Test
  public void assertion_error_with_message_differentiating_expected_double_and_actual_float() throws Exception {

    final Object expected = 42d;
    final Object actual = 42f;
    try {
      assertThat(actual).isEqualTo(expected);
    } catch (AssertionError e) {
      assertThat(expected.hashCode()).isNotEqualTo(actual.hashCode());
      assertThat(e).hasMessage(
          "expected:<'42.0 (Double@" + toHexString(expected.hashCode()) + ")'> but was:<'42.0 (Float@"
              + toHexString(actual.hashCode()) + ")'>");
      return;
    }
    fail("AssertionError expected");
  }

  

  
  
  // ------------------------------------------------------------------------------------------------------
  // methods used in our examples
  // ------------------------------------------------------------------------------------------------------


}
