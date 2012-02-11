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

/**
 * Race in Tolkien's Lord of the Rings.
 * 
 * @author Joel Costigliola
 */
public class Race {

  public final static Race hobbit = new Race("Hobbit", false);
  public final static Race maia = new Race("Maia", true);
  public final static Race man = new Race("Man", false);
  public final static Race elf = new Race("Elf", true);
  public final static Race dwarf = new Race("Dwarf", false);
  public final static Race orc = new Race("Orc", false);

  private final String name;
  private final boolean immortal;

  public Race(String name, boolean immortal) {
    super();
    this.name = name;
    this.immortal = immortal;
  }

  public String getName() {
    return name;
  }

  public boolean isImmortal() {
    return immortal;
  }

  @Override
  public String toString() {
    return "Race [name=" + name + ", immortal=" + immortal + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (immortal ? 1231 : 1237);
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Race other = (Race) obj;
    if (immortal != other.immortal) return false;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }
  
  

}
