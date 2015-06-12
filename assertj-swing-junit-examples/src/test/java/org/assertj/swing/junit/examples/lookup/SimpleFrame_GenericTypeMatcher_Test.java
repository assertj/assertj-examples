package org.assertj.swing.junit.examples.lookup;

import javax.swing.JButton;

import org.assertj.swing.aut.lookup.SimpleFrame;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.SwingJUnitExamples;
import org.junit.Test;

public class SimpleFrame_GenericTypeMatcher_Test extends SwingJUnitExamples {
  private FrameFixture window;

  @Override
  protected void onSetUp() {
    SimpleFrame frame = GuiActionRunner.execute(new GuiQuery<SimpleFrame>() {
      protected SimpleFrame executeInEDT() {
        return new SimpleFrame();
      }
    });
    window = new FrameFixture(robot(), frame);
    window.show();
  }

  @Test
  public void shoulFindOkButton() {
    GenericTypeMatcher<JButton> textMatcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override
      protected boolean isMatching(JButton button) {
        return "OK".equals(button.getText());
      }
    };
    window.button(textMatcher).requireVisible();
  }
}
