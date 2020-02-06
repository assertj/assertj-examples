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

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class Assertj_INVALID_usage extends AbstractAssertionsExamples {

  @Test
  public void should_report_a_spotbugs_violation_because_no_assertion_is_performed_after_assertThat() {
    assertThat("abc".equals("abc"));
  }


  @Test
  public void should_report_a_spotbugs_violation_because_no_assertion_is_performed_after_extracting() {
    assertThat(fellowshipOfTheRing).extracting("name");
  }

  @Test
  public void should_report_a_spotbugs_violation_because_no_assertion_is_performed_after_asString() {
    Object terryPratchettQuote = "It is often said that before you die your life passes before your eyes. It is in fact true. It's called living.";
    assertThat(terryPratchettQuote).asString();
  }

  @Test
  public void should_report_a_spotbugs_violation_because_no_assertion_is_performed_after_asList() {
    Object abc = Arrays.asList("a", "b", "c");
    assertThat(abc).asList();
  }

  @Test
  public void should_report_a_spotbugs_violation_because_no_assertion_is_performed_after_size() {
    Object abc = Arrays.asList("a", "b", "c");
    assertThat(abc).asList().size();
  }

}
