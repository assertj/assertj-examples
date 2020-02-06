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

import java.sql.SQLException;
import java.text.ParseException;

import org.assertj.db.type.ChangeType;
import org.assertj.db.type.Changes;
import org.assertj.db.type.DataType;
import org.assertj.db.type.DateValue;
import org.assertj.db.type.Request;
import org.assertj.db.type.Table;
import org.assertj.db.type.TimeValue;
import org.assertj.db.type.ValueType;
import org.junit.jupiter.api.Test;

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
                .hasValues(5, "McGuiness", "Paul", null, "1951-06-17", null)
        .change()
            .rowAtStartPoint()
                .exists()
                .hasNumberOfColumns(6)
                .hasValues(1, "Hewson", "Paul David", "Bono", DateValue.of(1960, 5, 10), 1.75)
            .rowAtEndPoint()
                .exists()
                .hasNumberOfColumns(6)
                .hasValues(1, "Hewson", "Paul David", "Bono Vox", DateValue.parse("1960-05-10"), 1.75)
        .change()
            .rowAtStartPoint()
                .exists()
                .hasNumberOfColumns(6)
                .hasValues(15, DateValue.of(2014, 9, 9), "Songs of Innocence", "11", TimeValue.of(0, 48, 11), null)
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
                .hasValues(null, 5)
            .column()
                .hasColumnName("name")
                .isText(true)
                .hasValues(null, "McGuiness")
            .column(4)
                .hasColumnName("birthdate")
                .isDate(true)
                .hasValues(null, "1951-06-17")
        .change()
            .columnAmongTheModifiedOnes()
                .hasColumnName("surname")
                .isModified()
                .isText(true)
                .hasValues("Bono", "Bono Vox")
            .column(1)
                .hasColumnName("name")
                .isNotModified()
                .isText(false)
                .hasValues("Hewson");
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
   * This example shows a simple case of test on change type.
   */
  @Test
  public void changetype_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change().isOfType(ChangeType.CREATION).isCreation()
        .change().isOfType(ChangeType.MODIFICATION).isModification()
        .change().isOfType(ChangeType.DELETION).isDeletion();
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

  /**
   * This example shows a simple case of test on equality on values of columns.
   */
  @Test
  public void column_equality_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .column().hasValues(null, 5)
            .column().hasValues(null, "McGuiness")
            .column().hasValues(null, "Paul")
        .change()
            .column().hasValues(1)
            .column().hasValues("Hewson") 
            .column().hasValues("Paul David") 
            .column().hasValues("Bono", "Bono Vox")
            .column().hasValues(DateValue.of(1960, 5, 10))
            .column().hasValues(1.75)
        .change()
            .column().hasValues(15, null)
            .column().hasValues(DateValue.of(2014, 9, 9), null)
            .column().hasValues("Songs of Innocence", null)
            .column().hasValues(11, null)
            .column().hasValues(TimeValue.of(0, 48, 11), null);
  }

  /**
   * This example shows a simple case of test on number of changes.
   */
  @Test
  public void changes_number_assertion_examples() throws SQLException {
    Changes changesDataSource = new Changes(dataSource).setStartPointNow();
    Changes changesRequest = new Changes(new Request(dataSource, "select title from albums"));
    changesRequest.setStartPointNow();
    makeChangesInTheData();
    changesDataSource.setEndPointNow();
    changesRequest.setEndPointNow();

    assertThat(changesDataSource).hasNumberOfChanges(3);
    assertThat(changesRequest).hasNumberOfChanges(1);
  }

  /**
   * This example shows a simple case of test on number of columns.
   */
  @Test
  public void columns_number_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource).setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change().hasNumberOfColumns(6)
            .rowAtEndPoint().hasNumberOfColumns(6)
        .change().hasNumberOfColumns(6)
            .rowAtStartPoint().hasNumberOfColumns(6)
            .rowAtEndPoint().hasNumberOfColumns(6)
        .change().hasNumberOfColumns(6)
            .rowAtStartPoint().hasNumberOfColumns(6);
  }

  /**
   * This example shows a simple case of test on primary keys.
   */
  @Test
  public void primary_keys_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource).setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change().hasPksNames("id").hasPksValues(5)
        .change().hasPksNames("id").hasPksValues(1)
        .change().hasPksNames("id").hasPksValues(15);
  }

  /**
   * This example shows a simple case of test on modified columns.
   */
  @Test
  public void modified_columns_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource).setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .hasNumberOfModifiedColumns(4)
            .hasModifiedColumns(0, 1, 2, 4)
            .hasModifiedColumns("id", "NAME", "firstname", "birthdate")
        .change()
            .hasNumberOfModifiedColumns(1)
            .hasModifiedColumns(3)
            .hasModifiedColumns("surname")
        .change()
            .hasNumberOfModifiedColumns(5)
            .hasModifiedColumns(0, 1, 2, 3, 4)
            .hasModifiedColumns("id", "release", "TITLE", "numberofsongs", "duration");
  }

  /**
   * This example shows a simple case of test on type of columns.
   */
  @Test
  public void column_type_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .column().isNumber(true).isOfType(ValueType.NUMBER, true).isOfAnyTypeIn(ValueType.NUMBER, ValueType.NOT_IDENTIFIED)
            .column().isText(true).isOfType(ValueType.TEXT, true).isOfAnyTypeIn(ValueType.TEXT, ValueType.NOT_IDENTIFIED)
            .column().isText(true).isOfType(ValueType.TEXT, true).isOfAnyTypeIn(ValueType.TEXT, ValueType.NOT_IDENTIFIED)
        .change()
            .column().isNumber(false).isOfType(ValueType.NUMBER, false).isOfAnyTypeIn(ValueType.NUMBER)
            .column().isText(false).isOfType(ValueType.TEXT, false).isOfAnyTypeIn(ValueType.TEXT)
            .column().isText(false).isOfType(ValueType.TEXT, false).isOfAnyTypeIn(ValueType.TEXT)
            .column().isText(false).isOfType(ValueType.TEXT, false).isOfAnyTypeIn(ValueType.TEXT)
            .column().isDate(false).isOfType(ValueType.DATE, false).isOfAnyTypeIn(ValueType.DATE)
            .column().isNumber(false).isOfType(ValueType.NUMBER, false).isOfAnyTypeIn(ValueType.NUMBER)
        .change()
            .column().isNumber(true).isOfType(ValueType.NUMBER, true).isOfAnyTypeIn(ValueType.NUMBER, ValueType.NOT_IDENTIFIED)
            .column().isDate(true).isOfType(ValueType.DATE, true).isOfAnyTypeIn(ValueType.DATE, ValueType.NOT_IDENTIFIED)
            .column().isText(true).isOfType(ValueType.TEXT, true).isOfAnyTypeIn(ValueType.TEXT, ValueType.NOT_IDENTIFIED)
            .column().isNumber(true).isOfType(ValueType.NUMBER, true).isOfAnyTypeIn(ValueType.NUMBER, ValueType.NOT_IDENTIFIED)
            .column().isTime(true).isOfType(ValueType.TIME, true).isOfAnyTypeIn(ValueType.TIME, ValueType.NOT_IDENTIFIED);
  }

  /**
   * This example shows a simple case of test on modified column.
   */
  @Test
  public void modified_column_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .column().isModified()
            .column().isModified()
            .column().isModified()
            .column().isNotModified()
            .column().isModified()
            .column().isNotModified()
        .change()
            .column().isNotModified()
            .column().isNotModified()
            .column().isNotModified()
            .column().isModified()
            .column().isNotModified()
            .column().isNotModified()
        .change()
            .column().isModified()
            .column().isModified()
            .column().isModified()
            .column().isModified()
            .column().isModified()
            .column().isNotModified();
  }

  /**
   * This example shows a simple case of test on column name.
   */
  @Test
  public void column_name_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .column().hasColumnName("id")
            .column().hasColumnName("name")
            .column().hasColumnName("FIRSTNAME")
            .column().hasColumnName("sUrNamE")
            .column().hasColumnName("birthDATE")
            .column().hasColumnName("Size")
            .rowAtEndPoint()
                .value().hasColumnName("id")
                .value().hasColumnName("name")
                .value().hasColumnName("FIRSTNAME")
                .value().hasColumnName("sUrNamE")
                .value().hasColumnName("birthDATE")
                .value().hasColumnName("Size")
        .change()
            .column().hasColumnName("id")
            .column().hasColumnName("name")
            .column().hasColumnName("FIRSTNAME")
            .column().hasColumnName("sUrNamE")
            .column().hasColumnName("birthDATE")
            .column().hasColumnName("Size")
        .change()
            .column().hasColumnName("id")
            .column().hasColumnName("ReleasE")
            .column().hasColumnName("title")
            .column().hasColumnName("numberOfSongs")
            .column().hasColumnName("duration")
            .column().hasColumnName("Live");
  }

  /**
   * This example shows a simple case of test on row existence.
   */
  @Test
  public void row_existence_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource).setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .rowAtStartPoint().doesNotExist()
            .rowAtEndPoint().exists()
        .change()
            .rowAtStartPoint().exists()
            .rowAtEndPoint().exists()
        .change()
            .rowAtStartPoint().exists()
            .rowAtEndPoint().doesNotExist();
  }

  /**
   * This example shows a simple case of test on equality on the values of a row.
   */
  @Test
  public void row_equality_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource).setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .rowAtEndPoint().hasValues(5, "McGuiness", "Paul", null, DateValue.of(1951, 6, 17), null)
        .change()
            .rowAtStartPoint().hasValues(1, "Hewson", "Paul David", "Bono", DateValue.of(1960, 5, 10), 1.75)
            .rowAtEndPoint().hasValues(1, "Hewson", "Paul David", "Bono Vox", DateValue.of(1960, 5, 10), 1.75)
        .change()
            .rowAtStartPoint().hasValues(15, DateValue.of(2014, 9, 9), "Songs of Innocence", 11, TimeValue.of(0, 48, 11), null);
  }

  /**
   * This example shows a simple case of test on equality on the values.
   */
  @Test
  public void value_equality_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource).setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .rowAtEndPoint()
                .value().isEqualTo(5)
                .value().isEqualTo("McGuiness")
                .value().isEqualTo("Paul")
                .value()
                .value().isEqualTo(DateValue.of(1951, 6, 17))
            .column()
                .valueAtEndPoint().isEqualTo(5)
            .column()
                .valueAtEndPoint().isEqualTo("McGuiness")
            .column()
                .valueAtEndPoint().isEqualTo("Paul")
            .column()
            .column()
                .valueAtEndPoint().isEqualTo(DateValue.of(1951, 6, 17))
        .change()
            .rowAtStartPoint()
                .value().isEqualTo(1)
                .value().isEqualTo("Hewson")
                .value().isEqualTo("Paul David")
                .value().isEqualTo("Bono")
                .value().isEqualTo(DateValue.of(1960, 5, 10))
                .value().isEqualTo(1.75)
            .rowAtEndPoint()
                .value().isEqualTo(1)
                .value().isEqualTo("Hewson")
                .value().isEqualTo("Paul David")
                .value().isEqualTo("Bono Vox")
                .value().isEqualTo(DateValue.of(1960, 5, 10))
                .value().isEqualTo(1.75)
            .column()
                .valueAtStartPoint().isEqualTo(1)
                .valueAtEndPoint().isEqualTo(1)
            .column()
                .valueAtStartPoint().isEqualTo("Hewson")
                .valueAtEndPoint().isEqualTo("Hewson")
            .column()
                .valueAtStartPoint().isEqualTo("Paul David")
                .valueAtEndPoint().isEqualTo("Paul David")
            .column()
                .valueAtStartPoint().isEqualTo("Bono")
                .valueAtEndPoint().isEqualTo("Bono Vox")
            .column()
                .valueAtStartPoint().isEqualTo(DateValue.of(1960, 5, 10))
                .valueAtEndPoint().isEqualTo(DateValue.of(1960, 5, 10))
            .column()
                .valueAtStartPoint().isEqualTo(1.75)
                .valueAtEndPoint().isEqualTo(1.75)
        .change()
            .rowAtStartPoint()
                .value().isEqualTo(15)
                .value().isEqualTo(DateValue.of(2014, 9, 9))
                .value().isEqualTo("Songs of Innocence")
                .value().isEqualTo(11)
                .value().isEqualTo(TimeValue.of(0, 48, 11))
            .column()
                .valueAtStartPoint().isEqualTo(15)
            .column()
                .valueAtStartPoint().isEqualTo(DateValue.of(2014, 9, 9))
            .column()
                .valueAtStartPoint().isEqualTo("Songs of Innocence")
            .column()
                .valueAtStartPoint().isEqualTo(11)
            .column()
                .valueAtStartPoint().isEqualTo(TimeValue.of(0, 48, 11));
  }

  /**
   * This example shows a simple case of test on non equality on the values.
   */
  @Test
  public void value_non_equality_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource).setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .rowAtEndPoint()
                .value().isNotEqualTo(6).isNotZero()
                .value().isNotEqualTo("-McGuiness")
                .value().isNotEqualTo("-Paul")
                .value()
                .value().isNotEqualTo(DateValue.of(1951, 6, 18))
            .column()
                .valueAtEndPoint().isNotEqualTo(6).isNotZero()
            .column()
                .valueAtEndPoint().isNotEqualTo("M-cGuiness")
            .column()
                .valueAtEndPoint().isNotEqualTo("Pa-ul")
            .column()
            .column()
                .valueAtEndPoint().isNotEqualTo(DateValue.of(1951, 7, 17))
        .change()
            .rowAtStartPoint()
                .value().isNotEqualTo(2).isNotZero()
                .value().isNotEqualTo("H-ewson")
                .value().isNotEqualTo("Pa-ul David")
                .value().isNotEqualTo("Bon-o")
                .value().isNotEqualTo(DateValue.of(1860, 5, 10))
                .value().isNotEqualTo(1.756).isNotZero()
            .rowAtEndPoint()
                .value().isNotEqualTo(3).isNotZero()
                .value().isNotEqualTo("-Hewson")
                .value().isNotEqualTo("P-aul David")
                .value().isNotEqualTo("Bo-no Vox")
                .value().isNotEqualTo(DateValue.of(1760, 5, 10))
                .value().isNotEqualTo(1.759).isNotZero()
            .column()
                .valueAtStartPoint().isNotEqualTo(16).isNotZero()
                .valueAtEndPoint().isNotEqualTo(15).isNotZero()
            .column()
                .valueAtStartPoint().isNotEqualTo("-Hewson")
                .valueAtEndPoint().isNotEqualTo("Hew-son")
            .column()
                .valueAtStartPoint().isNotEqualTo("Pa-ul David")
                .valueAtEndPoint().isNotEqualTo("Paul -David")
            .column()
                .valueAtStartPoint().isNotEqualTo("Bono-")
                .valueAtEndPoint().isNotEqualTo("Bono Vo-x")
            .column()
                .valueAtStartPoint().isNotEqualTo(DateValue.of(960, 5, 10))
                .valueAtEndPoint().isNotEqualTo(DateValue.of(190, 5, 10))
            .column()
                .valueAtStartPoint().isNotEqualTo(1.5).isNotZero()
                .valueAtEndPoint().isNotEqualTo(1.7).isNotZero()
        .change()
            .rowAtStartPoint()
                .value().isNotEqualTo(5).isNotZero()
                .value().isNotEqualTo(DateValue.of(2114, 9, 9))
                .value().isNotEqualTo("Songs of Innoc-ence")
                .value().isNotEqualTo(15).isNotZero()
                .value().isNotEqualTo(TimeValue.of(0, 58, 11))
            .column()
                .valueAtStartPoint().isNotEqualTo(1).isNotZero()
            .column()
                .valueAtStartPoint().isNotEqualTo(DateValue.of(2014, 9, 8))
            .column()
                .valueAtStartPoint().isNotEqualTo("Songs of Innocnce")
            .column()
                .valueAtStartPoint().isNotEqualTo(16).isNotZero()
            .column()
                .valueAtStartPoint().isNotEqualTo(TimeValue.of(1, 48, 11));
  }

  /**
   * This example shows a simple case of test on the type of the values.
   */
  @Test
  public void value_type_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource).setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .rowAtEndPoint()
                .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
                .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
                .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
                .value()
                .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE, ValueType.BOOLEAN)
            .column()
                .valueAtEndPoint().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
            .column()
                .valueAtEndPoint().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
            .column()
                .valueAtEndPoint().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
            .column()
            .column()
                .valueAtEndPoint().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE, ValueType.BOOLEAN)
        .change()
            .rowAtStartPoint()
                .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
                .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
                .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
                .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
                .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE, ValueType.BOOLEAN)
                .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
            .rowAtEndPoint()
                .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
                .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
                .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
                .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
                .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE, ValueType.BOOLEAN)
                .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
            .column()
                .valueAtStartPoint().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
                .valueAtEndPoint().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
            .column()
                .valueAtStartPoint().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
                .valueAtEndPoint().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
            .column()
                .valueAtStartPoint().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
                .valueAtEndPoint().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
            .column()
                .valueAtStartPoint().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
                .valueAtEndPoint().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
            .column()
                .valueAtStartPoint().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE, ValueType.BOOLEAN)
                .valueAtEndPoint().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE, ValueType.BOOLEAN)
            .column()
                .valueAtStartPoint().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
                .valueAtEndPoint().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
        .change()
            .rowAtStartPoint()
                .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
                .value().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE, ValueType.BOOLEAN)
                .value().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
                .value().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
                .value().isTime().isOfType(ValueType.TIME).isOfAnyTypeIn(ValueType.TIME, ValueType.BOOLEAN)
            .column()
                .valueAtStartPoint().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
            .column()
                .valueAtStartPoint().isDate().isOfType(ValueType.DATE).isOfAnyTypeIn(ValueType.DATE, ValueType.BOOLEAN)
            .column()
                .valueAtStartPoint().isText().isOfType(ValueType.TEXT).isOfAnyTypeIn(ValueType.TEXT, ValueType.BOOLEAN)
            .column()
                .valueAtStartPoint().isNumber().isOfType(ValueType.NUMBER).isOfAnyTypeIn(ValueType.NUMBER, ValueType.BOOLEAN)
            .column()
                .valueAtStartPoint().isTime().isOfType(ValueType.TIME).isOfAnyTypeIn(ValueType.TIME, ValueType.BOOLEAN);
  }

  /**
   * This example shows a simple case of test on nullity on the values.
   */
  @Test
  public void value_nullity_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource).setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .rowAtEndPoint()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNull()
                .value().isNotNull()
                .value().isNull()
            .column()
                .valueAtEndPoint().isNotNull()
            .column()
                .valueAtEndPoint().isNotNull()
            .column()
                .valueAtEndPoint().isNotNull()
            .column()
                .valueAtEndPoint().isNull()
            .column()
                .valueAtEndPoint().isNotNull()
            .column()
                .valueAtEndPoint().isNull()
        .change()
            .rowAtStartPoint()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNotNull()
            .rowAtEndPoint()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNotNull()
            .column()
                .valueAtStartPoint().isNotNull()
                .valueAtEndPoint().isNotNull()
            .column()
                .valueAtStartPoint().isNotNull()
                .valueAtEndPoint().isNotNull()
            .column()
                .valueAtStartPoint().isNotNull()
                .valueAtEndPoint().isNotNull()
            .column()
                .valueAtStartPoint().isNotNull()
                .valueAtEndPoint().isNotNull()
            .column()
                .valueAtStartPoint().isNotNull()
                .valueAtEndPoint().isNotNull()
            .column()
                .valueAtStartPoint().isNotNull()
                .valueAtEndPoint().isNotNull()
        .change()
            .rowAtStartPoint()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNotNull()
                .value().isNull()
            .column()
                .valueAtStartPoint().isNotNull()
            .column()
                .valueAtStartPoint().isNotNull()
            .column()
                .valueAtStartPoint().isNotNull()
            .column()
                .valueAtStartPoint().isNotNull()
            .column()
                .valueAtStartPoint().isNotNull()
            .column()
                .valueAtStartPoint().isNull();
  }

  /**
   * This example shows a simple case of test on comparison on the values.
   */
  @Test
  public void value_comparison_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource).setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .rowAtEndPoint()
                .value()
                    .isGreaterThan(4)
                    .isGreaterThanOrEqualTo(5)
                    .isLessThan(6)
                    .isLessThanOrEqualTo(5)
            .column()
                .valueAtEndPoint()
                    .isGreaterThan(4)
                    .isGreaterThanOrEqualTo(5)
                    .isLessThan(6)
                    .isLessThanOrEqualTo(5)
        .change()
            .rowAtStartPoint()
                .value()
                    .isGreaterThan(0)
                    .isGreaterThanOrEqualTo(1)
                    .isLessThan(2)
                    .isLessThanOrEqualTo(1)
                .value("size")
                    .isGreaterThan(1.74)
                    .isGreaterThanOrEqualTo(1.75)
                    .isLessThan(1.76)
                    .isLessThanOrEqualTo(1.75)
            .rowAtEndPoint()
                .value()
                    .isGreaterThan(0)
                    .isGreaterThanOrEqualTo(1)
                    .isLessThan(2)
                    .isLessThanOrEqualTo(1)
                .value("size")
                    .isGreaterThan(1.74)
                    .isGreaterThanOrEqualTo(1.75)
                    .isLessThan(1.76)
                    .isLessThanOrEqualTo(1.75)
            .column()
                .valueAtStartPoint()
                    .isGreaterThan(0)
                    .isGreaterThanOrEqualTo(1)
                    .isLessThan(2)
                    .isLessThanOrEqualTo(1)
                .valueAtEndPoint()
                    .isGreaterThan(0)
                    .isGreaterThanOrEqualTo(1)
                    .isLessThan(2)
                    .isLessThanOrEqualTo(1)
            .column("size")
                .valueAtStartPoint()
                    .isGreaterThan(1.74)
                    .isGreaterThanOrEqualTo(1.75)
                    .isLessThan(1.76)
                    .isLessThanOrEqualTo(1.75)
                .valueAtEndPoint()
                    .isGreaterThan(1.74)
                    .isGreaterThanOrEqualTo(1.75)
                    .isLessThan(1.76)
                    .isLessThanOrEqualTo(1.75)
        .change()
            .rowAtStartPoint()
                .value()
                    .isGreaterThan(14)
                    .isGreaterThanOrEqualTo(15)
                    .isLessThan(16)
                    .isLessThanOrEqualTo(15)
                .value("numberofsongs")
                    .isGreaterThan(10)
                    .isGreaterThanOrEqualTo(11)
                    .isLessThan(12)
                    .isLessThanOrEqualTo(11)
            .column()
                .valueAtStartPoint()
                    .isGreaterThan(14)
                    .isGreaterThanOrEqualTo(15)
                    .isLessThan(16)
                    .isLessThanOrEqualTo(15)
            .column("numberofsongs")
                .valueAtStartPoint()
                    .isGreaterThan(10)
                    .isGreaterThanOrEqualTo(11)
                    .isLessThan(12)
                    .isLessThanOrEqualTo(11);
  }

  /**
   * This example shows a simple case of test on chronology on the values.
   */
  @Test
  public void value_chronology_assertion_examples() throws SQLException {
    Changes changes = new Changes(dataSource).setStartPointNow();
    makeChangesInTheData();
    changes.setEndPointNow();

    assertThat(changes)
        .change()
            .rowAtEndPoint()
                .value("birthdate")
                    .isBeforeOrEqualTo(DateValue.of(1951, 6, 17))
                    .isAfterOrEqualTo(DateValue.of(1951, 6, 17))
                    .isBefore(DateValue.of(1951, 6, 18))
                    .isAfter(DateValue.of(1951, 6, 16))
            .column("birthdate")
                .valueAtEndPoint()
                    .isBeforeOrEqualTo(DateValue.of(1951, 6, 17))
                    .isAfterOrEqualTo(DateValue.of(1951, 6, 17))
                    .isBefore(DateValue.of(1951, 6, 18))
                    .isAfter(DateValue.of(1951, 6, 16))
        .change()
            .rowAtStartPoint()
                .value("birthdate")
                    .isBeforeOrEqualTo(DateValue.of(1960, 5, 10))
                    .isAfterOrEqualTo(DateValue.of(1960, 5, 10))
                    .isBefore(DateValue.of(1960, 5, 11))
                    .isAfter(DateValue.of(1960, 5, 9))
            .rowAtEndPoint()
                .value("birthdate")
                    .isBeforeOrEqualTo(DateValue.of(1960, 5, 10))
                    .isAfterOrEqualTo(DateValue.of(1960, 5, 10))
                    .isBefore(DateValue.of(1960, 5, 11))
                    .isAfter(DateValue.of(1960, 5, 9))
            .column("birthdate")
                .valueAtStartPoint()
                    .isBeforeOrEqualTo(DateValue.of(1960, 5, 10))
                    .isAfterOrEqualTo(DateValue.of(1960, 5, 10))
                    .isBefore(DateValue.of(1960, 5, 11))
                    .isAfter(DateValue.of(1960, 5, 9))
                .valueAtEndPoint()
                    .isBeforeOrEqualTo(DateValue.of(1960, 5, 10))
                    .isAfterOrEqualTo(DateValue.of(1960, 5, 10))
                    .isBefore(DateValue.of(1960, 5, 11))
                    .isAfter(DateValue.of(1960, 5, 9))
        .change()
            .rowAtStartPoint()
                .value("release")
                    .isBeforeOrEqualTo(DateValue.of(2014, 9, 9))
                    .isAfterOrEqualTo(DateValue.of(2014, 9, 9))
                    .isBefore(DateValue.of(2014, 9, 10))
                    .isAfter(DateValue.of(2014, 9, 8))
                .value("duration")
                    .isBeforeOrEqualTo(TimeValue.of(0, 48, 11))
                    .isAfterOrEqualTo(TimeValue.of(0, 48, 11))
                    .isBefore(TimeValue.of(0, 48, 12))
                    .isAfter(TimeValue.of(0, 48, 10))
            .column("release")
                .valueAtStartPoint()
                    .isBeforeOrEqualTo(DateValue.of(2014, 9, 9))
                    .isAfterOrEqualTo(DateValue.of(2014, 9, 9))
                    .isBefore(DateValue.of(2014, 9, 10))
                    .isAfter(DateValue.of(2014, 9, 8))
            .column("duration")
                .valueAtStartPoint()
                    .isBeforeOrEqualTo(TimeValue.of(0, 48, 11))
                    .isAfterOrEqualTo(TimeValue.of(0, 48, 11))
                    .isBefore(TimeValue.of(0, 48, 12))
                    .isAfter(TimeValue.of(0, 48, 10));
  }
}
