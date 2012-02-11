package org.fest.assertions.examples.comparator;

import java.util.Comparator;

import org.fest.assertions.examples.data.TolkienCharacter;


public class CharacterRaceComparator implements Comparator<TolkienCharacter> {
  public int compare(TolkienCharacter c1, TolkienCharacter c2) {
    return c1.getRace().getName().compareTo(c2.getRace().getName());
  }

}
