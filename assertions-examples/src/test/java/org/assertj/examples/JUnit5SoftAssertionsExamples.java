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

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.assertj.examples.data.Mansion;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
public class JUnit5SoftAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void successful_soft_assertions_example(SoftAssertions softly) {
    Mansion mansion = new Mansion();
    mansion.hostPotentiallyMurderousDinnerParty();
    softly.assertThat(mansion.guests()).as("Living Guests").isEqualTo(6);
    softly.assertThat(mansion.kitchen()).as("Kitchen").isEqualTo("clean");
    softly.assertThat(mansion.library()).as("Library").isEqualTo("messy");
    softly.assertThat(mansion.revolverAmmo()).as("Revolver Ammo").isEqualTo(6);
    softly.assertThat(mansion.candlestick()).as("Candlestick").isEqualTo("bent");
    softly.assertThat(mansion.colonel()).as("Colonel").isEqualTo("well kempt");
    softly.assertThat(mansion.professor()).as("Professor").isEqualTo("bloodied and disheveled");

    // chained assertion example
    String name = "Michael Jordan";
    softly.assertThat(name).startsWith("Mich").contains("Jor").endsWith("dan");

    // no need to call softly.assertAll(); (as with SoftAssertions) error gathering is handled by the JUnit rule
  }

  // comment the @Disabled to see the test failing with all the assertion error and not only the first one.
  @Test
  @Disabled
  public void failing_junit_soft_assertions_example(SoftAssertions softly) {
    Mansion mansion = new Mansion();
    mansion.hostPotentiallyMurderousDinnerParty();
    softly.assertThat(mansion.guests()).as("Living Guests").isEqualTo(7);
    softly.assertThat(mansion.kitchen()).as("Kitchen").isEqualTo("clean");
    softly.assertThat(mansion.library()).as("Library").isEqualTo("clean");
    softly.assertThat(mansion.revolverAmmo()).as("Revolver Ammo").isEqualTo(6);
    softly.assertThat(mansion.candlestick()).as("Candlestick").isEqualTo("pristine");
    softly.assertThat(mansion.colonel()).as("Colonel").isEqualTo("well kempt");
    softly.assertThat(mansion.professor()).as("Professor").isEqualTo("well kempt");
    // no need to call softly.assertAll(); (as with SoftAssertions) error gathering is handled by the JUnit rule
  }

  @Test
  public void filteredOn_with_junit_soft_assertions_example(SoftAssertions softly) {
    softly.assertThat(fellowshipOfTheRing).filteredOn("name", "Frodo").isNotEmpty();
  }

}
