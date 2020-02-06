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

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * Init data for assertions examples.
 *
 * @author Régis Pouiller
 */
public class AbstractAssertionsExamples {

  protected static JdbcConnectionPool dataSource;

  private static final String MEMBERS_CREATE_REQUEST = "create table members(id number primary key, name varchar not null, firstname varchar not null, surname varchar, birthdate date, size decimal);";
  private static final String[] MEMBERS_INSERT_REQUESTS = {
    "insert into members values (1, 'Hewson', 'Paul David', 'Bono', PARSEDATETIME('10/05/1960', 'dd/MM/yyyy'), 1.75);",
    "insert into members values (2, 'Evans', 'David Howell', 'The Edge', PARSEDATETIME('08/08/1961', 'dd/MM/yyyy'), 1.77);",
    "insert into members values (3, 'Clayton', 'Adam', null, PARSEDATETIME('13/03/1960', 'dd/MM/yyyy'), 1.78);",
    "insert into members values (4, 'Mullen', 'Larry', null, PARSEDATETIME('31/10/1961', 'dd/MM/yyyy'), 1.7);",
  };
  private static final String ALBUMS_CREATE_REQUEST = "create table albums(id number primary key, release date not null, title varchar not null, numberofsongs int, duration time, live boolean);";
  private static final String[] ALBUMS_INSERT_REQUESTS = {
    "insert into albums values (1, PARSEDATETIME('20/10/1980', 'dd/MM/yyyy'), 'Boy', 12, PARSEDATETIME('42:17', 'mm:ss'), null);",
    "insert into albums values (2, PARSEDATETIME('12/10/1981', 'dd/MM/yyyy'), 'October', 11, PARSEDATETIME('41:08', 'mm:ss'), null);",
    "insert into albums values (3, PARSEDATETIME('28/02/1983', 'dd/MM/yyyy'), 'War', 10, PARSEDATETIME('42:07', 'mm:ss'), null);",
    "insert into albums values (4, PARSEDATETIME('07/11/1983', 'dd/MM/yyyy'), 'Under a Blood Red Sky', 8, PARSEDATETIME('33:25', 'mm:ss'), true);",
    "insert into albums values (5, PARSEDATETIME('01/10/1984', 'dd/MM/yyyy'), 'The Unforgettable Fire', 10, PARSEDATETIME('42:42', 'mm:ss'), null);",
    "insert into albums values (6, PARSEDATETIME('10/06/1985', 'dd/MM/yyyy'), 'Wide Awake in America', 4, PARSEDATETIME('20:30', 'mm:ss'), true);",
    "insert into albums values (7, PARSEDATETIME('09/03/1987', 'dd/MM/yyyy'), 'The Joshua Tree', 11, PARSEDATETIME('50:11', 'mm:ss'), null);",
    "insert into albums values (8, PARSEDATETIME('10/10/1988', 'dd/MM/yyyy'), 'Rattle and Hum', 17, PARSEDATETIME('72:27', 'mm:ss'), null);",
    "insert into albums values (9, PARSEDATETIME('18/11/1991', 'dd/MM/yyyy'), 'Achtung Baby', 12, PARSEDATETIME('55:23', 'mm:ss'), null);",
    "insert into albums values (10, PARSEDATETIME('06/07/1993', 'dd/MM/yyyy'), 'Zooropa', 10, PARSEDATETIME('51:15', 'mm:ss'), null);",
    "insert into albums values (11, PARSEDATETIME('03/03/1997', 'dd/MM/yyyy'), 'Pop', 12, PARSEDATETIME('60:08', 'mm:ss'), null);",
    "insert into albums values (12, PARSEDATETIME('30/10/2000', 'dd/MM/yyyy'), 'All That You Can''t Leave Behind', 11, PARSEDATETIME('49:23', 'mm:ss'), null);",
    "insert into albums values (13, PARSEDATETIME('22/11/2004', 'dd/MM/yyyy'), 'How to Dismantle an Atomic Bomb', 11, PARSEDATETIME('49:08', 'mm:ss'), null);",
    "insert into albums values (14, PARSEDATETIME('02/03/2009', 'dd/MM/yyyy'), 'No Line on the Horizon', 11, PARSEDATETIME('53:44', 'mm:ss'), null);",
    "insert into albums values (15, PARSEDATETIME('09/09/2014', 'dd/MM/yyyy'), 'Songs of Innocence', 11, PARSEDATETIME('48:11', 'mm:ss'), null);",
  };

  @BeforeEach
  public void setUp() throws SQLException {
    if (dataSource == null) {
      dataSource = JdbcConnectionPool.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");
    }
    else {
      Connection conn = dataSource.getConnection();
      conn.createStatement().executeUpdate("drop table albums;");
      conn.createStatement().executeUpdate("drop table members;");
      conn.close();
    }
    Connection conn = dataSource.getConnection();
    conn.createStatement().executeUpdate(MEMBERS_CREATE_REQUEST);
    for (String request : MEMBERS_INSERT_REQUESTS) {
      conn.createStatement().executeUpdate(request);
    }
    conn.createStatement().executeUpdate(ALBUMS_CREATE_REQUEST);
    for (String request : ALBUMS_INSERT_REQUESTS) {
      conn.createStatement().executeUpdate(request);
    }
    conn.close();
  }

  protected void makeChangesInTheData() throws SQLException {
    Connection conn = dataSource.getConnection();
    conn.createStatement().executeUpdate("insert into members values (5, 'McGuiness', 'Paul', null, PARSEDATETIME('17/06/1951', 'dd/MM/yyyy'), null);");
    conn.createStatement().executeUpdate("update members set surname = 'Bono Vox' where id = 1;");
    conn.createStatement().executeUpdate("delete from albums where id = 15;");
    conn.close();
  }
}
