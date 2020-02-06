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
package org.assertj.examples.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.configuration.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class CustomConfigurationExample {

  @Test
  public void custom_configuration_usage_example() {
    // GIVEN
    Configuration configuration = new Configuration();
    configuration.setBareNamePropertyExtraction(false);
    configuration.setComparingPrivateFields(false);
    configuration.setExtractingPrivateFields(false);
    configuration.setLenientDateParsing(true);
    configuration.setMaxElementsForPrinting(1001);
    configuration.setMaxLengthForSingleLineDescription(81);
    configuration.setRemoveAssertJRelatedElementsFromStackTrace(false);
    // WHEN
    System.out.println(configuration.describe());
    // THEN
    assertThat(configuration.bareNamePropertyExtractionEnabled()).isFalse();
    assertThat(configuration.comparingPrivateFieldsEnabled()).isFalse();
    assertThat(configuration.extractingPrivateFieldsEnabled()).isFalse();
    assertThat(configuration.lenientDateParsingEnabled()).isTrue();
    assertThat(configuration.maxLengthForSingleLineDescription()).isEqualTo(81);
    assertThat(configuration.maxElementsForPrinting()).isEqualTo(1001);
    assertThat(configuration.removeAssertJRelatedElementsFromStackTraceEnabled()).isFalse();
  }

  @AfterEach
  public void revertToDefaultConfiguration() {
    Configuration configuration = new Configuration();
    configuration.applyAndDisplay();
  }
}
