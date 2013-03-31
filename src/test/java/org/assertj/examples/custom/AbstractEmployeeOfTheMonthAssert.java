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


public class AbstractEmployeeOfTheMonthAssert<S extends AbstractEmployeeOfTheMonthAssert<S, A>, A extends EmployeeOfTheMonth>
    extends AbstractEmployeeAssert<S, A> {

  public AbstractEmployeeOfTheMonthAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

  public S forMonth(int month) {
    isNotNull();
    // we overrides the default error message with a more explicit one
    String errorMessage = "\nExpected month of:\n  <%s>\nto be:\n  <%s>\n but was:\n  <%s>";

    // check
    if (actual.getMonth() != month) {
      failWithMessage(errorMessage, actual, month, actual.getMonth());
    }
    
    return myself;
  }

}
