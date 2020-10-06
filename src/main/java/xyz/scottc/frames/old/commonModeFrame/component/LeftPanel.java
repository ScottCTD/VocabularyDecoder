package xyz.scottc.frames.old.commonModeFrame.component;

import org.json.JSONObject;
import xyz.scottc.frames.old.commonModeFrame.CommonModeFrame;
import xyz.scottc.frames.old.commonModeFrame.dialog.review.ReviewData;
import xyz.scottc.frames.old.mainFrame.MainFrame;
import xyz.scottc.utils.*;
import xyz.scottc.utils.components.UtilJButton;
import xyz.scottc.utils.components.UtilJLabel;
import xyz.scottc.utils.components.UtilJPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LeftPanel extends UtilJPanel {

    private final CommonModeFrame parentFrame;
    private final FunctionPanel functionPanel;
    private final TopPanel topPanel;

    private final UtilJButton importButton = new UtilJButton("Import", 20);
    private final UtilJButton exportButton = new UtilJButton("Export", 20);
    private final UtilJLabel internalFileListLabel = new UtilJLabel("Internal Vocab List",
            new Font("微软雅黑", Font.BOLD, 24));
    private final DefaultListModel<String> internalFileListModel = new DefaultListModel<>();
    private final JList<String> internalFileList = new JList<>(this.internalFileListModel);
    private final JScrollPane internalFileListScrollPane = new JScrollPane(this.internalFileList);
    private final UtilJLabel externalFileListLabel = new UtilJLabel("External Vocab List",
            new Font("微软雅黑", Font.BOLD, 24));
    private final DefaultListModel<String> externalFileListModel = new DefaultListModel<>();
    private final JList<String> externalFileList = new JList<>(this.externalFileListModel);
    private final JScrollPane externalFileListScrollPane = new JScrollPane(this.externalFileList);
    private final UtilJButton backButton = new UtilJButton("Go Back", 45);

    private final List<File> internalVocabularyPool = new ArrayList<>();
    private final List<File> externalVocabularyPool = new ArrayList<>();

    protected String selectedListName;

    public LeftPanel(CommonModeFrame parent, FunctionPanel functionPanel, TopPanel topPanel) {
        this.parentFrame = parent;
        this.functionPanel = functionPanel;
        this.topPanel = topPanel;
        this.setLayout(new CommonModeTopPanelLayout());

        this.add(this.importButton);
        this.importButton.setToolTipText("Import the customized VD File which can be acquired by using \"Tools\" menu in the Main windows.");
        this.importButton.addActionListener(e -> this.importFile());

        this.add(this.exportButton);
        this.exportButton.setToolTipText("Export your answer report.");
        this.exportButton.addActionListener(e -> this.export());

        this.add(this.internalFileListLabel);
        this.add(this.internalFileListScrollPane);
        this.internalFileList.setFont(VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_20);
        this.internalFileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.internalFileList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadSelectedList(e, internalFileList, (ArrayList<File>) internalVocabularyPool);
            }
        });
        this.addInternalVocabularyPool();

        this.add(this.externalFileListLabel);
        this.add(this.externalFileListScrollPane);
        this.externalFileList.setFont(VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_20);
        this.externalFileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.externalFileList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadSelectedList(e, externalFileList, (ArrayList<File>) externalVocabularyPool);
            }
        });
        this.addExternalVocabularyPool();

        //backButton
        this.add(this.backButton);
        this.backButton.setToolTipText("Go back to the Main windows.");
        this.backButton.addActionListener(e -> this.goBackToMainFrame());
    }

    private void loadSelectedList(MouseEvent event, JList<String> jList, ArrayList<File> fileList) {
        if (!this.functionPanel.init) {
            if (event.getClickCount() == 2) {
                if (event.getSource() instanceof JList) {
                    int index = jList.locationToIndex(event.getPoint());
                    File file = fileList.get(index);
                    try {
                        JSONObject jsonObject = (JSONObject) JSONUtils.fromFile(file, "UTF-8");
                        this.functionPanel.questionList = jsonObject.getJSONArray("questions").toList();
                        this.functionPanel.answerList = jsonObject.getJSONArray("answers").toList();
                        this.topPanel.amountDisplay.setTotalAmount(this.functionPanel.questionList.size());
                        this.functionPanel.isListLoaded = true;
                        JOptionPane.showConfirmDialog(parentFrame, "Successfully load the list! Please click \"start\" to start studying!",
                                "Congratulation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            VDConstantsUtils.showWarningMessage(this.parentFrame, "Please stop the current list and then start a new one!");
        }
    }

    private void importFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON File", "json"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            FileUtils.copyFile(file, MainFrame.externalLibrary);
            this.addExternalVocabularyPool();
        }
    }

    private void export() {
        if (this.functionPanel.init) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            int result = fileChooser.showSaveDialog(this.parentFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                StringBuilder builder = new StringBuilder();

                String title = "VDList: " + this.selectedListName + VDConstantsUtils.SPACE +
                        "Finishing Time: " + this.topPanel.timer.getText() + "\n";
                builder.append(title);

                List<ReviewData> dataList = this.functionPanel.review.dataPanel.allDataList;
                for (ReviewData datum : dataList) {
                    builder.append(datum.toString()).append("\n");
                }

                File file = new File(fileChooser.getSelectedFile().getAbsolutePath().endsWith(".txt") ?
                        fileChooser.getSelectedFile().getAbsolutePath() : fileChooser.getSelectedFile().getAbsolutePath() + ".txt");
                OutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    outputStream.write(builder.toString().getBytes());
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    FileUtils.closeStream(null, outputStream);
                }
            }
        }
    }

    private void addExternalVocabularyPool() {
        this.externalVocabularyPool.clear();
        this.externalFileListModel.clear();
        File[] files = MainFrame.externalLibrary.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                this.externalVocabularyPool.add(file);
                this.externalFileListModel.addElement(file.getName());
            }
        }
    }

    private void addInternalVocabularyPool() {
        //copy the internal json file to the InternalLibrary directory
        String jarPath = FileUtils.getJarFilePath(this);
        JarFile jarFile;
        String internalPath = "internalVocabPool/";
        try {
            jarFile = new JarFile(new File(jarPath));
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String innerPath = jarEntry.getName();
                if (innerPath.startsWith(internalPath) && !innerPath.equals(internalPath)) {
                    InputStream inputStream = this.getClass().getResourceAsStream("/" + innerPath);
                    String target = MainFrame.internalLibrary.getAbsolutePath() + innerPath.substring(17);
                    Files.copy(inputStream, Paths.get(target));
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //add all the file in InternalLibrary directory to list
        for (File file : Objects.requireNonNull(MainFrame.internalLibrary.listFiles())) {
            this.internalVocabularyPool.add(file);
            this.internalFileListModel.addElement(file.getName());
            this.selectedListName = file.getName().replace(".json", VDConstantsUtils.EMPTY);
        }
    }

    private void goBackToMainFrame() {
        this.parentFrame.dispose();
        MainFrame frame = new MainFrame("Vocabulary Decoder");
        frame.setLocation(this.parentFrame.getLocation());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1000, 1000));
        frame.setVisible(true);
    }

    private class CommonModeTopPanelLayout implements LayoutManager {

        @Override
        public void layoutContainer(Container parent) {
            int width = parent.getWidth();
            int height = parent.getHeight();
            int marginX = width / 100;
            int marginY = height / 100;

            Dimension exportButtonSize = exportButton.getPreferredSize();
            Dimension importButtonSize = importButton.getPreferredSize();
            int widthSum01 = exportButtonSize.width + importButtonSize.width + marginX;
            //importButton
            importButton.setBounds((parentFrame.separator02.getX() - widthSum01) / 2 - 10, 0, importButtonSize.width, importButtonSize.height);
            //exportButton
            exportButton.setBounds(importButton.getX() + importButtonSize.width + marginX, 0,
                    exportButtonSize.width, exportButtonSize.height);

            //choseFileLabel
            Dimension choseFileLabelSize = internalFileListLabel.getPreferredSize();
            internalFileListLabel.setBounds((parentFrame.separator02.getX() - choseFileLabelSize.width) / 2 - 10, exportButton.getY() + exportButtonSize.height + marginY * 2 + 1,
                    choseFileLabelSize.width, choseFileLabelSize.height);
            //internalFileListScrollPane
            Dimension internalFileListScrollPaneSize = new Dimension(parentFrame.separator02.getX() - marginX * 2 - 20,
                    (parentFrame.separator03.getY() - parentFrame.separator01.getY()) / 2 - marginY * 2 - choseFileLabelSize.height - 5);
            internalFileListScrollPane.setBounds((parentFrame.separator02.getX() - internalFileListScrollPaneSize.width) / 2 - 10,
                    internalFileListLabel.getY() + choseFileLabelSize.height + marginY,
                    internalFileListScrollPaneSize.width, internalFileListScrollPaneSize.height);

            //externalFileListLabel
            Dimension externalFileListLabelSize = externalFileListLabel.getPreferredSize();
            externalFileListLabel.setBounds((parentFrame.separator02.getX() - externalFileListLabelSize.width) / 2 - 10,
                    (parentFrame.separator03.getY() - parentFrame.separator01.getY()) / 2 + marginY + 40,
                    externalFileListLabelSize.width, externalFileListLabelSize.height);
            //externalFileListScrollPane
            Dimension externalFileListScrollPaneSize = new Dimension(parentFrame.separator02.getX() - marginX * 2 - 20,
                    (parentFrame.separator03.getY() - parentFrame.separator01.getY()) / 2 - marginY - choseFileLabelSize.height - 10);
            externalFileListScrollPane.setBounds((parentFrame.separator02.getX() - externalFileListScrollPaneSize.width) / 2 - 10,
                    externalFileListLabel.getY() + externalFileListLabelSize.height + marginY,
                    externalFileListScrollPaneSize.width, externalFileListScrollPaneSize.height);

            //backButton
            Dimension backButtonSize = backButton.getPreferredSize();
            backButton.setBounds((parentFrame.separator02.getX() - backButtonSize.width) / 2 - 10, height - backButtonSize.height - marginY,
                    backButtonSize.width, backButtonSize.height);
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
