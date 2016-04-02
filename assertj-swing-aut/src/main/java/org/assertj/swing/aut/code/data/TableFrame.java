package org.assertj.swing.aut.code.data;

import static org.assertj.swing.aut.util.swing.TableUtil.newTable;

import javax.swing.JTable;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;

import org.assertj.swing.aut.components.SampleFrame;

public class TableFrame extends SampleFrame {
  private static final long serialVersionUID = 1L;

  public TableFrame() {
    setMiglayout(new LC().wrapAfter(1), new AC(), new AC());

    final JTable table = newTable("records", data(), columns());

    add(table);

    pack();
  }

  private Object[] columns() {
    return new Object[] { "First Name", "Last Name", "Sport" };
  }

  private Object[][] data() {
    return new Object[][] {
        { "Kathy", "Smith", "Snowboarding" },
        { "John", "Doe", "Rowing" },
        { "Sue", "Black", "Knitting" },
        { "Jane", "White", "Speed reading" },
        { "Joe", "Brown", "Pool" }
    };
  }
}
