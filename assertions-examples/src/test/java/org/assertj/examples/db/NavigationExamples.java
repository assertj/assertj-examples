package org.assertj.examples.db;

import static org.assertj.db.api.Assertions.assertThat;

import org.assertj.db.type.DateValue;
import org.assertj.db.type.Request;
import org.assertj.db.type.Table;
import org.junit.Test;

/**
 * Examples of navigation for a table, a request and changes
 * 
 * @author Régis Pouiller
 *
 */
public class NavigationExamples extends AbstractAssertionsExamples {

  /**
   * This example shows simple cases of navigation.
   */
  @Test
  public void basic_navigation_examples_for_a_table() {
    Table table = new Table(dataSource, "members");

    assertThat(table)
      .row()                                    // First row
        .value()                                // First value of the first row
          .isEqualTo(1)                         // Assertion on this value
          .isNotEqualTo(2)                      // Another assertion on this value
        .returnToRow()                          // Go back on the first row
        .hasNumberOfColumns(6)                  // Assertion on the row
        .hasValuesEqualTo(1,
                "Hewson",
                "Paul David",
                "Bono",
                "1960-05-10",
                1.75)                 // Another assertion on the row
        .value("surname")                       // Value of the "surname" column of the first row
          .isEqualTo("Bono")                    // Assertion on the value
          .isText()                             // Another assertion
        .returnToRow()                          // Go back on the first row
      .returnToTable()                          // Go back on the table
      .row(2)                                   // Row at index 2
        .value(2)                               // Value at index 2 of this row 
          .isEqualTo("Adam")                    // Assertion on this value
        .value()                                // The next value (note that returnToRow() is not mandatory)
          .isNull()                             // Assertion on this value
      .row()                                    // The next row (note that returnToTable() is not mandatory)
        .hasValuesEqualTo(4,
                          "Mullen", 
                          "Larry", 
                          null, 
                          "1961-10-31", 
                          1.7)                  // Assertion on the row
      .column()                                 // The first column (note that returnToTable() is not mandatory)
        .value(2)                               // Value at index 2 of the first column
          .isEqualTo(3)                         // Assertion on this value
      .column("surname")                        // Column with "surname" as name
        .value().value()                        // The second value of the column
          .isEqualTo("The Edge")                // Assertion on this value
        .returnToColumn()                       // Go back to the column
      .returnToTable()                          // Go back on the table
      .column(4)                                // Column at index 4
        .value(1)                               // Value at index 1 of this column
          .isAfter("1961-08-07")                // Assertion on this value
          .isEqualTo(DateValue.of(1961, 8, 8))  // Another assertion on this value
        ;
  }

  /**
   * This example shows simple cases of navigation for a request.
   */
  @Test
  public void basic_navigation_examples_for_a_request() {
    Request request = new Request(dataSource, "select * from members");

    assertThat(request)
      .row()                                    // First row
        .value()                                // First value of the first row
          .isEqualTo(1)                         // Assertion on this value
          .isNotEqualTo(2)                      // Another assertion on this value
        .returnToRow()                          // Go back on the first row
        .hasNumberOfColumns(6)                  // Assertion on the row
        .hasValuesEqualTo(1,
                "Hewson",
                "Paul David",
                "Bono",
                "1960-05-10",
                1.75)                 // Another assertion on the row
        .value("surname")                       // Value of the "surname" column of the first row
          .isEqualTo("Bono")                    // Assertion on the value
          .isText()                             // Another assertion
        .returnToRow()                          // Go back on the first row
      .returnToRequest()                        // Go back on the table
      .row(2)                                   // Row at index 2
        .value(2)                               // Value at index 2 of this row 
          .isEqualTo("Adam")                    // Assertion on this value
        .value()                                // The next value (note that returnToRow() is not mandatory)
          .isNull()                             // Assertion on this value
      .row()                                    // The next row (note that returnToTable() is not mandatory)
        .hasValuesEqualTo(4,
                          "Mullen", 
                          "Larry", 
                          null, 
                          "1961-10-31", 
                          1.7)                  // Assertion on the row
      .column()                                 // The first column (note that returnToTable() is not mandatory)
        .value(2)                               // Value at index 2 of the first column
          .isEqualTo(3)                         // Assertion on this value
      .column("surname")                        // Column with "surname" as name
        .value().value()                        // The second value of the column
          .isEqualTo("The Edge")                // Assertion on this value
        .returnToColumn()                       // Go back to the column
      .returnToRequest()                        // Go back on the table
      .column(4)                                // Column at index 4
        .value(1)                               // Value at index 1 of this column
          .isAfter("1961-08-07")                // Assertion on this value
          .isEqualTo(DateValue.of(1961, 8, 8))  // Another assertion on this value
        ;
  }
}
