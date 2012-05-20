package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Comparator;

import org.junit.Test;

import org.fest.assertions.examples.comparator.AbsValueComparator;

/**
 * Number assertions examples.<br>
 * 
 * @author Joel Costigliola
 */
public class NumberAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void number_assertions_examples() throws Exception {

    // equals / no equals assertions
    assertThat(sam.getAge()).isEqualTo(38);
    assertThat(frodo.getAge()).isEqualTo(33).isNotEqualTo(sam.getAge());

    // <= < > >= assertions
    assertThat(sam.getAge()).isGreaterThan(frodo.getAge()).isGreaterThanOrEqualTo(38);
    assertThat(frodo.getAge()).isLessThan(sam.getAge()).isLessThanOrEqualTo(33);

    // shortcuts for assertions : > 0, < 0 and == 0
    assertThat(frodo.getAge() - frodo.getAge()).isZero();
    assertThat(frodo.getAge() - sauron.getAge()).isNegative();
    assertThat(gandalf.getAge() - frodo.getAge()).isPositive();
  }

  @Test
  public void number_assertions_with_custom_comparison_examples() {

    // with absolute values comparator : |-8| == |8|
    assertThat(-8).usingComparator(absValueComparator).isEqualTo(8);
    assertThat(-8.0).usingComparator(new AbsValueComparator<Double>()).isEqualTo(8.0);
    assertThat((byte) -8).usingComparator(new AbsValueComparator<Byte>()).isEqualTo((byte) 8);
    assertThat(new BigDecimal("-8")).usingComparator(new AbsValueComparator<BigDecimal>()).isEqualTo(
        new BigDecimal("8"));

    // works with arrays !
    assertThat(new int[] { -1, 2, 3 }).usingElementComparator(absValueComparator).contains(1, 2, -3);
  }

  // new in FEST 2.0
  @Test
  public void assertion_error_with_message_differentiating_double_and_float() {

    // Assertion error message is built with a String description of involved objects.
    // Sometimes, the descriptions are the same, if you were to compare a double and a float with same values, the error
    // message would be confusing, ex :
    // "expected:<'42.0'> but was:<'42.0'> ... How bad !
    // In that case, Fest is smart enough and differentiates the number types in the error message.

    // we declare numbers instead of Double and Float to be able to compare them with isEqualTo.
    final Number expected = 42d;
    final Number actual = 42f;
    try {
      assertThat(actual).isEqualTo(expected);
    } catch (AssertionError e) {
      // this message is formatted by JUnit to show what is different (looks nice in IDE but not so in the error
      // message)
      assertThat(e).hasMessage("expected:<42.0[]> but was:<42.0[f]>");
      return;
    }
  }

  @Test
  public void big_decimals_assertions_examples() {

    // You can use String directly and we will create the corresponding BigDecimal for you, thus ...  
    assertThat(new BigDecimal("8.0")).isEqualTo("8.0");
    // ... is equivalent to :
    assertThat(new BigDecimal("8.0")).isEqualTo(new BigDecimal("8.0"));
    
    // With BigDecimal, 8.0 is not equals to 8.00 but it is if you use compareTo()
    assertThat(new BigDecimal("8.0")).isEqualByComparingTo(new BigDecimal("8.00"));

    // The following won't work because it relies on equals methos
    // assertThat(new BigDecimal("8.0")).isGreaterThanOrEqualTo(new BigDecimal("8.00"));
    // To have a consistent comparison ignoring BigDecimal scale, switch of comparison strategy :
    Comparator<BigDecimal> bigDecimalComparator = new Comparator<BigDecimal>() {
      public int compare(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        return bigDecimal1.compareTo(bigDecimal2);
      }
    };
    assertThat(new BigDecimal("8.0")).usingComparator(bigDecimalComparator)
        .isEqualTo(new BigDecimal("8.00"))
        .isGreaterThanOrEqualTo(new BigDecimal("8.00"));
  }

  @Test
  public void number_assertions_with_offset_examples() {
    // Offset in Fest 2.0 is like Delta in Fest 1.4
    assertThat(8.1).isEqualTo(8.0, offset(0.1));
    assertThat(8.2f).isEqualTo(8.0f, offset(0.2f));
    // TODO FEST-476 illustrate error message : assertThat(8.1f).isEqualTo(8.0f, offset(0.1f));

  }

}
