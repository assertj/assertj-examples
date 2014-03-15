package org.assertj.swing.junit.examples.getting_started;

import org.assertj.swing.aut.getting_started.SimpleCopyApplication;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;

public class SimpleCopyApplication_UseBaseTest extends AssertJSwingJUnitTestCase {
  private FrameFixture window;

  @Override
  protected void onSetUp() {
    SimpleCopyApplication frame = GuiActionRunner.execute(new GuiQuery<SimpleCopyApplication>() {
      protected SimpleCopyApplication executeInEDT() {
        return new SimpleCopyApplication();
      }
    });
    // IMPORTANT: note the call to 'robot()'
    // we must use the Robot from AssertJSwingJUnitTestCase
    window = new FrameFixture(robot(), frame);
    window.show(); // shows the frame to test
  }

  @Test
  public void shouldCopyTextInLabelWhenClickingButton() {
    window.textBox("textToCopy").enterText("Some random text");
    window.button("copyButton").click();
    window.label("copiedText").requireText("Some random text");
  }
}
