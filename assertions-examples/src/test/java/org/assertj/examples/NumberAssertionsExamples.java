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
package org.assertj.examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.offset;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.Assertions.withinPercentage;
import static org.assertj.core.data.Percentage.withPercentage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

import org.assertj.core.data.Offset;
import org.assertj.examples.comparator.AbsValueComparator;
import org.junit.jupiter.api.Test;

/**
 * Number assertions examples.<br>
 *
 * @author Joel Costigliola
 */
public class NumberAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void number_assertions_examples() throws Exception {

    // equals / no equals assertions
    assertThat(sam.age).isEqualTo(38)
                       .isCloseTo(40, within(10))
                       .isEven();
    assertThat(frodo.age).isEqualTo(33)
                         .isNotEqualTo(sam.age)
                         .isOdd();
    // <= < > >= assertions
    assertThat(sam.age).isGreaterThan(frodo.age)
                       .isGreaterThanOrEqualTo(38);
    assertThat(frodo.age).isLessThan(sam.age)
                         .isLessThanOrEqualTo(33);
    assertThat(sam.age).isBetween(frodo.age, gimli.age);

    // shortcuts for assertions : > 0, < 0 and == 0
    assertThat(frodo.age - frodo.age).isZero();
    assertThat(frodo.age - sauron.age).isNegative();
    assertThat(gandalf.age - frodo.age).isPositive();

