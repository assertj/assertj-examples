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

import static org.assertj.core.util.DateUtil.parse;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.examples.data.Race.DWARF;
import static org.assertj.examples.data.Race.ELF;
import static org.assertj.examples.data.Race.HOBBIT;
import static org.assertj.examples.data.Race.MAIA;
import static org.assertj.examples.data.Race.MAN;
import static org.assertj.examples.data.Race.ORC;
import static org.assertj.examples.data.Ring.dwarfRing;
import static org.assertj.examples.data.Ring.manRing;
import static org.assertj.examples.data.Ring.narya;
import static org.assertj.examples.data.Ring.nenya;
import static org.assertj.examples.data.Ring.oneRing;
import static org.assertj.examples.data.Ring.vilya;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.examples.comparator.AbsValueComparator;
import org.assertj.examples.comparator.AgeComparator;
import org.assertj.examples.comparator.CaseInsensitiveCharacterComparator;
import org.assertj.examples.comparator.CaseInsensitiveStringComparator;
import org.assertj.examples.comparator.TolkienCharacterRaceNameComparator;
import org.assertj.examples.comparator.YearAndMonthDateComparator;
import org.assertj.examples.condition.PotentialMvpCondition;
import org.assertj.examples.data.BasketBallPlayer;
import org.assertj.examples.data.Name;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;
import org.assertj.examples.data.movie.Movie;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Init data for assertions examples.
 *
 * @author Joel Costigliola
 */
public abstract class AbstractAssertionsExamples {

  {
    Assertions.setRemoveAssertJRelatedElementsFromStackTrace(false);
  }

  static final String ERROR_MESSAGE_EXAMPLE_FOR_ASSERTION = "{} assertion : {}\n";
  protected static final Logger logger = LoggerFactory.getLogger("[ERROR MESSAGE EXAMPLE]");
  protected static final Logger log = LoggerFactory.getLogger("\n");

  /**
   * log error message if one wants to see it "live".
   */
  protected static void logAssertionErrorMessage(String assertionContext, AssertionError e) {
    logger.info(ERROR_MESSAGE_EXAMPLE_FOR_ASSERTION, assertionContext, e.getMessage());
  }

  // Some of the Lord of the Rings characters :
  protected final TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
  protected final TolkienCharacter sam = new TolkienCharacter("Sam", 38, HOBBIT);
  protected final TolkienCharacter merry = new TolkienCharacter("Merry", 36, HOBBIT);
  protected final TolkienCharacter pippin = new TolkienCharacter("Pippin", 28, HOBBIT);
  protected final TolkienCharacter gandalf = new TolkienCharacter("Gandalf", 2020, MAIA);
  protected final TolkienCharacter gimli = new TolkienCharacter("Gimli", 139, DWARF);
  protected final TolkienCharacter legolas = new TolkienCharacter("Legolas", 1000, ELF);
  protected final TolkienCharacter aragorn = new TolkienCharacter("Aragorn", 87, MAN);
  protected final TolkienCharacter boromir = new TolkienCharacter("Boromir", 37, MAN);
  protected final TolkienCharacter sauron = new TolkienCharacter("Sauron", 50000, MAIA);
  protected final TolkienCharacter galadriel = new TolkienCharacter("Galadriel", 3000, ELF);
  protected final TolkienCharacter elrond = new TolkienCharacter("Elrond", 3000, ELF);
  protected final TolkienCharacter guruk = new TolkienCharacter("Guruk", 20, ORC);
  protected final TolkienCharacter isildur = new TolkienCharacter("Isildur", 100, MAN);
  protected final List<TolkienCharacter> fellowshipOfTheRing = new ArrayList<>();
  protected final List<TolkienCharacter> orcsWithHobbitPrisoners = new ArrayList<>();

  // Rings and their bearer
  protected final List<Ring> ringsOfPower = newArrayList(oneRing, vilya, nenya, narya, dwarfRing, manRing);
  protected final List<Ring> elvesRings = newArrayList(vilya, nenya, narya);
  protected final Map<Ring, TolkienCharacter> ringBearers = new HashMap<>();

  // Lord of the Rings movies
  protected final Movie theFellowshipOfTheRing = new Movie("the fellowship of the Ring", parse("2001-12-19"),
                                                           "178 min");
  protected final Movie theTwoTowers = new Movie("the two Towers", parse("2002-12-18"), "179 min");
  protected final Movie theReturnOfTheKing = new Movie("the Return of the King", parse("2003-12-17"), "201 min");
  protected final Movie theSilmarillion = new Movie("the Silmarillion", parse("2030-01-01"), "unknown");
  protected final List<Movie> trilogy = newArrayList(theFellowshipOfTheRing, theTwoTowers, theReturnOfTheKing);

