package xyz.scottc.frames.mainFrame;

import xyz.scottc.frames.mainFrame.component.ModeSelectionPanel;
import xyz.scottc.frames.mainFrame.dialog.CreateVDFileByInputDialog;
import xyz.scottc.frames.mainFrame.dialog.CreateVDFileByTXTDialog;
import xyz.scottc.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class MainFrame extends JFrame {

    private final UtilJPanel rootPanel = new UtilJPanel(new MainFrameLayout());

    private final JMenuBar menuBar = new JMenuBar();
    private final VDMenu toolMenu = new VDMenu("Tools");
    private final VDMenuItem txtToJson = new VDMenuItem("Create VD File by txt");
    private final VDMenuItem inputToJson = new VDMenuItem("Create VD File by Input");

    private final UtilJLabel nameLabel = new UtilJLabel("Vocabulary Decoder", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_40);
    private final UtilJLabel infoLabel = new UtilJLabel("A tool means for helping people memorize vocabularies more effectively.",
            VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_20);
    private final LineSeparator separator01 = new LineSeparator(LineSeparator.HORIZONTAL, Integer.MAX_VALUE);
    private final LineSeparator separator02 = new LineSeparator(LineSeparator.VERTICAL, 10000);
    private final VDTextArea aboutLabel = new VDTextArea("Author: ScottC\nEmail: ScottCTD@outlook.com", 0, 0, VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_20, false, false);
    private final ModeSelectionPanel modeSelectionPanel = new ModeSelectionPanel(this);
    public final CreateVDFileByTXTDialog createVDFileByTXTDialog = new CreateVDFileByTXTDialog(this,
            "Create valid vocabulary list file", false);
    private final CreateVDFileByInputDialog createVDFileByInputDialog = new CreateVDFileByInputDialog(this);

    public boolean isExternalLibraryExist = false;
    public static File externalLibrary;
    public boolean isInternalLibraryExist = false;
    public static File internalLibrary;

    public MainFrame(String title) throws HeadlessException {
        super(title);
        this.initialize();
        this.setContentPane(this.rootPanel);
        this.setJMenuBar(this.menuBar);
        this.menuBarHandler();
        this.rootPanelHandler();
    }

    private void rootPanelHandler() {
        //NameLabel
        this.rootPanel.add(this.nameLabel);

        //infoLabel
        this.rootPanel.add(this.infoLabel);

        //Separator01
        this.rootPanel.add(separator01);

        //aboutLabel
        this.rootPanel.add(this.aboutLabel);
        this.aboutLabel.setBackground(this.rootPanel.getBackground());
        this.aboutLabel.setEnabled(false);

        //Separator02
        this.rootPanel.add(separator02);

        //modeSelectionPanel
        this.rootPanel.add(this.modeSelectionPanel);
    }

    private void menuBarHandler() {
        this.menuBar.add(this.toolMenu);

        this.toolMenu.add(this.txtToJson);
        this.txtToJson.addActionListener(this.txtToJsonListener);
        this.toolMenu.add(this.inputToJson);
        this.inputToJson.addActionListener(this.inputToJsonListener);
    }

    //Initialize the directories required for internal and external vocabularies list.
    private void initialize() {
        File directory = FileUtils.getDirectoryFile(this);
        if (directory != null) {
            File[] files = directory.listFiles();
            if (files != null) {
                externalLibrary = new File(directory.getAbsolutePath() + "/ExternalLibrary");
                internalLibrary = new File(directory.getAbsolutePath() + "/InternalLibrary");
                for (File file : files) {
                    if (file.isDirectory() && "ExternalLibrary".equals(file.getName())) {
                        for (File subFile : Objects.requireNonNull(file.listFiles())) {
                            boolean success = subFile.delete();
                            if (!success) System.out.println("ExternalLibrary File deletion failed");
                        }
                        this.isExternalLibraryExist = true;
                    }
                    if (file.isDirectory() && "InternalLibrary".equals(file.getName())) {
                        for (File subFile : Objects.requireNonNull(file.listFiles())) {
                            boolean success = subFile.delete();
                            if (!success) System.out.println("InternalLibrary File deletion failed");
                        }
                        this.isInternalLibraryExist = true;
                    }
                }
                if (!this.isExternalLibraryExist) {
                    boolean success = externalLibrary.mkdir();
                    if (!success) System.out.println("Creating ExternalLibrary Fails!");
                }
                if (!this.isInternalLibraryExist) {
                    boolean success = internalLibrary.mkdir();
                    if (!success) System.out.println("Creating InternalLibrary Fails!");
                }
            }
        }
    }

    private final ActionListener txtToJsonListener = e -> {
        Point pos = this.getLocation();
        this.createVDFileByTXTDialog.setSize(690, 350);
        this.createVDFileByTXTDialog.setLocation(pos.x + (this.getWidth() - createVDFileByTXTDialog.getWidth()) / 2,
                pos.y + (this.getHeight() - createVDFileByTXTDialog.getHeight()) / 2);
        this.createVDFileByTXTDialog.setVisible(true);
    };

    private final ActionListener inputToJsonListener = e -> {
        Point pos = this.getLocation();
        this.createVDFileByInputDialog.setSize(1000, 1000);
        this.createVDFileByInputDialog.setLocation(pos);
        this.createVDFileByInputDialog.setVisible(true);
    };

    private class MainFrameLayout implements LayoutManager{

        @Override
        public void layoutContainer(Container parent) {
            int width = parent.getWidth();
            int height = parent.getHeight();
            int marginX = width / 100;
            int marginY = height / 100;

            //nameLabel
            Dimension nameLabelSize = nameLabel.getPreferredSize();
            nameLabel.setBounds((width - nameLabelSize.width) / 2, marginY,
                    nameLabelSize.width, nameLabelSize.height);
            //infoLabel
            Dimension infoLabelSize = infoLabel.getPreferredSize();
            infoLabel.setBounds((width - infoLabelSize.width) / 2, nameLabel.getY() + nameLabelSize.height + marginY,
                    infoLabelSize.width, infoLabelSize.height);
            //separator01
            separator01.setBounds(0, infoLabel.getY() + infoLabelSize.height + marginY,
                    width, 1);
            //aboutLabel
            Dimension aboutLabelSize = aboutLabel.getPreferredSize();
            aboutLabel.setBounds(width - aboutLabelSize.width - marginX, separator01.getY() + marginY,
                    aboutLabelSize.width, aboutLabelSize.height);
            //separator02
            separator02.setBounds(aboutLabel.getX() - marginX, separator01.getY(),
                    1, height);
            //modeSelectionPanel
            Dimension modeSelectionPanelSize = new Dimension(width - marginX * 4 - aboutLabelSize.width - 1,
                    height - marginY * 5 - nameLabelSize.height - infoLabelSize.height - 1);
            modeSelectionPanel.setBounds(marginX, separator01.getY() + marginY,
                    modeSelectionPanelSize.width, modeSelectionPanelSize.height);
        }

        @Override
        public void addLayoutComponent(String name, Component comp) {

        }

        @Override
        public void removeLayoutComponent(Component comp) {

        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return null;
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return null;
        }
    }
}
