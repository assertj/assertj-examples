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
package org.assertj.examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.jodatime.api.Assertions.assertThat;
import static org.joda.time.DateTimeZone.UTC;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.Test;

/**
 * Joda Time assertions examples.
 *
 * @author Joel Costigliola
 */
public class JodaTimeAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void dateTime_assertions_examples() {

    assertThat(new DateTime("2000-01-01")).isEqualTo(new DateTime("2000-01-01")).isNotEqualTo(
                                                                                              new DateTime("2000-01-15"));
    // same assertions but parameters is String based representation of DateTime
    assertThat(new DateTime("2000-01-01")).isEqualTo("2000-01-01").isNotEqualTo("2000-01-15");

    assertThat(new DateTime("2000-01-01")).isBeforeOrEqualTo(new DateTime("2000-01-01"));
    assertThat(new DateTime("2000-01-01")).isAfterOrEqualTo(new DateTime("2000-01-01"));
    assertThat(new DateTime("2000-01-01")).isBefore(new DateTime("2000-01-02")).isAfter(new DateTime("1999-12-31"));
    // same assertions but parameters is String based representation of DateTime
    assertThat(new DateTime("2000-01-01")).isBefore("2000-01-02").isAfter("1999-12-31");

    assertThat(new DateTime("2000-01-01")).isIn(new DateTime("1999-12-31"), new DateTime("2000-01-01"));
    assertThat(new DateTime("2000-01-01")).isNotIn(new DateTime("1999-12-31"), new DateTime("2000-01-02"));
    // same assertions but parameters is String based representation of DateTime
    assertThat(new DateTime("2000-01-01")).isIn("1999-12-31", "2000-01-01").isNotIn("1999-12-31", "2000-01-02");

