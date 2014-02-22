package org.assertj.swing.junit.util;

import static java.lang.String.format;
import static java.lang.String.valueOf;

import org.junit.internal.AssumptionViolatedException;

public class Assumptions {

  private Object objectUnderAssumption;

  private Assumptions(Object o) {
    objectUnderAssumption = o;
  }

  public static Assumptions assumeThat(Object o) {
    return new Assumptions(o);
  }

  private void throwAssumptionViolation(String msg) {
    throw new AssumptionViolatedException(msg);
  }

  public Assumptions isNull() {
    if (objectUnderAssumption != null) {
      throwAssumptionViolation(format("Assume >%s< to be null!", valueOf(objectUnderAssumption)));
    }
    return this;
  }

  public Assumptions isNotNull() {
    if (objectUnderAssumption == null) {
      throwAssumptionViolation(format("Assume >%s< to be not null!", valueOf(objectUnderAssumption)));
    }
    return this;
  }
}
