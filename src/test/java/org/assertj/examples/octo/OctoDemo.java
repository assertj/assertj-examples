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
package org.assertj.examples.octo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.util.Dates.parse;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.examples.data.Race.HOBBIT;
import static org.assertj.examples.data.Ring.*;
import static org.assertj.guava.api.Assertions.assertThat;
import static org.assertj.guava.api.Assertions.entry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Condition;
import org.assertj.examples.AbstractAssertionsExamples;
import org.assertj.examples.data.BasketBallPlayer;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.TolkienCharacter;

import org.junit.Before;
import org.junit.Test;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

public class OctoDemo extends AbstractAssertionsExamples {

  private List<Ring> elvesRings;

  @Override
  @Before
  public void setup() {
    super.setup();
    elvesRings = newArrayList(vilya, nenya, narya);
  }

  @Test
  public void common_assertions_examples() {

    // the most simple assertion
    assertThat(frodo.age).isEqualTo(33);

    // chained assertions
    assertThat(frodo.getName()).isEqualTo("Frodo").isNotEqualTo("Frodon");

    // isIn/isNotIn work with Iterable or varags
    assertThat(frodo).isIn(fellowshipOfTheRing);
    assertThat(frodo).isIn(sam, frodo, pippin);
    assertThat(sauron).isNotIn(fellowshipOfTheRing);
  }

  @Test
  public void string_assertions_examples() {

    assertThat("Frodo").startsWith("Fro").endsWith("do").hasSize(5);

    assertThat("Frodo").contains("rod")
                       .doesNotContain("fro")
                       .containsOnlyOnce("do");  // see javadoc 

    // you can ignore case for equals check
    assertThat("Frodo").isEqualToIgnoringCase("FROdO");

    // using regex
    assertThat("Frodo").matches("..o.o").doesNotMatch(".*d");
  }

  @Test
  public void iterable_assertions_examples() {

    assertThat(elvesRings).isNotEmpty()
                          .hasSize(3)
                          .contains(nenya)
                          .doesNotContain(oneRing);

    // with containsOnly, all the elements must be present (but the order is not important)
    assertThat(elvesRings).containsOnly(nenya, vilya, narya)
                          .doesNotContainNull()
                          .doesNotHaveDuplicates();

    // you can also check the start or end of your collection/iterable
    Iterable<Ring> allRings = newArrayList(oneRing, vilya, nenya, narya, dwarfRing, manRing);
    assertThat(allRings).startsWith(oneRing, vilya).endsWith(dwarfRing, manRing);
    assertThat(allRings).containsAll(elvesRings);

    // to show an error message, let's replace narya by the one ring
    elvesRings.remove(narya);
    elvesRings.add(oneRing);
    try {
      assertThat(elvesRings).containsOnly(nenya, vilya, narya);
    } catch (AssertionError e) {
      logAssertionErrorMessage("containsOnly", e);
    }
  }

  @Test
  public void iterable_assertions_on_extracted_property_values_example() {

    // extract simple property values having a java standard type
    assertThat(fellowshipOfTheRing).extracting("name").contains("Frodo", "Gandalf", "Legolas")
                                                      .doesNotContain("Sauron", "Elrond");

    // extract nested property on Race
    assertThat(fellowshipOfTheRing).extracting("race.name").contains("Hobbit", "Elf")
                                                           .doesNotContain("Orc");

    // extract 'name', 'age' and Race name values.
    assertThat(fellowshipOfTheRing).extracting("name", "race.name").contains(tuple("Boromir", "Man"),
                                                                             tuple("Sam", "Hobbit"),
                                                                             tuple("Legolas", "Elf"));
  }

  @Test
  public void exceptions_assertions_examples() {
    assertThat(elvesRings).hasSize(3);
    try {
      elvesRings.get(9); // argggl !
    } catch (Exception e) {
      // you can check exception type
      assertThat(e).isInstanceOf(IndexOutOfBoundsException.class)
                   .hasNoCause()
                   .hasMessage("Index: 9, Size: 3")
                   .hasMessageStartingWith("Index: 9")
                   .hasMessageContaining("9")
                   .hasMessageEndingWith("Size: 3");
    }
  }

  @Test
  public void map_assertions_examples() {
    // ringBearers is a Map of Ring -> TolkienCharacter who has the ring.
    assertThat(ringBearers).isNotEmpty()
                           .hasSize(4)
                           .contains(entry(oneRing, frodo), entry(nenya, galadriel))
                           .doesNotContain(entry(oneRing, aragorn))
                           .containsKey(Ring.nenya)
                           .containsValue(frodo)
                           .doesNotContainValue(sam);
  }

