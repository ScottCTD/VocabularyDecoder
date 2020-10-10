package xyz.scottc.vd.frames.transitional.listSelection;

import xyz.scottc.vd.Main;
import xyz.scottc.vd.core.VDList;
import xyz.scottc.vd.frames.functional.orderedMode.OrderedMode;
import xyz.scottc.vd.frames.transitional.Entry;
import xyz.scottc.vd.frames.transitional.TransitionalFrame;
import xyz.scottc.vd.utils.ENText;
import xyz.scottc.vd.utils.FileUtils;
import xyz.scottc.vd.utils.VDConstants;
import xyz.scottc.vd.utils.VDUtils;
import xyz.scottc.vd.utils.components.LineSeparator;
import xyz.scottc.vd.utils.components.UtilJButton;
import xyz.scottc.vd.utils.components.UtilJLabel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ListSelection extends TransitionalFrame {

    private final UtilJButton backButton = new UtilJButton("Back", VDConstants.MICROSOFT_YAHEI_BOLD_40);
    private final UtilJButton importButton = new UtilJButton("Import", VDConstants.MICROSOFT_YAHEI_BOLD_30);
    private final UtilJButton addFolderButton = new UtilJButton("Add New Folder", VDConstants.MICROSOFT_YAHEI_BOLD_30);
    private final UtilJButton deleteButton = new UtilJButton("Delete", VDConstants.MICROSOFT_YAHEI_BOLD_30);

    private final UtilJLabel inListLabel = new UtilJLabel(ENText.INTERNAL_VD_LISTS, VDConstants.MICROSOFT_YAHEI_BOLD_60);
    private final DefaultMutableTreeNode inListRoot = new DefaultMutableTreeNode(ENText.INTERNAL_VD_LISTS);
    private final JTree inList = new JTree(this.inListRoot);
    private final JScrollPane inListView = new JScrollPane(this.inList);

    private final UtilJLabel exListLabel = new UtilJLabel(ENText.EXTERNAL_VD_LISTS, VDConstants.MICROSOFT_YAHEI_BOLD_60);
    private final DefaultMutableTreeNode exListRoot = new DefaultMutableTreeNode(ENText.EXTERNAL_VD_LISTS);
    private final JTree exList = new JTree(this.exListRoot);
    private final JScrollPane exListView = new JScrollPane(this.exList);

    private final LineSeparator separator04 = new LineSeparator(LineSeparator.VERTICAL, VDUtils.getScreenRectangle().height);

    private final LineSeparator lineHelper01 = new LineSeparator(LineSeparator.HORIZONTAL, VDUtils.getScreenRectangle().width);

    public ListSelection() throws HeadlessException {
        super("VD List Selection");
        this.rootPanelHandler();
        this.layoutHandler();
        this.treeHandler();
    }

    @Override
    protected void rootPanelHandler() {
        super.rootPanelHandler();

        super.titleLabel.setFont(VDConstants.MICROSOFT_YAHEI_BOLD_60);
        super.titleLabel.setText(ENText.LIST_SELECTION);
        super.introLabel.setText(ENText.LIST_SELECTION_INTRODUCTION);

        super.rootPanel.add(this.backButton);
        this.backButton.addActionListener(e -> VDUtils.switchFrame(this, new Entry()));

        TreeCellRenderer cellRenderer = new ListTreeCellRenderer();
        MouseListener mouseListener = new ListTreeMouseListener();
        super.rootPanel.add(this.inListLabel);

        super.rootPanel.add(this.inListView);
        this.inList.setRowHeight(40);
        this.inList.setCellRenderer(cellRenderer);
        this.inList.addMouseListener(mouseListener);

        super.rootPanel.add(exListLabel);

        super.rootPanel.add(this.exListView);
        this.exList.setRowHeight(40);
        this.exList.setCellRenderer(cellRenderer);
        this.exList.addMouseListener(mouseListener);

        super.rootPanel.add(this.importButton);

        super.rootPanel.add(this.addFolderButton);

        super.rootPanel.add(this.deleteButton);

        super.rootPanel.add(this.separator04);

        super.rootPanel.add(this.lineHelper01);
        this.lineHelper01.setVisible(false);
    }

    private void treeHandler() {
        for (File file : Main.INTERNAL_LISTS) {
            this.addNode(this.inListRoot, new VDList(file));
        }
        for (File file : Main.EXTERNAL_LISTS) {
            this.addNode(this.exListRoot, new VDList(file));
        }
    }

    private void addNode(DefaultMutableTreeNode root, VDList list) {
        //Split the type from ancestor to child
        List<String> types = list.splitType();
        //make a node array. Root Node is at the 0 of the array.
        DefaultMutableTreeNode[] nodes = new DefaultMutableTreeNode[types.size() + 1];
        nodes[0] = root;
        //Auto increment to add every branch to the node in proper sequence
        for (int i = 0; i < types.size(); i++) {
            for (int j = 0; j < nodes[i].getChildCount(); j++) {
                if (nodes[i].getChildAt(j).toString().equals(types.get(i))) {
                    nodes[i + 1] = (DefaultMutableTreeNode) nodes[i].getChildAt(j);
                    break;
                }
            }
            if (nodes[i + 1] == null) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(types.get(i));
                nodes[i].add(node);
                nodes[i + 1] = node;
            }
        }
        //add leaves
        nodes[types.size()].add(new DefaultMutableTreeNode(list.getName(), false));
    }

    @Override
    protected void layoutHandler() {
        super.layoutHandler();

        this.layout.putConstraint(SpringLayout.SOUTH, this.introLabel, -MARGIN, SpringLayout.NORTH, this.backButton);

        super.layout.putConstraint(SpringLayout.WEST, this.backButton, MARGIN, SpringLayout.WEST, super.rootPanel);
        super.layout.putConstraint(SpringLayout.SOUTH, this.backButton, -MARGIN, SpringLayout.NORTH, super.separator03);
        super.layout.putConstraint(SpringLayout.EAST, this.backButton, -MARGIN, SpringLayout.WEST, super.separator01);

        super.layout.putConstraint(SpringLayout.WEST, this.inListLabel, MARGIN, SpringLayout.EAST, super.separator01);
        super.layout.putConstraint(SpringLayout.NORTH, this.inListLabel, MARGIN, SpringLayout.NORTH, super.rootPanel);

        super.layout.putConstraint(SpringLayout.WEST, this.inListView, MARGIN, SpringLayout.EAST, super.separator01);
        super.layout.putConstraint(SpringLayout.NORTH, this.inListView, MARGIN, SpringLayout.SOUTH, this.inListLabel);
        super.layout.putConstraint(SpringLayout.SOUTH, this.inListView, -MARGIN, SpringLayout.SOUTH, super.rootPanel);
        super.layout.putConstraint(SpringLayout.EAST, this.inListView, -MARGIN, SpringLayout.WEST, this.separator04);

        super.layout.putConstraint(SpringLayout.NORTH, this.exListLabel, MARGIN, SpringLayout.NORTH, super.rootPanel);
        super.layout.putConstraint(SpringLayout.WEST, this.exListLabel, MARGIN, SpringLayout.EAST, this.separator04);

        super.layout.putConstraint(SpringLayout.NORTH, this.exListView, MARGIN, SpringLayout.SOUTH, this.exListLabel);
        super.layout.putConstraint(SpringLayout.WEST, this.exListView, 0, SpringLayout.WEST, this.exListLabel);
        super.layout.putConstraint(SpringLayout.EAST, this.exListView, -MARGIN, SpringLayout.EAST, super.rootPanel);
        super.layout.putConstraint(SpringLayout.SOUTH, this.exListView, -MARGIN, SpringLayout.NORTH, this.importButton);

        super.layout.putConstraint(SpringLayout.WEST, this.importButton, 0, SpringLayout.WEST, this.exListLabel);
        super.layout.putConstraint(SpringLayout.SOUTH, this.importButton, -MARGIN, SpringLayout.SOUTH, super.rootPanel);

        super.layout.putConstraint(SpringLayout.SOUTH, this.addFolderButton, 0, SpringLayout.SOUTH, this.importButton);
        super.layout.putConstraint(SpringLayout.WEST, this.addFolderButton, MARGIN, SpringLayout.EAST, this.importButton);

        super.layout.putConstraint(SpringLayout.SOUTH, this.deleteButton, 0, SpringLayout.SOUTH, this.importButton);
        super.layout.putConstraint(SpringLayout.WEST, this.deleteButton, MARGIN, SpringLayout.EAST, this.addFolderButton);
        super.layout.putConstraint(SpringLayout.EAST, this.deleteButton, 0, SpringLayout.EAST, this.exListView);

        super.layout.putConstraint(SpringLayout.WEST, this.separator04, 0, SpringLayout.HORIZONTAL_CENTER, this.lineHelper01);

        super.layout.putConstraint(SpringLayout.WEST, this.lineHelper01, 0, SpringLayout.EAST, super.separator02);
        super.layout.putConstraint(SpringLayout.NORTH, this.lineHelper01, 0, SpringLayout.NORTH, super.separator02);
        super.layout.putConstraint(SpringLayout.EAST, this.lineHelper01, 0, SpringLayout.EAST, super.rootPanel);
    }

    private class ListTreeMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2 && e.getSource() instanceof JTree) {
                TreePath treePath = inList.getPathForLocation(e.getX(), e.getY());
                if (treePath != null) {
                    Object[] paths = treePath.getPath();
                    String path = VDList.parsePaths(paths);
                    for (File file : Main.INTERNAL_LISTS) {
                        if (file.getAbsolutePath().endsWith(path)) {
                            VDList list = new VDList(file);
                            if (list.toQAList()) {
                                VDUtils.switchFrame(ListSelection.this, new OrderedMode(list));
                            } else {
                                VDUtils.showErrorMessage(ListSelection.this, "Failed to process!");
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private static class ListTreeCellRenderer extends DefaultTreeCellRenderer {

        public ListTreeCellRenderer() {
            try {
                Icon leafIcon = FileUtils.createImageIcon("/images/icons/leafNode.png");
                Icon branchIcon = FileUtils.createImageIcon("/images/icons/branchNode.png");
                this.setLeafIcon(leafIcon);
                this.setClosedIcon(branchIcon);
                this.setOpenIcon(branchIcon);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            this.setFont(VDConstants.MICROSOFT_YAHEI_BOLD_30.deriveFont(Font.PLAIN));
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            String text = value.toString();
            if (text.contains(".json")) {
                text = text.replace(".json", VDConstants.EMPTY);
            }
            this.setText(text);
            return this;
        }
    }

}