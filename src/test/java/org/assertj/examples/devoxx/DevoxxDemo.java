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
 * Copyright 2012-2013 the original author or authors.
 */
package org.assertj.examples.devoxx;

// import static org.assertj.examples.devoxx.DevoxxCustomAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.Assertions.extractProperty;
import static org.assertj.core.util.Dates.parse;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.examples.data.Race.ELF;
import static org.assertj.examples.data.Race.HOBBIT;
import static org.assertj.examples.data.Race.ORC;
import static org.assertj.examples.data.Ring.dwarfRing;
import static org.assertj.examples.data.Ring.manRing;
import static org.assertj.examples.data.Ring.narya;
import static org.assertj.examples.data.Ring.nenya;
import static org.assertj.examples.data.Ring.oneRing;
import static org.assertj.examples.data.Ring.vilya;

import java.util.Date;
import java.util.List;

import org.assertj.examples.AbstractAssertionsExamples;
import org.assertj.examples.data.Ring;
import org.junit.Before;
import org.junit.Test;

public class DevoxxDemo extends AbstractAssertionsExamples {

  private List<Ring> elvesRings;

  @Override
  @Before
  public void setup() {
    super.setup();
    elvesRings = newArrayList(vilya, nenya, narya);
  }

  @Test
  public void common_assertions_examples() {

    assertThat("Test").containsIgnoringCase("test");

    // the most simple assertion
    assertThat(frodo.age).isEqualTo(33);
    assertThat(frodo.getName()).isEqualTo("Frodo").isNotEqualTo("Frodon");

    assertThat(frodo).isIn(fellowshipOfTheRing);
    assertThat(frodo).isIn(sam, frodo, pippin);
    assertThat(sauron).isNotIn(fellowshipOfTheRing);
  }

  @Test
  public void string_assertions_examples() {

    assertThat("Frodo").startsWith("Fro").endsWith("do").hasSize(5);
    assertThat("Frodo").contains("rod").doesNotContain("fro");
    assertThat("Frodo").containsOnlyOnce("do");

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
    // assertThat(elvesRings).containsOnly(nenya, vilya, narya);
  }

  @Test
  public void iterable_assertions_on_extracted_property_values_example() {

    // extract simple property values having a java standard type
    assertThat(extractProperty("name").from(fellowshipOfTheRing)).contains("Frodo", "Gandalf", "Legolas")
                                                                 .doesNotContain("Sauron", "Elrond");

    // extracting property works also with user's types (here Race)
    assertThat(extractProperty("race").from(fellowshipOfTheRing)).contains(HOBBIT, ELF)
                                                                 .doesNotContain(ORC);

    // extract nested property on Race
    assertThat(extractProperty("race.name").from(fellowshipOfTheRing)).contains("Hobbit", "Elf")
                                                                      .doesNotContain("Orc");
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
  public void basic_date_assertions_examples() {

    // theTwoTowers was released the 18th december of year 2002
    Date theTwoTowersReleaseDate = theTwoTowers.getReleaseDate();
    assertThat(theTwoTowersReleaseDate).isEqualTo(parse("2002-12-18"))
                                       .isEqualTo("2002-12-18")
                                       .isAfter(theFellowshipOfTheRing.getReleaseDate())
                                       .isBefore(theReturnOfTheKing.getReleaseDate());
  }

  @Test
  public void use_generate_assertions() {

    // use DevoxxCustomAssertions.assertThat static import instead of org.assertj.core.api.Assertions.assertThat;
    // assertThat(tonyParker).hasTeam("Spurs");

    // write assertion playsInSameTeamAs(player)
  }
}
