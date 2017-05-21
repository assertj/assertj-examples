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

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.assertj.examples.data.Race.HOBBIT;

import java.math.BigDecimal;
import java.util.Set;

import org.assertj.core.util.BigDecimalComparator;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.Test;

public class ObjectAssertionsExamples extends AbstractAssertionsExamples {

  private static final BigDecimalComparator BIG_DECIMAL_COMPARATOR = new BigDecimalComparator();

  @Test
  public void issue_841() {
    BigDecimal value1 = BigDecimal.valueOf(100);
    BigDecimal value2 = value1.setScale(2);

    assertThat(value1).isNotEqualTo(value2);
    assertThat(value1.hashCode()).isNotEqualTo(value2.hashCode());
    assertThat(value1).isEqualByComparingTo(value2);

    class Foo {
      private final BigDecimal value;

      Foo(BigDecimal value) {
        this.value = value;
      }

      @SuppressWarnings("unused")
      BigDecimal getValue() {
        return value;
      }
    }

    Foo foo1 = new Foo(value1);
    Foo foo2 = new Foo(value2);

    assertThat(foo1).usingComparatorForType(BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                    .isEqualToComparingFieldByFieldRecursively(foo2);

    class Bar {
      private final Set<Foo> foos;

      Bar(Set<Foo> foos) {
        this.foos = foos;
      }

      @SuppressWarnings("unused")
      Set<Foo> getFoos() {
        return foos;
      }
    }

    assertThat(new Bar(singleton(foo1))).usingComparatorForType(BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                                        .isEqualToComparingFieldByFieldRecursively(new Bar(singleton(foo2)));
  }

  @Test
  public void returns_assertion() {
    assertThat(frodo).returns("Frodo", from(TolkienCharacter::getName))
                     .returns(HOBBIT, from(TolkienCharacter::getRace))
                     .returns(HOBBIT, TolkienCharacter::getRace);
  }

}
