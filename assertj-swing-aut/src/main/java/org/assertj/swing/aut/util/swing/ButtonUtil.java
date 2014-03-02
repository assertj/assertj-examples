package org.assertj.swing.aut.util.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonUtil {
  public static JButton newButton(String name, String text) {
    JButton button = new JButton(text);
    button.setName(name);
    return button;
  }

  public static void addActionToButton(JButton button, final Runnable action) {
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        action.run();
      }
    });
  }
}
