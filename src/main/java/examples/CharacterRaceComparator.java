package examples;

import java.util.Comparator;

public class CharacterRaceComparator implements Comparator<TolkienCharacter> {
  public int compare(TolkienCharacter c1, TolkienCharacter c2) {
    return c1.getRace().getName().compareTo(c2.getRace().getName());
  }

}
