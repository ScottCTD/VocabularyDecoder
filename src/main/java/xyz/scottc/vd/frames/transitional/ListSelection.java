package xyz.scottc.vd.frames.transitional;

import xyz.scottc.vd.Main;
import xyz.scottc.vd.core.Mode;
import xyz.scottc.vd.core.VDList;
import xyz.scottc.vd.exceptions.FileDeletingException;
import xyz.scottc.vd.frames.functional.ExamMode;
import xyz.scottc.vd.frames.functional.OrderedMode;
import xyz.scottc.vd.utils.ENText;
import xyz.scottc.vd.utils.FileUtils;
import xyz.scottc.vd.utils.VDConstants;
import xyz.scottc.vd.utils.VDUtils;
import xyz.scottc.vd.utils.components.*;
import xyz.scottc.vd.utils.dialogs.CreateVDFileByInputDialog;
import xyz.scottc.vd.utils.dialogs.VDConfirmDialog;
import xyz.scottc.vd.utils.dialogs.VDErrorDialog;
import xyz.scottc.vd.utils.dialogs.VDFileConverter01Dialog;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ListSelection extends TransitionalFrame {

    private final UtilJButton backButton = new UtilJButton("Back", VDConstants.MICROSOFT_YAHEI_BOLD_40);
    private final UtilJButton importButton = new UtilJButton("Import", VDConstants.MICROSOFT_YAHEI_BOLD_32);
    private final UtilJButton deleteButton = new UtilJButton("Delete", VDConstants.MICROSOFT_YAHEI_BOLD_32);

    private final JMenuBar menuBar = new JMenuBar();
    private final VDMenu toolsMenu = new VDMenu("Tools");
    private final VDMenuItem createVDFile01MenuItem = new VDMenuItem("Create VD File by .txt");
    private final VDMenuItem createVDFile02MenuItem = new VDMenuItem("Create VD File by Input");


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
        this.menuHandler();
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
        this.inList.setRowHeight(50);
        this.inList.setCellRenderer(cellRenderer);
        this.inList.addMouseListener(mouseListener);

        super.rootPanel.add(exListLabel);

        super.rootPanel.add(this.exListView);
        this.exList.setRowHeight(50);
        this.exList.setCellRenderer(cellRenderer);
        this.exList.addMouseListener(mouseListener);

        super.rootPanel.add(this.importButton);
        this.importButton.addActionListener(this.importListener);

        super.rootPanel.add(this.deleteButton);
        this.deleteButton.addActionListener(this.deleteListener);

        super.rootPanel.add(this.separator04);

        super.rootPanel.add(this.lineHelper01);
        this.lineHelper01.setVisible(false);
    }

    private void menuHandler() {
        this.setJMenuBar(this.menuBar);

        this.menuBar.add(this.toolsMenu);
        this.toolsMenu.add(this.createVDFile01MenuItem);
        this.createVDFile01MenuItem.addActionListener(e -> VDFileConverter01Dialog.show(this));
        this.toolsMenu.add(this.createVDFile02MenuItem);
        this.createVDFile02MenuItem.addActionListener(e -> {
            CreateVDFileByInputDialog dialog = new CreateVDFileByInputDialog(ListSelection.this);
            dialog.setVisible(true);
        });

    }

    private void treeHandler() {
        try {
            this.updateInternalLists();
            this.updateExternalLists();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void updateExternalLists() throws IOException {
        this.exListRoot.removeAllChildren();
        Main.updateExternalFiles();
        for (File file : Main.EXTERNAL_LISTS) {
            this.addNode(this.exListRoot, new VDList(file));
        }
        DefaultTreeModel model = (DefaultTreeModel) exList.getModel();
        model.reload();
    }

    private void updateInternalLists() throws IOException {
        Main.updateInternalFiles();
        for (File file : Main.INTERNAL_LISTS) {
            this.addNode(this.inListRoot, new VDList(file));
        }
        DefaultTreeModel model = (DefaultTreeModel) inList.getModel();
        model.reload();
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
        if (!list.getName().equals(VDConstants.EMPTY)) {
            //add leaves
            nodes[types.size()].add(new DefaultMutableTreeNode(list.getName()));
        }
    }

    private final ActionListener importListener = e -> {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter(VDConstants.FILE_TYPE, "vd"));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setMultiSelectionEnabled(true);
            int result = fileChooser.showSaveDialog(ListSelection.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File[] selectedFiles = fileChooser.getSelectedFiles();
                File sourceDir = fileChooser.getCurrentDirectory();
                for (File file : selectedFiles) {
                    if (file.isDirectory()) {
                        try {
                            Files.walk(file.toPath()).filter(Files::isDirectory).forEach(path -> {
                                File sourceDir2 = path.toFile();
                                String targetDirName = sourceDir2.getAbsolutePath().substring(sourceDir.getAbsolutePath().length());
                                String targetDirPath = Main.externalLibrary.getAbsolutePath() + "\\" + targetDirName;
                                try {
                                    Files.createDirectory(Paths.get(targetDirPath));
                                } catch (FileAlreadyExistsException ignored) {

                                } catch (IOException exception) {
                                    exception.printStackTrace();
                                }
                            });
                            Files.walk(file.toPath()).filter(Files::isRegularFile).forEach(path -> {
                                File sourceFile = path.toFile();
                                String targetFileName = sourceFile.getAbsolutePath().substring(sourceDir.getAbsolutePath().length());
                                String targetFilePath = Main.externalLibrary.getAbsolutePath() + "\\" + targetFileName;
                                try {
                                    Files.copy(sourceFile.toPath(), Paths.get(targetFilePath), StandardCopyOption.REPLACE_EXISTING);
                                } catch (IOException exception) {
                                    exception.printStackTrace();
                                }
                            });
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                // Create the dirs first, avoiding potential problems, although performance consuming
                for (File file : selectedFiles) {
                    if (file.isFile()) {
                        try {
                            String targetFileName = file.getAbsolutePath().substring(sourceDir.getAbsolutePath().length());
                            String targetFilePath = Main.externalLibrary.getAbsolutePath() + "\\" + targetFileName;
                            Files.copy(file.toPath(), Paths.get(targetFilePath));
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                try {
                    Main.updateExternalFiles();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                try {
                    this.updateExternalLists();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            VDErrorDialog.show(this, "<html>Fail to import customized VD<br>list(s)!</html>",
                    VDConstants.MICROSOFT_YAHEI_BOLD_32);
        }
    };

    private final ActionListener deleteListener = e -> {
        try {
            TreePath[] treePaths = this.exList.getSelectionPaths();
            if (treePaths != null) {
                boolean confirm = false;
                int result;
                for (TreePath treePath : treePaths) {
                    File file = this.getFileFromTreePath(treePath, Main.EXTERNAL_LISTS);
                    if (!confirm) {
                        result = VDConfirmDialog.show(this, "Delete Selected File",
                                "Do you really want to delete this file?", VDConstants.MICROSOFT_YAHEI_PLAIN_20);
                        if (result == VDConfirmDialog.CONFIRM) {
                            confirm = true;
                        }
                    }
                    if (confirm && file != null) {
                        try {
                            if (!file.delete()) throw new FileDeletingException(file);
                        } catch (FileDeletingException exception) {
                            exception.printStackTrace();
                            return;
                        }
                        //delete the item in the tree
                        this.exList.removeSelectionPath(treePath);
                        //update
                        try {
                            this.updateExternalLists();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            VDErrorDialog.show(this, "<html>Fail to delete selected VD<br>list(s)!</html>",
                    VDConstants.MICROSOFT_YAHEI_BOLD_32);
        }
    };

    /**
     * @param treePath required TreePath
     * @param fileList internalLibrary or ExternalLibrary
     * @return a corresponding file in the fileList, or null if the fileList does not
     * contain that file
     */
    private File getFileFromTreePath(TreePath treePath, List<File> fileList) {
        Object[] paths = treePath.getPath();
        String path = VDList.parsePaths(paths);
        if (path != null) {
            for (File file : fileList) {
                if (file.getAbsolutePath().endsWith(path)) {
                    return file;
                }
            }
        }
        return null;
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
        super.layout.putConstraint(SpringLayout.EAST, this.importButton, -(MARGIN / 2), SpringLayout.HORIZONTAL_CENTER, this.exListView);

        super.layout.putConstraint(SpringLayout.SOUTH, this.deleteButton, 0, SpringLayout.SOUTH, this.importButton);
        super.layout.putConstraint(SpringLayout.WEST, this.deleteButton, MARGIN / 2, SpringLayout.HORIZONTAL_CENTER, this.exListView);
        super.layout.putConstraint(SpringLayout.EAST, this.deleteButton, 0, SpringLayout.EAST, this.exListView);

        super.layout.putConstraint(SpringLayout.WEST, this.separator04, 0, SpringLayout.HORIZONTAL_CENTER, this.lineHelper01);

        super.layout.putConstraint(SpringLayout.WEST, this.lineHelper01, 0, SpringLayout.EAST, super.separator02);
        super.layout.putConstraint(SpringLayout.NORTH, this.lineHelper01, 0, SpringLayout.NORTH, super.separator02);
        super.layout.putConstraint(SpringLayout.EAST, this.lineHelper01, 0, SpringLayout.EAST, super.rootPanel);
    }

    private class ListTreeMouseListener extends MouseAdapter {

        private void switchFrame(MouseEvent e, JTree tree, List<File> fileList) {
            TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());
            if (treePath != null) {
                File file = getFileFromTreePath(treePath, fileList);
                if (file != null) {
                    VDList list = new VDList(file);
                    if (list.toQAList()) {
                        switch (Mode.getSelectedMode().toString()) {
                            case VDConstants.ORDERED_MODE_NAME:
                                VDUtils.switchFrame(ListSelection.this, new OrderedMode(list));
                                break;
                            case VDConstants.EXAM_MODE_NAME:
                                VDUtils.switchFrame(ListSelection.this, new ExamMode());
                                break;
                        }
                    } else {
                        VDErrorDialog.show(ListSelection.this, "Failed to start!", VDConstants.MICROSOFT_YAHEI_BOLD_32);
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2 && e.getSource() instanceof JTree) {
                if (e.getSource() == inList) {
                    this.switchFrame(e, inList, Main.INTERNAL_LISTS);
                } else if (e.getSource() == exList) {
                    this.switchFrame(e, exList, Main.EXTERNAL_LISTS);
                }
            }
        }
    }

    private static class ListTreeCellRenderer extends DefaultTreeCellRenderer {

        private Icon textFile;
        private Icon folder;
        private Icon vdList;
        private Icon openedFolder;

        public ListTreeCellRenderer() {
            try {
                this.textFile = FileUtils.createImageIcon("/images/icons/ListSelection/textFile.png");
                this.folder = FileUtils.createImageIcon("/images/icons/ListSelection/folder.png");
                this.vdList = FileUtils.createImageIcon("/images/icons/ListSelection/vdList.png");
                this.openedFolder = FileUtils.createImageIcon("/images/icons/ListSelection/openedFolder.png");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            this.setFont(VDConstants.MICROSOFT_YAHEI_BOLD_32.deriveFont(Font.PLAIN));
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            String text = value.toString();
            if (!text.contains(".")) {
                this.setIcon(this.folder);
            } else {
                this.setIcon(this.textFile);
            }
            if (text.contains(VDConstants.VD_FILE_EXTENTION)) {
                text = text.replace(VDConstants.VD_FILE_EXTENTION, VDConstants.EMPTY);
                if (tree.getModel().getRoot().toString().equals(ENText.INTERNAL_VD_LISTS)) {
                    text = this.loadProgressPercentage(text, Main.INTERNAL_LISTS);
                } else {
                    text = this.loadProgressPercentage(text, Main.EXTERNAL_LISTS);
                }
                this.setIcon(this.vdList);
                this.setToolTipText("Double click to continue!");
            } else {
                this.setToolTipText("This is not a valid VD File!");
            }
            this.setText(text);

            if (expanded) {
                this.setIcon(this.openedFolder);
            }
            return this;
        }

        private String loadProgressPercentage(String name, List<File> fileList) {
            for (File file : fileList) {
                if (file.getName().contains(name)) {
                    VDList vdList = new VDList(file);
                    vdList.toQAList();
                    vdList.loadInput();
                    name = name + " | " + vdList.getPercentAnswered(VDConstants.KEY_INPUT_MEANINGS) + "%" +
                            " : " + vdList.getPercentAnswered(VDConstants.KEY_INPUT_VOCABULARIES) + "%";
                    return name;
                }
            }
            return null;
        }
    }

}