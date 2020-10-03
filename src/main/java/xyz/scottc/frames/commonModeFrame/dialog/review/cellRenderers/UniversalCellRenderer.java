package xyz.scottc.frames.commonModeFrame.dialog.review.cellRenderers;

import xyz.scottc.utils.UtilJLabel;
import xyz.scottc.utils.UtilJPanel;
import xyz.scottc.utils.VDConstantsUtils;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class UniversalCellRenderer extends UtilJPanel implements TableCellRenderer {

    public UtilJLabel label = new UtilJLabel(VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_20);

    public UniversalCellRenderer() {
        this.add(label, BorderLayout.CENTER);
        this.label.setHorizontalAlignment(UtilJLabel.CENTER);
        this.label.setVerticalAlignment(UtilJLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        this.label.setText(value.toString());

        this.setOpaque(true);
        if (isSelected) {
            this.setBackground(new Color(0xD9EBF9));
            this.setForeground(table.getSelectionForeground());
        } else {
            this.setBackground(Color.WHITE);
            this.setForeground(table.getForeground());
        }

        boolean correct = (boolean) table.getValueAt(row, 4);
        if (hasFocus) {
            this.setBackGround(new Color(0xBCDCF4));
        }
        if (correct) {
            this.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, new Color(0x00A74A)));
        } else {
            this.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, new Color(0xFE4365)));
        }
        return this;
    }
}
