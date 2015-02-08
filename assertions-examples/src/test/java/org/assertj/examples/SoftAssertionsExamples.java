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
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.examples;

import static org.assertj.core.api.Assertions.fail;

import org.assertj.core.api.AutoCloseableBDDSoftAssertions;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.SoftAssertionError;
import org.assertj.core.api.SoftAssertions;
import org.assertj.examples.data.Mansion;
import org.junit.Test;

public class SoftAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void host_dinner_party_where_nobody_dies() {
	Mansion mansion = new Mansion();
	mansion.hostPotentiallyMurderousDinnerParty();
	SoftAssertions softly = new SoftAssertions();
	softly.assertThat(mansion.guests()).as("Living Guests").isEqualTo(7);
	softly.assertThat(mansion.kitchen()).as("Kitchen").isEqualTo("clean");
	softly.assertThat(mansion.library()).as("Library").isEqualTo("clean");
	softly.assertThat(mansion.revolverAmmo()).as("Revolver Ammo").isEqualTo(6);
	softly.assertThat(mansion.candlestick()).as("Candlestick").isEqualTo("pristine");
	softly.assertThat(mansion.colonel()).as("Colonel").isEqualTo("well kempt");
	softly.assertThat(mansion.professor()).as("Professor").isEqualTo("well kempt");
	try {
	  softly.assertAll();
	} catch (SoftAssertionError e) {
	  logAssertionErrorMessage("SoftAssertion errors example", e);
	}
  }

  @Test
  public void chained_soft_assertions_example() {
	String name = "Michael Jordan - Bulls";
	SoftAssertions softly = new SoftAssertions();
	softly.assertThat(name).startsWith("Mike").contains("Lakers").endsWith("Chicago");
	try {
	  softly.assertAll();
	} catch (SoftAssertionError e) {
	  logAssertionErrorMessage("SoftAssertion errors example", e);
	}
  }

  @Test
  public void auto_closed_host_dinner_party_where_nobody_dies() {
	Mansion mansion = new Mansion();
	mansion.hostPotentiallyMurderousDinnerParty();
	try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
	  softly.assertThat(mansion.guests()).as("Living Guests").isEqualTo(7);
	  softly.assertThat(mansion.kitchen()).as("Kitchen").isEqualTo("clean");
	  softly.assertThat(mansion.library()).as("Library").isEqualTo("clean");
	  softly.assertThat(mansion.revolverAmmo()).as("Revolver Ammo").isEqualTo(6);
	  softly.assertThat(mansion.candlestick()).as("Candlestick").isEqualTo("pristine");
	  softly.assertThat(mansion.colonel()).as("Colonel").isEqualTo("well kempt");
	  softly.assertThat(mansion.professor()).as("Professor").isEqualTo("well kempt");
	} catch (SoftAssertionError e) {
	  // expected
	  return;
	}
	fail("SoftAssertionError expected.");
  }
  
  // same test but for BDD style soft assertions

  @Test
  public void host_dinner_party_where_nobody_dies_bdd_style() {
	Mansion mansion = new Mansion();
	mansion.hostPotentiallyMurderousDinnerParty();
	BDDSoftAssertions softly = new BDDSoftAssertions();
	softly.then(mansion.guests()).as("Living Guests").isEqualTo(7);
	softly.then(mansion.kitchen()).as("Kitchen").isEqualTo("clean");
	softly.then(mansion.library()).as("Library").isEqualTo("clean");
	softly.then(mansion.revolverAmmo()).as("Revolver Ammo").isEqualTo(6);
	softly.then(mansion.candlestick()).as("Candlestick").isEqualTo("pristine");
	softly.then(mansion.colonel()).as("Colonel").isEqualTo("well kempt");
	softly.then(mansion.professor()).as("Professor").isEqualTo("well kempt");
	try {
	  softly.assertAll();
	} catch (SoftAssertionError e) {
	  logAssertionErrorMessage("BDD SoftAssertion errors example", e);
	}
  }
  
  @Test
  public void chained_bdd_soft_assertions_example() {
	String name = "Michael Jordan - Bulls";
	BDDSoftAssertions softly = new BDDSoftAssertions();
	softly.then(name).startsWith("Mike").contains("Lakers").endsWith("Chicago");
	try {
	  softly.assertAll();
	} catch (SoftAssertionError e) {
	  logAssertionErrorMessage("BDD SoftAssertion errors example", e);
	}
  }
  
  @Test
  public void auto_closed_host_dinner_party_where_nobody_dies_bdd_style() {
	Mansion mansion = new Mansion();
	mansion.hostPotentiallyMurderousDinnerParty();
	try (AutoCloseableBDDSoftAssertions softly = new AutoCloseableBDDSoftAssertions()) {
	  softly.then(mansion.guests()).as("Living Guests").isEqualTo(7);
	  softly.then(mansion.kitchen()).as("Kitchen").isEqualTo("clean");
	  softly.then(mansion.library()).as("Library").isEqualTo("clean");
	  softly.then(mansion.revolverAmmo()).as("Revolver Ammo").isEqualTo(6);
	  softly.then(mansion.candlestick()).as("Candlestick").isEqualTo("pristine");
	  softly.then(mansion.colonel()).as("Colonel").isEqualTo("well kempt");
	  softly.then(mansion.professor()).as("Professor").isEqualTo("well kempt");
	} catch (SoftAssertionError e) {
	  // expected
	  return;
	}
	fail("BDD SoftAssertionError expected.");
  }
  
}
