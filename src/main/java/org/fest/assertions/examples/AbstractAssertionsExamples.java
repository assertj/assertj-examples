package org.fest.assertions.examples;

import static org.fest.assertions.examples.data.Ring.*;
import static org.fest.util.Collections.list;
import static org.fest.util.Dates.parse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;

import org.fest.assertions.examples.comparator.AbsValueComparator;
import org.fest.assertions.examples.comparator.AgeComparator;
import org.fest.assertions.examples.comparator.CaseInsensitiveCharacterComparator;
import org.fest.assertions.examples.comparator.CaseInsensitiveStringComparator;
import org.fest.assertions.examples.comparator.TolkienCharacterRaceNameComparator;
import org.fest.assertions.examples.comparator.YearAndMonthDateComparator;
import org.fest.assertions.examples.data.Movie;
import org.fest.assertions.examples.data.Race;
import org.fest.assertions.examples.data.Ring;
import org.fest.assertions.examples.data.TolkienCharacter;

/**
 * 
 * Init data for assertions examples.
 *
 * @author Joel Costigliola
 */
public abstract class AbstractAssertionsExamples {

  // Some of the Lord of the Rings races :
  protected static final Race HOBBIT = new Race("Hobbit", false);
  protected static final Race MAIA = new Race("Maia", true);
  protected static final Race MAN = new Race("Man", false);
  protected static final Race ELF = new Race("Elf", true);
  protected static final Race DWARF = new Race("Dwarf", false);
  protected static final Race ORC = new Race("Orc", false);
  
  // Some of the Lord of the Rings characters :
  protected final TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
  protected final TolkienCharacter sam = new TolkienCharacter("Sam", 38, HOBBIT);
  protected final TolkienCharacter merry = new TolkienCharacter("Merry", 36, HOBBIT);
  protected final TolkienCharacter pippin = new TolkienCharacter("Pippin", 28, HOBBIT);
  protected final TolkienCharacter gandalf = new TolkienCharacter("Gandalf", 2020, MAIA);
  private final TolkienCharacter gimli = new TolkienCharacter("Gimli", 139, DWARF);
  private final TolkienCharacter legolas = new TolkienCharacter("Legolas", 1000, ELF);
  protected final TolkienCharacter aragorn = new TolkienCharacter("Aragorn", 87, MAN);
  private final TolkienCharacter boromir = new TolkienCharacter("Boromir", 87, MAN);
  protected final TolkienCharacter sauron = new TolkienCharacter("Sauron", 50000, MAIA);
  protected final TolkienCharacter galadriel = new TolkienCharacter("Legolas", 3000, ELF);
  private final TolkienCharacter elrond = new TolkienCharacter("Legolas", 3000, ELF);
  protected final List<TolkienCharacter> fellowshipOfTheRing = new ArrayList<TolkienCharacter>();
  // Rings and their bearer
  protected final List<Ring> ringsOfPower = list(oneRing, vilya, nenya, narya, dwarfRing, manRing);
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
  }

}