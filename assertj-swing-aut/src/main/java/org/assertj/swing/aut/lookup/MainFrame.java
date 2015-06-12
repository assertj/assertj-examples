package org.assertj.swing.aut.lookup;

import static org.assertj.swing.aut.util.swing.LabelUtil.newLabel;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;

import org.assertj.swing.aut.components.SampleFrame;

public class MainFrame extends SampleFrame {
  private static final long serialVersionUID = 1L;

  public MainFrame(String name) {
    super("main");
    setTitle("News: " + getTitle());
    setMiglayout(new LC().wrapAfter(1), new AC().align("center"), new AC());

    add(newLabel("name", "The user has the name: '" + name + "'"));
    add(newLabel("pw", "and the password: '***"));

    pack();
  }
}
