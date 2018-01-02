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

import static java.util.concurrent.atomic.AtomicIntegerFieldUpdater.newUpdater;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.Assertions.withinPercentage;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.assertj.examples.data.TolkienCharacter;
import org.junit.Test;

public class AtomicAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void atomic_assertions_examples() {

    AtomicInteger atomicInteger = new AtomicInteger(42);
    assertThat(atomicInteger).hasValue(42)
                             .doesNotHaveValue(50)
                             .hasPositiveValue()
                             .hasValueLessThan(50)
                             .hasValueGreaterThan(40)
                             .hasValueBetween(40, 50)
                             .hasValueCloseTo(45, within(3))
                             .hasValueCloseTo(45, withinPercentage(10));

    AtomicBoolean atomicBoolean = new AtomicBoolean(true);
    assertThat(atomicBoolean).isTrue();

    AtomicLongArray atomicLongArray = new AtomicLongArray(new long[] { 1, 2, 3 });
    assertThat(atomicLongArray).isNotEmpty()
                               .startsWith(1, 2)
                               .endsWith(3);

    AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[] { 1, 2, 3 });
    assertThat(atomicIntegerArray).isNotEmpty()
                                  .startsWith(1, 2)
                                  .endsWith(3);

    AtomicReference<String> atomicReference = new AtomicReference<>("foo");
    assertThat(atomicReference).hasValue("foo")
                               .doesNotHaveValue("bar");

    AtomicReferenceArray<String> abc = new AtomicReferenceArray<>(new String[] { "a", "b", "c" });
    assertThat(abc).contains("b", "a")
                   .contains("b", "a", "b");

    int age = gandalf.age;
    // TolkienCharacter's age is a volatile public int field
    AtomicIntegerFieldUpdater<TolkienCharacter> ageUpdater = newUpdater(TolkienCharacter.class, "age");
    ageUpdater.set(gandalf, 25);
    assertThat(ageUpdater).hasValue(25, gandalf);
    ageUpdater.set(gandalf, age);

    assertThat(new AtomicLong(5)).hasValueCloseTo(7L, within(3L))
                                 .hasValueCloseTo(7L, byLessThan(3L))
                                 .hasValueCloseTo(7, within(2L));

    assertThat(new AtomicInteger(5)).hasValueCloseTo(7, within(3))
                                    .hasValueCloseTo(7, byLessThan(3))
                                    .hasValueCloseTo(7, within(2));
  }

}
