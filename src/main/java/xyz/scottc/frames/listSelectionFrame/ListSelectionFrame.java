package xyz.scottc.frames.listSelectionFrame;

import xyz.scottc.frames.ATransitionalFrame;
import xyz.scottc.utils.ENText;
import xyz.scottc.utils.FileUtils;
import xyz.scottc.utils.VDConstantsUtils;
import xyz.scottc.utils.components.UtilJButton;
import xyz.scottc.utils.components.UtilJLabel;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.IOException;

public class ListSelectionFrame extends ATransitionalFrame {

    private final UtilJButton backButton = new UtilJButton("Back", 40);

    private final UtilJLabel inListLabel = new UtilJLabel(ENText.INTERNAL_VD_LISTS, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_40);
    private final JTree inList = new JTree();
    private final JScrollPane inListView = new JScrollPane(this.inList);

    private final UtilJLabel exListLabel = new UtilJLabel(ENText.EXTERNAL_VD_LISTS, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_40);
    private final JTree exList = new JTree();
    private final JScrollPane exListView = new JScrollPane(this.exList);

    public ListSelectionFrame(String title) throws HeadlessException {
        super(title);
        this.rootPanelHandler();
    }

    @Override
    protected void rootPanelHandler() {
        super.rootPanelHandler();

        super.titleLabel.setFont(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_60);
        super.titleLabel.setText(ENText.LIST_SELECTION);
        super.introLabel.setText(ENText.LIST_SELECTION_INTRODUCTION);

        super.rootPanel.add(this.backButton);
        super.layout.putConstraint(SpringLayout.WEST, this.backButton, MARGIN, SpringLayout.WEST, super.rootPanel);
        super.layout.putConstraint(SpringLayout.SOUTH, this.backButton, -MARGIN, SpringLayout.NORTH, super.separator03);

        DefaultTreeCellRenderer cellRenderer = new DefaultTreeCellRenderer();
        try {
            cellRenderer.setLeafIcon(new ImageIcon(FileUtils.readFromInputStream(
                    this.getClass().getResourceAsStream("/images/leafNode.png")
            )));
            Icon branchIcon = new ImageIcon(FileUtils.readFromInputStream(
                    this.getClass().getResourceAsStream("/images/branchNode.png")
            ));
            cellRenderer.setClosedIcon(branchIcon);
            cellRenderer.setOpenIcon(branchIcon);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        cellRenderer.setPreferredSize(new Dimension(300, 100));
        cellRenderer.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 30));
        super.rootPanel.add(this.inListLabel);
        super.layout.putConstraint(SpringLayout.WEST, this.inListLabel, MARGIN, SpringLayout.EAST, super.separator01);
        super.layout.putConstraint(SpringLayout.NORTH, this.inListLabel, MARGIN, SpringLayout.NORTH, super.rootPanel);

        super.rootPanel.add(this.inListView);
        this.inList.setCellRenderer(cellRenderer);
        super.layout.putConstraint(SpringLayout.WEST, this.inListView, MARGIN, SpringLayout.EAST, super.separator01);
        super.layout.putConstraint(SpringLayout.NORTH, this.inListView, MARGIN, SpringLayout.SOUTH, this.inListLabel);
        super.layout.putConstraint(SpringLayout.SOUTH, this.inListView, -MARGIN, SpringLayout.SOUTH, super.rootPanel);
        super.layout.putConstraint(SpringLayout.EAST, this.inListView, 0, SpringLayout.EAST, this.inListLabel);

        super.rootPanel.add(this.exListView);
    }

}