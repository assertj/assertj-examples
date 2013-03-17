package org.assertj.examples.custom;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;


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