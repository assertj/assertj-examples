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
package org.assertj.examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

import java.math.BigDecimal;
import java.util.Comparator;

import org.assertj.examples.comparator.AbsValueComparator;
import org.junit.Test;


/**
 * Number assertions examples.<br>
 * 
 * @author Joel Costigliola
 */
public class NumberAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void number_assertions_examples() throws Exception {

    // equals / no equals assertions
    assertThat(sam.age).isEqualTo(38);
    assertThat(frodo.age).isEqualTo(33).isNotEqualTo(sam.age);

    // <= < > >= assertions
    assertThat(sam.age).isGreaterThan(frodo.age).isGreaterThanOrEqualTo(38);
    assertThat(frodo.age).isLessThan(sam.age).isLessThanOrEqualTo(33);
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
    assertThat(-8).usingComparator(absValueComparator).isEqualTo(8);
    assertThat(-8.0).usingComparator(new AbsValueComparator<Double>()).isEqualTo(8.0);
    assertThat((byte) -8).usingComparator(new AbsValueComparator<Byte>()).isEqualTo((byte) 8);
    assertThat(new BigDecimal("-8")).usingComparator(new AbsValueComparator<BigDecimal>()).isEqualTo(
        new BigDecimal("8"));

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
    assertThat(8.1).isEqualTo(8.0, offset(0.1));
    assertThat(8.2f).isEqualTo(8.0f, offset(0.2f));
    try {
      assertThat(8.1f).isEqualTo(8.0f, offset(0.1f));
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualTo with offset", e);
    }
  }

}




