package xyz.scottc.vd.frames.transitional;

import xyz.scottc.vd.utils.ENText;
import xyz.scottc.vd.utils.VDConstantsUtils;
import xyz.scottc.vd.utils.components.UtilJButton;
import xyz.scottc.vd.utils.components.UtilJLabel;

import javax.swing.*;
import java.awt.*;

public class EntryFrame extends TransitionalFrame {

    private final UtilJLabel modeSelectionLabel = new UtilJLabel(ENText.MODE_SELECTION, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_60);
    private final DefaultListModel<ModeListCell> modeListModel = new DefaultListModel<>();
    private final JList<ModeListCell> modeList = new JList<>(this.modeListModel);
    private final JScrollPane modeListScrollPane = new JScrollPane(this.modeList);
    private final UtilJButton confirmButton = new UtilJButton("Confirm", 60);

    public EntryFrame(String title) throws HeadlessException {
        super(title);
        this.rootPanelHandler();
        this.layoutHandler();
    }

    protected void rootPanelHandler() {
        super.rootPanelHandler();

        super.titleLabel.setText(VDConstantsUtils.NAME);
        super.introLabel.setText(ENText.MAIN_INTRODUCTION);

        super.rootPanel.add(this.modeSelectionLabel);
        this.modeSelectionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        super.rootPanel.add(this.confirmButton);

        super.rootPanel.add(this.modeListScrollPane);
        this.modeList.setBackground(super.rootPanel.getBackground());
        this.modeList.setCellRenderer(new ModeListCellRenderer());
        ModeListCell cell01 = new ModeListCell(ENText.ORDERED_MODE_NAME, ENText.ORDERED_MODE_DESCRIPTION);
        this.modeListModel.addElement(cell01);
    }

    @Override
    protected void layoutHandler() {
        super.layoutHandler();

        super.layout.putConstraint(SpringLayout.WEST, this.modeSelectionLabel, MARGIN, SpringLayout.EAST, super.separator01);
        super.layout.putConstraint(SpringLayout.NORTH, this.modeSelectionLabel, MARGIN, SpringLayout.NORTH, super.rootPanel);

        super.layout.putConstraint(SpringLayout.WEST, this.confirmButton, MARGIN, SpringLayout.EAST, super.separator01);
        super.layout.putConstraint(SpringLayout.SOUTH, this.confirmButton, -MARGIN, SpringLayout.SOUTH, super.rootPanel);
        super.layout.putConstraint(SpringLayout.EAST, this.confirmButton, -MARGIN, SpringLayout.EAST, super.rootPanel);

        super.layout.putConstraint(SpringLayout.WEST, this.modeListScrollPane, MARGIN, SpringLayout.EAST, super.separator01);
        super.layout.putConstraint(SpringLayout.NORTH, this.modeListScrollPane, MARGIN, SpringLayout.SOUTH, this.modeSelectionLabel);
        super.layout.putConstraint(SpringLayout.EAST, this.modeListScrollPane, -MARGIN, SpringLayout.EAST, super.rootPanel);
        super.layout.putConstraint(SpringLayout.SOUTH, this.modeListScrollPane, -MARGIN, SpringLayout.NORTH, this.confirmButton);
    }

}
