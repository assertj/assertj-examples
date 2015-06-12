package org.assertj.swing.aut.lookup;

import static org.assertj.swing.aut.util.swing.ButtonUtil.newButton;
import static org.assertj.swing.aut.util.swing.LabelUtil.newLabel;
import static org.assertj.swing.aut.util.swing.TextFieldUtil.newPasswordField;
import static org.assertj.swing.aut.util.swing.TextFieldUtil.newTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;

import org.assertj.swing.aut.components.SampleFrame;

public class LoginFrame extends SampleFrame {
  private static final long serialVersionUID = 1L;

  public LoginFrame() {
    setMiglayout(new LC().wrapAfter(2), new AC().align("right"), new AC());

    final JLabel nameLabel = newLabel("username", "Username");
    final JTextField textField = newTextField("username");
    final JLabel pwLabel = newLabel("password", "Password");
    final JTextField pwField = newPasswordField("password");

    JButton loginButton = newButton("login", "Login");

    loginButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        LoginFrame.this.dispose();
        login(textField);
      }
    });

    add(nameLabel);
    add(textField);
    add(pwLabel);
    add(pwField);
    add(loginButton);

    pack();
  }

  private void login(final JTextField textField) {
    // simulate database interaction
    new Thread(new Runnable() {

      @Override
      public void run() {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
        // show the great application
        SwingUtilities.invokeLater(new Runnable() {

          @Override
          public void run() {
            JFrame frame = new MainFrame(textField.getText());
            frame.setVisible(true);
          }
        });
      }
    }).start();
    ;
  }
}
