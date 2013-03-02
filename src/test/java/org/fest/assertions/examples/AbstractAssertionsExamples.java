package org.fest.assertions.examples;

import static org.fest.assertions.examples.data.Race.dwarf;
import static org.fest.assertions.examples.data.Race.elf;
import static org.fest.assertions.examples.data.Race.hobbit;
import static org.fest.assertions.examples.data.Race.maia;
import static org.fest.assertions.examples.data.Race.man;
import static org.fest.assertions.examples.data.Race.orc;
import static org.fest.assertions.examples.data.Ring.*;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Dates.parse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.fest.assertions.core.Condition;
import org.fest.assertions.examples.comparator.AbsValueComparator;
import org.fest.assertions.examples.comparator.AgeComparator;
import org.fest.assertions.examples.comparator.CaseInsensitiveCharacterComparator;
import org.fest.assertions.examples.comparator.CaseInsensitiveStringComparator;
import org.fest.assertions.examples.comparator.TolkienCharacterRaceNameComparator;
import org.fest.assertions.examples.comparator.YearAndMonthDateComparator;
import org.fest.assertions.examples.condition.PotentialMvpCondition;
import org.fest.assertions.examples.data.Name;
import org.fest.assertions.examples.data.Player;
import org.fest.assertions.examples.data.Race;
import org.fest.assertions.examples.data.Ring;
import org.fest.assertions.examples.data.TolkienCharacter;
import org.fest.assertions.examples.data.movie.Movie;

/**
 * 
 * Init data for assertions examples.
 * 
 * @author Joel Costigliola
 */
public abstract class AbstractAssertionsExamples {

  static final String ERROR_MESSAGE_EXAMPLE_FOR_ASSERTION = "'{}' assertion : {}\n";
  protected static final Logger logger = LoggerFactory.getLogger("[ERROR MESSAGE EXAMPLE]");
  /**
   * log error message if one wants to see it "live".
   */
  protected static void logAssertionErrorMessage(String assertionContext, AssertionError e) {
    logger.info(ERROR_MESSAGE_EXAMPLE_FOR_ASSERTION, assertionContext, e.getMessage());
  }


  // Some of the Lord of the Rings races :
  protected static final Race HOBBIT = hobbit;
  protected static final Race MAIA = maia;
  protected static final Race MAN = man;
  protected static final Race ELF = elf;
  protected static final Race DWARF = dwarf;
  protected static final Race ORC = orc;


  // Some of the Lord of the Rings characters :
  protected final TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
  protected final TolkienCharacter sam = new TolkienCharacter("Sam", 38, HOBBIT);
  protected final TolkienCharacter merry = new TolkienCharacter("Merry", 36, HOBBIT);
  protected final TolkienCharacter pippin = new TolkienCharacter("Pippin", 28, HOBBIT);
  protected final TolkienCharacter gandalf = new TolkienCharacter("Gandalf", 2020, MAIA);
  protected final TolkienCharacter gimli = new TolkienCharacter("Gimli", 139, DWARF);
  protected final TolkienCharacter legolas = new TolkienCharacter("Legolas", 1000, ELF);
  protected final TolkienCharacter aragorn = new TolkienCharacter("Aragorn", 87, MAN);
  protected final TolkienCharacter boromir = new TolkienCharacter("Boromir", 87, MAN);
  protected final TolkienCharacter sauron = new TolkienCharacter("Sauron", 50000, MAIA);
  protected final TolkienCharacter galadriel = new TolkienCharacter("Legolas", 3000, ELF);
  protected final TolkienCharacter elrond = new TolkienCharacter("Legolas", 3000, ELF);
  protected final TolkienCharacter guruk = new TolkienCharacter("Guruk", 20, orc);
  protected final List<TolkienCharacter> fellowshipOfTheRing = new ArrayList<TolkienCharacter>();
  protected final List<TolkienCharacter> orcsWithHobbitPrisoners = new ArrayList<TolkienCharacter>();

  // Rings and their bearer
  protected final List<Ring> ringsOfPower = newArrayList(oneRing, vilya, nenya, narya, dwarfRing, manRing);
  protected final Map<Ring, TolkienCharacter> ringBearers = new HashMap<Ring, TolkienCharacter>();

  // Lord of the Rings movies
  protected final Movie theFellowshipOfTheRing = new Movie("the fellowship of the Ring", parse("2001-12-19"));
  protected final Movie theTwoTowers = new Movie("the two Towers", parse("2002-12-18"));
  protected final Movie theReturnOfTheKing = new Movie("the Return of the King", parse("2003-12-17"));
  protected final Movie theSilmarillion = new Movie("the Silmarillion", parse("2030-01-01"));

  // Various comparators
  protected Comparator<TolkienCharacter> ageComparator = new AgeComparator();
  protected Comparator<TolkienCharacter> raceNameComparator = new TolkienCharacterRaceNameComparator();
  protected Comparator<String> caseInsensitiveStringComparator = new CaseInsensitiveStringComparator();
  protected Comparator<Integer> absValueComparator = new AbsValueComparator<Integer>();
  protected Comparator<Character> caseInsensitiveComparator = new CaseInsensitiveCharacterComparator();
  protected Comparator<Date> yearAndMonthComparator = new YearAndMonthDateComparator();

  protected static Player rose;
  protected static Player james;
  protected static Player durant;
  protected static Player noah;
  protected static List<Player> players;
  protected static PotentialMvpCondition potentialMvp;
  protected static Condition<Player> doubleDoubleStats;

  @BeforeClass
  public static void setUpOnce() {
    rose = new Player(new Name("Derrick", "Rose"), "Chicago Bulls");
    rose.setAssistsPerGame(8);
    rose.setPointsPerGame(25);
    rose.setReboundsPerGame(5);
    james = new Player(new Name("Lebron", "James"), "Miami Heat");
    james.setAssistsPerGame(6);
    james.setPointsPerGame(27);
    james.setReboundsPerGame(8);
    durant = new Player(new Name("Kevin", "Durant"), "OKC");
    durant.setAssistsPerGame(4);
    durant.setPointsPerGame(30);
    durant.setReboundsPerGame(5);
    noah = new Player(new Name("Joachim", "Noah"), "Chicago Bulls");
    noah.setAssistsPerGame(4);
    noah.setPointsPerGame(10);
    noah.setReboundsPerGame(11);
    players = newArrayList(rose, james, durant, noah);
    potentialMvp = new PotentialMvpCondition();
    doubleDoubleStats = new Condition<Player>("double double stats") {
      @Override
      public boolean matches(Player player) {
        if (player.getPointsPerGame() >= 10 && player.getAssistsPerGame() >= 10) return true;
        if (player.getPointsPerGame() >= 10 && player.getReboundsPerGame() >= 10) return true;
        if (player.getAssistsPerGame() >= 10 && player.getReboundsPerGame() >= 10) return true;
        return false;
      }
    };
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