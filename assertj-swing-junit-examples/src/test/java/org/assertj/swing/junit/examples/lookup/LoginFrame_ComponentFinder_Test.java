package org.assertj.swing.junit.examples.lookup;

import javax.swing.JButton;

import org.assertj.swing.aut.components.SampleFrame;
import org.assertj.swing.aut.lookup.LoginFrame;
import org.assertj.swing.core.BasicComponentFinder;
import org.assertj.swing.core.ComponentFinder;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.junit.SwingJUnitExamples;
import org.junit.Test;

public class LoginFrame_ComponentFinder_Test extends SwingJUnitExamples {
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
  }

  @Test
  public void shouldFindButtonWithFinderWithCurrentAWTHierarchy() {
    ComponentFinder finder = BasicComponentFinder.finderWithCurrentAwtHierarchy();
    new JButtonFixture(robot(), (JButton) finder.findByName("login", true)).requireVisible();
  }

  @Test(expected = ComponentLookupException.class)
  public void shouldNotFindExistingButtonWithFinderWithNewAWTHierarchy() {
    ComponentFinder finder = BasicComponentFinder.finderWithNewAwtHierarchy();
    finder.findByName("login", true);
  }

  @Test
  public void shouldFindNewButtonWithFinderWithNewAWTHierarchy() throws InterruptedException {
    ComponentFinder finder = BasicComponentFinder.finderWithNewAwtHierarchy();
    window.button("login").click();
    // wait until frame is active (> 1s)
    Thread.sleep(1500);
    finder.findByName("pw", true);
  }

  @Test
  public void shouldFindButtonWithFinderFromRobot() {
    ComponentFinder finder = robot().finder();
    new JButtonFixture(robot(), (JButton) finder.findByName("login", true)).requireVisible();
  }
}
