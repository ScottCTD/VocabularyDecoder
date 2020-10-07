package xyz.scottc.frames.listSelectionFrame;

import xyz.scottc.frames.TransitionalFrame;
import xyz.scottc.utils.ENText;
import xyz.scottc.utils.FileUtils;
import xyz.scottc.utils.VDConstantsUtils;
import xyz.scottc.utils.components.LineSeparator;
import xyz.scottc.utils.components.UtilJButton;
import xyz.scottc.utils.components.UtilJLabel;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.io.IOException;

public class ListSelectionFrame extends TransitionalFrame {

    private final UtilJButton backButton = new UtilJButton("Back", 40);

    private final UtilJLabel inListLabel = new UtilJLabel(ENText.INTERNAL_VD_LISTS, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_60);
    private final JTree inList = new JTree();
    private final JScrollPane inListView = new JScrollPane(this.inList);

    private final UtilJLabel exListLabel = new UtilJLabel(ENText.EXTERNAL_VD_LISTS, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_60);
    private final JTree exList = new JTree();
    private final JScrollPane exListView = new JScrollPane(this.exList);

    private final LineSeparator separator04 = new LineSeparator(LineSeparator.VERTICAL, VDConstantsUtils.getSreenRectangle().height);

    private final LineSeparator lineHelper01 = new LineSeparator(LineSeparator.HORIZONTAL, VDConstantsUtils.getSreenRectangle().width);

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

        TreeCellRenderer cellRenderer = new ListTreeCellRenderer();

        super.rootPanel.add(this.inListLabel);
        super.layout.putConstraint(SpringLayout.WEST, this.inListLabel, MARGIN, SpringLayout.EAST, super.separator01);
        super.layout.putConstraint(SpringLayout.NORTH, this.inListLabel, MARGIN, SpringLayout.NORTH, super.rootPanel);

        super.rootPanel.add(this.inListView);
        this.inList.setRowHeight(40);
        this.inList.setCellRenderer(cellRenderer);
        super.layout.putConstraint(SpringLayout.WEST, this.inListView, MARGIN, SpringLayout.EAST, super.separator01);
        super.layout.putConstraint(SpringLayout.NORTH, this.inListView, MARGIN, SpringLayout.SOUTH, this.inListLabel);
        super.layout.putConstraint(SpringLayout.SOUTH, this.inListView, -MARGIN, SpringLayout.SOUTH, super.rootPanel);
        super.layout.putConstraint(SpringLayout.EAST, this.inListView, -MARGIN, SpringLayout.WEST, this.separator04);


        super.rootPanel.add(exListLabel);
        super.layout.putConstraint(SpringLayout.NORTH, this.exListLabel, MARGIN, SpringLayout.NORTH, super.rootPanel);
        super.layout.putConstraint(SpringLayout.WEST, this.exListLabel, MARGIN, SpringLayout.EAST, this.separator04);

        super.rootPanel.add(this.exListView);
        this.exList.setRowHeight(40);
        this.exList.setCellRenderer(cellRenderer);
        super.layout.putConstraint(SpringLayout.NORTH, this.exListView, MARGIN, SpringLayout.SOUTH, this.exListLabel);
        super.layout.putConstraint(SpringLayout.WEST, this.exListView, 0, SpringLayout.WEST, this.exListLabel);
        super.layout.putConstraint(SpringLayout.EAST, this.exListView, -MARGIN, SpringLayout.EAST, super.rootPanel);

        super.rootPanel.add(this.separator04);
        super.layout.putConstraint(SpringLayout.WEST, this.separator04, 0, SpringLayout.HORIZONTAL_CENTER, this.lineHelper01);

        super.rootPanel.add(this.lineHelper01);
        this.lineHelper01.setVisible(false);
        super.layout.putConstraint(SpringLayout.WEST, this.lineHelper01, 0, SpringLayout.EAST, super.separator02);
        super.layout.putConstraint(SpringLayout.NORTH, this.lineHelper01, 0, SpringLayout.NORTH, super.separator02);
        super.layout.putConstraint(SpringLayout.EAST, this.lineHelper01, 0, SpringLayout.EAST, super.rootPanel);
    }

    private static class ListTreeCellRenderer extends DefaultTreeCellRenderer {

        public ListTreeCellRenderer() {
            try {
                Icon leafIcon = new ImageIcon(FileUtils.readFromInputStream(
                        this.getClass().getResourceAsStream("/images/icons/leafNode.png")
                ));
                Icon branchIcon = new ImageIcon(FileUtils.readFromInputStream(
                        this.getClass().getResourceAsStream("/images/icons/branchNode.png")
                ));
                this.setLeafIcon(leafIcon);
                this.setClosedIcon(branchIcon);
                this.setOpenIcon(branchIcon);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            this.setFont(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30.deriveFont(Font.PLAIN));
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            return this;
        }
    }

}