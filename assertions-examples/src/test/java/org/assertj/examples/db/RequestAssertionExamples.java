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
        .hasValuesEqualTo("Boy", "October", "War", "Under a Blood Red Sky",
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
        .hasValuesEqualTo(2, DateValue.of(1981, 10, 12), "October", 11, TimeValue.of(0, 41, 8), null)
        .hasValuesEqualTo("2", "1981-10-12", "October", "11", "00:41:08", null);
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
        .row().hasNumberOfColumns(2).hasValuesEqualTo("1991-11-18", "Achtung Baby")
        .row().hasValuesEqualTo("2000-10-30", "All That You Can't Leave Behind");
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
  public void colum_type_request_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
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
   * This example shows a simple case of test on column nullity.
   */
  @Test
  public void column_nullity_assertion_examples() {
    Request request = new Request(dataSource, "select * from members where id >= 3");

    assertThat(request)
        .column("surname").hasOnlyNullValues()
        .column().hasOnlyNotNullValues();
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
        .column().isNumber(false).isOfType(ValueType.NUMBER, false).isOfAnyOfTypes(ValueType.NUMBER)
        .column().isDate(false).isOfType(ValueType.DATE, false).isOfAnyOfTypes(ValueType.DATE)
        .column().isText(false).isOfType(ValueType.TEXT, false).isOfAnyOfTypes(ValueType.TEXT)
        .column().isNumber(false).isOfType(ValueType.NUMBER, false).isOfAnyOfTypes(ValueType.NUMBER)
        .column().isTime(false).isOfType(ValueType.TIME, false).isOfAnyOfTypes(ValueType.TIME)
        .column().isBoolean(true).isOfType(ValueType.BOOLEAN, true).isOfAnyOfTypes(ValueType.BOOLEAN, ValueType.NOT_IDENTIFIED);
  }

  /**
   * This example shows the assertion on equality on the values of columns.
   */
  @Test
  public void column_equality_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
        .column().hasValuesEqualTo(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
        .column().hasValuesEqualTo(DateValue.of(1980, 10, 20), DateValue.of(1981, 10, 12), 
            DateValue.of(1983, 2, 28), DateValue.of(1983, 11, 7), 
            DateValue.of(1984, 10, 1), DateValue.of(1985, 6, 10),
            DateValue.of(1987, 3, 9), DateValue.of(1988, 10, 10),
            DateValue.of(1991, 11, 18), DateValue.of(1993, 7, 6),
            DateValue.of(1997, 3, 3), DateValue.of(2000, 10, 30),
            DateValue.of(2004, 11, 22), DateValue.of(2009, 3, 2),
            DateValue.of(2014, 9, 9))
        .column().hasValuesEqualTo("Boy", "October", "War", "Under a Blood Red Sky", "The Unforgettable Fire", 
            "Wide Awake in America", "The Joshua Tree", "Rattle and Hum", "Achtung Baby", "Zooropa", "Pop", 
            "All That You Can't Leave Behind", "How to Dismantle an Atomic Bomb", "No Line on the Horizon", 
            "Songs of Innocence")
        .column().hasValuesEqualTo(12, 11, 10, 8, 10, 4, 11, 17, 12, 10, 12, 11, 11, 11, 11)
        .column().hasValuesEqualTo(TimeValue.of(0, 42, 17),
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
        .column().hasValuesEqualTo(null, null, null, true, null, true, null, null, null, null, null, null, null, null, null);
  }

  /**
   * This example shows the assertion on equality on the values of columns.
   */
  @Test
  public void row_equality_assertion_examples() {
    Request request = new Request(dataSource, "select * from albums");

    assertThat(request)
        .row().hasValuesEqualTo(1, DateValue.of(1980, 10, 20), "Boy", 12, TimeValue.of(0, 42, 17), null)
        .row().hasValuesEqualTo(2, DateValue.of(1981, 10, 12), "October", 11, TimeValue.of(0, 41, 8), null)
        .row().hasValuesEqualTo(3, DateValue.of(1983, 2, 28), "War", 10, TimeValue.of(0, 42, 7), null)
        .row().hasValuesEqualTo(4, DateValue.of(1983, 11, 7), "Under a Blood Red Sky", 8, TimeValue.of(0, 33, 25), true)
        .row().hasValuesEqualTo(5, DateValue.of(1984, 10, 1),"The Unforgettable Fire", 10, TimeValue.of(0, 42, 42), null)
        .row().hasValuesEqualTo(6, DateValue.of(1985, 6, 10),"Wide Awake in America", 4, TimeValue.of(0, 20, 30), true)
        .row().hasValuesEqualTo(7, DateValue.of(1987, 3, 9), "The Joshua Tree", 11, TimeValue.of(0, 50, 11), null)
        .row().hasValuesEqualTo(8, DateValue.of(1988, 10, 10),"Rattle and Hum", 17, TimeValue.of(1, 12, 27), null)
        .row().hasValuesEqualTo(9, DateValue.of(1991, 11, 18), "Achtung Baby", 12, TimeValue.of(0, 55, 23), null)
        .row().hasValuesEqualTo(10, DateValue.of(1993, 7, 6), "Zooropa", 10, TimeValue.of(0, 51, 15), null)
        .row().hasValuesEqualTo(11, DateValue.of(1997, 3, 3), "Pop", 12, TimeValue.of(1, 0, 8), null)
        .row().hasValuesEqualTo(12, DateValue.of(2000, 10, 30), "All That You Can't Leave Behind", 11, TimeValue.of(0, 49, 23), null)
        .row().hasValuesEqualTo(13, DateValue.of(2004, 11, 22), "How to Dismantle an Atomic Bomb", 11, TimeValue.of(0, 49, 8), null)
        .row().hasValuesEqualTo(14, DateValue.of(2009, 3, 2), "No Line on the Horizon", 11, TimeValue.of(0, 53, 44), null)
        .row().hasValuesEqualTo(15, DateValue.of(2014, 9, 9), "Songs of Innocence", 11, TimeValue.of(0, 48, 11), null);
  }
}
