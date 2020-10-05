package xyz.scottc.frames.commonModeFrame.dialog.review.cellRenderers;

import xyz.scottc.VocabularyState;
import xyz.scottc.utils.VDConstantsUtils;

import javax.swing.*;
import java.awt.*;

public class StandardAnswerCellRenderer extends UniversalCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        VocabularyState correct = (VocabularyState) table.getValueAt(row, 4);
        switch (correct) {
            case CORRECT:
            case INCORRECT:
            case ANSWERED:
                this.label.setText(value.toString());
                break;
            case NOT_ANSWERED:
                this.label.setText(VDConstantsUtils.EMPTY);
                break;
        }

        return this;
    }
}
