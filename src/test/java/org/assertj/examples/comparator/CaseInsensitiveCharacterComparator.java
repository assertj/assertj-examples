package org.assertj.examples.comparator;

import java.util.Comparator;

public class CaseInsensitiveCharacterComparator implements Comparator<Character> {

  public int compare(Character c1, Character c2) {
    return c1.toString().toUpperCase().compareTo(c2.toString().toUpperCase());
  }

}
