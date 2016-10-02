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

    // Check column "name" values
    assertThat(table).column("name")
        .value().isEqualTo("Hewson")
        .value().isEqualTo("Evans")
        .value().isEqualTo("Clayton")
        .value().isEqualTo("Mullen");

    // Check row at index 1 (the second row) values
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
        .hasValues("Hewson", "Evans", "Clayton", "Mullen");
  }

  /**
   * This example shows a simple case of test on row.
   */
  @Test
  public void basic_row_table_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table).row(1)
        .hasValues(2, "Evans", "David Howell", "The Edge", DateValue.of(1961, 8, 8), 1.77)
        .hasValues("2", "Evans", "David Howell", "The Edge", "1961-08-08", "1.77");

    Table table1 = new Table(dataSource, "albums");

    assertThat(table1).row()
        .hasValues(1, DateValue.of(1980, 10, 20), "Boy", 12, TimeValue.of(0, 42, 17), null)
        .hasValues("1", "1980-10-20", "Boy", "12", "00:42:17", null);
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
        .hasValues(1, "Hewson", "Paul David");
  }

  /**
   * This example shows the exclusion of columns in table.
   */
  @Test
  public void table_exclusion_examples() {
    Table table = new Table(dataSource, "members", null, new String[] {"id", "name", "firstname"});

    assertThat(table).hasNumberOfColumns(3)
        .row().hasNumberOfColumns(3)
            .hasValues("Bono", "1960-05-10", 1.75);
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
        .value().isDate().isOfAnyTypeIn(ValueType.DATE, ValueType.NUMBER)
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
        .column().isDate(false).isOfAnyTypeIn(ValueType.DATE, ValueType.NUMBER)
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

  /**
   * This example shows the assertion on number of columns.
   */
  @Test
  public void columns_number_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table).hasNumberOfColumns(6)
        .row().hasNumberOfColumns(6);
  }

  /**
   * This example shows the assertion on number of rows.
   */
  @Test
  public void rows_number_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table).hasNumberOfRows(4)
        .column().hasNumberOfRows(4);
  }

  /**
   * This example shows the assertion on name of columns.
   */
  @Test
  public void column_name_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table)
        .column().hasColumnName("id")
        .column().hasColumnName("Name")
        .column().hasColumnName("firstName")
        .column().hasColumnName("SURNAME")
        .column().hasColumnName("BiRThDate")
        .column().hasColumnName("size");
  }

  /**
   * This example shows the assertion on type of columns.
   */
  @Test
  public void column_type_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table)
        .column().isNumber(false).isOfType(ValueType.NUMBER, false).isOfAnyTypeIn(ValueType.NUMBER)
        .column().isText(false).isOfType(ValueType.TEXT, false).isOfAnyTypeIn(ValueType.TEXT)
        .column().isText(false).isOfType(ValueType.TEXT, false).isOfAnyTypeIn(ValueType.TEXT)
        .column().isText(true).isOfType(ValueType.TEXT, true).isOfAnyTypeIn(ValueType.TEXT, ValueType.NOT_IDENTIFIED)
        .column().isDate(true).isOfType(ValueType.DATE, false).isOfAnyTypeIn(ValueType.DATE)
        .column().isNumber(false).isOfType(ValueType.NUMBER, false).isOfAnyTypeIn(ValueType.NUMBER);
  }

  /**
   * This example shows the assertion on equality on the values of columns.
   */
  @Test
  public void column_equality_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table)
        .column().hasValues(1, 2, 3, 4)
        .column().hasValues("Hewson", "Evans", "Clayton", "Mullen")
        .column().hasValues("Paul David", "David Howell", "Adam", "Larry")
        .column().hasValues("Bono", "The Edge", null, null)
        .column().hasValues(DateValue.of(1960, 5, 10), DateValue.of(1961, 8, 8), 
            DateValue.of(1960, 3, 13), DateValue.of(1961, 10, 31))
        .column().hasValues(1.75, 1.77, 1.78, 1.7);
  }

  /**
   * This example shows the assertion on equality on the values of columns.
   */
  @Test
  public void row_equality_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table)
        .row().hasValues(1, "Hewson", "Paul David", "Bono", DateValue.of(1960, 5, 10), 1.75)
        .row().hasValues(2, "Evans", "David Howell", "The Edge", DateValue.of(1961, 8, 8), 1.77)
        .row().hasValues(3, "Clayton", "Adam", null, DateValue.of(1960, 3, 13), 1.78)
        .row().hasValues(4, "Mullen", "Larry", null, DateValue.of(1961, 10, 31), 1.7);
  }

  /**
   * This example shows the assertion on nullity on the values of columns.
   */
  @Test
  public void column_nullity_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table)
        .column().hasOnlyNotNullValues()
        .column().hasOnlyNotNullValues()
        .column().hasOnlyNotNullValues()
        .column()
        .column().hasOnlyNotNullValues()
        .column().hasOnlyNotNullValues();
  }

  /**
   * This example shows the assertion on equality on the values.
   */
  @Test
  public void value_equality_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table)
        .column()
            .value().isEqualTo(1)
            .value().isEqualTo(2)
            .value().isEqualTo(3)
            .value().isEqualTo(4)
        .column()
            .value().isEqualTo("Hewson")
            .value().isEqualTo("Evans")
            .value().isEqualTo("Clayton")
            .value().isEqualTo("Mullen")
        .column()
            .value().isEqualTo("Paul David")
            .value().isEqualTo("David Howell")
            .value().isEqualTo("Adam")
            .value().isEqualTo("Larry")
        .column()
            .value().isEqualTo("Bono")
            .value().isEqualTo("The Edge")
            .value()
            .value()
        .column()
            .value().isEqualTo(DateValue.of(1960, 5, 10))
            .value().isEqualTo(DateValue.of(1961, 8, 8))
            .value().isEqualTo(DateValue.of(1960, 3, 13))
            .value().isEqualTo(DateValue.of(1961, 10, 31))
        .column()
            .value().isEqualTo(1.75)
            .value().isEqualTo(1.77)
            .value().isEqualTo(1.78)
            .value().isEqualTo(1.7)
        .row()
            .value().isEqualTo(1)
            .value().isEqualTo("Hewson")
            .value().isEqualTo("Paul David")
            .value().isEqualTo("Bono")
            .value().isEqualTo(DateValue.of(1960, 5, 10))
            .value().isEqualTo(1.75)
        .row()
            .value().isEqualTo(2)
            .value().isEqualTo("Evans")
            .value().isEqualTo("David Howell")
            .value().isEqualTo("The Edge")
            .value().isEqualTo(DateValue.of(1961, 8, 8))
            .value().isEqualTo(1.77)
        .row()
            .value().isEqualTo(3)
            .value().isEqualTo("Clayton")
            .value().isEqualTo("Adam")
            .value()
            .value().isEqualTo(DateValue.of(1960, 3, 13))
            .value().isEqualTo(1.78)
        .row()
            .value().isEqualTo(4)
            .value().isEqualTo("Mullen")
            .value().isEqualTo("Larry")
            .value()
            .value().isEqualTo(DateValue.of(1961, 10, 31))
            .value().isEqualTo(1.7);
  }

  /**
   * This example shows the assertion on nullity on the values.
   */
  @Test
  public void value_nullity_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table)
        .column()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
        .column()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
        .column()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
        .column()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNull()
            .value().isNull()
        .column()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
        .column()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
        .row()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
        .row()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
        .row()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNull()
            .value().isNotNull()
            .value().isNotNull()
        .row()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNull()
            .value().isNotNull()
            .value().isNotNull();
  }

  /**
   * This example shows the assertion on type of the values.
   */
  @Test
  public void value_type_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table)
        .column()
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
        .column()
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
        .column()
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
        .column()
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
        .column()
            .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE)
            .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE)
            .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE)
            .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE)
        .column()
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
        .row()
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE)
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
        .row()
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE)
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
        .row()
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE)
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
        .row()
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE)
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER);
  }

  /**
   * This example shows the assertion on non equality on the values.
   */
  @Test
  public void value_non_equality_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table)
        .column()
            .value().isNotEqualTo(10)
            .value().isNotEqualTo(20)
            .value().isNotEqualTo(30)
            .value().isNotEqualTo(40)
        .column()
            .value().isNotEqualTo("H0ewson")
            .value().isNotEqualTo("Ev0ans")
            .value().isNotEqualTo("Cla0yton")
            .value().isNotEqualTo("Mull0en")
        .column()
            .value().isNotEqualTo("Paul 0David")
            .value().isNotEqualTo("David 0Howell")
            .value().isNotEqualTo("0Adam")
            .value().isNotEqualTo("L0arry")
        .column()
            .value().isNotEqualTo("Bo0no")
            .value().isNotEqualTo("The0 Edge")
            .value()
            .value()
        .column()
            .value().isNotEqualTo(DateValue.of(160, 5, 10))
            .value().isNotEqualTo(DateValue.of(161, 8, 8))
            .value().isNotEqualTo(DateValue.of(160, 3, 13))
            .value().isNotEqualTo(DateValue.of(161, 10, 31))
        .column()
            .value().isNotEqualTo(175)
            .value().isNotEqualTo(177)
            .value().isNotEqualTo(178)
            .value().isNotEqualTo(17)
        .row()
            .value().isNotEqualTo(10)
            .value().isNotEqualTo("H0ewson")
            .value().isNotEqualTo("Pa0ul David")
            .value().isNotEqualTo("Bon0o")
            .value().isNotEqualTo(DateValue.of(196, 5, 10))
            .value().isNotEqualTo(1.7)
        .row()
            .value().isNotEqualTo(12)
            .value().isNotEqualTo("vans")
            .value().isNotEqualTo("avid Howell")
            .value().isNotEqualTo("he Edge")
            .value().isNotEqualTo(DateValue.of(1961, 9, 8))
            .value().isNotEqualTo(1.7)
        .row()
            .value().isNotEqualTo(39)
            .value().isNotEqualTo("Cayton")
            .value().isNotEqualTo("Aam")
            .value()
            .value().isNotEqualTo(DateValue.of(1960, 2, 13))
            .value().isNotEqualTo(1.8)
        .row()
            .value().isNotEqualTo(48)
            .value().isNotEqualTo("Mulln")
            .value().isNotEqualTo("Larr")
            .value()
            .value().isNotEqualTo(DateValue.of(1961, 10, 1))
            .value().isNotEqualTo(1.07);
  }

  /**
   * This example shows the assertion on comparison on the values.
   */
  @Test
  public void value_comparison_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table)
        .column()
            .value()
                .isGreaterThanOrEqualTo(1)
                .isLessThanOrEqualTo(1)
                .isGreaterThan(0)
                .isLessThan(2)
            .value()
                .isGreaterThanOrEqualTo(2)
                .isLessThanOrEqualTo(2)
                .isGreaterThan(1)
                .isLessThan(3)
            .value()
                .isGreaterThanOrEqualTo(3)
                .isLessThanOrEqualTo(3)
                .isGreaterThan(2)
                .isLessThan(4)
            .value()
                .isGreaterThanOrEqualTo(4)
                .isLessThanOrEqualTo(4)
                .isGreaterThan(3)
                .isLessThan(5)
        .column("size")
            .value()
                .isGreaterThanOrEqualTo(1.75)
                .isLessThanOrEqualTo(1.75)
                .isGreaterThan(1.74)
                .isLessThan(1.76)
            .value()
                .isGreaterThanOrEqualTo(1.77)
                .isLessThanOrEqualTo(1.77)
                .isGreaterThan(1.76)
                .isLessThan(1.78)
            .value()
                .isGreaterThanOrEqualTo(1.78)
                .isLessThanOrEqualTo(1.78)
                .isGreaterThan(1.77)
                .isLessThan(1.79)
            .value()
                .isGreaterThanOrEqualTo(1.7)
                .isLessThanOrEqualTo(1.7)
                .isGreaterThan(1.6)
                .isLessThan(1.8)
        .row()
            .value()
                .isGreaterThanOrEqualTo(1)
                .isLessThanOrEqualTo(1)
                .isGreaterThan(0)
                .isLessThan(2)
            .value("size")
                .isGreaterThanOrEqualTo(1.75)
                .isLessThanOrEqualTo(1.75)
                .isGreaterThan(1.74)
                .isLessThan(1.76)
        .row()
            .value()
                .isGreaterThanOrEqualTo(2)
                .isLessThanOrEqualTo(2)
                .isGreaterThan(1)
                .isLessThan(3)
            .value("size")
                .isGreaterThanOrEqualTo(1.77)
                .isLessThanOrEqualTo(1.77)
                .isGreaterThan(1.76)
                .isLessThan(1.78)
        .row()
            .value()
                .isGreaterThanOrEqualTo(3)
                .isLessThanOrEqualTo(3)
                .isGreaterThan(2)
                .isLessThan(4)
            .value("size")
                .isGreaterThanOrEqualTo(1.78)
                .isLessThanOrEqualTo(1.78)
                .isGreaterThan(1.77)
                .isLessThan(1.79)
        .row()
            .value()
                .isGreaterThanOrEqualTo(4)
                .isLessThanOrEqualTo(4)
                .isGreaterThan(3)
                .isLessThan(5)
            .value("size")
                .isGreaterThanOrEqualTo(1.7)
                .isLessThanOrEqualTo(1.7)
                .isGreaterThan(1.6)
                .isLessThan(1.8);
  }

  /**
   * This example shows the assertion on chronology on the values.
   */
  @Test
  public void value_chronology_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table)
        .column("birthdate")
            .value()
                .isBeforeOrEqualTo(DateValue.of(1960, 5, 10))
                .isAfterOrEqualTo(DateValue.of(1960, 5, 10))
                .isBefore(DateValue.of(1960, 5, 11))
                .isAfter(DateValue.of(1960, 5, 9))
            .value()
                .isBeforeOrEqualTo(DateValue.of(1961, 8, 8))
                .isAfterOrEqualTo(DateValue.of(1961, 8, 8))
                .isBefore(DateValue.of(1961, 8, 9))
                .isAfter(DateValue.of(1961, 8, 7))
            .value()
                .isBeforeOrEqualTo(DateValue.of(1960, 3, 13))
                .isAfterOrEqualTo(DateValue.of(1960, 3, 13))
                .isBefore(DateValue.of(1960, 3, 14))
                .isAfter(DateValue.of(1960, 3, 12))
            .value()
                .isBeforeOrEqualTo(DateValue.of(1961, 10, 31))
                .isAfterOrEqualTo(DateValue.of(1961, 10, 31))
                .isBefore(DateValue.of(1961, 11, 1))
                .isAfter(DateValue.of(1961, 10, 30))
        .column()
        .row()
            .value("birthdate")
                .isBeforeOrEqualTo(DateValue.of(1960, 5, 10))
                .isAfterOrEqualTo(DateValue.of(1960, 5, 10))
                .isBefore(DateValue.of(1960, 5, 11))
                .isAfter(DateValue.of(1960, 5, 9))
        .row()
            .value("birthdate")
                .isBeforeOrEqualTo(DateValue.of(1961, 8, 8))
                .isAfterOrEqualTo(DateValue.of(1961, 8, 8))
                .isBefore(DateValue.of(1961, 8, 9))
                .isAfter(DateValue.of(1961, 8, 7))
        .row()
            .value("birthdate")
                .isBeforeOrEqualTo(DateValue.of(1960, 3, 13))
                .isAfterOrEqualTo(DateValue.of(1960, 3, 13))
                .isBefore(DateValue.of(1960, 3, 14))
                .isAfter(DateValue.of(1960, 3, 12))
        .row()
            .value("birthdate")
                .isBeforeOrEqualTo(DateValue.of(1961, 10, 31))
                .isAfterOrEqualTo(DateValue.of(1961, 10, 31))
                .isBefore(DateValue.of(1961, 11, 1))
                .isAfter(DateValue.of(1961, 10, 30));
  }
}
