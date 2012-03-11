package org.fest.assertions.examples.advanced;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.util.Collections.set;

import java.util.Set;

import org.junit.Test;

import org.fest.assertions.core.Condition;
import org.fest.assertions.examples.AbstractAssertionsExamples;

public class UsingConditionExamples extends AbstractAssertionsExamples {

  @Test
  public void is_condition_example() {
    assertThat("Yoda").is(jedi);
    assertThat("Vador").isNot(jedi);
  }

  @Test
  public void has_condition_example() {
    assertThat("Yoda").has(jediPower);
    assertThat("Solo").doesNotHave(jediPower);
  }
  
  @Test
  public void condition_example_on_multiple_elements() {
    // are & areNot
    assertThat(set("Luke", "Yoda")).are(jedi);
    assertThat(set("Leia", "Solo")).areNot(jedi);
    
    // have & doNotHave
    assertThat(set("Luke", "Yoda")).have(jediPower);
    assertThat(set("Leia", "Solo")).doNotHave(jediPower);
  }
  
  private final Condition<String> jedi = new Condition<String>("jedi") {
    private final Set<String> jedis = set("Luke", "Yoda", "Obiwan");

    @Override
    public boolean matches(String value) {
      return jedis.contains(value);
    };
  };

  private final Condition<String> jediPower = new Condition<String>("jedi power") {
    private final Set<String> jedis = set("Luke", "Yoda", "Obiwan");
    
    @Override
    public boolean matches(String value) {
      return jedis.contains(value);
    };
  };
  
}
