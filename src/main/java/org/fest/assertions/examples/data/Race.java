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
package org.fest.assertions.examples.data;

import static org.fest.assertions.examples.data.EvilLevel.AMBIVALENT;
import static org.fest.assertions.examples.data.EvilLevel.KIND;
import static org.fest.assertions.examples.data.EvilLevel.SUPER_EVIL;
import static org.fest.assertions.examples.data.EvilLevel.SUPER_KIND;

/**
 * Race in Tolkien's Lord of the Rings.
 * 
 * @author Joel Costigliola
 * @author Florent Biville
 */
public enum Race {

  hobbit("Hobbit", false, SUPER_KIND), maia("Maia", true, KIND), man("Man", false, AMBIVALENT), elf("Elf", true, KIND), dwarf("Dwarf", false, KIND), orc("Orc", false, SUPER_EVIL);

  private final String name;
  private final boolean immortal;
  private EvilLevel evilLevel;

  Race(String name, boolean immortal, EvilLevel evilLevel) {
    this.name = name;
    this.immortal = immortal;
    this.evilLevel = evilLevel;
  }

  public String getName() {
    return name;
  }

  public boolean isImmortal() {
    return immortal;
  }

  public EvilLevel getEvilLevel() {
    return evilLevel;
  }

  @Override
  public String toString() {
    return "Race [name=" + name + ", immortal=" + immortal + "]";
  }
}
