package org.fest.assertions.examples.comparator;

import static org.fest.util.Dates.*;

import java.util.Comparator;
import java.util.Date;

public class YearAndMonthDateComparator implements Comparator<Date> {

  public int compare(Date d1, Date d2) {
    int yearDifference = yearOf(d1) - yearOf(d2);
    return yearDifference != 0 ? yearDifference : monthOf(d1) - monthOf(d2);
  }

}
