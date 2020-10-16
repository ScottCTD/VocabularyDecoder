package xyz.scottc.vd.review.cellRenderers;

import xyz.scottc.vd.core.Input;
import xyz.scottc.vd.utils.VDConstants;
import xyz.scottc.vd.utils.components.UtilJLabel;
import xyz.scottc.vd.utils.components.UtilJPanel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class UniversalCellRenderer extends UtilJPanel implements TableCellRenderer {

    public UtilJLabel label = new UtilJLabel(VDConstants.MICROSOFT_YAHEI_PLAIN_20);

    public UniversalCellRenderer() {
        this.label.setFont(VDConstants.MICROSOFT_YAHEI_PLAIN_20);
        this.add(label, BorderLayout.CENTER);
        this.label.setHorizontalAlignment(UtilJLabel.CENTER);
        this.label.setVerticalAlignment(UtilJLabel.CENTER);
        this.label.setBackground(Color.WHITE);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        this.label.setText(value.toString());

        this.setOpaque(true);
        if (isSelected) {
            this.setBackground(new Color(0xD9EBF9));
            this.label.setBackground(new Color(0xD9EBF9));
            this.setForeground(table.getSelectionForeground());
        } else {
            this.setBackground(Color.WHITE);
            this.label.setBackground(Color.WHITE);
            this.setForeground(table.getForeground());
        }

        Input.InputState correct = (Input.InputState) table.getValueAt(row, 4);
        if (hasFocus) {
            this.setBackGround(new Color(0xBCDCF4));
            this.label.setBackground(new Color(0xBCDCF4));
        }
        switch (correct) {
            case CORRECT:
                this.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Input.CORRECT_COLOR));
                break;
            case INCORRECT:
                this.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Input.INCORRECT_COLOR));
                break;
            case UNSURE:
                this.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Input.UNSURE_COLOR));
                break;
            case NOT_ANSWERED:
                this.setBorder(BorderFactory.createEmptyBorder());
                break;
        }
        return this;
    }
}
