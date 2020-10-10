package xyz.scottc.vd.frames.transitional;

import xyz.scottc.vd.core.Mode;
import xyz.scottc.vd.utils.ENText;
import xyz.scottc.vd.utils.VDConstants;
import xyz.scottc.vd.utils.VDUtils;
import xyz.scottc.vd.utils.components.UtilJButton;
import xyz.scottc.vd.utils.components.UtilJLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Entry extends TransitionalFrame {

    private final UtilJLabel modeSelectionLabel = new UtilJLabel(ENText.MODE_SELECTION, VDConstants.MICROSOFT_YAHEI_BOLD_60);
    private final DefaultListModel<Mode> modeListModel = new DefaultListModel<>();
    private final JList<Mode> modeList = new JList<>(this.modeListModel);
    private final JScrollPane modeListScrollPane = new JScrollPane(this.modeList);
    private final UtilJButton confirmButton = new UtilJButton("Confirm", VDConstants.MICROSOFT_YAHEI_BOLD_60);

    public Entry() throws HeadlessException {
        super("VD Entry");
        this.rootPanelHandler();
        this.layoutHandler();
    }

    @Override
    protected void rootPanelHandler() {
        super.rootPanelHandler();

        super.titleLabel.setText(VDConstants.NAME);
        super.introLabel.setText(ENText.MAIN_INTRODUCTION);

        super.rootPanel.add(this.modeSelectionLabel);
        this.modeSelectionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        super.rootPanel.add(this.confirmButton);
        this.confirmButton.addActionListener(e -> {
            Mode.setSelectedMode(modeList.getSelectedValue());
            if (Mode.getSelectedMode() != null) {
                VDUtils.switchFrame(this, new ListSelection());
            }
        });

        super.rootPanel.add(this.modeListScrollPane);
        this.modeList.setBackground(super.rootPanel.getBackground());
        this.modeList.setCellRenderer(new ModeListCellRenderer());
        this.modeListHandler();
        this.modeList.addMouseListener(new SelectModeMouseListener());
    }

    private void modeListHandler() {
        Mode mode01 = new Mode(ENText.ORDERED_MODE_NAME, ENText.ORDERED_MODE_DESCRIPTION);
        this.addMode(mode01);
    }

    private void addMode(Mode mode) {
        this.modeListModel.addElement(mode);
        Mode.MODE_LIST.add(mode);
    }

    private class SelectModeMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 &&
                    e.getClickCount() == 2 &&
                    e.getSource() instanceof JList) {
                Mode.setSelectedMode(modeList.getSelectedValue());
                VDUtils.switchFrame(Entry.this, new ListSelection());
            }
        }
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
