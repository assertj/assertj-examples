package org.assertj.examples.comparator;

import java.util.Comparator;

import org.assertj.examples.data.TolkienCharacter;


/**
 * Compare {@link TolkienCharacter} age.
 *
 * @author Joel Costigliola 
 */
public class AgeComparator implements Comparator<TolkienCharacter> {
  public int compare(TolkienCharacter tolkienCharacter1, TolkienCharacter tolkienCharacter2) {
    Integer age1 = tolkienCharacter1.getAge();
    return age1.compareTo(tolkienCharacter2.getAge());
  }

}
