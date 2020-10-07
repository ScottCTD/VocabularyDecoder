package xyz.scottc.frames.entryFrame;

import xyz.scottc.frames.ATransitionalFrame;
import xyz.scottc.utils.ENText;
import xyz.scottc.utils.VDConstantsUtils;
import xyz.scottc.utils.components.UtilJButton;
import xyz.scottc.utils.components.UtilJLabel;

import javax.swing.*;
import java.awt.*;

public class EntryFrame extends ATransitionalFrame {

    private final UtilJLabel modeSelectionLabel = new UtilJLabel(ENText.MODE_SELECTION, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_60);
    private final DefaultListModel<ModeListCell> modeListModel = new DefaultListModel<>();
    private final JList<ModeListCell> modeList = new JList<>(this.modeListModel);
    private final JScrollPane modeListScrollPane = new JScrollPane(this.modeList);
    private final UtilJButton confirmButton = new UtilJButton("Confirm", 60);

    public EntryFrame(String title) throws HeadlessException {
        super(title);
        this.rootPanelHandler();
    }

    protected void rootPanelHandler() {
        super.rootPanelHandler();

        super.titleLabel.setText(VDConstantsUtils.NAME);
        super.introLabel.setText(ENText.MAIN_INTRODUCTION);

        super.rootPanel.add(this.modeSelectionLabel);
        this.modeSelectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        super.layout.putConstraint(SpringLayout.WEST, this.modeSelectionLabel, MARGIN, SpringLayout.EAST, super.separator01);
        super.layout.putConstraint(SpringLayout.NORTH, this.modeSelectionLabel, MARGIN, SpringLayout.NORTH, super.rootPanel);

        super.rootPanel.add(this.confirmButton);
        super.layout.putConstraint(SpringLayout.WEST, this.confirmButton, MARGIN, SpringLayout.EAST, super.separator01);
        super.layout.putConstraint(SpringLayout.SOUTH, this.confirmButton, -MARGIN, SpringLayout.SOUTH, super.rootPanel);
        super.layout.putConstraint(SpringLayout.EAST, this.confirmButton, -MARGIN, SpringLayout.EAST, super.rootPanel);

        super.rootPanel.add(this.modeListScrollPane);
        this.modeList.setBackground(super.rootPanel.getBackground());
        this.modeList.setCellRenderer(new ModeListCellRenderer());
        ModeListCell cell01 = new ModeListCell(ENText.ORDERED_MODE_NAME, ENText.ORDERED_MODE_DESCRIPTION);
        this.modeListModel.addElement(cell01);
        super.layout.putConstraint(SpringLayout.WEST, this.modeListScrollPane, MARGIN, SpringLayout.EAST, super.separator01);
        super.layout.putConstraint(SpringLayout.NORTH, this.modeListScrollPane, MARGIN, SpringLayout.SOUTH, this.modeSelectionLabel);
        super.layout.putConstraint(SpringLayout.EAST, this.modeListScrollPane, -MARGIN, SpringLayout.EAST, super.rootPanel);
        super.layout.putConstraint(SpringLayout.SOUTH, this.modeListScrollPane, -MARGIN, SpringLayout.NORTH, this.confirmButton);
    }

}
