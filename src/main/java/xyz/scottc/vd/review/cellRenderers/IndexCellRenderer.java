package xyz.scottc.vd.review.cellRenderers;

import xyz.scottc.vd.core.Input;

import javax.swing.*;
import java.awt.*;

public class IndexCellRenderer extends UniversalCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Input.InputState correct = (Input.InputState) table.getValueAt(row, 4);
        switch (correct) {
            case CORRECT:
                this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, Input.CORRECT_COLOR));
                break;
            case INCORRECT:
                this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, Input.INCORRECT_COLOR));
                break;
            case UNSURE:
                this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, Input.UNSURE_COLOR));
                break;
            case NOT_ANSWERED:
                this.setBorder(BorderFactory.createEmptyBorder());
                break;
        }
        return this;
    }
}
