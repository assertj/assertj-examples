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
package org.assertj.examples.custom;

import java.math.BigDecimal;

import org.assertj.core.api.AbstractBigDecimalAssert;

// public class MyBigDecimalAssert extends BigDecimalAssert {
public class MyAbstractBigDecimalAssert<S extends MyAbstractBigDecimalAssert<S>> extends AbstractBigDecimalAssert<S> {

  protected MyAbstractBigDecimalAssert(BigDecimal actual, Class<S> selfType) {
    super(actual, selfType);
  }

  public S isOne() {
    // check condition
    if (actual != null && actual.compareTo(BigDecimal.ONE) != 0) {
      failWithMessage("Expected BigDecimal to be one but was <%s>", actual);
    }

    // return the current assertion for method chaining
    return myself;
  }

}
