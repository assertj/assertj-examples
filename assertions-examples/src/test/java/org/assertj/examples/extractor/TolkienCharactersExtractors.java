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
package org.assertj.examples.extractor;

import java.util.function.Function;

import org.assertj.core.groups.Tuple;
import org.assertj.examples.data.Race;
import org.assertj.examples.data.TolkienCharacter;

public class TolkienCharactersExtractors {
  TolkienCharactersExtractors() {

  }

  public static Function<TolkienCharacter, Race> race() {
    return TolkienCharacter::getRace;
  }

  public static Function<TolkienCharacter, Integer> age() {
    return tolkienCharacter -> tolkienCharacter.age;
  }

  public static Function<TolkienCharacter, Tuple> ageAndRace() {
    return tolkienCharacter -> new Tuple(tolkienCharacter.age, tolkienCharacter.getRace());
  }
}
