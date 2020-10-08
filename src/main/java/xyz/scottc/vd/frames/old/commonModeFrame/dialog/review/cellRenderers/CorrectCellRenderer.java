package xyz.scottc.vd.frames.old.commonModeFrame.dialog.review.cellRenderers;

import xyz.scottc.vd.VocabularyState;

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
            case NOT_ANSWERED:
                this.label.setText("Not Answer");
                this.setBorder(BorderFactory.createEmptyBorder());
                break;
            case ANSWERED:
                this.label.setText("Answer");
                this.setBorder(BorderFactory.createEmptyBorder());
                break;

        }
        return this;
    }
}
