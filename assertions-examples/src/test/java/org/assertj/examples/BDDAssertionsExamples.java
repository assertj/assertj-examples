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

import static java.util.Arrays.asList;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.examples.data.BasketBallPlayer;
import org.assertj.examples.data.Mansion;
import org.assertj.examples.data.service.GameService;
import org.assertj.examples.data.service.TeamManager;
import org.junit.jupiter.api.Test;

/**
 * BDD Style Assertions examples
 * <p/>
 * Given When Then - http://guide.agilealliance.org/guide/gwt.html
 *
 * @author Mariusz Smykula
 */
public class BDDAssertionsExamples extends AbstractAssertionsExamples {

  private TeamManager teamManager = mock(TeamManager.class);

  private GameService sut = new GameService(teamManager); // system under test

  @Test
  public void given_when_then_with_mockito() {

    given(teamManager.getPlayers()).willReturn(asList(rose, james, durant));

    // when
    boolean result = sut.play();

    then(result).isTrue();
  }

  @Test
  public void bdd_host_dinner_party_where_nobody_dies() {

    // given
    Mansion mansion = new Mansion();

    // when
    mansion.hostPotentiallyMurderousDinnerParty();

    then(mansion.guests()).isEqualTo(6);
    then(mansion.kitchen()).isEqualTo("clean");
    then(mansion.library()).isEqualTo("messy");
    then(mansion.revolverAmmo()).isEqualTo(6);
    then(mansion.candlestick()).isEqualTo("bent");
    then(mansion.colonel()).isEqualTo("well kempt");
    then(mansion.professor()).isEqualTo("bloodied and disheveled");
  }

  @Test
  public void bdd_assertions_examples() {
    // given
    List<BasketBallPlayer> bulls = new ArrayList<>();

    // when
    bulls.add(rose);
    bulls.add(noah);

    then(bulls).contains(rose, noah).doesNotContain(james);
  }

  @Test
  public void bdd_soft_assertions_examples() {
    BDDSoftAssertions softly = new BDDSoftAssertions();
    // given
    List<BasketBallPlayer> bulls = new ArrayList<>();

    // when
    bulls.add(rose);
    bulls.add(noah);

    // then
    softly.then(bulls).contains(rose, noah).doesNotContain(james);

    softly.assertAll();
  }

}
