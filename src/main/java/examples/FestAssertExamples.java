package examples;

import static examples.Ring.*;
import static java.lang.Integer.toHexString;
import static java.util.Collections.sort;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Fail.fail;
import static org.fest.assertions.data.Index.atIndex;
import static org.fest.assertions.data.MapEntry.entry;
import static org.fest.util.Arrays.array;
import static org.fest.util.Collections.list;
import static org.fest.util.Dates.*;

import java.io.*;
import java.math.BigDecimal;
import java.text.*;
import java.util.*;

import org.junit.*;

import org.fest.assertions.api.DateAssert;
import org.fest.assertions.internal.*;

public class FestAssertExamples {

  private final Race hobbit = new Race("Hobbit", false);
  private final Race maia = new Race("Maia", true);
  private final Race man = new Race("Man", false);
  private final Race elf = new Race("Elf", true);
  private final Race dwarf = new Race("Dwarf", false);
  private final Race orc = new Race("Orc", false);

  private final TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, hobbit);
  private final TolkienCharacter sam = new TolkienCharacter("Sam", 38, hobbit);
  private final TolkienCharacter merry = new TolkienCharacter("Merry", 36, hobbit);
  private final TolkienCharacter pippin = new TolkienCharacter("Pippin", 28, hobbit);
  private final TolkienCharacter gandalf = new TolkienCharacter("Gandalf", 2020, maia);
  private final TolkienCharacter gimli = new TolkienCharacter("Gimli", 139, dwarf);
  private final TolkienCharacter legolas = new TolkienCharacter("Legolas", 1000, elf);
  private final TolkienCharacter aragorn = new TolkienCharacter("Aragorn", 87, man);
  private final TolkienCharacter boromir = new TolkienCharacter("Boromir", 87, man);
  private final TolkienCharacter sauron = new TolkienCharacter("Sauron", 50000, maia);
  private final TolkienCharacter galadriel = new TolkienCharacter("Legolas", 3000, elf);
  private final TolkienCharacter elrond = new TolkienCharacter("Legolas", 3000, elf);
  private final List<TolkienCharacter> fellowshipOfTheRing = new ArrayList<TolkienCharacter>();
  private final Map<Ring, TolkienCharacter> ringBearers = new HashMap<Ring, TolkienCharacter>();

  private final Movie theFellowshipOfTheRing = new Movie("the fellowship of the Ring", parse("2001-12-19"));
  private final Movie theTwoTowers = new Movie("the two Towers", parse("2002-12-18"));
  private final Movie theReturnOfTheKing = new Movie("the Return of the King", parse("2003-12-17"));
  private final Movie theSilmarillion = new Movie("the Silmarillion", parse("2030-01-01"));
  private Comparator<TolkienCharacter> ageComparator = new Comparator<TolkienCharacter>() {
    public int compare(TolkienCharacter c1, TolkienCharacter c2) {
      if (c1.getAge() == c2.getAge()) return 0;
      return c1.getAge() - c2.getAge() > 0 ? -1 : 1;
    }
  };
  private Comparator<TolkienCharacter> raceComparator = new CharacterRaceComparator();
  private Comparator<String> caseInsensitiveStringComparator = new CaseInsensitiveStringComparator();
  private Comparator<Integer> absValueComparator = new AbsValueComparator<Integer>();
  private Comparator<Character> caseInsensitiveComparator = new CaseInsensitiveComparator();

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

  @Test
  public void basic_assertions_examples() {
    assertThat(frodo.getAge()).isEqualTo(33);
    assertThat(frodo.getName()).as("check Frodo's name").isEqualTo("Frodo");
    //
    try {
      // set a bad name for Mr Frodo !
      frodo.setName("Frodon");
      assertThat(frodo.getName()).as("check Frodo's name").isEqualTo("Frodo");
    } catch (AssertionError e) {
      assertThat(e).hasMessage("[check Frodo's name] expected:<'Frodo[]'> but was:<'Frodo[n]'>");
      frodo.setName("Frodo");
    }
  }

  @Test
  public void string_assertions_with_custom_comparison_examples() {
    assertThat(frodo.getName()).startsWith("Fro").endsWith("do");
    assertThat(frodo.getName()).usingComparator(caseInsensitiveStringComparator).startsWith("FR").endsWith("ODO").contains("ROD");
    assertThat(frodo.getName()).usingComparator(caseInsensitiveStringComparator).contains("fro").doesNotContain("don");
  }

  @Test
  public void assertions_with_custom_equals_comparison_examples() {
    // standard comparison : frodo is not equal to sam ...
    assertThat(frodo).isNotEqualTo(sam);
    // ... but if we compare only character's race frodo is equal to sam
    assertThat(frodo).usingComparator(raceComparator).isEqualTo(sam).isEqualTo(merry).isEqualTo(pippin);
    // isIn assertion should be consistent with raceComparator :
    assertThat(frodo).usingComparator(raceComparator).isIn(sam, merry, pippin);

    // note that error message mentions the comparator used to understand the failure better.
    boolean errorHasBeenThrown = false;
    try {
      assertThat(frodo).usingComparator(raceComparator).isEqualTo(sauron);
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "Expecting actual:<Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]> to be equal to "
              + "<Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]> "
              + "according to 'CharacterRaceComparator' comparator but was not.");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();

    errorHasBeenThrown = false;
    // custom comparison by race : frodo IS equal to sam => isNotEqual must fail
    try {
      assertThat(frodo).usingComparator(raceComparator).isNotEqualTo(sam);
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "<Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]> should not be equal to:"
              + "<Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]> "
              + "according to 'CharacterRaceComparator' comparator");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();
  }

  @Test
  public void assertions_with_custom_contains_comparison_examples() {
    boolean errorHasBeenThrown = false;
    // standard comparison : the fellowshipOfTheRing does not include sauron ...
    assertThat(fellowshipOfTheRing).doesNotContain(sauron);
    // ... but if we compare only character's race sauron is in fellowshipOfTheRing because he's a maia like Gandalf.
    assertThat(fellowshipOfTheRing).contains(gandalf);
    assertThat(fellowshipOfTheRing).usingComparator(raceComparator).contains(sauron);
    assertThat(list(gandalf)).usingComparator(raceComparator).contains(sauron);
    assertThat(list(gandalf)).usingComparator(raceComparator).containsOnly(sauron);
    assertThat(list(sam, gandalf)).usingComparator(raceComparator).contains(sauron, atIndex(1));
    assertThat(list(sam, gandalf)).usingComparator(raceComparator).doesNotContain(sauron, atIndex(0));
    assertThat(list(sam, gandalf)).isSortedAccordingTo(raceComparator);
    assertThat(list(sam, gandalf)).usingComparator(raceComparator).isSorted();

    // note that error message mentions the comparator used to understand the failure better.
    try {
      assertThat(list(gandalf)).usingComparator(raceComparator).doesNotContain(sauron);
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "expecting:<[Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020]]> not to contain:"
              + "<[Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]]> but found:"
              + "<[Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]]> "
              + "according to 'CharacterRaceComparator' comparator");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();

    // note that error message mentions the comparator used to understand the failure better.
    try {
      errorHasBeenThrown = false;
      assertThat(list(sam, gandalf)).usingComparator(raceComparator).contains(sauron, atIndex(0));
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "expecting <Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]> at index <0> "
              + "but found <Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]> in "
              + "<[Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]"
              + ", Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020]]>"
              + " according to 'CharacterRaceComparator' comparator");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();

    // note that error message mentions the comparator used to understand the failure better.
    try {
      errorHasBeenThrown = false;
      assertThat(list(sam, gandalf)).usingComparator(raceComparator).doesNotContain(sauron, atIndex(1));
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "expecting <[Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38], "
              + "Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020]]> "
              + "not to contain <Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]> "
              + "at index <1> according to 'CharacterRaceComparator' comparator");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();

    // duplicates assertion honors custom comparator :
    assertThat(fellowshipOfTheRing).doesNotHaveDuplicates();
    assertThat(list(sam, gandalf)).usingComparator(raceComparator).doesNotHaveDuplicates();
    try {
      errorHasBeenThrown = false;
      assertThat(list(sam, gandalf, frodo)).usingComparator(raceComparator).doesNotHaveDuplicates();
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "found duplicate(s):<[Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]]> in:<"
              + "[Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38], "
              + "Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020], "
              + "Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]]> "
              + "according to 'CharacterRaceComparator' comparator");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();
  }

  @Test
  public void assertions_on_arrays_with_custom_comparison_examples() {
    boolean errorHasBeenThrown = false;
    TolkienCharacter[] fellowshipOfTheRingCharacters = fellowshipOfTheRing.toArray(new TolkienCharacter[0]);
    // standard comparison : the fellowshipOfTheRing does not include sauron ...
    assertThat(fellowshipOfTheRingCharacters).doesNotContain(sauron);
    // ... but if we compare only character's race sauron is in fellowshipOfTheRing because he's a maia like Gandalf.
    assertThat(fellowshipOfTheRingCharacters).contains(gandalf);
    assertThat(fellowshipOfTheRingCharacters).usingComparator(raceComparator).contains(sauron);
    assertThat(array(gandalf, sam)).usingComparator(raceComparator).startsWith(sauron);
    assertThat(array(sam, gandalf)).usingComparator(raceComparator).endsWith(sauron);
    assertThat(array(gandalf)).usingComparator(raceComparator).contains(sauron);
    assertThat(array(gandalf)).usingComparator(raceComparator).containsOnly(sauron);

    assertThat(array(sam, gandalf)).isSortedAccordingTo(raceComparator);
    assertThat(array(sam, gandalf)).usingComparator(raceComparator).isSorted();
    errorHasBeenThrown = false;
    try {
      assertThat(array(gandalf, sam)).usingComparator(raceComparator).isSorted();
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "group is not sorted according to 'CharacterRaceComparator' comparator because "
              + "element 0:<Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020]> "
              + "is not less or equal than "
              + "element 1:<Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]>, group was:<["
              + "Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020], "
              + "Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38]]>");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();
    //

    // note that error message mentions the comparator used to understand the failure better.
    try {
      assertThat(array(gandalf)).usingComparator(raceComparator).doesNotContain(sauron);
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "expecting:<[Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020]]> not to contain:"
              + "<[Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]]> but found:"
              + "<[Character [name=Sauron, race=Race [name=Maia, immortal=true], age=50000]]> "
              + "according to 'CharacterRaceComparator' comparator");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();

    // duplicates assertion honors custom comparator :
    assertThat(fellowshipOfTheRingCharacters).doesNotHaveDuplicates();
    assertThat(array(sam, gandalf)).usingComparator(raceComparator).doesNotHaveDuplicates();
    errorHasBeenThrown = false;
    try {
      assertThat(array(sam, gandalf, frodo)).usingComparator(raceComparator).doesNotHaveDuplicates();
    } catch (AssertionError e) {
      errorHasBeenThrown = true;
      assertThat(e).hasMessage(
          "found duplicate(s):<[Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]]> in:<"
              + "[Character [name=Sam, race=Race [name=Hobbit, immortal=false], age=38], "
              + "Character [name=Gandalf, race=Race [name=Maia, immortal=true], age=2020], "
              + "Character [name=Frodo, race=Race [name=Hobbit, immortal=false], age=33]]> "
              + "according to 'CharacterRaceComparator' comparator");
    }
    assertThat(errorHasBeenThrown).as("expecting an assertion error").isTrue();
  }

  @Test
  public void list_assertions_examples() {
    assertThat(fellowshipOfTheRing).hasSize(9).contains(frodo, sam).doesNotContain(sauron);
    // isOrdered example
    List<TolkienCharacter> fellowshipOfTheRingSortedByAge = new ArrayList<TolkienCharacter>();
    fellowshipOfTheRingSortedByAge.addAll(fellowshipOfTheRing);
    sort(fellowshipOfTheRingSortedByAge, ageComparator);
    assertThat(fellowshipOfTheRingSortedByAge).isSortedAccordingTo(ageComparator);
    // is empty
    fellowshipOfTheRing.clear();
    assertThat(fellowshipOfTheRing).isEmpty();
  }

  // --> New 1.4 FEST ASSERT Feature
  @Test
  public void isIn_isNotIn_assertions_examples() {
    assertThat(frodo).isIn(fellowshipOfTheRing);
    assertThat(frodo).isIn(sam, frodo, pippin);
    assertThat(sauron).isNotIn(fellowshipOfTheRing);
  }

  @Test
  public void onProperty_usage() { // CHANGED IN FEST 2.x
    // with simple property
    assertThat(PropertySupport.instance().propertyValues("name", fellowshipOfTheRing)).contains("Boromir", "Gandalf",
        "Frodo", "Legolas").doesNotContain("Sauron", "Elrond");
    // with simple property on Race
    assertThat(PropertySupport.instance().propertyValues("race", fellowshipOfTheRing)).contains(hobbit, man, elf)
        .doesNotContain(orc);
    // nested property introspection on Race
    assertThat(PropertySupport.instance().propertyValues("race.name", fellowshipOfTheRing)).contains("Hobbit", "Man",
        "Elf").doesNotContain("Orc");
  }

  @Test
  public void exceptions_assertions_examples() {
    assertThat(fellowshipOfTheRing).hasSize(9);
    try {
      fellowshipOfTheRing.get(9); // argggl !
    } catch (Exception e) {
      assertThat(e).isInstanceOf(IndexOutOfBoundsException.class).hasMessage("Index: 9, Size: 9")
          .hasMessageStartingWith("Index: 9").hasMessageContaining("9").hasMessageEndingWith("Size: 9").hasNoCause();
    }
  }

  @Test
  public void map_assertions_examples() {
    assertThat(ringBearers).hasSize(4).contains(entry(Ring.oneRing, frodo), entry(Ring.nenya, galadriel))
        .doesNotContain(entry(Ring.oneRing, aragorn));
  }

  @Test
  public void number_assertions_examples() throws Exception {
    assertThat(gandalf.getAge()).isGreaterThan(frodo.getAge());
    File emptyFile = writeFile("emptyFile", "");
    assertThat(emptyFile.length()).isZero();
    // another way to assert file size/length
    // assertThat(emptyFile).hasSize(0); // ONLY IN FEST 1.x
  }

  @Test
  public void number_assertions_with_custom_comparison_examples() {
    assertThat(-8).usingComparator(absValueComparator).isEqualTo(8);
    assertThat(new Integer("-8")).usingComparator(new AbsValueComparator<Integer>()).isEqualTo(new Integer("8"));
    
    assertThat(new Long("-8")).usingComparator(new AbsValueComparator<Long>()).isEqualTo(new Long("8"));
    assertThat(-8l).usingComparator(absValueComparator).isEqualTo(8l);
    
    assertThat(new Short("-8")).usingComparator(new AbsValueComparator<Short>()).isEqualTo(new Short("8"));
    assertThat((short)-8).usingComparator(absValueComparator).isEqualTo((short)8);
    
    assertThat(new Float("-8")).usingComparator(new AbsValueComparator<Float>()).isEqualTo(new Float("8"));
    assertThat(-8.0f).usingComparator(absValueComparator).isEqualTo(8.0f);
    
    assertThat(new Double("-8")).usingComparator(new AbsValueComparator<Double>()).isEqualTo(new Double("8"));
    assertThat(-8.0).usingComparator(absValueComparator).isEqualTo(8.0);
    
    assertThat('a').usingComparator(caseInsensitiveComparator).isEqualTo('A');
    assertThat(new Character('a')).usingComparator(caseInsensitiveComparator).isEqualTo(new Character('A'));
    
    assertThat(new Byte("-8")).usingComparator(new AbsValueComparator<Byte>()).isEqualTo(new Byte("8"));
    assertThat((byte)-8).usingComparator(absValueComparator).isEqualTo((byte)8);
    
    assertThat(new BigDecimal("-8")).usingComparator(new AbsValueComparator<BigDecimal>()).isEqualTo(new BigDecimal("8"));
    //assertThat(-8).usingComparator(absValueComparator).isEqualTo(9);
  }
  
  @Test
  public void fail_usage() throws Exception {
    try {
      Failures.instance().failure(null);
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  @Test
  public void file_assertions_examples() throws Exception {
    File xFile = writeFile("xFile", "The Truth Is Out There");
    File xFileClone = writeFile("xFileClone", "The Truth Is Out There");
    assertThat(xFile).exists().isFile().hasContentEqualTo(xFileClone).isRelative();
  }

  @Test
  // new in FEST 2.0
  public void date_assertions_examples() throws Exception {
    // isEqualTo, isAfter, isBefore assertions
    assertThat(theTwoTowers.getReleaseDate()).isEqualTo(parse("2002-12-18")).isEqualTo("2002-12-18")
        .isAfter(theFellowshipOfTheRing.getReleaseDate()).isBefore(theReturnOfTheKing.getReleaseDate())
        .isNotEqualTo(parse("2002-12-19")).isNotEqualTo("2002-12-19");
    assertThat(theTwoTowers.getReleaseDate()).isEqualTo(parse("2002-12-18")).isAfter("2002-12-17")
        .isBefore("2002-12-19");

    // various usage of isBetween assertion
    assertThat(theTwoTowers.getReleaseDate())
        .isBetween(theFellowshipOfTheRing.getReleaseDate(), theReturnOfTheKing.getReleaseDate())
        .isBetween(parse("2002-12-17"), parse("2002-12-19")).isBetween("2002-12-17", "2002-12-19") // [2002-12-17,
                                                                                                   // 2002-12-19[
        .isNotBetween("2002-12-17", "2002-12-18") // [2002-12-17, 2002-12-18[
        .isBetween("2002-12-17", "2002-12-18", true, true); // [2002-12-17, 2002-12-18]

    assertThat(theReturnOfTheKing.getReleaseDate()).isBeforeYear(2004).isAfterYear(2001);

    // equals assertion with delta
    Date date1 = new Date();
    Date date2 = new Date(date1.getTime() + 100);
    assertThat(date1).isCloseTo(date2, 100); // would not be true for delta of 99ms

    // isIn isNotIn works with String based date parameter
    assertThat(theTwoTowers.getReleaseDate()).isIn("2002-12-17", "2002-12-18", "2002-12-19");
    assertThat(theTwoTowers.getReleaseDate()).isNotIn("2002-12-17", "2002-12-19");

    assertThat(theTwoTowers.getReleaseDate()).isInThePast();
    assertThat(new Date()).isToday();
    assertThat(theSilmarillion.getReleaseDate()).isInTheFuture();
  }

  @Test
  // new in FEST 2.0
  public void is_in_same_date_assertions_examples() throws Exception {

    Date date1 = parseDatetime("2003-04-26T13:01:02");
    Date date2 = parseDatetime("2003-04-26T13:01:03");
    // assertThat(date1).isInSameSecondAs(date2); // would fail since date1 and are date2 are not in the same second
    assertThat(date1).isInSameMinuteAs(date2);
    assertThat(date1).isInSameHourAs(date2);
    assertThat(date1).isInSameDayAs(date2);
    assertThat(date1).isInSameMonthAs(date2);
    assertThat(date1).isInSameYearAs(date2);

    date1 = parse("2003-04-26");
    assertThat(date1).isInSameMonthAs("2003-04-27");
    assertThat(date1).isInSameYearAs("2003-05-13");
  }

  @Test
  // new in FEST 2.0
  public void is_within_date_assertions_examples() throws Exception {

    Date date1 = new Date(parseDatetime("2003-04-26T13:20:35").getTime() + 17);
    assertThat(date1).isWithinMillisecond(17);
    assertThat(date1).isWithinSecond(35);
    assertThat(date1).isWithinMinute(20);
    assertThat(date1).isWithinHourOfDay(13);
    assertThat(date1).isWithinDayOfWeek(Calendar.SATURDAY);
    assertThat(date1).isWithinMonth(4);
    assertThat(date1).isWithinYear(2003);
  }

  // new in FEST 2.0
  @Test
  public void date_assertions_with_date_represented_as_string_with_iso_or_custom_date_format() throws Exception {
    // one can use directly date String representation (default is ISO format yyyy-MM-dd) to avoid parsing String as
    // Date.
    assertThat(theTwoTowers.getReleaseDate()).isEqualTo("2002-12-18");
    // but you can use custom date format if you prefer
    DateFormat userCustomDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    assertThat(theTwoTowers.getReleaseDate()).withDateFormat(userCustomDateFormat).isEqualTo("18/12/2002");
    // once set, custom format is used for all following assertions
    assertThat(theReturnOfTheKing.getReleaseDate()).isEqualTo("17/12/2003");
    // you can easily get back to default ISO date format ...
    assertThat(theTwoTowers.getReleaseDate()).withIsoDateFormat().isEqualTo("2002-12-18");
    // ... which is then used for all following assertions
    assertThat(theReturnOfTheKing.getReleaseDate()).isEqualTo("2003-12-17");

    // another way of using custom date format by calling static method useDateFormat(DateFormat) and
    // useDefaultIsoDateFormat()
    // same examples used before
    DateAssert.useDateFormat(userCustomDateFormat);
    assertThat(theTwoTowers.getReleaseDate()).isEqualTo("18/12/2002");
    assertThat(theReturnOfTheKing.getReleaseDate()).isEqualTo("17/12/2003");
    DateAssert.useIsoDateFormat();
    assertThat(theTwoTowers.getReleaseDate()).isEqualTo("2002-12-18");
    assertThat(theReturnOfTheKing.getReleaseDate()).isEqualTo("2003-12-17");

    // choose whatever approach suits you best !
  }

  // new in FEST 2.0
  @Test
  public void assertion_error_with_message_differentiating_expected_double_and_actual_float() throws Exception {

    final Object expected = 42d;
    final Object actual = 42f;
    try {
      assertThat(actual).isEqualTo(expected);
    } catch (AssertionError e) {
      assertThat(expected.hashCode()).isNotEqualTo(actual.hashCode());
      assertThat(e).hasMessage(
          "expected:<'42.0 (Double@" + toHexString(expected.hashCode()) + ")'> but was:<'42.0 (Float@"
              + toHexString(actual.hashCode()) + ")'>");
      return;
    }
    fail("AssertionError expected");
  }

  // new in FEST 2.0
  @Test
  public void assertion_error_with_message_differentiating_expected_and_actual_persons() throws Exception {

    Person actual = new Person("Jake", 43);
    Person expected = new Person("Jake", 47);
    try {
      assertThat(actual).isEqualTo(expected);
    } catch (AssertionError e) {
      assertThat(expected.hashCode()).isNotEqualTo(actual.hashCode());
      assertThat(e).hasMessage(
          "expected:<'Person[name=Jake] (Person@" + toHexString(expected.hashCode())
              + ")'> but was:<'Person[name=Jake] (Person@" + toHexString(actual.hashCode()) + ")'>");
      return;
    }
    fail("AssertionError expected");
  }

  // new in FEST 2.0
  @Test
  public void is_sorted_assertion() {
    // enum order = order of declaration = ring power
    assertThat(new Ring[] { oneRing, vilya, nenya, narya, dwarfRing, manRing }).isSorted();
    // ring comparison by increasing power
    Comparator<Ring> increasingPowerRingComparator = new Comparator<Ring>() {
      public int compare(Ring ring1, Ring ring2) {
        return -ring1.compareTo(ring2);
      }
    };
    assertThat(new Ring[] { manRing, dwarfRing, narya, nenya, vilya, oneRing }).isSortedAccordingTo(
        increasingPowerRingComparator);
  }

  // ------------------------------------------------------------------------------------------------------
  // methods used in our examples
  // ------------------------------------------------------------------------------------------------------

  private File writeFile(String fileName, String fileContent) throws Exception {
    File file = new File("target/"+fileName);
    BufferedWriter out = new BufferedWriter(new FileWriter(file));
    out.write(fileContent);
    out.close();
    return file;
  }

}
