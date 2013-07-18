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
package presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;
import static org.assertj.core.api.Assertions.entry;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;

import org.assertj.examples.AbstractAssertionsExamples;
import org.assertj.examples.data.Ring;
import org.assertj.examples.data.movie.Movie;
import org.junit.Test;

public class AssertJExamples extends AbstractAssertionsExamples {

  // the data used are initialized in AbstractAssertionsExamples.

  @Test
  public void isEqualTo_isNotEqualTo_assertions_examples() {
    // the most simple assertion
    assertThat(frodo.getName()).isEqualTo("Frodo").isNotEqualTo("Frodon");
  }

  @Test
  public void iterable_basic_assertions_examples() {

    // would work the same way with Iterable<Ring>,
    Iterable<Ring> elvesRings = newArrayList(vilya, nenya, narya);
    Iterable<Movie> trilogy = newArrayList(theFellowshipOfTheRing, theTwoTowers, theReturnOfTheKing);
    assertThat(elvesRings).isNotEmpty().hasSize(3);
    assertThat(elvesRings).hasSameSizeAs(trilogy);
    assertThat(elvesRings).contains(nenya).doesNotContain(oneRing);

    // with containsOnly, all the elements must be present (but the order is not important)
    assertThat(elvesRings).containsOnly(nenya, vilya, narya);
    assertThat(elvesRings).doesNotContainNull().doesNotHaveDuplicates();

    // special check for null, empty collection or both
    assertThat(newArrayList(frodo, null, sam)).containsNull();
    List<Object> newArrayList = newArrayList();
    assertThat(newArrayList).isEmpty();
    assertThat(newArrayList).isNullOrEmpty();
    newArrayList = null;
    assertThat(newArrayList).isNullOrEmpty();

    // you can also check the start or end of your collection/iterable
    Iterable<Ring> allRings = newArrayList(oneRing, vilya, nenya, narya, dwarfRing, manRing);
    assertThat(allRings).startsWith(oneRing, vilya).endsWith(dwarfRing, manRing);
    assertThat(allRings).containsSequence(nenya, narya, dwarfRing);
    assertThat(allRings).containsAll(elvesRings);

    // extract simple property values having a java standard type
    assertThat(fellowshipOfTheRing).extracting("name")
                                   .contains("Boromir", "Gandalf", "Frodo", "Legolas")
                                   .doesNotContain("Sauron", "Elrond");

    // extracting property works also with user's types (here Race)
    assertThat(fellowshipOfTheRing).extracting("race")
                                   .contains(HOBBIT, ELF)
                                   .doesNotContain(ORC);

    // extract nested property on Race
    assertThat(fellowshipOfTheRing).extracting("race.name")
                                   .contains("Hobbit", "Elf")
                                   .doesNotContain("Orc");
  }

  @Test
  public void map_assertions_examples() {
    // ringBearers is a Map of TolkienCharacter indexed by the Ring they are wearing.
    assertThat(ringBearers).isNotEmpty().hasSize(4);

    // note the usage of Assertions.entry(key, value) synthetic sugar for better readability (similar to
    // MapEntry.entry(key, value)).
    assertThat(ringBearers).contains(entry(oneRing, frodo), entry(nenya, galadriel));
    assertThat(ringBearers).doesNotContain(entry(oneRing, aragorn));

    // Assertion on key
    assertThat(ringBearers).containsKey(Ring.nenya);
    assertThat(ringBearers).doesNotContainKey(Ring.manRing);

    // Assertion on value
    assertThat(ringBearers).containsValue(frodo);
    assertThat(ringBearers).doesNotContainValue(sam);

    ringBearers.clear();
    assertThat(ringBearers).isEmpty();

  }

  @Test
  public void exceptions_assertions_examples() {
    assertThat(fellowshipOfTheRing).hasSize(9);
    try {
      fellowshipOfTheRing.get(9); // argggl !
    } catch (Exception e) {
      // you can check exception type
      assertThat(e).isInstanceOf(IndexOutOfBoundsException.class);

      // you can check if exception nas no cause
      assertThat(e).hasNoCause();

      // you can check exception message
      assertThat(e).hasMessage("Index: 9, Size: 9");

      // sometimes message are not entirely predictible, you can then check for start, end or containing string.
      assertThat(e).hasMessageStartingWith("Index: 9").hasMessageContaining("9").hasMessageEndingWith("Size: 9");
      // this equivalent to (unless for error message which is more explicit in assertThat(e).hasMessageXXX)
      assertThat(e.getMessage()).startsWith("Index: 9").contains("9").endsWith("Size: 9");
    }
  }

  @Test
  public void file_assertions_examples() throws Exception {
    // file assertion
    File xFile = writeFile("xFile", "The Truth Is Out There");
    assertThat(xFile).exists().isFile().isRelative();
    assertThat(xFile).canRead().canWrite();
    assertThat(contentOf(xFile)).startsWith("The Truth").contains("Is Out").endsWith("There");

    // compare content with another file
    File xFileClone = writeFile("xFileClone", "The Truth Is Out There");
    assertThat(xFile).hasContentEqualTo(xFileClone);

    // compare content with a string
    assertThat(xFile).hasContent("The Truth Is Out There");

    // compare content with a string, specifying a character set
    Charset turkishCharset = Charset.forName("windows-1254");
    File xFileWithTurkishCharset = writeFile("xFileWithTurkishCharset", "La Vérité Est Ailleurs", turkishCharset);
    assertThat(xFileWithTurkishCharset).usingCharset(turkishCharset).hasContent("La Vérité Est Ailleurs");
  }

  // helper methods
  private File writeFile(String fileName, String fileContent) throws Exception {
    return writeFile(fileName, fileContent, Charset.defaultCharset());
  }

  private File writeFile(String fileName, String fileContent, Charset charset) throws Exception {
    File file = new File("target/" + fileName);
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
    out.write(fileContent);
    out.close();
    return file;
  }

}
