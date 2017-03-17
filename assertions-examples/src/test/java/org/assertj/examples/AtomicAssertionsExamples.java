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
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.Assertions.withinPercentage;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.util.Arrays.array;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.assertj.examples.comparator.AbsValueComparator;
import org.junit.Test;

public class AtomicAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void atomic_number_assertions_examples() throws Exception {

    // equals / no equals assertions
    assertThat(new AtomicInteger(sam.age)).hasValue(38)
                                          .hasValueCloseTo(40, within(10))
                                          .hasValueCloseTo(40, withinPercentage(10));
    assertThat(new AtomicInteger(frodo.age)).hasValue(33)
                                            .doesNotHaveValue(sam.age);

    // <= < > >= assertions
    assertThat(new AtomicInteger(sam.age)).hasValueGreaterThan(frodo.age)
                                          .hasValueGreaterThanOrEqualTo(38);
    assertThat(new AtomicInteger(frodo.age)).hasValueLessThan(sam.age)
                                            .hasValueLessThanOrEqualTo(33);
    assertThat(new AtomicInteger(sam.age)).hasValueBetween(frodo.age, gimli.age);

    // shortcuts for assertions : > 0, < 0 and == 0
    assertThat(new AtomicInteger(frodo.age - sauron.age)).hasNegativeValue();
    assertThat(new AtomicInteger(gandalf.age - frodo.age)).hasPositiveValue();

    assertThat(new AtomicInteger(frodo.age - frodo.age)).hasNonNegativeValue();
    assertThat(new AtomicInteger(frodo.age - frodo.age)).hasNonPositiveValue();
    assertThat(new AtomicInteger(gandalf.age - frodo.age)).hasNonNegativeValue();
    assertThat(new AtomicInteger(frodo.age - sauron.age)).hasNonPositiveValue();
  }

  @Test
  public void number_assertions_with_custom_comparison_examples() {

    Comparator<AtomicInteger> atomicAbsValueComparator = new AbsValueComparator<AtomicInteger>();

    // with absolute values comparator : |-8| == |8|
    assertThat(new AtomicInteger(-8)).usingComparator(atomicAbsValueComparator)
                                     .hasValue(8);

    // works with arrays !
    assertThat(new AtomicLongArray(new long[] { -1, 2, 3 })).usingElementComparator(absLongComparator)
                                                            .contains(1, 2, -3);
  }

  @Test
  public void atomic_number_array_assertions_examples() {
    AtomicIntegerArray actual = null;
    assertThat(actual).isNull();
    then(actual).isNull();

    actual = new AtomicIntegerArray(new int[] { 1, 2, 3, 4 });
    assertThat(actual).startsWith(1, 2)
                      .contains(3, atIndex(2))
                      .endsWith(4);

    then(actual).containsExactly(1, 2, 3, 4);
    then(actual).hasArray(new int[] { 1, 2, 3, 4 });

    then(actual).usingElementComparator(absValueComparator).containsExactly(-1, 2, 3, -4);
  }

  @Test
  public void atomic_reference_assertions_examples() {
    AtomicReference<String> actual = new AtomicReference<>("abc");
    assertThat(actual).hasValue("abc");
  }

  @Test
  public void atomic_reference_array_assertions_examples() {
    AtomicReferenceArray<String> actual = null;
    assertThat(actual).isNull();
    then(actual).isNull();

    actual = new AtomicReferenceArray<>(array("a", "b", "c", "d"));
    assertThat(actual).startsWith("a", "b")
                      .contains("c", atIndex(2))
                      .endsWith("d");
    then(actual).containsExactly("a", "b", "c", "d");
    then(actual).isSameAs(actual);
    then(actual).isEqualTo(actual);

    TolkienCharacter frodo = new TolkienCharacter("Frodo", 1.2);
    TolkienCharacter tallerFrodo = new TolkienCharacter("Frodo", 1.3);

    Comparator<Double> closeEnough = new Comparator<Double>() {
      double precision = 0.5;

      public int compare(Double d1, Double d2) {
        return Math.abs(d1 - d2) <= precision ? 0 : 1;
      }
    };

    AtomicReferenceArray<TolkienCharacter> hobbits = new AtomicReferenceArray<>(new TolkienCharacter[] { frodo });

    // assertions will pass
    assertThat(hobbits).usingComparatorForElementFieldsWithNames(closeEnough, "height")
                       .usingFieldByFieldElementComparator()
                       .contains(tallerFrodo);

    assertThat(hobbits).usingComparatorForElementFieldsWithNames(closeEnough, "height")
                       .usingElementComparatorOnFields("height")
                       .contains(tallerFrodo);

    assertThat(hobbits).usingComparatorForElementFieldsWithNames(closeEnough, "height")
                       .usingElementComparatorIgnoringFields("name")
                       .contains(tallerFrodo);

    assertThat(hobbits).usingComparatorForElementFieldsWithNames(closeEnough, "height")
                       .usingRecursiveFieldByFieldElementComparator()
                       .contains(tallerFrodo);

    AtomicReferenceArray<Byte> bytes = new AtomicReferenceArray<>(new Byte[] { 0x10, 0x20, 0x30 });
    assertThat(bytes).inHexadecimal()
                     .hasArray(new Byte[] { 0x10, 0x20, 0x30 })
                     .contains((byte) 0x30);
  }

  @SuppressWarnings("unused")
  public static class TolkienCharacter {
    private String name;
    private double height;

    public TolkienCharacter(String name, double height) {
      super();
      this.name = name;
      this.height = height;
    }

  }
}
