package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.util.Dates.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import org.fest.assertions.api.DateAssert;

public class DateAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  // new in FEST 2.0
  public void date_assertions_examples() throws Exception {
    // isEqualTo, isAfter, isBefore assertions
    assertThat(theTwoTowers.getReleaseDate()).isEqualTo(parse("2002-12-18")).isEqualTo("2002-12-18")
        .isAfter(theFellowshipOfTheRing.getReleaseDate()).isBefore(theReturnOfTheKing.getReleaseDate())
        .isNotEqualTo(parse("2002-12-19")).isNotEqualTo("2002-12-19");
    assertThat(theTwoTowers.getReleaseDate()).isEqualTo(parse("2002-12-18")).isAfter("2002-12-17")
        .isBefore("2002-12-19");

    // various usage of isBetween assertion
    assertThat(theTwoTowers.getReleaseDate())
        .isBetween(theFellowshipOfTheRing.getReleaseDate(), theReturnOfTheKing.getReleaseDate())
        .isBetween(parse("2002-12-17"), parse("2002-12-19")).isBetween("2002-12-17", "2002-12-19") // [2002-12-17,
                                                                                                   // 2002-12-19[
        .isNotBetween("2002-12-17", "2002-12-18") // [2002-12-17, 2002-12-18[
        .isBetween("2002-12-17", "2002-12-18", true, true); // [2002-12-17, 2002-12-18]

    assertThat(theReturnOfTheKing.getReleaseDate()).isBeforeYear(2004).isAfterYear(2001);

    // equals assertion with delta
    Date date1 = new Date();
    Date date2 = new Date(date1.getTime() + 100);
    assertThat(date1).isCloseTo(date2, 100); // would not be true for delta of 99ms

    // isIn isNotIn works with String based date parameter
    assertThat(theTwoTowers.getReleaseDate()).isIn("2002-12-17", "2002-12-18", "2002-12-19");
    assertThat(theTwoTowers.getReleaseDate()).isNotIn("2002-12-17", "2002-12-19");

    assertThat(theTwoTowers.getReleaseDate()).isInThePast();
    assertThat(new Date()).isToday();
    assertThat(theSilmarillion.getReleaseDate()).isInTheFuture();
  }

  @Test
  // new in FEST 2.0
  public void is_in_same_date_assertions_examples() throws Exception {

    Date date1 = parseDatetime("2003-04-26T13:01:02");
    Date date2 = parseDatetime("2003-04-26T13:01:03");
    // assertThat(date1).isInSameSecondAs(date2); // would fail since date1 and are date2 are not in the same second
    assertThat(date1).isInSameMinuteAs(date2);
    assertThat(date1).isInSameHourAs(date2);
    assertThat(date1).isInSameDayAs(date2);
    assertThat(date1).isInSameMonthAs(date2);
    assertThat(date1).isInSameYearAs(date2);

    date1 = parse("2003-04-26");
    assertThat(date1).isInSameMonthAs("2003-04-27");
    assertThat(date1).isInSameYearAs("2003-05-13");
  }

  @Test
  // new in FEST 2.0
  public void is_within_date_assertions_examples() throws Exception {

    Date date1 = new Date(parseDatetime("2003-04-26T13:20:35").getTime() + 17);
    assertThat(date1).isWithinMillisecond(17);
    assertThat(date1).isWithinSecond(35);
    assertThat(date1).isWithinMinute(20);
    assertThat(date1).isWithinHourOfDay(13);
    assertThat(date1).isWithinDayOfWeek(Calendar.SATURDAY);
    assertThat(date1).isWithinMonth(4);
    assertThat(date1).isWithinYear(2003);
  }

  // new in FEST 2.0
  @Test
  public void date_assertions_with_date_represented_as_string_with() throws Exception {
    
    // you can use date String representation (default is ISO format yyyy-MM-dd), we take care of parsing String as Date.
    assertThat(theTwoTowers.getReleaseDate()).isEqualTo("2002-12-18");
    
    // but you can use custom date format if you prefer
    DateFormat userCustomDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    assertThat(theTwoTowers.getReleaseDate()).withDateFormat(userCustomDateFormat).isEqualTo("18/12/2002");
    // once set, custom format is used for all following assertions !
    assertThat(theReturnOfTheKing.getReleaseDate()).isEqualTo("17/12/2003");
    
    // you can easily get back to default ISO date format ...
    assertThat(theTwoTowers.getReleaseDate()).withIsoDateFormat().isEqualTo("2002-12-18");
    // ... which is then used for all following assertions
    assertThat(theReturnOfTheKing.getReleaseDate()).isEqualTo("2003-12-17");

    // another way of using custom date format by calling static method useDateFormat(DateFormat) and
    // useDefaultIsoDateFormat()
    // same examples used before
    DateAssert.useDateFormat(userCustomDateFormat);
    assertThat(theTwoTowers.getReleaseDate()).isEqualTo("18/12/2002");
    assertThat(theReturnOfTheKing.getReleaseDate()).isEqualTo("17/12/2003");
    DateAssert.useIsoDateFormat();
    assertThat(theTwoTowers.getReleaseDate()).isEqualTo("2002-12-18");
    assertThat(theReturnOfTheKing.getReleaseDate()).isEqualTo("2003-12-17");

    // choose whatever approach suits you best !
  }

  @Test
  // new in FEST 2.0
  public void date_assertions_with_custom_comparison_examples() throws Exception {
    // yearAndMonthComparator
    assertThat(theTwoTowers.getReleaseDate()).usingComparator(yearAndMonthComparator).isEqualTo("2002-12-01")
        .isEqualTo("2002-12-02").isNotEqualTo("2002-11-18");
    assertThat(theTwoTowers.getReleaseDate()).isEqualTo(parse("2002-12-18")).isAfter("2002-12-17")
        .isBefore("2002-12-19");

    // various usage of isBetween assertion
    assertThat(theTwoTowers.getReleaseDate()).usingComparator(yearAndMonthComparator)
        .isBetween("2002-12-01", "2002-12-10", true, true).isNotBetween("2002-12-17", "2002-12-18") // [2002-12-17, 2002-12-18[
        .isBetween("2002-12-17", "2002-12-18", true, true); // [2002-12-17, 2002-12-18]

    assertThat(theReturnOfTheKing.getReleaseDate()).isBeforeYear(2004).isAfterYear(2001);

    // equals assertion with delta
    Date date1 = new Date();
    Date date2 = new Date(date1.getTime() + 100);
    assertThat(date1).isCloseTo(date2, 100); // would not be true for delta of 99ms

    // isIn isNotIn works with String based date parameter
    assertThat(theTwoTowers.getReleaseDate()).usingComparator(yearAndMonthComparator).isIn("2002-12-17", "2002-12-19")
        .isNotIn("2002-11-17", "2002-10-19");

    assertThat(parse("2010-12-18")).usingComparator(yearAndMonthComparator).isInThePast();
    // build date away from today by one day (if we are at the end of the month we subtract one day, otherwise we add one)
    Date oneDayFromTodayInSameMonth = monthOf(tomorrow()) == monthOf(new Date()) ? tomorrow() : yesterday();
    assertThat(oneDayFromTodayInSameMonth).usingComparator(yearAndMonthComparator).isToday();
  }
  
}
