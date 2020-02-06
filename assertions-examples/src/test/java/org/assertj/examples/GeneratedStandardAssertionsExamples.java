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

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.Assertions.assertThat;

import java.util.Date;

import org.assertj.examples.data.Book;
import org.assertj.examples.data.Book.Title;
import org.assertj.examples.data.Name;
import org.assertj.examples.data.Team;
import org.assertj.examples.data.bug26.WithGenericListType;
import org.assertj.examples.data.movie.Movie;
import org.assertj.examples.exception.NameException;
import org.junit.jupiter.api.Test;

public class GeneratedStandardAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void generated_standard_assertions_example() throws NameException {
    // use the generated assertions
    assertThat(rose).hasName(new Name("Derrick", "Rose")).hasTeamMates(james, wade).hasOnlyTeamMates(james, wade);
    
    // 
    Team cavs = new Team(newArrayList(rose, james, wade));
    assertThat(cavs).as("cavs players").hasPlayers(rose, james, wade).doesNotHavePlayers(antetokounmpo);
    
    try {
	  assertThat(cavs).as("cavs players").hasPlayers(rose, noah, james);
    } catch (AssertionError e) {
      AbstractAssertionsExamples.logger.info(e.getMessage());
    }

    // use other Team class
    org.assertj.examples.data.movie.Team lotr = new org.assertj.examples.data.movie.Team(newArrayList("vigo mortensen",
                                                                                                      "elijah wood"));
    assertThat(lotr).hasActors("vigo mortensen", "elijah wood");

    WithGenericListType test = new WithGenericListType();
    assertThat(test).hasNoMovies();
    test.movies = newArrayList(theFellowshipOfTheRing);
    assertThat(test).hasOnlyMovies(theFellowshipOfTheRing)
                    .doesNotHaveMovies(theTwoTowers);

    Movie movie = new Movie("boom", new Date(), "1h");
    movie.setCreator("foo");
    assertThat(movie).hasCreator("foo")
                     .hasTitle("boom");
    try {
      movie = null;
      assertThat(movie).as("can be given ?").canBeGiven();
    } catch (AssertionError e) {
      AbstractAssertionsExamples.logger.info(e.getMessage());
    }

    // Name should have comparable assertions
    assertThat(new Name("A", "BC")).isLessThan(new Name("D", "EF"));

    assertThat(new Book("Death Notes")).hasTitle(new Title("Death Notes"));
  }

}
