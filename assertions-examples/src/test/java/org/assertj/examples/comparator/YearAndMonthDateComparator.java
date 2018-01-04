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
package org.assertj.examples.comparator;

import static org.assertj.core.util.DateUtil.monthOf;
import static org.assertj.core.util.DateUtil.yearOf;

import java.util.Comparator;
import java.util.Date;

public class YearAndMonthDateComparator implements Comparator<Date> {

  @Override
  public int compare(Date d1, Date d2) {
    int yearDifference = yearOf(d1) - yearOf(d2);
    return yearDifference != 0 ? yearDifference : monthOf(d1) - monthOf(d2);
  }

  @Override
  public String toString() {
    return "YearAndMonthDateComparator";
  }

}
