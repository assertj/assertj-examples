package org.assertj.swing.junit.examples.lookup;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.swing.finder.WindowFinder.findFrame;

import javax.swing.JFrame;

import org.assertj.swing.aut.components.SampleFrame;
import org.assertj.swing.aut.lookup.LoginFrame;
import org.assertj.swing.aut.lookup.MainFrame;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.SwingJUnitExamples;
import org.junit.Test;

public class LoginFrame_LongDurationTask_Test extends SwingJUnitExamples {
  private FrameFixture window;

  @Override
  protected void onSetUp() {
    SampleFrame frame = GuiActionRunner.execute(new GuiQuery<SampleFrame>() {
      protected SampleFrame executeInEDT() {
        return new LoginFrame();
      }
    });
    window = new FrameFixture(robot(), frame);
    window.show();

    window.textBox("username").enterText("yvonne");
    window.textBox("password").enterText("welcome");
    window.button("login").click();
  }

  @Test
  public void shouldLookupWithDefaultTimeoutByName() {
    // now the interesting part, we need to wait till the main window is shown.
    FrameFixture mainFrame = findFrame("main").using(robot());
    mainFrame.requireVisible();
  }

  @Test
  public void shouldLookupWithCustomTimeoutByName1() {
    FrameFixture mainFrame = findFrame("main").withTimeout(10000).using(robot());
    mainFrame.requireVisible();
  }

  @Test
  public void shouldLookupWithCustomTimeoutByName2() {
    FrameFixture mainFrame = findFrame("main").withTimeout(10, SECONDS).using(robot());
    mainFrame.requireVisible();
  }

  @Test
  public void shouldLookupWithDefaultTimeoutByType() {
    FrameFixture mainFrame = findFrame(MainFrame.class).using(robot());
    mainFrame.requireVisible();
  }

  @Test
  public void shouldLookupWithDefaultTimeoutByGenericTypeMatcher() {
    GenericTypeMatcher<JFrame> matcher = new GenericTypeMatcher<JFrame>(JFrame.class) {
      protected boolean isMatching(JFrame frame) {
        return frame.getTitle() != null && frame.getTitle().startsWith("News") && frame.isShowing();
      }
    };
    FrameFixture frame = findFrame(matcher).using(robot());
    frame.requireVisible();
  }
}