  // Various comparators
  protected Comparator<TolkienCharacter> ageComparator = new AgeComparator();
  protected Comparator<TolkienCharacter> raceNameComparator = new TolkienCharacterRaceNameComparator();
  protected Comparator<String> caseInsensitiveStringComparator = new CaseInsensitiveStringComparator();
  protected Comparator<Long> absLongComparator = new AbsValueComparator<>();
  protected Comparator<Integer> absValueComparator = new AbsValueComparator<>();
  protected Comparator<Character> caseInsensitiveComparator = new CaseInsensitiveCharacterComparator();
  protected Comparator<Date> yearAndMonthComparator = new YearAndMonthDateComparator();

  protected static BasketBallPlayer rose;
  protected static BasketBallPlayer james;
  protected static BasketBallPlayer durant;
  protected static BasketBallPlayer noah;
  protected static BasketBallPlayer parker;
  protected static BasketBallPlayer wade;
  protected static BasketBallPlayer antetokounmpo;
  protected static List<BasketBallPlayer> basketBallPlayers;
  protected static PotentialMvpCondition potentialMvp;
  protected static Condition<BasketBallPlayer> doubleDoubleStats;

  @BeforeClass
  public static void setUpOnce() {
    rose = new BasketBallPlayer(new Name("Derrick", "Rose"), "Cavs");
    rose.setAssistsPerGame(8);
    rose.setPointsPerGame(25);
    rose.setReboundsPerGame(5);
    parker = new BasketBallPlayer(new Name("Tony", "Parker"), "Spurs");
    parker.setAssistsPerGame(9);
    parker.setPointsPerGame(21);
    parker.setReboundsPerGame(5);
    james = new BasketBallPlayer(new Name("Lebron", "James"), "Cavs");
    james.setAssistsPerGame(6);
    james.setPointsPerGame(27);
    james.setReboundsPerGame(8);
    wade = new BasketBallPlayer(new Name("Dwayne", "Wade"), "Cavs");
    wade.setAssistsPerGame(16);
    wade.setPointsPerGame(55);
    wade.setReboundsPerGame(16);
    durant = new BasketBallPlayer(new Name("Kevin", "Durant"), "Warriors");
    durant.setAssistsPerGame(4);
    durant.setPointsPerGame(30);
    durant.setReboundsPerGame(5);
    noah = new BasketBallPlayer(new Name("Joachim", "Noah"), "Knicks");
    noah.setAssistsPerGame(4);
    noah.setPointsPerGame(10);
    noah.setReboundsPerGame(11);
    antetokounmpo = new BasketBallPlayer(new Name("Giannis", "Antetokounmpo"), "Bucks");
    antetokounmpo.setAssistsPerGame(5);
    antetokounmpo.setPointsPerGame(30);
    antetokounmpo.setReboundsPerGame(10);

    wade.getTeamMates().add(james);
    james.getTeamMates().add(wade);
    james.getTeamMates().add(rose);
    rose.getTeamMates().add(james);
    rose.getTeamMates().add(wade);
    wade.getTeamMates().add(rose);

    basketBallPlayers = newArrayList(rose, james, wade, durant, noah);
    potentialMvp = new PotentialMvpCondition();
    doubleDoubleStats = new Condition<BasketBallPlayer>("double double stats") {
      @Override
      public boolean matches(BasketBallPlayer player) {
        if (player.getPointsPerGame() >= 10 && player.getAssistsPerGame() >= 10) return true;
        if (player.getPointsPerGame() >= 10 && player.getReboundsPerGame() >= 10) return true;
        if (player.getAssistsPerGame() >= 10 && player.getReboundsPerGame() >= 10) return true;
        return false;
      }
    };

    Assertions.setRemoveAssertJRelatedElementsFromStackTrace(false);
  }

  @Before
  public void setup() {
    // let's do some team building :)
    fellowshipOfTheRing.add(frodo);
    fellowshipOfTheRing.add(sam);
    fellowshipOfTheRing.add(merry);
    fellowshipOfTheRing.add(pippin);
    fellowshipOfTheRing.add(gandalf);
    fellowshipOfTheRing.add(legolas);
    fellowshipOfTheRing.add(gimli);
    fellowshipOfTheRing.add(aragorn);
    fellowshipOfTheRing.add(boromir);
    // ring bearers
    ringBearers.put(Ring.nenya, galadriel);
    ringBearers.put(Ring.narya, gandalf);
    ringBearers.put(Ring.vilya, elrond);
    ringBearers.put(Ring.oneRing, frodo);
    orcsWithHobbitPrisoners.add(guruk);
    orcsWithHobbitPrisoners.add(merry);
    orcsWithHobbitPrisoners.add(pippin);
  }

}
