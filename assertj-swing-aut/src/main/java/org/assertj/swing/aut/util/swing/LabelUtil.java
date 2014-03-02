package org.assertj.swing.aut.util.swing;

import javax.swing.JLabel;

public class LabelUtil {
  public static JLabel newLabel(String name) {
    JLabel label = new JLabel(" ");
    label.setName(name);
    return label;
  }
}
