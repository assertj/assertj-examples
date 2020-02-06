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

import static org.assertj.db.api.Assertions.assertThat;

import java.sql.SQLException;

import org.assertj.db.type.Changes;
import org.assertj.db.type.DateValue;
import org.assertj.db.type.Request;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.Test;

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
        .hasValues(1,
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
        .hasValues(4,
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
        .hasValues(1,
                "Hewson",
                "Paul David",
                "Bono",
                "1960-05-10",
                1.75)                           // Another assertion on the row
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
        .hasValues(4,
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

  /**
   * This example shows simple cases of navigation for changes.
   */
  @Test
  public void basic_navigation_examples_for_changes() throws SQLException {
    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();                // Start point (the moment when the changes start to be taken into account)
    makeChangesInTheData();
    changes.setEndPointNow();                  // End point (the moment when the changes stop to be taken into account)

    assertThat(changes)
        .change()                               // First change
            .isCreation()                       // Assertion on the first change
            .rowAtStartPoint()                  // Row at the start point
                .doesNotExist()                 // Assertion on the row at start point of the first change
            .returnToChange()                   // Go back to the change
            .rowAtEndPoint()                    // Row at the end point
                .hasNumberOfColumns(6)          // Assertion on the row at end point of the first change
                .exists()                       // Another assertion on the same row
                .value(1)                       // Value at index 1 of the row at end point of the first change
                    .isEqualTo("McGuiness")     // Assertion on the value
                .returnToRow()                  // Go back to the row
            .returnToChange()                   // Go back to the change
        .change()                               // Next change
            .rowAtEndPoint()                    // Row at end point of this change
                .hasValues(1, 
                                  "Hewson", 
                                  "Paul David", 
                                  "Bono Vox", 
                                  "1960-05-10", 
                                  1.75)
            .column("surname")                  // Column with name is "surname" of the second change (note that returnToChange() is not mandatory)
                .isModified()                   // Assertion on column
                .hasValues("Bono",
                                  "Bono Vox")
            .column()                           // Next column
                .isNotModified()                // Assertion on the column
                .valueAtEndPoint()              // Value at end point in the column after "surname" ("birth") of the second change
                    .isEqualTo(DateValue.of(
                                  1960, 5, 10))
        .ofDeletion()                           // All the changes of deletion (note that the returnToXxxx() methods are not mandatory)
            .change()                           // First change of these changes of deletion
                .isOnTable("albums")
                .hasPksValues(15)
        .changeOnTableWithPks("members", 5)     // Change with primary key 5 on "members" table
            .isCreation()
                .rowAtEndPoint()                // Row at end point of change with primary key 5 on "members" table 
                    .hasValues(5, 
                                      "McGuiness", 
                                      "Paul", 
                                      null,
                                      "1951-06-17", 
                                      null)
                ;
  }
}
