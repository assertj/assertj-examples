package org.assertj.swing.aut.util.swing;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class TextFieldUtil {
  public static JTextField newTextField(String name) {
    JTextField field = new JTextField(20);
    field.setName(name);
    return field;
  }

  public static JTextField newPasswordField(String name) {
    JTextField field = new JPasswordField(20);
    field.setName(name);
    return field;
  }
}
