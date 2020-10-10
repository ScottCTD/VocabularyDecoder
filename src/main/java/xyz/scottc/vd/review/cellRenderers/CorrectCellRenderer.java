package xyz.scottc.vd.review.cellRenderers;

import xyz.scottc.vd.core.Input;

import javax.swing.*;
import java.awt.*;

public class CorrectCellRenderer extends UniversalCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Input.InputState correct = (Input.InputState) value;
        switch (correct) {
            case CORRECT:
                this.label.setText("Correct");
                this.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 2, Input.CORRECT_COLOR));
                break;
            case INCORRECT:
                this.label.setText("Incorrect");
                this.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 2, Input.INCORRECT_COLOR));
                break;
            case NOT_ANSWERED:
                this.label.setText("Not Answered");
                this.setBorder(BorderFactory.createEmptyBorder());
                break;
            case UNSURE:
                this.label.setText("Unsure");
                this.setBorder(BorderFactory.createEmptyBorder());
                break;

        }
        return this;
    }
}
