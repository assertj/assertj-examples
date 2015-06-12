package org.assertj.swing.aut.util.swing;

import javax.swing.JLabel;

public class LabelUtil {
  public static JLabel newLabel(String name) {
    return newLabel(name, " ");
  }

  public static JLabel newLabel(String name, String text) {
    JLabel label = new JLabel(text);
    label.setName(name);
    return label;
  }
}
