package xyz.scottc.frames.commonModeFrame.dialog.review.cellRenderers;

import xyz.scottc.VocabularyState;

import javax.swing.*;
import java.awt.*;

public class CorrectCellRenderer extends UniversalCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        VocabularyState correct = (VocabularyState) value;
        switch (correct) {
            case CORRECT:
                this.label.setText("Correct");
                this.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 2, new Color(0x00A74A)));
                break;
            case INCORRECT:
                this.label.setText("Incorrect");
                this.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 2, new Color(0xFE4365)));
                break;
            case EMPTY:
                this.label.setText("Not Answer");
                this.setBorder(BorderFactory.createEmptyBorder());
                break;
        }
        return this;
    }
}
