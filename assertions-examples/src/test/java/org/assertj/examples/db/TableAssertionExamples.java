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

import org.assertj.db.type.*;
import org.junit.Test;

import static org.assertj.db.api.Assertions.assertThat;

/**
 * {@link Table} assertions example.
 * 
 * @author Régis Pouiller
 */
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
   * This example shows a simple case of test on column.
   */
  @Test
  public void basic_column_table_assertion_examples() {
    Source source = new Source("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");
    Table table = new Table(source, "members");

    assertThat(table).column("name")
        .hasValuesEqualTo("Hewson", "Evans", "Clayton", "Mullen");
  }

  /**
   * This example shows a simple case of test on row.
   */
  @Test
  public void basic_row_table_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table).row(1)
        .hasValuesEqualTo(2, "Evans", "David Howell", "The Edge", DateValue.of(1961, 8, 8), 1.77)
        .hasValuesEqualTo("2", "Evans", "David Howell", "The Edge", "1961-08-08", "1.77");

    Table table1 = new Table(dataSource, "albums");

    assertThat(table1).row()
        .hasValuesEqualTo(1, DateValue.of(1980, 10, 20), "Boy", 12, TimeValue.of(0, 42, 17), null)
        .hasValuesEqualTo("1", "1980-10-20", "Boy", "12", "00:42:17", null);
  }

  /**
   * This example shows how test the size.
   */
  @Test
  public void size_table_assertion_examples() {
    Table table = new Table(dataSource, "members");

    // There is assertion to test the column and row size.
    assertThat(table).hasNumberOfColumns(6);
    assertThat(table).hasNumberOfRows(4);

    // There are equivalences of these size assertions on the column and on the row.
    assertThat(table).column().hasNumberOfRows(4);
    assertThat(table).row().hasNumberOfColumns(6);
  }

  /**
   * This example shows the inclusion of columns in table.
   */
  @Test
  public void table_inclusion_examples() {
    Table table = new Table(dataSource, "members", new String[] {"id", "name", "firstname"}, null);

    assertThat(table).hasNumberOfColumns(3);
    assertThat(table).row().hasNumberOfColumns(3)
        .hasValuesEqualTo(1, "Hewson", "Paul David");
  }

  /**
   * This example shows the exclusion of columns in table.
   */
  @Test
  public void table_exclusion_examples() {
    Table table = new Table(dataSource, "members", null, new String[] {"id", "name", "firstname"});

    assertThat(table).hasNumberOfColumns(3)
        .row().hasNumberOfColumns(3)
            .hasValuesEqualTo("Bono", "1960-05-10", 1.75);
  }

  /**
   * This example shows that the numeric value can be test with text.
   */
  @Test
  public void text_for_numeric_table_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table).row(1)
        .value("size").isEqualTo("1.77").isNotEqualTo("1.78");

    Table table1 = new Table(dataSource, "albums");

    assertThat(table1).row(14)
        .value("numberofsongs").isEqualTo("11").isNotEqualTo("12");
  }

  /**
   * This example shows tests on numeric values.
   */
  @Test
  public void numeric_table_assertion_examples() {
    Source source = new Source("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");
    Table table = new Table(source, "members");

    assertThat(table).row(1)
        .value("size").isNotZero()
            .isGreaterThan(1.5).isGreaterThanOrEqualTo(1.77)
            .isLessThan(2).isLessThanOrEqualTo(1.77);

    Table table1 = new Table(dataSource, "albums");

    assertThat(table1).row(14)
        .value("numberofsongs").isNotZero()
            .isGreaterThan(10).isGreaterThanOrEqualTo(11)
            .isLessThan(11.5).isLessThanOrEqualTo(11);
  }

  /**
   * This example shows boolean assertions.
   */
  @Test
  public void boolean_table_assertion_examples() {
    Table table = new Table(dataSource, "albums");

    assertThat(table).column("live")
        .value(3).isTrue()
        .value().isNull()
        .value().isEqualTo(true).isNotEqualTo(false);
  }

  /**
   * This example shows type assertions.
   */
  @Test
  public void type_table_assertion_examples() {
    Source source = new Source("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");
    Table table = new Table(source, "albums");

    assertThat(table).row(3)
        .value().isNumber()
        .value().isDate().isOfAnyOfTypes(ValueType.DATE, ValueType.NUMBER)
        .value().isText()
        .value().isNumber().isOfType(ValueType.NUMBER)
        .value().isTime()
        .value().isBoolean();
  }

  /**
   * This example shows type assertions on column.
   */
  @Test
  public void colum_type_table_assertion_examples() {
    Table table = new Table(dataSource, "albums");

    assertThat(table)
        .column().isNumber(false)
        .column().isDate(false).isOfAnyOfTypes(ValueType.DATE, ValueType.NUMBER)
        .column().isText(false)
        .column().isNumber(false).isOfType(ValueType.NUMBER, false)
        .column().isTime(false)
        .column().isBoolean(true);
  }

  /**
   * This example shows possible assertions on the date.
   * In this example, we can see that the assertions are 
   */
  @Test
  public void date_table_assertion_examples() {
    Source source = new Source("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");
    Table table = new Table(source, "members");

    // Compare date to date or date in string format
    assertThat(table).row(1)
        .value("birthdate")
            .isEqualTo(DateValue.of(1961, 8, 8))
            .isEqualTo("1961-08-08")
            .isNotEqualTo("1968-08-09")
            .isNotEqualTo(DateValue.of(1961, 8, 9))
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
        .value("birthdate")
            .isEqualTo(DateTimeValue.of(DateValue.of(1961, 8, 8), TimeValue.of(0, 0, 0)))
            .isEqualTo("1961-08-08T00:00")
            .isNotEqualTo("1961-08-08T00:00:01")
            .isNotEqualTo(DateTimeValue.of(DateValue.of(1961, 8, 8), TimeValue.of(0, 0, 1)))
            .isAfter("1961-08-07T23:59")
            .isAfter(DateTimeValue.of(DateValue.of(1961, 8, 7)))
            .isBefore("1961-08-08T00:00:01")
            .isBefore(DateTimeValue.of(DateValue.of(1961, 8, 8), TimeValue.of(0, 0, 1)))
            .isAfterOrEqualTo("1961-08-08T00:00")
            .isAfterOrEqualTo(DateValue.of(1961, 8, 7))
            .isBeforeOrEqualTo("1961-08-08T00:00:00.000000000")
            .isBeforeOrEqualTo(DateTimeValue.of(DateValue.of(1961, 8, 9), TimeValue.of(0, 0, 1, 3)));
  }
}
