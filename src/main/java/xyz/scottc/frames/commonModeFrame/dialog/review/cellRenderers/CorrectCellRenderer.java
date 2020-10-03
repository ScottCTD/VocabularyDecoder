package xyz.scottc.frames.commonModeFrame.dialog.review.cellRenderers;

import javax.swing.*;
import java.awt.*;

public class CorrectCellRenderer extends UniversalCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        boolean correct = (boolean) value;
        if (correct) {
            this.label.setText("Correct");
            this.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 2, new Color(0x00A74A)));
        } else {
            this.label.setText("Incorrect");
            this.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 2, new Color(0xFE4365)));
        }
        return this;
    }
}
