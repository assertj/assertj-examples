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
 * {@link Request} assertions example.
 * 
 * @author Régis Pouiller
 */
public class RequestAssertionExamples extends AbstractAssertionsExamples {

  /**
   * This example shows a simple case of test.
   */
  @Test
  public void basic_request_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    // On the values of a column by using the name of the column
    assertThat(request).column("title")
        .value().isEqualTo("Boy")
        .value().isEqualTo("October")
        .value().isEqualTo("War")
        .value().isEqualTo("Under a Blood Red Sky")
        .value().isEqualTo("The Unforgettable Fire")
        .value().isEqualTo("Wide Awake in America")
        .value().isEqualTo("The Joshua Tree")
        .value().isEqualTo("Rattle and Hum")
        .value().isEqualTo("Achtung Baby")
        .value().isEqualTo("Zooropa")
        .value().isEqualTo("Pop")
        .value().isEqualTo("All That You Can't Leave Behind")
        .value().isEqualTo("How to Dismantle an Atomic Bomb")
        .value().isEqualTo("No Line on the Horizon")
        .value().isEqualTo("Songs of Innocence");

    // On the values of a row by using the index of the row
    assertThat(request).row(1)
        .value().isEqualTo(2)
        .value().isEqualTo(DateValue.of(1981, 10, 12))
        .value().isEqualTo("October")
        .value().isEqualTo(11)
        .value().isEqualTo(TimeValue.of(0, 41, 8))
        .value().isNull();
  }

  /**
   * This example shows a simple case of test on column.
   */
  @Test
  public void basic_column_request_assertion_examples() {
    Source source = new Source("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");
    Request request = new Request(source, "select * from albums");

    assertThat(request).column("title")
        .hasValues("Boy", "October", "War", "Under a Blood Red Sky",
                "The Unforgettable Fire", "Wide Awake in America", "The Joshua Tree",
                "Rattle and Hum", "Achtung Baby", "Zooropa", "Pop", "All That You Can't Leave Behind",
                "How to Dismantle an Atomic Bomb", "No Line on the Horizon", "Songs of Innocence");
  }

  /**
   * This example shows a simple case of test on row.
   */
  @Test
  public void basic_row_request_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request).row(1)
        .hasValues(2, DateValue.of(1981, 10, 12), "October", 11, TimeValue.of(0, 41, 8), null)
        .hasValues("2", "1981-10-12", "October", "11", "00:41:08", null);
  }

  /**
   * This example shows how test the size.
   */
  @Test
  public void size_request_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    // There is assertion to test the column and row size.
    assertThat(request).hasNumberOfColumns(6);
    assertThat(request).hasNumberOfRows(15);

    // There are equivalences of these size assertions on the column and on the row.
    assertThat(request).column().hasNumberOfRows(15);
    assertThat(request).row().hasNumberOfColumns(6);
  }

  /**
   * This example shows the inclusion of columns in table.
   */
  @Test
  public void request_parameters_examples() {
    Request request = new Request(dataSource, "select release, title from albums where title like ?", "A%");

    assertThat(request).hasNumberOfColumns(2).hasNumberOfRows(2);
    assertThat(request)
        .row().hasNumberOfColumns(2).hasValues("1991-11-18", "Achtung Baby")
        .row().hasValues("2000-10-30", "All That You Can't Leave Behind");
  }

  /**
   * This example shows that the numeric value can be test with text.
   */
  @Test
  public void text_for_numeric_request_assertion_examples() {
    Request request = new Request(dataSource, "select * from members");

    assertThat(request).row(1)
        .value("size").isEqualTo("1.77").isNotEqualTo("1.78");

    Request request1 = new Request(dataSource, "select * from albums");

    assertThat(request1).row(14)
        .value("numberofsongs").isEqualTo("11").isNotEqualTo("12");
  }

  /**
   * This example shows tests on numeric values.
   */
  @Test
  public void numeric_request_assertion_examples() {
    Source source = new Source("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");
    Request request = new Request(source, "select * from members");

    assertThat(request).row(1)
        .value("size").isNotZero()
            .isGreaterThan(1.5).isGreaterThanOrEqualTo(1.77)
            .isLessThan(2).isLessThanOrEqualTo(1.77);

    Request request1 = new Request(dataSource, "select * from albums");

    assertThat(request1).row(14)
        .value("numberofsongs").isNotZero()
            .isGreaterThan(10).isGreaterThanOrEqualTo(11)
            .isLessThan(11.5).isLessThanOrEqualTo(11);
  }

  /**
   * This example shows boolean assertions.
   */
  @Test
  public void boolean_request_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request).column("live")
        .value(3).isTrue()
        .value().isNull()
        .value().isEqualTo(true).isNotEqualTo(false);
  }

  /**
   * This example shows type assertions.
   */
  @Test
  public void type_request_assertion_examples() {
    Source source = new Source("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");
    Request request = new Request(source, "select * from albums");

    assertThat(request).row(3)
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
  public void colum_type_request_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
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
  public void date_request_assertion_examples() {
    Source source = new Source("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");
    Request request = new Request(source, "select * from members");

    // Compare date to date or date in string format
    assertThat(request).row(1)
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
    assertThat(request).row(1)
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
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request).hasNumberOfColumns(6)
        .row().hasNumberOfColumns(6);
  }

  /**
   * This example shows the assertion on number of rows.
   */
  @Test
  public void rows_number_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request).hasNumberOfRows(15)
        .column().hasNumberOfRows(15);
  }

  /**
   * This example shows the assertion on name of columns.
   */
  @Test
  public void column_name_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
        .column().hasColumnName("id")
        .column().hasColumnName("reLease")
        .column().hasColumnName("tiTle")
        .column().hasColumnName("numberOfSongs")
        .column().hasColumnName("DuraTion")
        .column().hasColumnName("Live");
  }

  /**
   * This example shows the assertion on type of columns.
   */
  @Test
  public void column_type_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
        .column().isNumber(false).isOfType(ValueType.NUMBER, false).isOfAnyTypeIn(ValueType.NUMBER)
        .column().isDate(false).isOfType(ValueType.DATE, false).isOfAnyTypeIn(ValueType.DATE)
        .column().isText(false).isOfType(ValueType.TEXT, false).isOfAnyTypeIn(ValueType.TEXT)
        .column().isNumber(false).isOfType(ValueType.NUMBER, false).isOfAnyTypeIn(ValueType.NUMBER)
        .column().isTime(false).isOfType(ValueType.TIME, false).isOfAnyTypeIn(ValueType.TIME)
        .column().isBoolean(true).isOfType(ValueType.BOOLEAN, true).isOfAnyTypeIn(ValueType.BOOLEAN, ValueType.NOT_IDENTIFIED);
  }

  /**
   * This example shows the assertion on equality on the values of columns.
   */
  @Test
  public void column_equality_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
        .column().hasValues(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
        .column().hasValues(DateValue.of(1980, 10, 20), DateValue.of(1981, 10, 12), 
            DateValue.of(1983, 2, 28), DateValue.of(1983, 11, 7), 
            DateValue.of(1984, 10, 1), DateValue.of(1985, 6, 10),
            DateValue.of(1987, 3, 9), DateValue.of(1988, 10, 10),
            DateValue.of(1991, 11, 18), DateValue.of(1993, 7, 6),
            DateValue.of(1997, 3, 3), DateValue.of(2000, 10, 30),
            DateValue.of(2004, 11, 22), DateValue.of(2009, 3, 2),
            DateValue.of(2014, 9, 9))
        .column().hasValues("Boy", "October", "War", "Under a Blood Red Sky", "The Unforgettable Fire", 
            "Wide Awake in America", "The Joshua Tree", "Rattle and Hum", "Achtung Baby", "Zooropa", "Pop", 
            "All That You Can't Leave Behind", "How to Dismantle an Atomic Bomb", "No Line on the Horizon", 
            "Songs of Innocence")
        .column().hasValues(12, 11, 10, 8, 10, 4, 11, 17, 12, 10, 12, 11, 11, 11, 11)
        .column().hasValues(TimeValue.of(0, 42, 17),
            TimeValue.of(0, 41, 8),
            TimeValue.of(0, 42, 7),
            TimeValue.of(0, 33, 25),
            TimeValue.of(0, 42, 42),
            TimeValue.of(0, 20, 30),
            TimeValue.of(0, 50, 11),
            TimeValue.of(1, 12, 27),
            TimeValue.of(0, 55, 23),
            TimeValue.of(0, 51, 15),
            TimeValue.of(1, 0, 8),
            TimeValue.of(0, 49, 23),
            TimeValue.of(0, 49, 8),
            TimeValue.of(0, 53, 44),
            TimeValue.of(0, 48, 11))
        .column().hasValues(null, null, null, true, null, true, null, null, null, null, null, null, null, null, null);
  }

  /**
   * This example shows the assertion on equality on the values of columns.
   */
  @Test
  public void row_equality_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
        .row().hasValues(1, DateValue.of(1980, 10, 20), "Boy", 12, TimeValue.of(0, 42, 17), null)
        .row().hasValues(2, DateValue.of(1981, 10, 12), "October", 11, TimeValue.of(0, 41, 8), null)
        .row().hasValues(3, DateValue.of(1983, 2, 28), "War", 10, TimeValue.of(0, 42, 7), null)
        .row().hasValues(4, DateValue.of(1983, 11, 7), "Under a Blood Red Sky", 8, TimeValue.of(0, 33, 25), true)
        .row().hasValues(5, DateValue.of(1984, 10, 1), "The Unforgettable Fire", 10, TimeValue.of(0, 42, 42), null)
        .row().hasValues(6, DateValue.of(1985, 6, 10), "Wide Awake in America", 4, TimeValue.of(0, 20, 30), true)
        .row().hasValues(7, DateValue.of(1987, 3, 9), "The Joshua Tree", 11, TimeValue.of(0, 50, 11), null)
        .row().hasValues(8, DateValue.of(1988, 10, 10), "Rattle and Hum", 17, TimeValue.of(1, 12, 27), null)
        .row().hasValues(9, DateValue.of(1991, 11, 18), "Achtung Baby", 12, TimeValue.of(0, 55, 23), null)
        .row().hasValues(10, DateValue.of(1993, 7, 6), "Zooropa", 10, TimeValue.of(0, 51, 15), null)
        .row().hasValues(11, DateValue.of(1997, 3, 3), "Pop", 12, TimeValue.of(1, 0, 8), null)
        .row().hasValues(12, DateValue.of(2000, 10, 30), "All That You Can't Leave Behind", 11, TimeValue.of(0, 49, 23), null)
        .row().hasValues(13, DateValue.of(2004, 11, 22), "How to Dismantle an Atomic Bomb", 11, TimeValue.of(0, 49, 8), null)
        .row().hasValues(14, DateValue.of(2009, 3, 2), "No Line on the Horizon", 11, TimeValue.of(0, 53, 44), null)
        .row().hasValues(15, DateValue.of(2014, 9, 9), "Songs of Innocence", 11, TimeValue.of(0, 48, 11), null);
  }

  /**
   * This example shows the assertion on nullity on the values of columns.
   */
  @Test
  public void column_nullity_assertion_examples() {
    Request request = new Request(dataSource, "select * from members where id >= 3");

    assertThat(request)
        .column("surname").hasOnlyNullValues()
        .column().hasOnlyNotNullValues();
  }

  /**
   * This example shows the assertion on equality on the values.
   */
  @Test
  public void value_equality_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
        .column()
            .value().isEqualTo(1)
            .value().isEqualTo(2)
            .value().isEqualTo(3)
            .value().isEqualTo(4)
            .value().isEqualTo(5)
            .value().isEqualTo(6)
            .value().isEqualTo(7)
            .value().isEqualTo(8)
            .value().isEqualTo(9)
            .value().isEqualTo(10)
            .value().isEqualTo(11)
            .value().isEqualTo(12)
            .value().isEqualTo(13)
            .value().isEqualTo(14)
            .value().isEqualTo(15)
        .column()
            .value().isEqualTo(DateValue.of(1980, 10, 20))
            .value().isEqualTo(DateValue.of(1981, 10, 12))
            .value().isEqualTo(DateValue.of(1983, 2, 28))
            .value().isEqualTo(DateValue.of(1983, 11, 7))
            .value().isEqualTo(DateValue.of(1984, 10, 1))
            .value().isEqualTo(DateValue.of(1985, 6, 10))
            .value().isEqualTo(DateValue.of(1987, 3, 9))
            .value().isEqualTo(DateValue.of(1988, 10, 10))
            .value().isEqualTo(DateValue.of(1991, 11, 18))
            .value().isEqualTo(DateValue.of(1993, 7, 6))
            .value().isEqualTo(DateValue.of(1997, 3, 3))
            .value().isEqualTo(DateValue.of(2000, 10, 30))
            .value().isEqualTo(DateValue.of(2004, 11, 22))
            .value().isEqualTo(DateValue.of(2009, 3, 2))
            .value().isEqualTo(DateValue.of(2014, 9, 9))
        .column()
            .value().isEqualTo("Boy")
            .value().isEqualTo("October")
            .value().isEqualTo("War")
            .value().isEqualTo("Under a Blood Red Sky")
            .value().isEqualTo("The Unforgettable Fire")
            .value().isEqualTo("Wide Awake in America")
            .value().isEqualTo("The Joshua Tree")
            .value().isEqualTo("Rattle and Hum")
            .value().isEqualTo("Achtung Baby")
            .value().isEqualTo("Zooropa")
            .value().isEqualTo("Pop")
            .value().isEqualTo("All That You Can't Leave Behind")
            .value().isEqualTo("How to Dismantle an Atomic Bomb")
            .value().isEqualTo("No Line on the Horizon")
            .value().isEqualTo("Songs of Innocence")
        .column()
            .value().isEqualTo(12)
        .column()
            .value().isEqualTo(TimeValue.of(0, 42, 17))
            .value().isEqualTo(TimeValue.of(0, 41, 8))
            .value().isEqualTo(TimeValue.of(0, 42, 7))
            .value().isEqualTo(TimeValue.of(0, 33, 25))
            .value().isEqualTo(TimeValue.of(0, 42, 42))
            .value().isEqualTo(TimeValue.of(0, 20, 30))
            .value().isEqualTo(TimeValue.of(0, 50, 11))
            .value().isEqualTo(TimeValue.of(1, 12, 27))
            .value().isEqualTo(TimeValue.of(0, 55, 23))
            .value().isEqualTo(TimeValue.of(0, 51, 15))
            .value().isEqualTo(TimeValue.of(1, 0, 8))
            .value().isEqualTo(TimeValue.of(0, 49, 23))
            .value().isEqualTo(TimeValue.of(0, 49, 8))
            .value().isEqualTo(TimeValue.of(0, 53, 44))
            .value().isEqualTo(TimeValue.of(0, 48, 11))
        .column()
            .value()
            .value()
            .value()
            .value().isEqualTo(true).isTrue()
            .value()
            .value().isEqualTo(true).isTrue()
        .row()
            .value().isEqualTo(1)
            .value().isEqualTo(DateValue.of(1980, 10, 20))
            .value().isEqualTo("Boy")
            .value().isEqualTo(12)
            .value().isEqualTo(TimeValue.of(0, 42, 17))
            .value()
        .row()
            .value().isEqualTo(2)
            .value().isEqualTo(DateValue.of(1981, 10, 12))
            .value().isEqualTo("October")
            .value().isEqualTo(11)
            .value().isEqualTo(TimeValue.of(0, 41, 8))
            .value()
        .row()
            .value().isEqualTo(3)
            .value().isEqualTo(DateValue.of(1983, 2, 28))
            .value().isEqualTo("War")
            .value().isEqualTo(10)
            .value().isEqualTo(TimeValue.of(0, 42, 7))
            .value()
        .row()
            .value().isEqualTo(4)
            .value().isEqualTo(DateValue.of(1983, 11, 7))
            .value().isEqualTo("Under a Blood Red Sky")
            .value().isEqualTo(8)
            .value().isEqualTo(TimeValue.of(0, 33, 25))
            .value().isEqualTo(true).isTrue()
        .row()
            .value().isEqualTo(5)
            .value().isEqualTo(DateValue.of(1984, 10, 1))
            .value().isEqualTo("The Unforgettable Fire")
            .value().isEqualTo(10)
            .value().isEqualTo(TimeValue.of(0, 42, 42))
            .value()
        .row()
            .value().isEqualTo(6)
            .value().isEqualTo(DateValue.of(1985, 6, 10))
            .value().isEqualTo("Wide Awake in America")
            .value().isEqualTo(4)
            .value().isEqualTo(TimeValue.of(0, 20, 30))
            .value().isEqualTo(true).isTrue()
        .row()
            .value().isEqualTo(7)
            .value().isEqualTo(DateValue.of(1987, 3, 9))
            .value().isEqualTo("The Joshua Tree")
            .value().isEqualTo(11)
            .value().isEqualTo(TimeValue.of(0, 50, 11))
            .value()
        .row()
            .value().isEqualTo(8)
            .value().isEqualTo(DateValue.of(1988, 10, 10))
            .value().isEqualTo("Rattle and Hum")
            .value().isEqualTo(17)
            .value().isEqualTo(TimeValue.of(1, 12, 27))
            .value()
        .row()
            .value().isEqualTo(9)
            .value().isEqualTo(DateValue.of(1991, 11, 18))
            .value().isEqualTo("Achtung Baby")
            .value().isEqualTo(12)
            .value().isEqualTo(TimeValue.of(0, 55, 23))
            .value();
  }

  /**
   * This example shows the assertion on nullity on the values.
   */
  @Test
  public void value_nullity_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
        .column()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
        .column()
        .column()
        .column()
        .column()
        .column()
            .value().isNull()
            .value().isNull()
            .value().isNull()
            .value().isNotNull()
            .value().isNull()
            .value().isNotNull()
            .value().isNull()
            .value().isNull()
            .value().isNull()
            .value().isNull()
            .value().isNull()
            .value().isNull()
            .value().isNull()
            .value().isNull()
            .value().isNull()
        .row()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNull()
        .row()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNull()
        .row()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNotNull()
            .value().isNull();
  }

  /**
   * This example shows the assertion on type of the values.
   */
  @Test
  public void value_type_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
        .column()
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
        .column()
            .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE)
        .column()
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
        .column()
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
        .column()
            .value().isTime().isOfType(ValueType.TIME).isOfAnyTypeIn(ValueType.TIME)
        .column()
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isBoolean().isOfType(ValueType.BOOLEAN).isOfAnyTypeIn(ValueType.BOOLEAN)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isBoolean().isOfType(ValueType.BOOLEAN).isOfAnyTypeIn(ValueType.BOOLEAN)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
        .row()
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isTime().isOfType(ValueType.TIME).isOfAnyTypeIn(ValueType.TIME)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED)
        .row()
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE)
            .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT)
            .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER)
            .value().isTime().isOfType(ValueType.TIME).isOfAnyTypeIn(ValueType.TIME)
            .value().isOfType(ValueType.NOT_IDENTIFIED).isOfAnyTypeIn(ValueType.NOT_IDENTIFIED);
  }

  /**
   * This example shows the assertion on non equality on the values.
   */
  @Test
  public void value_non_equality_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
        .column()
            .value().isNotEqualTo(11)
            .value().isNotEqualTo(12)
            .value().isNotEqualTo(13)
            .value().isNotEqualTo(14)
            .value().isNotEqualTo(15)
            .value().isNotEqualTo(16)
            .value().isNotEqualTo(17)
            .value().isNotEqualTo(18)
            .value().isNotEqualTo(19)
            .value().isNotEqualTo(110)
            .value().isNotEqualTo(111)
            .value().isNotEqualTo(112)
            .value().isNotEqualTo(113)
            .value().isNotEqualTo(114)
            .value().isNotEqualTo(115)
        .column()
            .value().isNotEqualTo(DateValue.of(1180, 10, 20))
            .value().isNotEqualTo(DateValue.of(1181, 10, 12))
            .value().isNotEqualTo(DateValue.of(1183, 2, 28))
            .value().isNotEqualTo(DateValue.of(1183, 11, 7))
            .value().isNotEqualTo(DateValue.of(1184, 10, 1))
            .value().isNotEqualTo(DateValue.of(1185, 6, 10))
            .value().isNotEqualTo(DateValue.of(1187, 3, 9))
            .value().isNotEqualTo(DateValue.of(1188, 10, 10))
            .value().isNotEqualTo(DateValue.of(1191, 11, 18))
            .value().isNotEqualTo(DateValue.of(1193, 7, 6))
            .value().isNotEqualTo(DateValue.of(1197, 3, 3))
            .value().isNotEqualTo(DateValue.of(2100, 10, 30))
            .value().isNotEqualTo(DateValue.of(2104, 11, 22))
            .value().isNotEqualTo(DateValue.of(2109, 3, 2))
            .value().isNotEqualTo(DateValue.of(2114, 9, 9))
        .column()
            .value().isNotEqualTo("oy")
            .value().isNotEqualTo("ctober")
            .value().isNotEqualTo("ar")
            .value().isNotEqualTo("nder a Blood Red Sky")
            .value().isNotEqualTo("he Unforgettable Fire")
            .value().isNotEqualTo("ide Awake in America")
            .value().isNotEqualTo("he Joshua Tree")
            .value().isNotEqualTo("attle and Hum")
            .value().isNotEqualTo("chtung Baby")
            .value().isNotEqualTo("ooropa")
            .value().isNotEqualTo("op")
            .value().isNotEqualTo("ll That You Can't Leave Behind")
            .value().isNotEqualTo("ow to Dismantle an Atomic Bomb")
            .value().isNotEqualTo("o Line on the Horizon")
            .value().isNotEqualTo("ongs of Innocence")
        .column()
            .value().isNotEqualTo(112)
            .value().isNotEqualTo(111)
            .value().isNotEqualTo(110)
            .value().isNotEqualTo(18)
            .value().isNotEqualTo(110)
            .value().isNotEqualTo(14)
            .value().isNotEqualTo(111)
            .value().isNotEqualTo(117)
            .value().isNotEqualTo(112)
            .value().isNotEqualTo(110)
            .value().isNotEqualTo(112)
            .value().isNotEqualTo(111)
            .value().isNotEqualTo(111)
            .value().isNotEqualTo(111)
            .value().isNotEqualTo(111)
        .column()
            .value().isNotEqualTo(TimeValue.of(1, 42, 17))
            .value().isNotEqualTo(TimeValue.of(1, 41, 8))
            .value().isNotEqualTo(TimeValue.of(1, 42, 7))
            .value().isNotEqualTo(TimeValue.of(1, 33, 25))
            .value().isNotEqualTo(TimeValue.of(1, 42, 42))
            .value().isNotEqualTo(TimeValue.of(1, 20, 30))
            .value().isNotEqualTo(TimeValue.of(1, 50, 11))
            .value().isNotEqualTo(TimeValue.of(1, 11, 27))
            .value().isNotEqualTo(TimeValue.of(1, 55, 23))
            .value().isNotEqualTo(TimeValue.of(1, 51, 15))
            .value().isNotEqualTo(TimeValue.of(1, 10, 8))
            .value().isNotEqualTo(TimeValue.of(1, 49, 23))
            .value().isNotEqualTo(TimeValue.of(1, 49, 8))
            .value().isNotEqualTo(TimeValue.of(1, 53, 44))
            .value().isNotEqualTo(TimeValue.of(1, 48, 11))
        .column()
            .value()
            .value()
            .value()
            .value().isNotEqualTo(false)
            .value()
            .value().isNotEqualTo(false)
        .row()
            .value().isNotEqualTo(11)
            .value().isNotEqualTo(DateValue.of(1180, 10, 20))
            .value().isNotEqualTo("Bo1y")
            .value().isNotEqualTo(121)
            .value().isNotEqualTo(TimeValue.of(1, 42, 17))
            .value()
        .row()
            .value().isNotEqualTo(21)
            .value().isNotEqualTo(DateValue.of(1181, 10, 12))
            .value().isNotEqualTo("Oc1tober")
            .value().isNotEqualTo(111)
            .value().isNotEqualTo(TimeValue.of(1, 41, 8))
            .value()
        .row()
            .value().isNotEqualTo(11)
            .value().isNotEqualTo(DateValue.of(1183, 2, 28))
            .value().isNotEqualTo("Wa0r")
            .value().isNotEqualTo(101)
            .value().isNotEqualTo(TimeValue.of(1, 42, 7))
            .value()
        .row()
            .value().isNotEqualTo(41)
            .value().isNotEqualTo(DateValue.of(1913, 11, 7))
            .value().isNotEqualTo("U1nder a Blood Red Sky")
            .value().isNotEqualTo(81)
            .value().isNotEqualTo(TimeValue.of(1, 33, 25))
            .value().isNotEqualTo(false)
        .row()
            .value().isNotEqualTo(15)
            .value().isNotEqualTo(DateValue.of(1914, 10, 1))
            .value().isNotEqualTo("Th1e Unforgettable Fire")
            .value().isNotEqualTo(101)
            .value().isNotEqualTo(TimeValue.of(1, 42, 42))
            .value()
        .row()
            .value().isNotEqualTo(11)
            .value().isNotEqualTo(DateValue.of(1185, 6, 10))
            .value().isNotEqualTo("W1ide Awake in America")
            .value().isNotEqualTo(41)
            .value().isNotEqualTo(TimeValue.of(0, 10, 30))
            .value().isNotEqualTo(false)
        .row()
            .value().isNotEqualTo(1)
            .value().isNotEqualTo(DateValue.of(1981, 3, 9))
            .value().isNotEqualTo("Th1e Joshua Tree")
            .value().isNotEqualTo(111)
            .value().isNotEqualTo(TimeValue.of(1, 50, 11))
            .value()
        .row()
            .value().isNotEqualTo(1)
            .value().isNotEqualTo(DateValue.of(1918, 10, 10))
            .value().isNotEqualTo("R1attle and Hum")
            .value().isNotEqualTo(11)
            .value().isNotEqualTo(TimeValue.of(1, 11, 27))
            .value()
        .row()
            .value().isNotEqualTo(1)
            .value().isNotEqualTo(DateValue.of(1911, 11, 18))
            .value().isNotEqualTo("A1chtung Baby")
            .value().isNotEqualTo(11)
            .value().isNotEqualTo(TimeValue.of(1, 55, 23))
            .value();
  }

  /**
   * This example shows the assertion on comparison on the values.
   */
  @Test
  public void value_comparison_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
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
            .value()
                .isGreaterThanOrEqualTo(5)
                .isLessThanOrEqualTo(5)
                .isGreaterThan(4)
                .isLessThan(6)
            .value()
                .isGreaterThanOrEqualTo(6)
                .isLessThanOrEqualTo(6)
                .isGreaterThan(5)
                .isLessThan(7)
            .value()
                .isGreaterThanOrEqualTo(7)
                .isLessThanOrEqualTo(7)
                .isGreaterThan(6)
                .isLessThan(8)
            .value()
                .isGreaterThanOrEqualTo(8)
                .isLessThanOrEqualTo(8)
                .isGreaterThan(7)
                .isLessThan(9)
            .value()
                .isGreaterThanOrEqualTo(9)
                .isLessThanOrEqualTo(9)
                .isGreaterThan(8)
                .isLessThan(10)
            .value()
                .isGreaterThanOrEqualTo(10)
                .isLessThanOrEqualTo(10)
                .isGreaterThan(9)
                .isLessThan(11)
            .value()
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12)
            .value()
                .isGreaterThanOrEqualTo(12)
                .isLessThanOrEqualTo(12)
                .isGreaterThan(11)
                .isLessThan(13)
            .value()
                .isGreaterThanOrEqualTo(13)
                .isLessThanOrEqualTo(13)
                .isGreaterThan(12)
                .isLessThan(14)
            .value()
                .isGreaterThanOrEqualTo(14)
                .isLessThanOrEqualTo(14)
                .isGreaterThan(13)
                .isLessThan(15)
            .value()
                .isGreaterThanOrEqualTo(15)
                .isLessThanOrEqualTo(15)
                .isGreaterThan(14)
                .isLessThan(16)
        .column("numberofsongs")
            .value()
                .isGreaterThanOrEqualTo(12)
                .isLessThanOrEqualTo(12)
                .isGreaterThan(11)
                .isLessThan(13)
            .value()
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12)
            .value()
                .isGreaterThanOrEqualTo(10)
                .isLessThanOrEqualTo(10)
                .isGreaterThan(9)
                .isLessThan(11)
            .value()
                .isGreaterThanOrEqualTo(8)
                .isLessThanOrEqualTo(8)
                .isGreaterThan(7)
                .isLessThan(9)
            .value()
                .isGreaterThanOrEqualTo(10)
                .isLessThanOrEqualTo(10)
                .isGreaterThan(9)
                .isLessThan(11)
            .value()
                .isGreaterThanOrEqualTo(4)
                .isLessThanOrEqualTo(4)
                .isGreaterThan(3)
                .isLessThan(5)
            .value()
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12)
            .value()
                .isGreaterThanOrEqualTo(17)
                .isLessThanOrEqualTo(17)
                .isGreaterThan(16)
                .isLessThan(18)
            .value()
                .isGreaterThanOrEqualTo(12)
                .isLessThanOrEqualTo(12)
                .isGreaterThan(11)
                .isLessThan(13)
            .value()
                .isGreaterThanOrEqualTo(10)
                .isLessThanOrEqualTo(10)
                .isGreaterThan(9)
                .isLessThan(11)
            .value()
                .isGreaterThanOrEqualTo(12)
                .isLessThanOrEqualTo(12)
                .isGreaterThan(11)
                .isLessThan(13)
            .value()
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12)
            .value()
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12)
            .value()
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12)
            .value()
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12)
        .row()
            .value()
                .isGreaterThanOrEqualTo(1)
                .isLessThanOrEqualTo(1)
                .isGreaterThan(0)
                .isLessThan(2)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(12)
                .isLessThanOrEqualTo(12)
                .isGreaterThan(11)
                .isLessThan(13)
        .row()
            .value()
                .isGreaterThanOrEqualTo(2)
                .isLessThanOrEqualTo(2)
                .isGreaterThan(1)
                .isLessThan(3)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12)
        .row()
            .value()
                .isGreaterThanOrEqualTo(3)
                .isLessThanOrEqualTo(3)
                .isGreaterThan(2)
                .isLessThan(4)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(10)
                .isLessThanOrEqualTo(10)
                .isGreaterThan(9)
                .isLessThan(11)
        .row()
            .value()
                .isGreaterThanOrEqualTo(4)
                .isLessThanOrEqualTo(4)
                .isGreaterThan(3)
                .isLessThan(5)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(8)
                .isLessThanOrEqualTo(8)
                .isGreaterThan(7)
                .isLessThan(9)
        .row()
            .value()
                .isGreaterThanOrEqualTo(5)
                .isLessThanOrEqualTo(5)
                .isGreaterThan(4)
                .isLessThan(6)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(10)
                .isLessThanOrEqualTo(10)
                .isGreaterThan(9)
                .isLessThan(11)
        .row()
            .value()
                .isGreaterThanOrEqualTo(6)
                .isLessThanOrEqualTo(6)
                .isGreaterThan(5)
                .isLessThan(7)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(4)
                .isLessThanOrEqualTo(4)
                .isGreaterThan(3)
                .isLessThan(5)
        .row()
            .value()
                .isGreaterThanOrEqualTo(7)
                .isLessThanOrEqualTo(7)
                .isGreaterThan(6)
                .isLessThan(8)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12)
        .row()
            .value()
                .isGreaterThanOrEqualTo(8)
                .isLessThanOrEqualTo(8)
                .isGreaterThan(7)
                .isLessThan(9)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(17)
                .isLessThanOrEqualTo(17)
                .isGreaterThan(16)
                .isLessThan(18)
        .row()
            .value()
                .isGreaterThanOrEqualTo(9)
                .isLessThanOrEqualTo(9)
                .isGreaterThan(8)
                .isLessThan(10)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(12)
                .isLessThanOrEqualTo(12)
                .isGreaterThan(11)
                .isLessThan(13)
        .row()
            .value()
                .isGreaterThanOrEqualTo(10)
                .isLessThanOrEqualTo(10)
                .isGreaterThan(9)
                .isLessThan(11)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(10)
                .isLessThanOrEqualTo(10)
                .isGreaterThan(9)
                .isLessThan(11)
        .row()
            .value()
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(12)
                .isLessThanOrEqualTo(12)
                .isGreaterThan(11)
                .isLessThan(13)
        .row()
            .value()
                .isGreaterThanOrEqualTo(12)
                .isLessThanOrEqualTo(12)
                .isGreaterThan(11)
                .isLessThan(13)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12)
        .row()
            .value()
                .isGreaterThanOrEqualTo(13)
                .isLessThanOrEqualTo(13)
                .isGreaterThan(12)
                .isLessThan(14)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12)
        .row()
            .value()
                .isGreaterThanOrEqualTo(14)
                .isLessThanOrEqualTo(14)
                .isGreaterThan(13)
                .isLessThan(15)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12)
        .row()
            .value()
                .isGreaterThanOrEqualTo(15)
                .isLessThanOrEqualTo(15)
                .isGreaterThan(14)
                .isLessThan(16)
            .value("numberofsongs")
                .isGreaterThanOrEqualTo(11)
                .isLessThanOrEqualTo(11)
                .isGreaterThan(10)
                .isLessThan(12);
  }

  /**
   * This example shows the assertion on chronology on the values.
   */
  @Test
  public void value_chronology_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
        .column("release")
            .value()
                .isBeforeOrEqualTo(DateValue.of(1980, 10, 20))
                .isAfterOrEqualTo(DateValue.of(1980, 10, 20))
                .isBefore(DateValue.of(1980, 10, 21))
                .isAfter(DateValue.of(1980, 10, 19))
            .value()
                .isBeforeOrEqualTo(DateValue.of(1981, 10, 12))
                .isAfterOrEqualTo(DateValue.of(1981, 10, 12))
                .isBefore(DateValue.of(1981, 10, 13))
                .isAfter(DateValue.of(1981, 10, 11))
            .value()
                .isBeforeOrEqualTo(DateValue.of(1983, 2, 28))
                .isAfterOrEqualTo(DateValue.of(1983, 2, 28))
                .isBefore(DateValue.of(1983, 2, 29))
                .isAfter(DateValue.of(1983, 2, 27))
            .value()
                .isBeforeOrEqualTo(DateValue.of(1983, 11, 7))
                .isAfterOrEqualTo(DateValue.of(1983, 11, 7))
                .isBefore(DateValue.of(1983, 11, 8))
                .isAfter(DateValue.of(1983, 11, 6))
            .value()
                .isBeforeOrEqualTo(DateValue.of(1984, 10, 1))
                .isAfterOrEqualTo(DateValue.of(1984, 10, 1))
                .isBefore(DateValue.of(1984, 10, 2))
                .isAfter(DateValue.of(1984, 9, 30))
            .value()
                .isBeforeOrEqualTo(DateValue.of(1985, 6, 10))
                .isAfterOrEqualTo(DateValue.of(1985, 6, 10))
                .isBefore(DateValue.of(1985, 6, 11))
                .isAfter(DateValue.of(1985, 6, 9))
            .value()
                .isBeforeOrEqualTo(DateValue.of(1987, 3, 9))
                .isAfterOrEqualTo(DateValue.of(1987, 3, 9))
                .isBefore(DateValue.of(1987, 3, 10))
                .isAfter(DateValue.of(1987, 3, 8))
            .value()
                .isBeforeOrEqualTo(DateValue.of(1988, 10, 10))
                .isAfterOrEqualTo(DateValue.of(1988, 10, 10))
                .isBefore(DateValue.of(1988, 10, 11))
                .isAfter(DateValue.of(1988, 10, 9))
            .value()
                .isBeforeOrEqualTo(DateValue.of(1991, 11, 18))
                .isAfterOrEqualTo(DateValue.of(1991, 11, 18))
                .isBefore(DateValue.of(1991, 11, 19))
                .isAfter(DateValue.of(1991, 11, 17))
            .value()
                .isBeforeOrEqualTo(DateValue.of(1993, 7, 6))
                .isAfterOrEqualTo(DateValue.of(1993, 7, 6))
                .isBefore(DateValue.of(1993, 7, 7))
                .isAfter(DateValue.of(1993, 7, 5))
            .value()
                .isBeforeOrEqualTo(DateValue.of(1997, 3, 3))
                .isAfterOrEqualTo(DateValue.of(1997, 3, 3))
                .isBefore(DateValue.of(1997, 3, 4))
                .isAfter(DateValue.of(1997, 3, 2))
            .value()
                .isBeforeOrEqualTo(DateValue.of(2000, 10, 30))
                .isAfterOrEqualTo(DateValue.of(2000, 10, 30))
                .isBefore(DateValue.of(2000, 10, 31))
                .isAfter(DateValue.of(2000, 10, 29))
            .value()
                .isBeforeOrEqualTo(DateValue.of(2004, 11, 22))
                .isAfterOrEqualTo(DateValue.of(2004, 11, 22))
                .isBefore(DateValue.of(2004, 11, 23))
                .isAfter(DateValue.of(2004, 11, 21))
            .value()
                .isBeforeOrEqualTo(DateValue.of(2009, 3, 2))
                .isAfterOrEqualTo(DateValue.of(2009, 3, 2))
                .isBefore(DateValue.of(2009, 3, 3))
                .isAfter(DateValue.of(2009, 3, 1))
            .value()
                .isBeforeOrEqualTo(DateValue.of(2014, 9, 9))
                .isAfterOrEqualTo(DateValue.of(2014, 9, 9))
                .isBefore(DateValue.of(2014, 9, 10))
                .isAfter(DateValue.of(2014, 9, 8))
        .column("duration")
            .value()
                .isBeforeOrEqualTo(TimeValue.of(0, 42, 17))
                .isAfterOrEqualTo(TimeValue.of(0, 42, 17))
                .isBefore(TimeValue.of(0, 42, 18))
                .isAfter(TimeValue.of(0, 42, 16))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(0, 41, 8))
                .isAfterOrEqualTo(TimeValue.of(0, 41, 8))
                .isBefore(TimeValue.of(0, 41, 9))
                .isAfter(TimeValue.of(0, 41, 7))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(0, 42, 7))
                .isAfterOrEqualTo(TimeValue.of(0, 42, 7))
                .isBefore(TimeValue.of(0, 42, 8))
                .isAfter(TimeValue.of(0, 42, 6))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(0, 33, 25))
                .isAfterOrEqualTo(TimeValue.of(0, 33, 25))
                .isBefore(TimeValue.of(0, 33, 26))
                .isAfter(TimeValue.of(0, 33, 24))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(0, 42, 42))
                .isAfterOrEqualTo(TimeValue.of(0, 42, 42))
                .isBefore(TimeValue.of(0, 42, 43))
                .isAfter(TimeValue.of(0, 42, 41))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(0, 20, 30))
                .isAfterOrEqualTo(TimeValue.of(0, 20, 30))
                .isBefore(TimeValue.of(0, 20, 31))
                .isAfter(TimeValue.of(0, 20, 29))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(0, 50, 11))
                .isAfterOrEqualTo(TimeValue.of(0, 50, 11))
                .isBefore(TimeValue.of(0, 50, 12))
                .isAfter(TimeValue.of(0, 50, 10))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(1, 12, 27))
                .isAfterOrEqualTo(TimeValue.of(1, 12, 27))
                .isBefore(TimeValue.of(1, 12, 28))
                .isAfter(TimeValue.of(1, 12, 26))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(0, 55, 23))
                .isAfterOrEqualTo(TimeValue.of(0, 55, 23))
                .isBefore(TimeValue.of(0, 55, 24))
                .isAfter(TimeValue.of(0, 55, 22))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(0, 51, 15))
                .isAfterOrEqualTo(TimeValue.of(0, 51, 15))
                .isBefore(TimeValue.of(0, 51, 16))
                .isAfter(TimeValue.of(0, 51, 14))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(1, 0, 8))
                .isAfterOrEqualTo(TimeValue.of(1, 0, 8))
                .isBefore(TimeValue.of(1, 0, 9))
                .isAfter(TimeValue.of(1, 0, 7))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(0, 49, 23))
                .isAfterOrEqualTo(TimeValue.of(0, 49, 23))
                .isBefore(TimeValue.of(0, 49, 24))
                .isAfter(TimeValue.of(0, 49, 22))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(0, 49, 8))
                .isAfterOrEqualTo(TimeValue.of(0, 49, 8))
                .isBefore(TimeValue.of(0, 49, 9))
                .isAfter(TimeValue.of(0, 49, 7))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(0, 53, 44))
                .isAfterOrEqualTo(TimeValue.of(0, 53, 44))
                .isBefore(TimeValue.of(0, 53, 45))
                .isAfter(TimeValue.of(0, 53, 43))
            .value()
                .isBeforeOrEqualTo(TimeValue.of(0, 48, 11))
                .isAfterOrEqualTo(TimeValue.of(0, 48, 11))
                .isBefore(TimeValue.of(0, 48, 12))
                .isAfter(TimeValue.of(0, 48, 10))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(1980, 10, 20))
                .isAfterOrEqualTo(DateValue.of(1980, 10, 20))
                .isBefore(DateValue.of(1980, 10, 21))
                .isAfter(DateValue.of(1980, 10, 19))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(0, 42, 17))
                .isAfterOrEqualTo(TimeValue.of(0, 42, 17))
                .isBefore(TimeValue.of(0, 42, 18))
                .isAfter(TimeValue.of(0, 42, 16))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(1981, 10, 12))
                .isAfterOrEqualTo(DateValue.of(1981, 10, 12))
                .isBefore(DateValue.of(1981, 10, 13))
                .isAfter(DateValue.of(1981, 10, 11))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(0, 41, 8))
                .isAfterOrEqualTo(TimeValue.of(0, 41, 8))
                .isBefore(TimeValue.of(0, 41, 9))
                .isAfter(TimeValue.of(0, 41, 7))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(1983, 2, 28))
                .isAfterOrEqualTo(DateValue.of(1983, 2, 28))
                .isBefore(DateValue.of(1983, 2, 29))
                .isAfter(DateValue.of(1983, 2, 27))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(0, 42, 7))
                .isAfterOrEqualTo(TimeValue.of(0, 42, 7))
                .isBefore(TimeValue.of(0, 42, 8))
                .isAfter(TimeValue.of(0, 42, 6))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(1983, 11, 7))
                .isAfterOrEqualTo(DateValue.of(1983, 11, 7))
                .isBefore(DateValue.of(1983, 11, 8))
                .isAfter(DateValue.of(1983, 11, 6))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(0, 33, 25))
                .isAfterOrEqualTo(TimeValue.of(0, 33, 25))
                .isBefore(TimeValue.of(0, 33, 26))
                .isAfter(TimeValue.of(0, 33, 24))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(1984, 10, 1))
                .isAfterOrEqualTo(DateValue.of(1984, 10, 1))
                .isBefore(DateValue.of(1984, 10, 2))
                .isAfter(DateValue.of(1984, 9, 30))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(0, 42, 42))
                .isAfterOrEqualTo(TimeValue.of(0, 42, 42))
                .isBefore(TimeValue.of(0, 42, 43))
                .isAfter(TimeValue.of(0, 42, 41))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(1985, 6, 10))
                .isAfterOrEqualTo(DateValue.of(1985, 6, 10))
                .isBefore(DateValue.of(1985, 6, 11))
                .isAfter(DateValue.of(1985, 6, 9))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(0, 20, 30))
                .isAfterOrEqualTo(TimeValue.of(0, 20, 30))
                .isBefore(TimeValue.of(0, 20, 31))
                .isAfter(TimeValue.of(0, 20, 29))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(1987, 3, 9))
                .isAfterOrEqualTo(DateValue.of(1987, 3, 9))
                .isBefore(DateValue.of(1987, 3, 10))
                .isAfter(DateValue.of(1987, 3, 8))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(0, 50, 11))
                .isAfterOrEqualTo(TimeValue.of(0, 50, 11))
                .isBefore(TimeValue.of(0, 50, 12))
                .isAfter(TimeValue.of(0, 50, 10))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(1988, 10, 10))
                .isAfterOrEqualTo(DateValue.of(1988, 10, 10))
                .isBefore(DateValue.of(1988, 10, 11))
                .isAfter(DateValue.of(1988, 10, 9))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(1, 12, 27))
                .isAfterOrEqualTo(TimeValue.of(1, 12, 27))
                .isBefore(TimeValue.of(1, 12, 28))
                .isAfter(TimeValue.of(1, 12, 26))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(1991, 11, 18))
                .isAfterOrEqualTo(DateValue.of(1991, 11, 18))
                .isBefore(DateValue.of(1991, 11, 19))
                .isAfter(DateValue.of(1991, 11, 17))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(0, 55, 23))
                .isAfterOrEqualTo(TimeValue.of(0, 55, 23))
                .isBefore(TimeValue.of(0, 55, 24))
                .isAfter(TimeValue.of(0, 55, 22))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(1993, 7, 6))
                .isAfterOrEqualTo(DateValue.of(1993, 7, 6))
                .isBefore(DateValue.of(1993, 7, 7))
                .isAfter(DateValue.of(1993, 7, 5))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(0, 51, 15))
                .isAfterOrEqualTo(TimeValue.of(0, 51, 15))
                .isBefore(TimeValue.of(0, 51, 16))
                .isAfter(TimeValue.of(0, 51, 14))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(1997, 3, 3))
                .isAfterOrEqualTo(DateValue.of(1997, 3, 3))
                .isBefore(DateValue.of(1997, 3, 4))
                .isAfter(DateValue.of(1997, 3, 2))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(1, 0, 8))
                .isAfterOrEqualTo(TimeValue.of(1, 0, 8))
                .isBefore(TimeValue.of(1, 0, 9))
                .isAfter(TimeValue.of(1, 0, 7))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(2000, 10, 30))
                .isAfterOrEqualTo(DateValue.of(2000, 10, 30))
                .isBefore(DateValue.of(2000, 10, 31))
                .isAfter(DateValue.of(2000, 10, 29))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(0, 49, 23))
                .isAfterOrEqualTo(TimeValue.of(0, 49, 23))
                .isBefore(TimeValue.of(0, 49, 24))
                .isAfter(TimeValue.of(0, 49, 22))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(2004, 11, 22))
                .isAfterOrEqualTo(DateValue.of(2004, 11, 22))
                .isBefore(DateValue.of(2004, 11, 23))
                .isAfter(DateValue.of(2004, 11, 21))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(0, 49, 8))
                .isAfterOrEqualTo(TimeValue.of(0, 49, 8))
                .isBefore(TimeValue.of(0, 49, 9))
                .isAfter(TimeValue.of(0, 49, 7))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(2009, 3, 2))
                .isAfterOrEqualTo(DateValue.of(2009, 3, 2))
                .isBefore(DateValue.of(2009, 3, 3))
                .isAfter(DateValue.of(2009, 3, 1))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(0, 53, 44))
                .isAfterOrEqualTo(TimeValue.of(0, 53, 44))
                .isBefore(TimeValue.of(0, 53, 45))
                .isAfter(TimeValue.of(0, 53, 43))
        .row()
            .value("release")
                .isBeforeOrEqualTo(DateValue.of(2014, 9, 9))
                .isAfterOrEqualTo(DateValue.of(2014, 9, 9))
                .isBefore(DateValue.of(2014, 9, 10))
                .isAfter(DateValue.of(2014, 9, 8))
            .value("duration")
                .isBeforeOrEqualTo(TimeValue.of(0, 48, 11))
                .isAfterOrEqualTo(TimeValue.of(0, 48, 11))
                .isBefore(TimeValue.of(0, 48, 12))
                .isAfter(TimeValue.of(0, 48, 10));
  }
}
