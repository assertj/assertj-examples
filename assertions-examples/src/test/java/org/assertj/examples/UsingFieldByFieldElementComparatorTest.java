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
package org.assertj.examples;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class UsingFieldByFieldElementComparatorTest {

  @Test
  public void usingFieldByFieldElementComparatorTest() throws Exception {
    List<Animal> animals = new ArrayList<>();
    Bird bird = new Bird("White");
    Snake snake = new Snake(15);
    animals.add(bird);
    animals.add(snake);

    assertThat(animals).usingFieldByFieldElementComparator()
                       .containsExactly(bird, snake);
  }

  private class Animal {
    private final String name;

    private Animal(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  private class Bird extends Animal {
    private final String color;

    private Bird(String color) {
      super("Bird");
      this.color = color;
    }

    public String getColor() {
      return color;
    }
  }

  private class Snake extends Animal {
    private final int length;

    private Snake(int length) {
      super("Snake");
      this.length = length;
    }

    public int getLength() {
      return length;
    }
  }
}
