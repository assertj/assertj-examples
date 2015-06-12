package org.assertj.swing.junit.examples.lookup;

import static org.assertj.swing.core.matcher.JButtonMatcher.withName;

import org.assertj.swing.aut.lookup.SimpleFrame;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.SwingJUnitExamples;
import org.junit.Test;

public class SimpleFrame_JButtonMatcher_Test extends SwingJUnitExamples {
  private SimpleFrame frame;

  @Override
  protected void onSetUp() {
    frame = GuiActionRunner.execute(new GuiQuery<SimpleFrame>() {
      protected SimpleFrame executeInEDT() {
        return new SimpleFrame();
      }
    });
  }

  @Test
  public void shoulFindOkButton() {
    FrameFixture window = new FrameFixture(robot(), frame);
    window.show();
    window.button(withName("ok").andText("OK")).click();
  }
}
