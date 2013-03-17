package org.assertj.examples.custom;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeAssert extends HumanAssert<EmployeeAssert> {

  private final Employee actual;

  public EmployeeAssert(Employee actual) {
    super(actual);
    this.actual = actual;
  }

  public EmployeeAssert hasJobTitle(String jobTitle) {
    isNotNull();
    assertThat(actual.jobTitle).isEqualTo(jobTitle);
    return this;
  }

}
