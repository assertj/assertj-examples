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
package org.assertj.examples.custom;

import org.assertj.core.api.*;
import org.assertj.examples.data.*;

public class EmployeeOfTheMonthAssert<T extends EmployeeOfTheMonthAssert<?,?>, A extends EmployeeOfTheMonth> extends EmployeeAssert<T,A> {

    public static <T extends EmployeeOfTheMonthAssert<?,?>, A extends EmployeeOfTheMonth> EmployeeOfTheMonthAssert<T,A> assertThat(A actual) {
      return new EmployeeOfTheMonthAssert<T,A>(actual);
    }

    public EmployeeOfTheMonthAssert(A actual) {
        super(actual);
    }

    public T forMonth(int month) {
      isNotNull();
      if (actual.getMonth() != month) {
        failWithMessage("Expected month to be <%s> but was <%s>", month, actual.getMonth());
      }
      return (T) this;
    }

}

