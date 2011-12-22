/*
 * Created on May 26, 2010
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2010 the original author or authors.
 */
package examples;

import static java.lang.String.format;

import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.core.WritableAssertionInfo;
import org.fest.assertions.internal.Objects;

/**
 * 
 * A simple class to illustrate how to extend Fest assertions with custom ones.
 * 
 * @author Joel Costigliola
 */
public class TolkienCharacterAssert extends AbstractAssert<TolkienCharacterAssert, TolkienCharacter> {

  /**
   * 
   * Creates a new </code>{@link TolkienCharacterAssert}</code> to make assertions on actual Character.
   * @param actual the Character we want to make assertions on.
   */
  public TolkienCharacterAssert(TolkienCharacter actual) {
    super(actual, TolkienCharacterAssert.class);
  }

  /**
   * an entry point for CharacterAssert to follow Fest standard <code>assertThat()</code> statements.<br>
   * With a static import, one's can write directly : <code>assertThat(frodo).hasName("Frodo");</code> 
   * @param actual the Character we want to make assertions on.
   * @return a new </code>{@link TolkienCharacterAssert}</code> 
   */
  public static TolkienCharacterAssert assertThat(TolkienCharacter actual) {
    return new TolkienCharacterAssert(actual);
  }
  
  /**
   * Verifies that the actual Character's name is equal to the given one.
   * @param name the given name to compare the actual Character's name to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Character's name is not equal to the given one.
   */
  public TolkienCharacterAssert hasName(String name) {
    // check that actual Character we want to make assertions on is not null.
    isNotNull();
    
    // TODO : EASE WRITING NEW ASSERTIONS
    // check that actual Character's name is equal to the given name (we assume here that Character's name is not null). 
    // we overrides the default error message with a more explicit one
    String errorMessage = format("Expected character's name to be <%s> but was <%s>",name, actual.getName());

    // option 1 : raw assertion
    if (!actual.getName().equals(name)) {
      throw new AssertionError(errorMessage);
    }

    // option 2 : use of other Fest assertions 
    WritableAssertionInfo info = new WritableAssertionInfo();
    info.overridingErrorMessage(errorMessage);
    Objects.instance().assertEqual(info, actual.getName(), name);
    
    // return the current assertion for method chaining
    return this;
  }

}
