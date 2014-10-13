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
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.examples.db;

import static org.assertj.db.api.Assertions.assertThat;

import org.assertj.db.type.DateValue;
import org.assertj.db.type.Table;
import org.junit.Test;

public class TableAssertionExamples extends AbstractAssertionsExamples {

  /**
   * This example shows a simple case of test.
   */
  @Test
  public void basic_table_assertion_examples() {
    Table table = new Table(dataSource, "members");

    // On the values of a column by using the name of the column
    assertThat(table).column("name")
        .value().isEqualTo("Hewson")
        .value().isEqualTo("Evans")
        .value().isEqualTo("Clayton")
        .value().isEqualTo("Mullen");

    // On the values of a row by using the index of the row
    assertThat(table).row(1)
        .value().isEqualTo(2)
        .value().isEqualTo("Evans")
        .value().isEqualTo("David Howell")
        .value().isEqualTo("The Edge")
        .value().isEqualTo(DateValue.of(1961, 8, 8))
        .value().isEqualTo(1.77);
  }

  /**
   * This example shows how test the size.
   */
  @Test
  public void size_table_assertion_examples() {
    Table table = new Table(dataSource, "members");

    // There is assertion to test the column and row size.
    assertThat(table).hasColumnsSize(6);
    assertThat(table).hasRowsSize(4);

    // There are equivalences of these size assertions on the column and on the row.
    assertThat(table).column().hasSize(4);
    assertThat(table).row().hasSize(6);
  }

  /**
   * This example shows that the numeric value can be test with text.
   */
  @Test
  public void text_for_numeric_table_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table).row(1)
        .value("size").isEqualTo("1.77");

    Table table1 = new Table(dataSource, "albums");

    assertThat(table1).row(14)
        .value("numberofsongs").isEqualTo("11");
  }

  /**
   * This example shows possible assertions on the date.
   */
  @Test
  public void date_table_assertion_examples() {
    Table table = new Table(dataSource, "members");

    // Compare date to date or date in string format
    assertThat(table).row(1)
        .value("birthdate").isEqualTo("1961-08-08")
            .isAfter("1961-08-07")
            .isAfter(DateValue.of(1961, 8, 7))
            .isBefore("1961-08-09")
            .isBefore(DateValue.of(1961, 8, 9))
            .isAfterOrEqualTo("1961-08-08")
            .isAfterOrEqualTo(DateValue.of(1961, 8, 7))
            .isBeforeOrEqualTo("1961-08-08")
            .isBeforeOrEqualTo(DateValue.of(1961, 8, 9));

    // Compare date to date/time or date/time in string format
    assertThat(table).row(1)
        .value("birthdate").isEqualTo("1961-08-08T00:00")
            .isAfter("1961-08-07T23:59")
            .isAfter(DateTimeValue.of(DateValue.of(1961, 8, 7)))
            .isBefore("1961-08-08T00:00:01")
            .isBefore(DateTimeValue.of(DateValue.of(1961, 8, 8), TimeValue(0, 0, 1)))
            .isAfterOrEqualTo("1961-08-08T00:00")
            .isAfterOrEqualTo(DateValue.of(1961, 8, 7))
            .isBeforeOrEqualTo("1961-08-08T00:00:00.000000000")
            .isBeforeOrEqualTo(DateTimeValue.of(DateValue.of(1961, 8, 9), TimeValue(0, 0, 1, 6, 3)));
  }
}
