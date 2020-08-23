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

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.examples.data.Race.HOBBIT;

import java.util.function.Consumer;

import org.assertj.core.api.Assertions;
import org.assertj.core.description.Description;
import org.assertj.examples.data.TolkienCharacter;
import org.junit.jupiter.api.Test;

public class DescriptionConsumerExample extends AbstractAssertionsExamples {

  // the data used are initialized in AbstractAssertionsExamples.

  @Test
  public void isEqualTo_isNotEqualTo_assertions_examples() {
    // GIVEN
    final StringBuilder descriptionReportBuilder = new StringBuilder(String.format("Assertions:%n"));
    Consumer<Description> descriptionConsumer = desc -> descriptionReportBuilder.append(String.format("-- %s%n", desc));

    // WHEN
    Assertions.setDescriptionConsumer(descriptionConsumer);

    TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
    assertThat(frodo.getName()).as("check name")
                               .isEqualTo("Frodo");
    assertThat(frodo.getRace()).as("check race")
                               .isEqualTo(HOBBIT);

    // THEN
    Assertions.setDescriptionConsumer(null);
    String descriptionReport = descriptionReportBuilder.toString();
    assertThat(descriptionReport).isEqualTo(format("Assertions:%n" +
                                                   "-- check name%n" +
                                                   "-- check race%n"));
    // unset the consumer
    Assertions.setDescriptionConsumer(null);
  }

}
