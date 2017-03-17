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
package org.assertj.examples.data;

import static org.assertj.examples.data.Alignment.EVIL;
import static org.assertj.examples.data.Alignment.GOOD;
import static org.assertj.examples.data.Alignment.NEUTRAL;

/**
 * Race in Tolkien's Lord of the Rings.
 * 
 * @author Joel Costigliola
 * @author Florent Biville
 */
public enum Race {

  HOBBIT("Hobbit", false, GOOD), MAIA("Maia", true, GOOD), MAN("Man", false, NEUTRAL), ELF("Elf", true, GOOD), DWARF("Dwarf", false, GOOD), ORC("Orc", false, EVIL);

  private final String name;
  public final boolean immortal;
  private Alignment alignment;

  Race(String name, boolean immortal, Alignment alignment) {
    this.name = name;
    this.immortal = immortal;
    this.alignment = alignment;
  }

  public String getName() {
    return name;
  }

  public Alignment getAlignment() {
    return alignment;
  }

  public String getFullname() {
    return immortal ? "immortal " + name : name;
  }

  @Override
  public String toString() {
    return "Race [name=" + name + ", immortal=" + immortal + "]";
  }
}
