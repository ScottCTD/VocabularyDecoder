package xyz.scottc.frames.mainFrame;

import xyz.scottc.utils.*;

import javax.swing.*;
import java.awt.*;

public class ModeListCellRenderer extends UtilJPanel implements ListCellRenderer {

    private final UtilJCheckBox checkBox = new UtilJCheckBox();
    private final UtilJLabel nameLabel = new UtilJLabel(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_40);

    private final VDTextArea description = new VDTextArea(3, 5,
            VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_20, false, false);

    public ModeListCellRenderer() {
        this.setPreferredSize(new Dimension(400, 200));
        this.setLayout(new AfColumnLayout());

        UtilJPanel topPanel = new UtilJPanel(new AfRowLayout());
        this.add(topPanel, "1w");
        topPanel.add(checkBox, "20px");
        topPanel.add(this.nameLabel, "1w");
        this.nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(this.description, "2w");
        this.description.setAlignmentY(CENTER_ALIGNMENT);
        this.description.setLineWrap(true);
        this.description.setBorder(BorderFactory.createLineBorder(new Color(0x828790)));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        this.checkBox.setSelected(isSelected);
        ModeListCell cell = (ModeListCell) value;
        this.nameLabel.setText(cell.getName());
        this.description.setText(cell.getDescription());

        this.setOpaque(true);

        if (isSelected) {
            this.setBackGround(new Color(0xCCCCCC));
            this.setForeground(list.getSelectionForeground());
            this.checkBox.setBackground(new Color(0xCCCCCC));
            this.checkBox.setForeground(list.getSelectionForeground());
            this.description.setBackground(new Color(0xCCCCCC));
            this.description.setForeground(list.getSelectionForeground());
        } else {
            this.setBackground(list.getBackground());
            this.setForeground(list.getForeground());
            this.checkBox.setBackground(list.getBackground());
            this.checkBox.setForeground(list.getForeground());
            this.description.setBackground(list.getBackground());
            this.description.setForeground(list.getForeground());
        }

        return this;
    }
}
