package org.assertj.swing.aut.util.swing;

import javax.swing.JComboBox;

public class ComboBoxUtil {
  public static JComboBox<String> newBox(String name, String... items) {
    JComboBox<String> box = new JComboBox<>(items);
    box.setName(name);
    return box;
  }
}
