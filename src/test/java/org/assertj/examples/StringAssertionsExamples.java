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
package org.assertj.examples;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * String assertions specific examples
 * 
 * @author Joel Costigliola
 */
public class StringAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void string_assertions_examples() {

    assertThat("Frodo").startsWith("Fro").endsWith("do").hasSize(5);
    assertThat("Frodo").contains("rod").doesNotContain("fro");
    assertThat("Frodo").containsOnlyOnce("do");
    // contains accepts multiple String to avoid chaining contains several times.
    assertThat("Gandalf the grey").contains("alf", "grey");
    try {
      assertThat("Gandalf the grey").contains("alf", "grey", "wizard", "ring");
    } catch (AssertionError e) {
      logAssertionErrorMessage("String contains with several values", e);
    }
    
    String bookDescription = "{ 'title':'Games of Thrones', 'author':'George Martin'}";
    assertThat(bookDescription).containsSequence("{", "title", "Games of Thrones", "}");

    try {
      assertThat(bookDescription).containsSequence("{", "title", "author", "Games of Thrones", "}");
    } catch (AssertionError e) {
      logAssertionErrorMessage("String containsSequence with incorrect order", e);
    }
    
    // you can ignore case for equals check
    assertThat("Frodo").isEqualToIgnoringCase("FROdO");

    // using regex
    assertThat("Frodo").matches("..o.o").doesNotMatch(".*d");

    // check for empty string, or not.
    assertThat("").isEmpty();
    assertThat("").isNullOrEmpty();
    assertThat("not empty").isNotEmpty();
  }

  @Test
  public void string_assertions_with_custom_comparison_examples() {

    // standard assertion are based on equals() method, but what if you want to ignore String case ?
    // one way is to use isEqualToIgnoringCase
    assertThat("Frodo").isEqualToIgnoringCase("FROdO");
    // this is good but it doest not work with the other assertions : contains, startsWith, ...
    // if you want consistent ignoring case comparison, you must use a case iNsenSiTIve comparison strategy :
    // We see that assertions succeeds even though case is not the same - ok that was the point ;-)
    assertThat("Frodo").usingComparator(caseInsensitiveStringComparator).startsWith("fr").endsWith("ODO");

    // All assertions made after usingComparator(caseInsensitiveStringComparator) are based on the given comparator ...
    assertThat("Frodo").usingComparator(caseInsensitiveStringComparator).contains("fro").doesNotContain("don");
    // ... but a new assertions is not
    // assertThat("Frodo").startsWith("fr").endsWith("ODO"); // FAILS !!!

    // Last thing : matches or doesNotMatch don't use the given comparator
    assertThat("Frodo").usingComparator(caseInsensitiveStringComparator).doesNotMatch("..O.O");
    try {
      // failed assertion with specific comparator mention the comparison strategy to help interpreting the error
      assertThat("Frodo").usingComparator(caseInsensitiveStringComparator).startsWith("FROO");
    } catch (AssertionError e) {
      assertThat(e).hasMessage(
          "expecting\n" + "<'Frodo'>\n" + " to start with\n" + "<'FROO'>\n"
              + " according to 'CaseInsensitiveStringComparator' comparator");
    }
  }

}
