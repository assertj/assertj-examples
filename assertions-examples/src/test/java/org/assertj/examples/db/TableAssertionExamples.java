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

  @Test
  public void size_table_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table).hasColumnsSize(6);
    assertThat(table).hasRowsSize(4);

    assertThat(table).column().hasSize(4);
    assertThat(table).row().hasSize(6);
  }

  @Test
  public void date_table_assertion_examples() {
    Table table = new Table(dataSource, "members");

    assertThat(table).row(1)
        .value("birthdate").isEqualTo("1961-08-08")
            .isAfter("1961-08-07")
            .isAfter(DateValue.of(1961, 8, 7))
        .value().isEqualTo("1.77");
  }
}