    DateTime dateTime = new DateTime(1999, 12, 31, 23, 59, 59, 999, DateTimeZone.UTC);
    assertThat(dateTime).hasYear(1999)
                        .hasMonthOfYear(12)
                        .hasDayOfMonth(31)
                        .hasHourOfDay(23)
                        .hasMinuteOfHour(59)
                        .hasSecondOfMinute(59)
                        .hasMillisOfSecond(999);
  }

  @Test
  public void localDateTime_assertions_examples() {

    assertThat(new LocalDateTime("2000-01-01")).isEqualTo(new LocalDateTime("2000-01-01")).isNotEqualTo(
                                                                                                        new LocalDateTime("2000-01-15"));
    // same assertions but parameters is String based representation of LocalDateTime
    assertThat(new LocalDateTime("2000-01-01")).isEqualTo("2000-01-01").isNotEqualTo("2000-01-15");

    assertThat(new LocalDateTime("2000-01-01")).isBefore(new LocalDateTime("2000-01-02")).isAfter(
                                                                                                  new LocalDateTime("1999-12-31"));
    // same assertions but parameters is String based representation of LocalDateTime
    assertThat(new LocalDateTime("2000-01-01")).isBefore("2000-01-02").isAfter("1999-12-31");

    assertThat(new LocalDateTime("2000-01-01")).isIn(new LocalDateTime("1999-12-31"), new LocalDateTime("2000-01-01"));
    assertThat(new LocalDateTime("2000-01-01")).isNotIn(new LocalDateTime("1999-12-31"),
                                                        new LocalDateTime("2000-01-02"));
    // same assertions but parameters is String based representation of LocalDateTime
    assertThat(new LocalDateTime("2000-01-01")).isIn("1999-12-31", "2000-01-01").isNotIn("1999-12-31", "2000-01-02");

    LocalDateTime localDateTime = new LocalDateTime(1999, 12, 31, 23, 59, 59, 999);
    assertThat(localDateTime).hasYear(1999)
                             .hasMonthOfYear(12)
                             .hasDayOfMonth(31)
                             .hasHourOfDay(23)
                             .hasMinuteOfHour(59)
                             .hasSecondOfMinute(59)
                             .hasMillisOfSecond(999);
  }

  @Test
  public void mixing_core_and_joda_time_assertions_examples() {

    // assertThat comes from org.assertj.jodatime.api.JODA_TIME.assertThat static import
    assertThat(new DateTime("2000-01-01")).isEqualTo(new DateTime("2000-01-01"));

    // assertThat comes from org.assertj.core.api.Assertions.assertThat static import
    assertThat("hello world").startsWith("hello");

    // let's see if ShouldBeAfter error
    try {
      assertThat(new DateTime(10)).isAfter(new DateTime(1000));
    } catch (AssertionError e) {
      logAssertionErrorMessage("isAfter", e);
    }
  }

  @Test
  public void date_time_comparison_with_precision_level_examples() {
    // successful assertions ignoring ...
    // ... milliseconds
    DateTime dateTime1 = new DateTime(2000, 1, 1, 0, 0, 1, 0, UTC);
    DateTime dateTime2 = new DateTime(2000, 1, 1, 0, 0, 1, 456, UTC);
    assertThat(dateTime1).isEqualToIgnoringMillis(dateTime2);
    // ... seconds
    dateTime1 = new DateTime(2000, 1, 1, 23, 50, 0, 0, UTC);
    dateTime2 = new DateTime(2000, 1, 1, 23, 50, 10, 456, UTC);
    assertThat(dateTime1).isEqualToIgnoringSeconds(dateTime2);
    // ... minutes
    dateTime1 = new DateTime(2000, 1, 1, 23, 50, 0, 0, UTC);
    dateTime2 = new DateTime(2000, 1, 1, 23, 00, 2, 7, UTC);
    assertThat(dateTime1).isEqualToIgnoringMinutes(dateTime2);
    // ... hours
    dateTime1 = new DateTime(2000, 1, 1, 23, 59, 59, 999, UTC);
    dateTime2 = new DateTime(2000, 1, 1, 00, 00, 00, 000, UTC);
    assertThat(dateTime1).isEqualToIgnoringHours(dateTime2);

    // failing assertions even if time difference is 1ms (compared fields differ)
    try {
      DateTime dateTimeA = new DateTime(2000, 1, 1, 0, 0, 1, 0);
      DateTime dateTimeB = new DateTime(2000, 1, 1, 0, 0, 0, 999);
      assertThat(dateTimeA).isEqualToIgnoringMillis(dateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("DateTimeAssert.isEqualToIgnoringMillis", e);
    }
    try {
      DateTime dateTimeA = new DateTime(2000, 1, 1, 23, 50, 00, 000);
      DateTime dateTimeB = new DateTime(2000, 1, 1, 23, 49, 59, 999);
      assertThat(dateTimeA).isEqualToIgnoringSeconds(dateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("DateTimeAssert.isEqualToIgnoringSeconds", e);
    }
    try {
      DateTime dateTimeA = new DateTime(2000, 1, 1, 01, 00, 00, 000);
      DateTime dateTimeB = new DateTime(2000, 1, 1, 00, 59, 59, 999);
      assertThat(dateTimeA).isEqualToIgnoringMinutes(dateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("DateTimeAssert.isEqualToIgnoringMinutes", e);
    }
    try {
      DateTime dateTimeA = new DateTime(2000, 1, 2, 00, 00, 00, 000);
      DateTime dateTimeB = new DateTime(2000, 1, 1, 23, 59, 59, 999);
      assertThat(dateTimeA).isEqualToIgnoringHours(dateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("DateTimeAssert.isEqualToIgnoringHours", e);
    }
  }

  @Test
  public void local_date_time_comparison_with_precision_level_examples() {
    // successful assertions ignoring ...
    // ... milliseconds
    LocalDateTime localDateTime1 = new LocalDateTime(2000, 1, 1, 0, 0, 1, 0);
    LocalDateTime localDateTime2 = new LocalDateTime(2000, 1, 1, 0, 0, 1, 456);
    assertThat(localDateTime1).isEqualToIgnoringMillis(localDateTime2);
    // ... seconds
    localDateTime1 = new LocalDateTime(2000, 1, 1, 23, 50, 0, 0);
    localDateTime2 = new LocalDateTime(2000, 1, 1, 23, 50, 10, 456);
    assertThat(localDateTime1).isEqualToIgnoringSeconds(localDateTime2);
    // ... minutes
    localDateTime1 = new LocalDateTime(2000, 1, 1, 23, 50, 0, 0);
    localDateTime2 = new LocalDateTime(2000, 1, 1, 23, 00, 2, 7);
    assertThat(localDateTime1).isEqualToIgnoringMinutes(localDateTime2);
    // ... hours
    localDateTime1 = new LocalDateTime(2000, 1, 1, 23, 59, 59, 999);
    localDateTime2 = new LocalDateTime(2000, 1, 1, 00, 00, 00, 000);
    assertThat(localDateTime1).isEqualToIgnoringHours(localDateTime2);

    // failing assertions even if time difference is 1ms (compared fields differ)
    try {
      LocalDateTime localDateTimeA = new LocalDateTime(2000, 1, 1, 0, 0, 1, 0);
      LocalDateTime localDateTimeB = new LocalDateTime(2000, 1, 1, 0, 0, 0, 999);
      assertThat(localDateTimeA).isEqualToIgnoringMillis(localDateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("LocalDateTimeAssert.isEqualToIgnoringMillis", e);
    }
    try {
      LocalDateTime localDateTimeA = new LocalDateTime(2000, 1, 1, 23, 50, 00, 000);
      LocalDateTime localDateTimeB = new LocalDateTime(2000, 1, 1, 23, 49, 59, 999);
      assertThat(localDateTimeA).isEqualToIgnoringSeconds(localDateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("LocalDateTimeAssert.isEqualToIgnoringSeconds", e);
    }
    try {
      LocalDateTime localDateTimeA = new LocalDateTime(2000, 1, 1, 01, 00, 00, 000);
      LocalDateTime localDateTimeB = new LocalDateTime(2000, 1, 1, 00, 59, 59, 999);
      assertThat(localDateTimeA).isEqualToIgnoringMinutes(localDateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("LocalDateTimeAssert.isEqualToIgnoringMinutes", e);
    }
    try {
      LocalDateTime localDateTimeA = new LocalDateTime(2000, 1, 2, 00, 00, 00, 000);
      LocalDateTime localDateTimeB = new LocalDateTime(2000, 1, 1, 23, 59, 59, 999);
      assertThat(localDateTimeA).isEqualToIgnoringHours(localDateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("LocalDateTimeAssert.isEqualToIgnoringHours", e);
    }
  }

  @Test
  public void localDate_assertions_examples() {

    assertThat(new LocalDate("2000-01-01")).isEqualTo(new LocalDate("2000-01-01"))
                                           .isNotEqualTo(new LocalDate("2000-01-15"));
    // same assertions but parameters is String based representation of LocalDate
    assertThat(new LocalDate("2000-01-01")).isEqualTo("2000-01-01")
                                           .isNotEqualTo("2000-01-15");

    assertThat(new LocalDate("2000-01-01")).isBefore(new LocalDate("2000-01-02"))
                                           .isAfter(new LocalDate("1999-12-31"));
    // same assertions but parameters is String based representation of LocalDate
    assertThat(new LocalDate("2000-01-01")).isBefore("2000-01-02")
                                           .isAfter("1999-12-31");

    assertThat(new LocalDate("2000-01-01")).isIn(new LocalDate("1999-12-31"),
                                                 new LocalDate("2000-01-01"));
    assertThat(new LocalDate("2000-01-01")).isNotIn(new LocalDate("1999-12-31"),
                                                    new LocalDate("2000-01-02"));
    // same assertions but parameters is String based representation of LocalDate
    assertThat(new LocalDate("2000-01-01")).isIn("1999-12-31", "2000-01-01")
                                           .isNotIn("1999-12-31", "2000-01-02");
    LocalDate localDate = new LocalDate(2000, 1, 1);
    assertThat(localDate).hasYear(2000)
                         .hasMonthOfYear(1)
                         .hasDayOfMonth(1)
                         .isBefore(new LocalDate(2000, 1, 2))
                         .isBefore("2000-01-02")
                         .isBeforeOrEqualTo(new LocalDate(2000, 1, 1))
                         .isBeforeOrEqualTo("2000-01-01")
                         .isBeforeOrEqualTo(new LocalDate(2000, 1, 2))
                         .isBeforeOrEqualTo("2000-01-02")
                         .isEqualTo(new LocalDate(2000, 1, 1))
                         .isEqualTo("2000-01-01")
                         .isAfterOrEqualTo(new LocalDate(2000, 1, 1))
                         .isAfterOrEqualTo("2000-01-01")
                         .isAfterOrEqualTo(new LocalDate(1999, 12, 31))
                         .isAfterOrEqualTo("1999-12-31")
                         .isAfter(new LocalDate(1999, 12, 31))
                         .isAfter("1999-12-31")
                         .isNotEqualTo("2000-01-15")
                         .isNotEqualTo(new LocalDate(2000, 1, 15))
                         .isIn(new LocalDate(1999, 12, 31), new LocalDate(2000, 1, 1))
                         .isIn("1999-12-31", "2000-01-01")
                         .isNotIn(new LocalDate(1999, 12, 31), new LocalDate(2000, 1, 2))
                         .isNotIn("1999-12-31", "2000-01-02");
  }
}
