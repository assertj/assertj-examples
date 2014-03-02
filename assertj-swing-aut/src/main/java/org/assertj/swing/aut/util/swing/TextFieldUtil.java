package org.assertj.swing.aut.util.swing;

import javax.swing.JTextField;

public class TextFieldUtil {
  public static JTextField newTextField(String name) {
    JTextField field = new JTextField(20);
    field.setName(name);
    return field;
  }
}
