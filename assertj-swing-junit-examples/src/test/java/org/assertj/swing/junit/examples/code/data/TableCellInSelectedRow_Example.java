package org.assertj.swing.junit.examples.code.data;

import static org.assertj.swing.data.TableCell.row;
import static org.assertj.swing.data.TableCellInSelectedRow.selectedRow;

import javax.swing.JFrame;

import org.assertj.swing.aut.code.data.TableFrame;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTableCellFixture;
import org.assertj.swing.junit.SwingJUnitExamples;
import org.junit.Test;

public class TableCellInSelectedRow_Example extends SwingJUnitExamples {

  private FrameFixture window;

  @Override
  protected void onSetUp() {
    JFrame frame = GuiActionRunner.execute(new GuiQuery<JFrame>() {
      @Override
      protected JFrame executeInEDT() {
        return new TableFrame();
      }
    });
    window = new FrameFixture(robot(), frame);
    window.show();
  }

  @SuppressWarnings("unused")
  @Test
  public void example() {
    window.table("records").selectCell(row(1).column(2));
    JTableCellFixture cell = window.table("records").cell(selectedRow().column(2));
  }
}
