package org.assertj.swing.junit.examples.lookup;

import org.assertj.swing.aut.components.DisabledTableFrame;
import org.assertj.swing.aut.components.SampleFrame;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.SwingJUnitExamples;
import org.junit.Test;

public class DisabledTableFrame_DisabledComponent_Test extends SwingJUnitExamples {
  private FrameFixture window;

  @Override
  protected void onSetUp() {
    SampleFrame frame = GuiActionRunner.execute(new GuiQuery<SampleFrame>() {
      @Override
      protected SampleFrame executeInEDT() {
        return new DisabledTableFrame();
      }
    });
    window = new FrameFixture(robot(), frame);
    window.show();
  }

  @Test
  public void shouldOpenPopupMenuOnDisabledTable() {
    window.table().showPopupMenu();
  }
}
