package xyz.scottc.frames.commonModeFrame.dialog.review.cellRenderers;

import javax.swing.*;
import java.awt.*;

public class NumCellRenderer extends UniversalCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        boolean correct = (boolean) table.getValueAt(row, 4);
        if (correct) {
            this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, new Color(0x00A74A)));
        } else {
            this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, new Color(0xFE4365)));
        }
        return this;
    }
}
