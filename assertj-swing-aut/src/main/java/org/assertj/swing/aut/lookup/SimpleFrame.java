package org.assertj.swing.aut.lookup;

import javax.swing.JButton;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;

import org.assertj.swing.aut.components.SampleFrame;

public class SimpleFrame extends SampleFrame {
  private static final long serialVersionUID = 1L;

  public SimpleFrame() {
    setMiglayout(new LC().wrapAfter(1), new AC().align("center"), new AC());

    JButton okButton = new JButton("OK");
    okButton.setName("ok");

    add(okButton);

    pack();
  }
}
