package xyz.scottc.frames.entryFrame;

import xyz.scottc.utils.*;
import xyz.scottc.utils.components.LineSeparator;
import xyz.scottc.utils.components.UtilJButton;
import xyz.scottc.utils.components.UtilJLabel;
import xyz.scottc.utils.components.UtilJPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EntryFrame extends JFrame {

    private final SpringLayout layout = new SpringLayout();
    private final UtilJPanel rootPanel = new UtilJPanel(this.layout);

    private final UtilJLabel titleLabel = new UtilJLabel(VDConstantsUtils.NAME, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_100);
    private final UtilJLabel introLabel = new UtilJLabel(ENText.MAIN_INTRODUCTION, VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_20);
    private final UtilJLabel aboutLabel = new UtilJLabel(VDConstantsUtils.ABOUT, VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_20);

    private final UtilJLabel modeSelectionLabel = new UtilJLabel(ENText.MODE_SELECTION, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_60);
    private final DefaultListModel<ModeListCell> modeListModel = new DefaultListModel<>();
    private final JList<ModeListCell> modeList = new JList<>(this.modeListModel);
    private final JScrollPane modeListScrollPane = new JScrollPane(this.modeList);

    private final UtilJButton confirmButton = new UtilJButton("Confirm", 60);

    private final LineSeparator separator01 = new LineSeparator(LineSeparator.VERTICAL, VDConstantsUtils.getSreenRectangle().height);
    private final LineSeparator separator02 = new LineSeparator(LineSeparator.HORIZONTAL, VDConstantsUtils.getSreenRectangle().width);
    private final LineSeparator separator03 = new LineSeparator(LineSeparator.HORIZONTAL, VDConstantsUtils.getSreenRectangle().width);

    private static final int MARGIN = 20;

    public EntryFrame(String title) throws HeadlessException {
        super(title);
        this.setContentPane(this.rootPanel);
        this.rootPanelHandler();
    }

    private void rootPanelHandler() {
        this.rootPanel.add(this.titleLabel);
        this.layout.putConstraint(SpringLayout.WEST, this.titleLabel, MARGIN, SpringLayout.WEST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.NORTH, this.titleLabel, MARGIN, SpringLayout.NORTH, this.rootPanel);

        this.rootPanel.add(this.aboutLabel);
        this.layout.putConstraint(SpringLayout.WEST, this.aboutLabel, MARGIN, SpringLayout.WEST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.SOUTH, this.aboutLabel, -MARGIN, SpringLayout.SOUTH, this.rootPanel);

        this.rootPanel.add(this.separator01);
        this.layout.putConstraint(SpringLayout.WEST, this.separator01, MARGIN, SpringLayout.EAST, this.titleLabel);

        this.rootPanel.add(this.separator02);
        this.layout.putConstraint(SpringLayout.NORTH, this.separator02, MARGIN, SpringLayout.SOUTH, this.titleLabel);
        this.layout.putConstraint(SpringLayout.EAST, this.separator02, 0, SpringLayout.WEST, this.separator01);
        this.separator02.setBorder(new LineBorder(Color.BLACK, 20));

        this.rootPanel.add(this.separator03);
        this.layout.putConstraint(SpringLayout.SOUTH, this.separator03, -MARGIN, SpringLayout.NORTH, this.aboutLabel);
        this.layout.putConstraint(SpringLayout.EAST, this.separator03, 0, SpringLayout.WEST, this.separator01);

        this.rootPanel.add(this.introLabel);
        this.layout.putConstraint(SpringLayout.NORTH, this.introLabel, MARGIN, SpringLayout.SOUTH, this.separator02);
        this.layout.putConstraint(SpringLayout.SOUTH, this.introLabel, -MARGIN, SpringLayout.NORTH, this.separator03);
        this.layout.putConstraint(SpringLayout.WEST, this.introLabel, MARGIN, SpringLayout.WEST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.EAST, this.introLabel, -MARGIN, SpringLayout.WEST, this.separator01);

        this.rootPanel.add(this.modeSelectionLabel);
        this.modeSelectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.layout.putConstraint(SpringLayout.WEST, this.modeSelectionLabel, MARGIN, SpringLayout.EAST, this.separator01);
        this.layout.putConstraint(SpringLayout.NORTH, this.modeSelectionLabel, MARGIN, SpringLayout.NORTH, this.rootPanel);

        this.rootPanel.add(this.confirmButton);
        this.layout.putConstraint(SpringLayout.WEST, this.confirmButton, MARGIN, SpringLayout.EAST, this.separator01);
        this.layout.putConstraint(SpringLayout.SOUTH, this.confirmButton, -MARGIN, SpringLayout.SOUTH, this.rootPanel);
        this.layout.putConstraint(SpringLayout.EAST, this.confirmButton, -MARGIN, SpringLayout.EAST, this.rootPanel);

        this.rootPanel.add(this.modeListScrollPane);
        this.modeList.setBackground(this.rootPanel.getBackground());
        this.modeList.setCellRenderer(new ModeListCellRenderer());
        ModeListCell cell01 = new ModeListCell(ENText.ORDERED_MODE_NAME, ENText.ORDERED_MODE_DESCRIPTION);
        this.modeListModel.addElement(cell01);
        this.layout.putConstraint(SpringLayout.WEST, this.modeListScrollPane, MARGIN, SpringLayout.EAST, this.separator01);
        this.layout.putConstraint(SpringLayout.NORTH, this.modeListScrollPane, MARGIN, SpringLayout.SOUTH, this.modeSelectionLabel);
        this.layout.putConstraint(SpringLayout.EAST, this.modeListScrollPane, -MARGIN, SpringLayout.EAST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.SOUTH, this.modeListScrollPane, -MARGIN, SpringLayout.NORTH, this.confirmButton);
    }

}
