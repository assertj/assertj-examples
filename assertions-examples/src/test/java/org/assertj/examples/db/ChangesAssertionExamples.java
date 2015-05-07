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

import java.sql.SQLException;
import java.text.ParseException;

import org.assertj.db.type.Changes;
import org.assertj.db.type.DataType;
import org.assertj.db.type.DateValue;
import org.assertj.db.type.Request;
import org.assertj.db.type.Table;
import org.assertj.db.type.TimeValue;
import org.junit.Test;

import static org.assertj.db.api.Assertions.assertThat;

/**
 * {@link Changes} assertions example.
 * 
 * @author Régis Pouiller
 */
public class ChangesAssertionExamples extends AbstractAssertionsExamples {

  /**
   * This example shows a simple case of test on changes.
   */
  @Test
  public void basic_changes_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes).hasNumberOfChanges(3)
        .ofCreation().hasNumberOfChanges(1)
        .ofDeletion().hasNumberOfChanges(1)
        .ofModification().hasNumberOfChanges(1)
        .ofAll().hasNumberOfChanges(3)
        .onTable("members").hasNumberOfChanges(2)
        .ofCreationOnTable("albums").hasNumberOfChanges(0);
  }

  /**
   * This example shows a simple case of test on a change.
   */
  @Test
  public void basic_change_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .isCreation()
            .isOnTable("members")
            .hasPksNames("id")
            .hasPksValues(5)
        .change()
            .isModification()
            .isOnTable("members")
            .hasPksNames("id")
            .hasPksValues(1)
        .change()
            .isDeletion()
            .isOnTable("albums")
            .hasPksNames("id")
            .hasPksValues(15)
        .changeOnTableWithPks("members", 1)
            .isModification()
            .hasNumberOfModifiedColumns(1)
            .hasModifiedColumns("surname")
            .hasModifiedColumns(3);
  }

  /**
   * This example shows a simple case of test on a row.
   * @throws ParseException 
   */
  @Test
  public void basic_row_assertion_examples() throws SQLException, ParseException {
    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .rowAtStartPoint()
                .doesNotExist()
            .rowAtEndPoint()
                .exists()
                .hasNumberOfColumns(6)
                .hasValuesEqualTo(5, "McGuiness", "Paul", null, "1951-06-17", null)
        .change()
            .rowAtStartPoint()
                .exists()
                .hasNumberOfColumns(6)
                .hasValuesEqualTo(1, "Hewson", "Paul David", "Bono", DateValue.of(1960, 5, 10), 1.75)
            .rowAtEndPoint()
                .exists()
                .hasNumberOfColumns(6)
                .hasValuesEqualTo(1, "Hewson", "Paul David", "Bono Vox", DateValue.parse("1960-05-10"), 1.75)
        .change()
            .rowAtStartPoint()
                .exists()
                .hasNumberOfColumns(6)
                .hasValuesEqualTo(15, DateValue.of(2014, 9, 9), "Songs of Innocence", "11", TimeValue.of(0, 48, 11), null)
            .rowAtEndPoint()
                .doesNotExist();
  }

  /**
   * This example shows a simple case of test on a column.
   */
  @Test
  public void basic_column_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .column()
                .hasColumnName("id")
                .isNumber(true)
                .hasValuesEqualTo(null, 5)
            .column()
                .hasColumnName("name")
                .isText(true)
                .hasValuesEqualTo(null, "McGuiness")
            .column(4)
                .hasColumnName("birthdate")
                .isDate(true)
                .hasValuesEqualTo(null, "1951-06-17")
        .change()
            .columnAmongTheModifiedOnes()
                .hasColumnName("surname")
                .isModified()
                .isText(true)
                .hasValuesEqualTo("Bono", "Bono Vox")
            .column(1)
                .hasColumnName("name")
                .isNotModified()
                .isText(false)
                .hasValuesEqualTo("Hewson");
  }

  /**
   * This example shows a simple case of test on a value.
   * @throws ParseException 
   */
  @Test
  public void basic_value_assertion_examples() throws SQLException, ParseException {
    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .rowAtEndPoint()
                .value()
                    .hasColumnName("id")
                    .isNumber()
                    .isEqualTo(5)
        .change()
            .columnAmongTheModifiedOnes()
                .valueAtStartPoint()
                    .isText()
                    .isEqualTo("Bono")
                .valueAtEndPoint()
                    .isText()
                    .isEqualTo("Bono Vox");
  }

  /**
   * This example shows a simple case of test on changes about a table.
   */
  @Test
  public void changes_about_table_assertion_examples() throws SQLException {
    Table table = new Table(dataSource, "members");
    Changes changes = new Changes(table);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes).hasNumberOfChanges(2)
        .change().isCreation().hasPksValues(5)
            .rowAtEndPoint().value("name").isEqualTo("McGuiness")
        .change().isModification().hasPksValues(1)
            .columnAmongTheModifiedOnes().valueAtEndPoint().isEqualTo("Bono Vox");
  }

  /**
   * This example shows a simple case of test on changes about a table.
   */
  @Test
  public void changes_about_request_assertion_examples() throws SQLException {
    Request request = new Request(dataSource, "select title from albums");
    Changes changes = new Changes(request);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes).hasNumberOfChanges(1)
        .change().isDeletion()
            .rowAtStartPoint().value().isEqualTo("Songs of Innocence");
  }

  /**
   * This example shows a simple case of test on data type for change on a table.
   */
  @Test
  public void datatype_isontable_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change().isOnDataType(DataType.TABLE).isOnTable().isOnTable("MEMBERS")
        .change().isOnDataType(DataType.TABLE).isOnTable().isOnTable("MEMBERS")
        .change().isOnDataType(DataType.TABLE).isOnTable().isOnTable("ALBUMS");
  }

  /**
   * This example shows a simple case of test on data type for change on a request.
   */
  @Test
  public void datatype_isonrequest_assertion_examples() throws SQLException {
    Request request = new Request(dataSource, "select title from albums");
    Changes changes = new Changes(request);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change().isOnDataType(DataType.REQUEST).isOnRequest();
  }
}
