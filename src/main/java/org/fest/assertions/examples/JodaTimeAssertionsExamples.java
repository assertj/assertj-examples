package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.JODA_TIME.assertThat;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.junit.Test;

/**
 * Joda Time assertions example.
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
  }

  @Test
  public void mixing_core_and_joda_time_assertions_examples() {

    // assertThat comes from org.fest.assertions.api.JODA_TIME.assertThat static import
    assertThat(new DateTime("2000-01-01")).isEqualTo(new DateTime("2000-01-01"));

    // assertThat comes from org.fest.assertions.api.Assertions.assertThat static import
    assertThat("hello world").startsWith("hello");

    // let's see if ShouldBeAfter error
    try {
      assertThat(new DateTime(10)).isAfter(new DateTime(1000));
    } catch (AssertionError e) {
      assertThat(e.getMessage()).isEqualTo(
          "expected:<1970-01-01T01:00:00.010+01:00> to be strictly after:<1970-01-01T01:00:01.000+01:00>");
    }

  }

}
