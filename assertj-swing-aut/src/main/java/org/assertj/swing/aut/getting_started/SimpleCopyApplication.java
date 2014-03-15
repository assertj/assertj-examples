package org.assertj.swing.aut.getting_started;

import static javax.swing.SwingUtilities.invokeAndWait;
import static org.assertj.swing.aut.util.swing.ButtonUtil.addActionToButton;
import static org.assertj.swing.aut.util.swing.ButtonUtil.newButton;
import static org.assertj.swing.aut.util.swing.LabelUtil.newLabel;
import static org.assertj.swing.aut.util.swing.TextFieldUtil.newTextField;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;

import org.assertj.swing.aut.components.SampleFrame;

public class SimpleCopyApplication extends SampleFrame {
  private static final long serialVersionUID = 1L;

  public SimpleCopyApplication() {
    setMiglayout(new LC().wrapAfter(1), new AC().align("center"), new AC());

    final JTextField textField = newTextField("textToCopy");
    JButton button = newButton("copyButton", "Copy text to label");
    final JLabel label = newLabel("copiedText");

    addActionToButton(button, new Runnable() {

      @Override
      public void run() {
        label.setText(textField.getText());
      }
    });

    add(textField);
    add(button);
    add(label);

    pack();
  }

  public static void main(String[] args) throws InvocationTargetException, InterruptedException {
    invokeAndWait(new Runnable() {

      public void run() {
        JFrame frame = new SimpleCopyApplication();
        frame.setVisible(true);
      }
    });
  }
}
