package org.assertj.swing.testng.examples.lookup;

import org.assertj.swing.aut.components.DisabledTableFrame;
import org.assertj.swing.aut.components.SampleFrame;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.testng.SwingTestNGExamples;
import org.testng.annotations.Test;

public class DisabledTableFrame_DisabledComponent_Test extends SwingTestNGExamples {
  private FrameFixture window;

  @Override
  protected void onSetUp() {
    SampleFrame frame = GuiActionRunner.execute(() -> new DisabledTableFrame());
    window = new FrameFixture(robot(), frame);
    window.show();
  }

  @Test
  public void shouldOpenPopupMenuOnDisabledTable() {
    window.table().showPopupMenu();
  }
}
