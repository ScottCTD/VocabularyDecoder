package xyz.scottc.frames.commonModeFrame.dialog.review.cellRenderers;

import xyz.scottc.VocabularyState;

import javax.swing.*;
import java.awt.*;

public class NumCellRenderer extends UniversalCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        VocabularyState correct = (VocabularyState) table.getValueAt(row, 4);
        switch (correct) {
            case CORRECT:
                this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, new Color(0x00A74A)));
                break;
            case INCORRECT:
                this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, new Color(0xFE4365)));
                break;
            case EMPTY:
                this.setBorder(BorderFactory.createEmptyBorder());
                break;
        }
        return this;
    }
}