  @Test
  public void date_assertions_examples() {

    // theTwoTowers was released the 18th december of year 2002
    Date theTwoTowersReleaseDate = theTwoTowers.getReleaseDate();
    assertThat(theTwoTowersReleaseDate).isEqualTo(parse("2002-12-18"))
                                       .isEqualTo("2002-12-18")
                                       .isAfter(theFellowshipOfTheRing.getReleaseDate())
                                       .isBefore(theReturnOfTheKing.getReleaseDate());

    // there is much more in DateAssertionsExamples !
  }

  @Test
  public void file_assertions() {
    // SEE : CustomAssertExamples FileAndStreamAssertionsExamples
  }

  @Test
  public void multimap_assertions_examples() {

    // Mix core and guava assertions in a very natural way thanks to static import

    Multimap<String, String> actual = ArrayListMultimap.create();
    actual.putAll("Lakers", Lists.newArrayList("Kobe Bryant", "Magic Johnson", "Kareem Abdul Jabbar"));
    actual.putAll("Spurs", Lists.newArrayList("Tony Parker", "Tim Duncan", "Manu Ginobili"));

    // assertThat comes from : org.assertj.guava.api.Assertions.assertThat
    assertThat(actual).hasSize(6);
    assertThat(actual).containsKeys("Lakers", "Spurs");
    assertThat(actual).contains(entry("Lakers", "Kobe Bryant"), entry("Spurs", "Tim Duncan"));
    assertThat(actual).containsValues("Kareem Abdul Jabbar", "Tony Parker");

    Multimap<String, String> emptyMultimap = ArrayListMultimap.create();
    assertThat(emptyMultimap).isEmpty();
  }

  @Test
  public void bdd_assertions_examples() {
    //given
    List<BasketBallPlayer> bulls = new ArrayList<BasketBallPlayer>();

    //when
    bulls.add(rose);
    bulls.add(noah);

    // assertThat is replaced by then
    then(bulls).contains(rose, noah).doesNotContain(james);
  }

  @Test
  public void assertions_with_custom_comparator_examples() {

    // standard comparison : frodo is not equal to sam ...
    assertThat(frodo).isNotEqualTo(sam);
    // ... but if we compare only character's race frodo is equal to sam
    assertThat(frodo).usingComparator(raceNameComparator).isEqualTo(sam).isIn(merry, pippin);

    // note that error message mentions the comparator used to understand the failure better.
    try {
      assertThat(frodo).usingComparator(raceNameComparator).isEqualTo(sauron);
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualTo with custom comparator", e);
    }
  }

  @Test
  public void assertions_with_lenient_equals_examples() {

    TolkienCharacter frodoClone = new TolkienCharacter("Frodo", 33, HOBBIT);
    // Frodo and his clone are equals by comparing fields
    assertThat(frodo).isEqualToComparingFieldByField(frodoClone);

    // frodo and sam both are hobbits, so they are lenient equals on race
    assertThat(frodo).isEqualToComparingOnlyGivenFields(sam, "race");

    // Except name and age, frodo and sam both are hobbits, so they are lenient equals ignoring name and age
    assertThat(frodo).isEqualToIgnoringGivenFields(sam, "name", "age");

    // but not when accepting name and race
    try {
      assertThat(frodo).isEqualToComparingOnlyGivenFields(sam, "name", "race", "age");
    } catch (AssertionError e) {
      logAssertionErrorMessage("isEqualToComparingOnlyGivenFields", e);
    }
  }

  @Test
  public void soft_assertions() {
    // SEE : SoftAssertionsExamples and JUnitSoftAssertionsExamples
  }


  @Test
   public void using_condition() {
     try {
       assertThat("Acta").is(new Condition<String>("valid Octo string") {
          @Override
          public boolean matches(String value) {
             return value.startsWith("O") && value.endsWith("o");
          }
       });
     } catch (AssertionError e) {
        logAssertionErrorMessage("condition error", e);
     }

     // SEE UsingConditionExamples for more
   }

  @Test
  public void use_custom_assertions() {
    // SEE : CustomAssertExamples
    // SEE : Assertion generator -> pom.xml, BasketBallPlayerAssert and BasketBallAssertExamples
  }

}
