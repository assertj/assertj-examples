package org.assertj.swing.testng.examples.code.data;

import static org.assertj.swing.data.TableCellByColumnId.row;

import javax.swing.JFrame;

import org.assertj.swing.aut.code.data.TableFrame;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTableCellFixture;
import org.assertj.swing.testng.SwingTestNGExamples;
import org.testng.annotations.Test;

public class TableCellByColumnId_Example extends SwingTestNGExamples {

  private FrameFixture window;

  @Override
  protected void onSetUp() {
    JFrame frame = GuiActionRunner.execute(() -> new TableFrame());
    window = new FrameFixture(robot(), frame);
    window.show();
  }

  @SuppressWarnings("unused")
  @Test
  public void example() {
    JTableCellFixture cell = window.table("records").cell(row(3).columnId("First Name"));
  }
}
