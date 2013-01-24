package org.fest.assertions.examples.custom;

import static org.fest.assertions.api.Assertions.assertThat;

import org.fest.assertions.api.AbstractAssert;

public class HumanAssert<T extends HumanAssert<T>> extends AbstractAssert<HumanAssert<T>, Human> {
  public HumanAssert(Human actual) {
    super(actual, HumanAssert.class);
  }

  public T hasName(String name) {
    isNotNull();
    assertThat(actual.name).isEqualTo(name);
    return (T) this;
  }

}