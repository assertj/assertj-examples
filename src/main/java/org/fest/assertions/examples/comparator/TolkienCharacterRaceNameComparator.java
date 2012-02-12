package org.fest.assertions.examples.comparator;

import java.util.Comparator;

import org.fest.assertions.examples.data.TolkienCharacter;

/**
 * Compare {@link TolkienCharacter} race's name.
 *
 * @author Joel Costigliola 
 */
public class TolkienCharacterRaceNameComparator implements Comparator<TolkienCharacter> {
  public int compare(TolkienCharacter tolkienCharacter1, TolkienCharacter tolkienCharacter2) {
    return tolkienCharacter1.getRace().getName().compareTo(tolkienCharacter2.getRace().getName());
  }

}
