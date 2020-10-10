package xyz.scottc.vd.review.cellRenderers;

import xyz.scottc.vd.core.Input;
import xyz.scottc.vd.utils.VDConstants;

import javax.swing.*;
import java.awt.*;

public class StandardAnswerCellRenderer extends UniversalCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Input.InputState correct = (Input.InputState) table.getValueAt(row, 4);
        switch (correct) {
            case CORRECT:
            case INCORRECT:
            case UNSURE:
                this.label.setText(value.toString());
                break;
            case NOT_ANSWERED:
                this.label.setText(VDConstants.EMPTY);
                break;
        }

        return this;
    }
}
