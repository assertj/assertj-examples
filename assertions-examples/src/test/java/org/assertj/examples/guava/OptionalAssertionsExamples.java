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
package org.assertj.examples.guava;

import static org.assertj.guava.api.Assertions.assertThat;

import org.assertj.examples.AbstractAssertionsExamples;
import org.junit.Test;

import com.google.common.base.Optional;


/**
 * {@link Optional} assertions example.
 * 
 * @author Joel Costigliola
 */
public class OptionalAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void optional_assertions_examples() {
    final Optional<String> optional = Optional.of("Test");
    assertThat(optional).isPresent().contains("Test");
    assertThat(Optional.of("Test")).extractingValue().isEqualTo("Test");
    assertThat(Optional.of("Test")).extractingCharSequence().startsWith("T");

    Optional<Object> absentOptional = Optional.fromNullable(null);
    assertThat(absentOptional).isAbsent();
    
    // log some error messages to have a look at them
    try {
      assertThat(absentOptional).isPresent();
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.isPresent", e);
    }
    try {
      assertThat(absentOptional).contains("Test");
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.contains", e);
    }
    try {
      assertThat(optional).contains("Foo");
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.contains", e);
    }
    try {
      assertThat(optional).isAbsent();
    } catch (AssertionError e) {
      logAssertionErrorMessage("OptionalAssert.isAbsent", e);
    }
  }
  
}
