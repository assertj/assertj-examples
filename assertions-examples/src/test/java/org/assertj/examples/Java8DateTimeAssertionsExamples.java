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
import static org.assertj.core.api.Assertions.byLessThan;
import static org.assertj.core.api.Assertions.within;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Java8DateTimeAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void zonedDateTime_assertions_examples() {

    ZonedDateTime firstOfJanuary2000InUTC = ZonedDateTime.parse("2000-01-01T00:00:00Z");
    assertThat(firstOfJanuary2000InUTC).isEqualTo(ZonedDateTime.parse("2000-01-01T00:00:00Z"));
    // same assertion but AssertJ takes care of expected String to ZonedDateTime conversion
    assertThat(firstOfJanuary2000InUTC).isEqualTo("2000-01-01T00:00:00Z");
    // example showing that ZonedDateTime are compared in actual's time zone
    assertThat(firstOfJanuary2000InUTC).isEqualTo("2000-01-01T01:00:00+01:00");

    try {
      // 2000-01-01T00:00+01:00 = 1999-12-31T23:00:00Z !
      assertThat(firstOfJanuary2000InUTC).isEqualTo("2000-01-01T00:00+01:00");
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualTo with time zone change adjustment", e);
    }

    assertThat(firstOfJanuary2000InUTC).isAfter("1999-12-31T23:59:59Z");
    // assertion succeeds as ZonedDateTime are compared in actual's time zone
    // 2000-01-01T00:30:00+01:00 = 1999-12-31T23:30:00Z
    assertThat(firstOfJanuary2000InUTC).isAfter("2000-01-01T00:30:00+01:00")
                                       .isAfterOrEqualTo("1999-12-31T23:59:59Z")
                                       .isAfterOrEqualTo("2000-01-01T00:00:00Z");

    assertThat(firstOfJanuary2000InUTC).isBefore("2000-01-01T00:00:01Z")
                                       .isBeforeOrEqualTo("2000-01-01T00:00:01Z")
                                       .isBeforeOrEqualTo("2000-01-01T00:00:00Z");

    assertThat(firstOfJanuary2000InUTC).isBetween("1999-12-31T23:59:59Z", "2000-01-01T00:00:01Z")
                                       .isBetween("2000-01-01T00:00:00Z", "2000-01-01T00:00:01Z")
                                       .isBetween("1999-12-31T23:59:59Z", "2000-01-01T00:00:00Z")
                                       .isBetween("2000-01-01T00:00:00Z", "2000-01-01T00:00:00Z")
                                       .isStrictlyBetween("1999-12-31T23:59:59Z", "2000-01-01T00:00:01Z");

    ZonedDateTime zonedDateTime1 = ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
    ZonedDateTime zonedDateTime2 = ZonedDateTime.of(2000, 1, 1, 23, 59, 59, 999, ZoneOffset.UTC);
    ZonedDateTime zonedDateTime3 = ZonedDateTime.of(2000, 1, 1, 0, 59, 59, 999, ZoneOffset.UTC);
    ZonedDateTime zonedDateTime4 = ZonedDateTime.of(2000, 1, 1, 0, 0, 59, 999, ZoneOffset.UTC);
    ZonedDateTime zonedDateTime5 = ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 999, ZoneOffset.UTC);

    assertThat(zonedDateTime1).isEqualToIgnoringHours(zonedDateTime2);
    assertThat(zonedDateTime1).isEqualToIgnoringMinutes(zonedDateTime3);
    assertThat(zonedDateTime1).isEqualToIgnoringSeconds(zonedDateTime4);
    assertThat(zonedDateTime1).isEqualToIgnoringNanos(zonedDateTime5);

    // example showing that ZonedDateTime are compared in actual's time zone
    ZonedDateTime zonedDateTimeNotInUTC = ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC+1"));
    try {
      assertThat(firstOfJanuary2000InUTC).isEqualToIgnoringHours(zonedDateTimeNotInUTC);
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualToIgnoringHours with time zone change adjustment", e);
    }

    ZonedDateTime zonedDateTime = ZonedDateTime.now();
    assertThat(zonedDateTime).isBetween(zonedDateTime.minusSeconds(1), zonedDateTime.plusSeconds(1))
                             .isBetween(zonedDateTime, zonedDateTime.plusSeconds(1))
                             .isBetween(zonedDateTime.minusSeconds(1), zonedDateTime)
                             .isBetween(zonedDateTime, zonedDateTime)
                             .isStrictlyBetween(zonedDateTime.minusSeconds(1), zonedDateTime.plusSeconds(1));
  }

  @Test
  public void instant_assertions_examples() {
    Instant firstOfJanuary2000 = Instant.parse("2000-01-01T00:00:00.00Z");

    assertThat(firstOfJanuary2000).isEqualTo("2000-01-01T00:00:00.00Z")
                                  .isAfter("1999-12-31T23:59:59.99Z")
                                  .isAfter(firstOfJanuary2000.minusSeconds(1))
                                  .isAfterOrEqualTo("2000-01-01T00:00:00.00Z")
                                  .isBefore(firstOfJanuary2000.plusSeconds(1))
                                  .isBefore("2000-01-01T00:00:00.01Z")
                                  .isBetween(firstOfJanuary2000.minusSeconds(1), firstOfJanuary2000.plusSeconds(1))
                                  .isCloseTo("1999-12-31T23:59:59.99Z", within(10, ChronoUnit.MILLIS))
                                  .isCloseTo("1999-12-31T23:59:59.99Z", byLessThan(11, ChronoUnit.MILLIS))
                                  .isBetween("1999-01-01T00:00:00.00Z", "2001-01-01T00:00:00.00Z")
                                  .isBetween("2000-01-01T00:00:00.00Z", "2001-01-01T00:00:00.00Z")
                                  .isBetween("1999-01-01T00:00:00.00Z", "2000-01-01T00:00:00.00Z")
                                  .isBetween("2000-01-01T00:00:00.00Z", "2000-01-01T00:00:00.00Z")
                                  .isStrictlyBetween("1999-01-01T00:00:00.00Z", "2001-01-01T00:00:00.00Z");
    try {
      assertThat(firstOfJanuary2000).isCloseTo("1999-12-31T23:59:59.99Z", within(1, ChronoUnit.MILLIS));
    } catch (AssertionError e) {
      logAssertionErrorMessage("Instant.isCloseTo", e);
    }

    Instant instant = Instant.now();
    assertThat(instant).isBetween(instant.minusSeconds(1), instant.plusSeconds(1))
                       .isBetween(instant, instant.plusSeconds(1))
                       .isBetween(instant.minusSeconds(1), instant)
                       .isBetween(instant, instant)
                       .isStrictlyBetween(instant.minusSeconds(1), instant.plusSeconds(1));
  }

  @Test
  public void localDateTime_assertions_examples() {
    LocalDateTime firstOfJanuary2000 = LocalDateTime.parse("2000-01-01T00:00:00");

    assertThat(firstOfJanuary2000).isEqualTo("2000-01-01T00:00:00");

    assertThat(firstOfJanuary2000).isAfter("1999-12-31T23:59:59")
                                  .isAfterOrEqualTo("1999-12-31T23:59:59")
                                  .isAfterOrEqualTo("2000-01-01T00:00:00");

    assertThat(firstOfJanuary2000).isBefore("2000-01-01T00:00:01")
                                  .isBeforeOrEqualTo("2000-01-01T00:00:01")
                                  .isBeforeOrEqualTo("2000-01-01T00:00:00");

    assertThat(firstOfJanuary2000).isBetween("1999-12-31T23:59:59", "2000-01-01T00:01:01")
                                  .isBetween("2000-01-01T00:00:00", "2000-01-01T00:00:01")
                                  .isBetween("1999-12-31T23:59:59", "2000-01-01T00:00:00")
                                  .isBetween("2000-01-01T00:00:00", "2000-01-01T00:00:00")
                                  .isStrictlyBetween("1999-12-31T23:59:59", "2000-01-01T00:00:01");

    LocalDateTime localDateTime = LocalDateTime.now();
    assertThat(localDateTime).isBetween(localDateTime.minusSeconds(1), localDateTime.plusSeconds(1))
                             .isBetween(localDateTime, localDateTime.plusSeconds(1))
                             .isBetween(localDateTime.minusSeconds(1), localDateTime)
                             .isBetween(localDateTime, localDateTime)
                             .isStrictlyBetween(localDateTime.minusSeconds(1), localDateTime.plusSeconds(1));

    // successful assertions ignoring ...
    // ... nanoseconds
    LocalDateTime localDateTime1 = LocalDateTime.of(2000, 1, 1, 0, 0, 1, 0);
    LocalDateTime localDateTime2 = LocalDateTime.of(2000, 1, 1, 0, 0, 1, 456);
    assertThat(localDateTime1).isEqualToIgnoringNanos(localDateTime2);
    // ... seconds
    localDateTime1 = LocalDateTime.of(2000, 1, 1, 23, 50, 0, 0);
    localDateTime2 = LocalDateTime.of(2000, 1, 1, 23, 50, 10, 456);
    assertThat(localDateTime1).isEqualToIgnoringSeconds(localDateTime2);
    // ... minutes
    localDateTime1 = LocalDateTime.of(2000, 1, 1, 23, 50, 0, 0);
    localDateTime2 = LocalDateTime.of(2000, 1, 1, 23, 00, 2, 7);
    assertThat(localDateTime1).isEqualToIgnoringMinutes(localDateTime2);
    // ... hours
    localDateTime1 = LocalDateTime.of(2000, 1, 1, 23, 59, 59, 999);
    localDateTime2 = LocalDateTime.of(2000, 1, 1, 00, 00, 00, 000);
    assertThat(localDateTime1).isEqualToIgnoringHours(localDateTime2);

    // failing assertions even if time difference is 1ns (compared fields differ)
    try {
      LocalDateTime localDateTimeA = LocalDateTime.of(2000, 1, 1, 0, 0, 1, 0);
      LocalDateTime localDateTimeB = LocalDateTime.of(2000, 1, 1, 0, 0, 0, 999_999_999);
      assertThat(localDateTimeA).isEqualToIgnoringNanos(localDateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("LocalDateTimeAssert.isEqualToIgnoringNanos", e);
    }
    try {
      LocalDateTime localDateTimeA = LocalDateTime.of(2000, 1, 1, 23, 50, 00, 000);
      LocalDateTime localDateTimeB = LocalDateTime.of(2000, 1, 1, 23, 49, 59, 999_999_999);
      assertThat(localDateTimeA).isEqualToIgnoringSeconds(localDateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("LocalDateTimeAssert.isEqualToIgnoringSeconds", e);
    }
    try {
      LocalDateTime localDateTimeA = LocalDateTime.of(2000, 1, 1, 01, 00, 00, 000);
      LocalDateTime localDateTimeB = LocalDateTime.of(2000, 1, 1, 00, 59, 59, 999_999_999);
      assertThat(localDateTimeA).isEqualToIgnoringMinutes(localDateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("LocalDateTimeAssert.isEqualToIgnoringMinutes", e);
    }
    try {
      LocalDateTime localDateTimeA = LocalDateTime.of(2000, 1, 2, 00, 00, 00, 000);
      LocalDateTime localDateTimeB = LocalDateTime.of(2000, 1, 1, 23, 59, 59, 999_999_999);
      assertThat(localDateTimeA).isEqualToIgnoringHours(localDateTimeB);
    } catch (AssertionError e) {
      logAssertionErrorMessage("LocalDateTimeAssert.isEqualToIgnoringHours", e);
    }
  }

  @Test
  public void localDate_assertions_examples() {
    LocalDate firstOfJanuary2000 = LocalDate.parse("2000-01-01");
    LocalDate secondOfJanuary2000 = LocalDate.parse("2000-01-02");
    LocalDate thirdOfJanuary2000 = LocalDate.parse("2000-01-03");

    List<LocalDate> localDates = Arrays.asList(secondOfJanuary2000, thirdOfJanuary2000);

    assertThat(localDates).allSatisfy(localDate -> assertThat(localDate).isAfter(firstOfJanuary2000));

    assertThat(firstOfJanuary2000).isEqualTo("2000-01-01");

    assertThat(firstOfJanuary2000).isAfter("1999-12-31")
                                  .isAfterOrEqualTo("1999-12-31")
                                  .isAfterOrEqualTo("2000-01-01");

    assertThat(firstOfJanuary2000).isBefore("2000-01-02")
                                  .isBeforeOrEqualTo("2000-01-02")
                                  .isBeforeOrEqualTo("2000-01-01");

    assertThat(firstOfJanuary2000).isBetween("1999-01-01", "2001-01-01")
                                  .isBetween("2000-01-01", "2001-01-01")
                                  .isBetween("1999-01-01", "2000-01-01")
                                  .isBetween("2000-01-01", "2000-01-01")
                                  .isStrictlyBetween("1999-01-01", "2001-01-01");

    assertThat(LocalDate.now()).isToday();

    LocalTime _07_10 = LocalTime.of(7, 10);
    LocalTime _07_42 = LocalTime.of(7, 42);

    assertThat(_07_10).isCloseTo(_07_42, within(32, ChronoUnit.MINUTES));
    assertThat(_07_10).isCloseTo(_07_42, within(1, ChronoUnit.HOURS));

    LocalDate localDate = LocalDate.now();
    assertThat(localDate).isBetween(localDate.minusDays(1), localDate.plusDays(1))
                         .isBetween(localDate, localDate.plusDays(1))
                         .isBetween(localDate.minusDays(1), localDate)
                         .isBetween(localDate, localDate)
                         .isStrictlyBetween(localDate.minusDays(1), localDate.plusDays(1));
  }

  @Test
  public void localTime_assertions_examples() {
    LocalTime oneAm = LocalTime.parse("01:00:00");

    assertThat(oneAm).isEqualTo("01:00:00");

    assertThat(oneAm).isAfter("00:00:00")
                     .isAfterOrEqualTo("00:00:00")
                     .isAfterOrEqualTo("01:00:00");

    assertThat(oneAm).isBefore("02:00:00")
                     .isBeforeOrEqualTo("02:00:00")
                     .isBeforeOrEqualTo("01:00:00");

    assertThat(oneAm).isBetween("00:59:59", "01:00:01")
                     .isBetween("01:00:00", "01:00:01")
                     .isBetween("00:59:59", "01:00:00")
                     .isBetween("01:00:00", "01:00:00")
                     .isStrictlyBetween("00:59:59", "01:00:01");

    assertThat(LocalTime.parse("07:10:30")).isCloseTo("07:12:11", within(5, ChronoUnit.MINUTES));

    LocalTime now = LocalTime.now();
    assertThat(now).isBetween(now.minusSeconds(1), now.plusSeconds(1))
                   .isBetween(now, now.plusSeconds(1))
                   .isBetween(now.minusSeconds(1), now)
                   .isBetween(now, now)
                   .isStrictlyBetween(now.minusSeconds(1), now.plusSeconds(1));
  }

  @Test
  public void offsetTime_assertions_examples() {
    OffsetTime oneAm = OffsetTime.parse("01:00:00+02:00");

    assertThat(oneAm).isEqualTo("01:00:00+02:00");

    assertThat(oneAm).isAfter("00:00:00+02:00")
                     .isAfterOrEqualTo("00:00:00+02:00")
                     .isAfterOrEqualTo("01:00:00+02:00");

    assertThat(oneAm).isBefore("02:00:00+02:00")
                     .isBeforeOrEqualTo("02:00:00+02:00")
                     .isBeforeOrEqualTo("01:00:00+02:00");

    assertThat(oneAm).isBetween("00:59:59+02:00", "01:00:01+02:00")
                     .isBetween("01:00:00+02:00", "01:00:01+02:00")
                     .isBetween("00:59:59+02:00", "01:00:00+02:00")
                     .isBetween("01:00:00+02:00", "01:00:00+02:00")
                     .isStrictlyBetween("00:59:59+02:00", "01:00:01+02:00");

    assertThat(OffsetTime.parse("07:10:30+00:00")).isCloseTo("07:12:11+00:00", within(5, ChronoUnit.MINUTES));

    OffsetTime offsetTime = OffsetTime.now();
    assertThat(offsetTime).isBetween(offsetTime.minusSeconds(1), offsetTime.plusSeconds(1))
                          .isBetween(offsetTime, offsetTime.plusSeconds(1))
                          .isBetween(offsetTime.minusSeconds(1), offsetTime)
                          .isBetween(offsetTime, offsetTime)
                          .isStrictlyBetween(offsetTime.minusSeconds(1), offsetTime.plusSeconds(1));
  }

  @Test
  public void offsetDateTime_assertions_examples() {

    OffsetDateTime firstOfJanuary2000InUTC = OffsetDateTime.parse("2000-01-01T00:00:00Z");
    assertThat(firstOfJanuary2000InUTC).isEqualTo(OffsetDateTime.parse("2000-01-01T00:00:00Z"));
    // same assertion but AssertJ takes care of expected String to OffsetDateTime conversion
    assertThat(firstOfJanuary2000InUTC).isEqualTo("2000-01-01T00:00:00Z");

    try {
      // 2000-01-01T00:00+01:00 = 1999-12-31T23:00:00Z !
      assertThat(firstOfJanuary2000InUTC).isEqualTo("2000-01-01T00:00+01:00");
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualTo with time zone change adjustment", e);
    }

    assertThat(firstOfJanuary2000InUTC).isAfter("1999-12-31T23:59:59Z");
    // assertion succeeds as OffsetDateTime are compared in actual's time zone
    // 2000-01-01T00:30:00+01:00 = 1999-12-31T23:30:00Z
    assertThat(firstOfJanuary2000InUTC).isAfter("2000-01-01T00:30:00+01:00")
                                       .isAfterOrEqualTo("1999-12-31T23:59:59Z")
                                       .isAfterOrEqualTo("2000-01-01T00:00:00Z");

    assertThat(firstOfJanuary2000InUTC).isBefore("2000-01-01T00:00:01Z")
                                       .isBeforeOrEqualTo("2000-01-01T00:00:01Z")
                                       .isBeforeOrEqualTo("2000-01-01T00:00:00Z");

    assertThat(firstOfJanuary2000InUTC).isBetween("1999-12-31T23:59:59Z", "2000-01-01T00:00:01Z")
                                       .isBetween("2000-01-01T00:00:00Z", "2000-01-01T00:00:01Z")
                                       .isBetween("1999-12-31T23:59:59Z", "2000-01-01T00:00:00Z")
                                       .isBetween("2000-01-01T00:00:00Z", "2000-01-01T00:00:00Z")
                                       .isStrictlyBetween("1999-12-31T23:59:59Z", "2000-01-01T00:00:01Z");

    OffsetDateTime offsetDateTime1 = OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
    OffsetDateTime offsetDateTime2 = OffsetDateTime.of(2000, 1, 1, 23, 59, 59, 999, ZoneOffset.UTC);
    OffsetDateTime offsetDateTime3 = OffsetDateTime.of(2000, 1, 1, 0, 59, 59, 999, ZoneOffset.UTC);
    OffsetDateTime offsetDateTime4 = OffsetDateTime.of(2000, 1, 1, 0, 0, 59, 999, ZoneOffset.UTC);
    OffsetDateTime offsetDateTime5 = OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 999, ZoneOffset.UTC);
    assertThat(offsetDateTime1).isEqualToIgnoringHours(offsetDateTime2);
    assertThat(offsetDateTime1).isEqualToIgnoringMinutes(offsetDateTime3);
    assertThat(offsetDateTime1).isEqualToIgnoringSeconds(offsetDateTime4);
    assertThat(offsetDateTime1).isEqualToIgnoringNanos(offsetDateTime5);

    // example showing that OffsetDateTime are compared in actual's time zone
    OffsetDateTime offsetDateTimeNotInUTC = OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.MAX);
    try {
      assertThat(firstOfJanuary2000InUTC).isEqualToIgnoringHours(offsetDateTimeNotInUTC);
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualToIgnoringHours with time zone change adjustment", e);
    }

    OffsetDateTime offsetDateTime = OffsetDateTime.now();
    assertThat(offsetDateTime).isBetween(offsetDateTime.minusSeconds(1), offsetDateTime.plusSeconds(1))
                              .isBetween(offsetDateTime, offsetDateTime.plusSeconds(1))
                              .isBetween(offsetDateTime.minusSeconds(1), offsetDateTime)
                              .isBetween(offsetDateTime, offsetDateTime)
                              .isStrictlyBetween(offsetDateTime.minusSeconds(1), offsetDateTime.plusSeconds(1));
  }

}