    assertThat(frodo.age - frodo.age).isNotNegative();
    assertThat(frodo.age - frodo.age).isNotPositive();
    assertThat(gandalf.age - frodo.age).isNotNegative();
    assertThat(frodo.age - sauron.age).isNotPositive();
  }

  @Test
  public void number_assertions_with_custom_comparison_examples() {

    // with absolute values comparator : |-8| == |8|
    assertThat(-8).usingComparator(absValueComparator)
                  .isEqualTo(8);
    assertThat(-8.0).usingComparator(new AbsValueComparator<Double>())
                    .isEqualTo(8.0);
    assertThat((byte) -8).usingComparator(new AbsValueComparator<Byte>())
                         .isEqualTo((byte) 8);
    assertThat(new BigDecimal("-8")).usingComparator(new AbsValueComparator<BigDecimal>())
                                    .isEqualTo(new BigDecimal("8"));
    // works with arrays !
    assertThat(new int[] { -1, 2, 3 }).usingElementComparator(absValueComparator).contains(1, 2, -3);
  }

  @Test
  public void assertion_error_with_message_differentiating_double_and_float() {

    // Assertion error message is built with a String description of involved objects.
    // Sometimes, the descriptions are the same, if you were to compare a double and a float with same values, the error
    // message would be confusing, ex :
    // "expected:<'42.0'> but was:<'42.0'> ... How bad !
    // In that case, AssertJ is smart enough and differentiates the number types in the error message.

    // we declare numbers instead of Double and Float to be able to compare them with isEqualTo.
    final Number expected = 42d;
    final Number actual = 42f;
    try {
      assertThat(actual).isEqualTo(expected);
    } catch (AssertionError e) {
      // this message is formatted by JUnit to show what is different (looks nice in IDE but not so in the error
      // message)
      assertThat(e).hasMessage("\n" +
                               "Expecting:\n" +
                               " <42.0f>\n" +
                               "to be equal to:\n" +
                               " <42.0>\n" +
                               "but was not.");
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
    assertThat(new BigDecimal("8.0")).isEqualByComparingTo("8.00");
    assertThat(new BigDecimal("8.0")).isNotEqualByComparingTo("8.01");

    // isGreaterThanOrEqualTo uses compareTo semantics
    assertThat(new BigDecimal("8.0")).isGreaterThanOrEqualTo(new BigDecimal("8.00"));
    assertThat(new BigDecimal("8.1")).isGreaterThanOrEqualTo(new BigDecimal("8.10"));
  }

  @Test
  public void number_assertions_with_offset_examples() {
    assertThat(8.1).isEqualTo(8.0, offset(0.1));
    assertThat(8.1f).isEqualTo(8.2f, offset(0.1f));
    try {
      assertThat(8.1f).isEqualTo(8.0f, offset(0.1f));
    } catch (AssertionError e) {
      logAssertionErrorMessage("float isEqualTo with offset", e);
    }

    // same stuff using within instead of offset
    assertThat(8.1).isCloseTo(8.0, within(0.1));
    assertThat(0.2).isCloseTo(0.0, within(0.2));
    assertThat(0.2).isCloseTo(0.0, byLessThan(0.20001));
    assertThat(5.0).isCloseTo(6.0, withinPercentage(20.0));
    assertThat(5.0).isCloseTo(6.0, withinPercentage(20));
    assertThat(5).isCloseTo(6, withinPercentage(20));

    assertThat(8.2f).isCloseTo(8.0f, within(0.2f));
    assertThat(new BigDecimal("8.1")).isCloseTo(new BigDecimal("8.0"), within(new BigDecimal("0.1")));
    // just to see that the BigDecimal format does not have impact on the assertion
    assertThat(new BigDecimal("8.1")).isCloseTo(new BigDecimal("8.00"), within(new BigDecimal("0.100")));
    try {
      assertThat(8.1f).isCloseTo(8.0f, within(0.1f));
    } catch (AssertionError e) {
      logAssertionErrorMessage("float isCloseTo within ", e);
    }

    try {
      assertThat(new BigDecimal("8.1")).isCloseTo(new BigDecimal("8.0"), within(new BigDecimal("0.01")));
    } catch (AssertionError e) {
      logAssertionErrorMessage("BigDecimal isCloseTo within offset", e);
    }

    assertThat(sam.age).isCloseTo(40, within(10));
    assertThat(10l).isCloseTo(8l, within(2l));
    assertThat((short) 5).isCloseTo((short) 7, within((short) 3));
    assertThat((byte) 5).isCloseTo((byte) 7, within((byte) 3));

    assertThat(8.1).isCloseTo(8.0, byLessThan(0.2));
    // assertions succeed when the difference == offset value ...
    assertThat(8.1).isCloseTo(8.0, within(0.1));

    // ok as byLessThan is a strict offset
    assertThat(8.1).isNotCloseTo(8.0, byLessThan(0.01));
    assertThat(8.1).isNotCloseTo(8.0, within(0.01));
    assertThat(8.1).isNotCloseTo(8.0, offset(0.01));
    // diff == offset but isNotCloseTo succeeds as we use byLessThan
    assertThat(0.1).isNotCloseTo(0.0, byLessThan(0.1));
    try {
      assertThat(8).isNotCloseTo(10, within(2));
    } catch (AssertionError e) {
      logAssertionErrorMessage("int isNotCloseTo within ", e);
    }

    assertThat(8.1f).isCloseTo(8.0f, within(0.2f));
    assertThat(8.1f).isCloseTo(8.0f, offset(0.2f)); // alias of within
    assertThat(8.1f).isCloseTo(8.0f, byLessThan(0.2f)); // strict

    assertThat(8.1f).isNotCloseTo(8.0f, byLessThan(0.01f));
    assertThat(8.1f).isNotCloseTo(8.0f, within(0.01f));
    assertThat(8.1f).isNotCloseTo(8.0f, offset(0.01f));
    // diff == offset but isNotCloseTo succeeds as we use byLessThan
    assertThat(0.1f).isNotCloseTo(0.0f, byLessThan(0.1f));
    assertThat(8.1f).isEqualTo(8.0f, within(0.2f));
    assertThat(8.1f).isEqualTo(8.0f, offset(0.2f)); // alias of within
    assertThat(8.1f).isEqualTo(8.0f, byLessThan(0.2f)); // strict

    // assertions succeed when the difference == offset value ...
    assertThat(0.1f).isCloseTo(0.0f, within(0.1f));
    assertThat(0.1f).isEqualTo(0.0f, within(0.1f));

    assertThat(8.1f).isNotCloseTo(8.0f, byLessThan(0.01f));
    assertThat(8.1f).isNotCloseTo(8.0f, within(0.01f));
    assertThat(8.1f).isNotCloseTo(8.0f, offset(0.01f));
    // diff == offset but isNotCloseTo succeeds as we use byLessThan

    assertThat(0.1f).isNotCloseTo(0.0f, byLessThan(0.1f));
    assertThat(8.1f).isEqualTo(8.0f, within(0.2f));
    assertThat(8.1f).isEqualTo(8.0f, offset(0.2f)); // alias of within
    assertThat(8.1f).isEqualTo(8.0f, byLessThan(0.2f)); // strict

    // assertions succeed when the difference == offset value ...
    assertThat(0.1f).isEqualTo(0.0f, within(0.1f));
    assertThat(0.1f).isEqualTo(0.0f, offset(0.1f));
    // ... except when using byLessThan which implies a strict comparison
    // assertThat(0.1f).isEqualTo(0.0f, byLessThan(0.1f)); // strict => fail
    assertThat(8.1f).isEqualTo(8.0f, within(0.2f));
    assertThat(8.1f).isEqualTo(8.0f, offset(0.2f)); // alias of within
    assertThat(8.1f).isEqualTo(8.0f, byLessThan(0.2f)); // strict

    // assertions succeed when the difference == offset value ...
    assertThat(0.1f).isEqualTo(0.0f, within(0.1f));
    assertThat(0.1f).isEqualTo(0.0f, offset(0.1f));

    assertThat(5).isCloseTo(7, within(3));
    assertThat(5).isCloseTo(7, byLessThan(3));

    // if difference is exactly equals to the offset, it's ok ...
    assertThat(5).isNotCloseTo(7, byLessThan(1));
    assertThat(5).isNotCloseTo(7, within(1));
    // diff == offset but isNotCloseTo succeeds as we use byLessThan
    assertThat(5).isNotCloseTo(7, byLessThan(2));

    final BigDecimal eightDotOne = new BigDecimal("8.1");
    final BigDecimal eight = new BigDecimal("8.0");

    // assertions succeed
    assertThat(eightDotOne).isCloseTo(eight, within(new BigDecimal("0.2")));
    assertThat(eightDotOne).isCloseTo(eight, Offset.offset(new BigDecimal("0.2"))); // alias of within
    assertThat(eightDotOne).isCloseTo(eight, byLessThan(new BigDecimal("0.2"))); // strict

    assertThat(eightDotOne).isCloseTo(eight, within(new BigDecimal("0.1")));
    assertThat(eightDotOne).isCloseTo(eight, Offset.offset(new BigDecimal("0.1")));

    // assertions succeed
    assertThat(eightDotOne).isNotCloseTo(eight, byLessThan(new BigDecimal("0.01")));
    assertThat(eightDotOne).isNotCloseTo(eight, within(new BigDecimal("0.01")));
    assertThat(eightDotOne).isNotCloseTo(eight, Offset.offset(new BigDecimal("0.01")));
    // diff == offset but isNotCloseTo succeeds as we use byLessThan
    assertThat(eightDotOne).isNotCloseTo(eight, byLessThan(new BigDecimal("0.1")));

    assertThat(5l).isNotCloseTo(7l, byLessThan(1l));
    assertThat(5l).isNotCloseTo(7l, within(1l));
    // diff == offset but isNotCloseTo succeeds as we use byLessThan
    assertThat(5l).isNotCloseTo(7l, byLessThan(2l));

    assertThat(5L).isCloseTo(7L, within(3L));
    assertThat(5L).isCloseTo(7L, byLessThan(3L));

    // if difference is exactly equals to the offset, it's ok ...
    assertThat(5L).isCloseTo(7L, within(2L));
    // ... but not with byLessThan which implies a strict comparison
    assertThat((short) 10).isCloseTo((short) 11, byLessThan((short) 2));

    final BigInteger ten = BigInteger.TEN;
    assertThat(new BigInteger("8")).isCloseTo(ten, within(new BigInteger("3")));
    assertThat(new BigInteger("8")).isCloseTo(ten, byLessThan(new BigInteger("3")));

    // if difference is exactly equals to given offset value, it's ok
    assertThat(new BigInteger("8")).isCloseTo(ten, within(new BigInteger("2")));
    assertThat(new BigInteger("8")).isCloseTo(ten, within(new BigInteger("2")));

  }

  @Test
  public void isNotCloseTo_examples() {
    final BigInteger eight = new BigInteger("8");
    final BigInteger ten = BigInteger.TEN;

    // this assertion succeeds
    assertThat(eight).isNotCloseTo(ten, byLessThan(BigInteger.ONE));
    assertThat(eight).isNotCloseTo(ten, within(BigInteger.ONE));

    // diff == offset but isNotCloseTo succeeds as we use byLessThan
    assertThat(eight).isNotCloseTo(ten, byLessThan(new BigInteger("2")));
  }

  @Test
  public void number_assertions_with_binary_representation_examples() {
    assertThat(1).inBinary().isEqualTo(1);
    try {
      assertThat(1).inBinary().isEqualTo(2);
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualTo with binary representation ", e);
    }
  }

  @Test
  public void comparing_array_of_real_numbers() {
    Comparator<Double> closeToComparator = new Comparator<Double>() {
      @Override
      public int compare(Double o1, Double o2) {
        return Math.abs(o1.doubleValue() - o2.doubleValue()) < 0.001 ? 0 : -1;
      }
    };
    assertThat(new double[] { 7.2, 3.6, -12.0 }).usingElementComparator(closeToComparator)
                                                .containsExactly(7.2000001, 3.5999999, -12.000001);
  }

  @Test
  public void subsequence_of_real_numbers() {
    assertThat(new double[] { 1.0, 2.0, 3.0 }).containsSubsequence(1.0, 3.0);
    assertThat(new float[] { 1.0f, 2.0f, 3.0f }).containsSubsequence(1.0f, 3.0f);
  }

  @Test
  public void bigInteger_assertions_examples() {

    BigInteger eleven = new BigInteger("11");
    // equals / no equals assertions
    assertThat(BigInteger.ZERO).isEqualTo(0)
                               .isZero()
                               .isNotEqualTo(BigInteger.ONE);

    // <= < > >= assertions
    assertThat(BigInteger.TEN).isGreaterThan(BigInteger.ONE)
                              .isGreaterThanOrEqualTo(BigInteger.TEN)
                              .isLessThan(eleven)
                              .isLessThanOrEqualTo(BigInteger.TEN)
                              .isBetween(BigInteger.ONE, eleven)
                              .isCloseTo(eleven, within(BigInteger.ONE))
                              .isCloseTo(eleven, byLessThan(BigInteger.TEN))
                              .isCloseTo(eleven, withinPercentage(20))
                              .isPositive()
                              .isNotNegative();

    assertThat(BigInteger.ONE).isOne();

    assertThat(new BigInteger("-1")).isNegative()
                                    .isNotPositive();
  }

  @Test
  public void should_consider_primitive_negative_zero_as_zero_fixing_issue_919() {
    assertThat(-0.).isZero();
  }

  @Test
  public void should_handle_NaN_and_infinity_correctly_fixing_issue_984() {
    assertThat(Double.NaN == Double.NaN).isFalse();
    // https://github.com/joel-costigliola/assertj-core/issues/1775
    // assertThat(Double.NaN).isNotEqualTo(Double.NaN);
    assertThat(Double.POSITIVE_INFINITY).isEqualTo(Double.POSITIVE_INFINITY);
    assertThat(Double.NEGATIVE_INFINITY).isEqualTo(Double.NEGATIVE_INFINITY);
    try {
      assertThat(Double.NaN).isCloseTo(0.007, withPercentage(0.1));
    } catch (AssertionError e) {
      return;
    }
    fail("assertThat(Double.NaN).isCloseTo(0.007, withPercentage(0.1)) should have failed");
  }

  @Test
  public void should_behave_according_to_whether_expected_value_is_primitive_or_not() {
    // JDK behavior
    assertThat(Double.NaN != Double.NaN).isTrue();
    assertThat(Double.valueOf(Double.NaN).equals(Double.NaN)).isTrue();
    assertThat(-0.0 == 0.0).isTrue();
    assertThat(Double.valueOf(0.0).equals(-0.0)).isFalse();
    // AssertJ behavior
    assertThat(0.0).isEqualTo(-0.0);
    assertThat(-0.0).isEqualTo(0.0);
    assertThat(0.0).isNotEqualTo(Double.valueOf(-0.0));
    assertThat(Double.NaN).isNotEqualTo(Double.NaN);
    assertThat(Double.NaN).isEqualTo(Double.valueOf(Double.NaN));
    assertThat(-0.0).isZero();
  }

}
