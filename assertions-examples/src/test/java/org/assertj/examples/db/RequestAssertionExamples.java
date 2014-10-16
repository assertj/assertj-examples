package org.assertj.examples.db;

import static org.assertj.db.api.Assertions.assertThat;

import org.assertj.db.type.DateValue;
import org.assertj.db.type.Request;
import org.assertj.db.type.TimeValue;
import org.junit.Test;

public class RequestAssertionExamples extends AbstractAssertionsExamples {

  /**
   * This example shows a simple case of test.
   */
  @Test
  public void basic_table_assertion_examples() {
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

}
