package org.assertj.swing.junit.examples.lookup;

import javax.swing.JButton;

import org.assertj.swing.aut.components.SampleFrame;
import org.assertj.swing.aut.lookup.LoginFrame;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.junit.SwingJUnitExamples;
import org.junit.Test;

public class LoginFrame_ComponentLookup_Test extends SwingJUnitExamples {
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
  public void shouldLookupByName() {
    JButtonFixture button = window.button("login");
    button.requireVisible();
  }

  @Test
  public void shouldLookupByType() {
    JButtonFixture button = window.button();
    button.requireVisible();
  }

  @Test
  public void shouldLookupByCustomSearchCriteria() {
    JButtonFixture button = window.button(new GenericTypeMatcher<JButton>(JButton.class) {
      @Override
      protected boolean isMatching(JButton button) {
        return "Login".equals(button.getText());
      }
    });
    button.requireVisible();
  }
}
