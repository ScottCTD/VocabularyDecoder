package xyz.scottc.vd.utils.dialogs;

import org.json.JSONObject;
import xyz.scottc.vd.utils.JSONUtils;
import xyz.scottc.vd.utils.VDConstants;
import xyz.scottc.vd.utils.components.UtilJButton;
import xyz.scottc.vd.utils.components.UtilJLabel;
import xyz.scottc.vd.utils.components.UtilJPanel;
import xyz.scottc.vd.utils.components.VDTextArea;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CreateVDFileByTXTDialog extends JDialog {

    private final UtilJPanel rootPanel = new UtilJPanel(new CreateVDFileLayout());

    private static final String HELP_TEXT = "This is a small tool for creating vocabularies list " +
            "file which can be used in all the modes. You should first choose a file only contains " +
            "the vocabularies, and then choose a file only contains the answers. Finally, hitting the " +
            "\"generate\" button will help you to generate the valid file.";

    private final VDTextArea helpText = new VDTextArea(HELP_TEXT, 5, 33, VDConstants.MICROSOFT_YAHEI_PLAIN_20);
    private final UtilJLabel vocabLabel = new UtilJLabel("Vocabulary File", VDConstants.MICROSOFT_YAHEI_PLAIN_20);
    private final UtilJButton chooseVocabFile = new UtilJButton("Choose", VDConstants.MICROSOFT_YAHEI_PLAIN_20);
    private final JTextField vocabFilePath = new JTextField(19);
    private final JScrollPane vocabFilePathScrollPane = new JScrollPane(this.vocabFilePath);
    private final UtilJLabel answerLabel = new UtilJLabel("Answer File", VDConstants.MICROSOFT_YAHEI_PLAIN_20);
    private final UtilJButton chooseAnswerFile = new UtilJButton("Choose", VDConstants.MICROSOFT_YAHEI_PLAIN_20);
    private final JTextField answerFilePath = new JTextField(19);
    private final JScrollPane answerFilePathScrollPane = new JScrollPane(this.answerFilePath);
    private final UtilJButton generate = new UtilJButton("Generate", VDConstants.MICROSOFT_YAHEI_BOLD_32);

    private final List<String> vocabList = new ArrayList<>(100);
    private final List<String> answerList = new ArrayList<>(100);

    public CreateVDFileByTXTDialog(Frame owner) {
        super(owner);
        this.setTitle("Create VD File by .txt");
        this.setModal(false);
        this.rootPanelHandler();
    }

    private void rootPanelHandler() {
        this.setContentPane(this.rootPanel);

        this.rootPanel.add(this.helpText);
        this.helpText.setEnabled(false);
        this.helpText.setBackground(this.getBackground());
        this.helpText.setLineWrap(true);
        this.helpText.setWrapStyleWord(true);

        this.rootPanel.add(this.vocabLabel);
        this.rootPanel.add(this.chooseVocabFile);
        this.chooseVocabFile.addActionListener(e -> this.chooseVocabFile(this.vocabList, this.vocabFilePath));
        this.rootPanel.add(this.vocabFilePathScrollPane);
        this.vocabFilePath.setFont(VDConstants.MICROSOFT_YAHEI_PLAIN_20);
        this.vocabFilePath.setEnabled(false);

        this.rootPanel.add(this.answerLabel);
        this.rootPanel.add(this.chooseAnswerFile);
        this.chooseAnswerFile.addActionListener(e -> this.chooseAnswerFile());
        this.rootPanel.add(this.answerFilePathScrollPane);
        this.answerFilePath.setFont(VDConstants.MICROSOFT_YAHEI_PLAIN_20);
        this.answerFilePath.setEnabled(false);

        this.rootPanel.add(this.generate);
        this.generate.addActionListener(e -> this.generateFile());
    }

    private void chooseVocabFile(List<String> list, JTextField pathField) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text File", "txt"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            InputStream inputStream = null;
            try {
                StringBuilder stringBuilder = new StringBuilder();
                inputStream = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    stringBuilder.append(new String(buffer, 0, length));
                }
                String input = stringBuilder.toString();
                input = input.replaceAll("\n", VDConstants.EMPTY);

                int startValue = 0;
                for (int i = 0; i < input.length(); i++) {
                    if ('\r' == input.charAt(i)) {
                        list.add(input.substring(startValue, i).replaceAll("\r", VDConstants.EMPTY));
                        startValue = i;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            pathField.setText(file.getAbsolutePath());
        }
    }

    private void chooseAnswerFile() {
        this.chooseVocabFile(this.answerList, this.answerFilePath);
    }

    private void generateFile() {
        if (this.vocabFilePath.getText() == null && this.answerFilePath == null) {
            JOptionPane.showConfirmDialog(this,
                    "You should firstly import both vocabulary file and answer file!", "Warning",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        } else {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("JSON File", "json"));
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                ArrayList<String> VIList = new ArrayList<>();
                for (int i = 0; i < this.vocabList.size(); i++) {
                    VIList.add(VDConstants.EMPTY);
                }

                ArrayList<String> MIList = new ArrayList<>();
                for (int i = 0; i < this.vocabList.size(); i++) {
                    MIList.add(VDConstants.EMPTY);
                }

                File output = fileChooser.getSelectedFile();
                String path = output.getAbsolutePath();
                if (!path.endsWith(VDConstants.VD_FILE_EXTENTION)) {
                    output = new File(path + VDConstants.VD_FILE_EXTENTION);
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(VDConstants.KEY_QUESTIONS, this.vocabList);
                jsonObject.put(VDConstants.KEY_ANSWERS, this.answerList);
                jsonObject.put(VDConstants.KEY_INPUT_VOCABULARIES, VIList);
                jsonObject.put(VDConstants.KEY_INPUT_MEANINGS, MIList);

                JSONUtils.toFile(jsonObject, output);
            }
        }
    }

    private class CreateVDFileLayout implements LayoutManager {

        @Override
        public void layoutContainer(Container parent) {
            int width = parent.getWidth();
            int height = parent.getHeight();
            int marginX = width / 50;
            int marginY = height / 50;

            //helpText
            Dimension helpTextSize = helpText.getPreferredSize();
            helpText.setBounds(marginX, marginY, helpTextSize.width, helpTextSize.height);

            //vocabLabel
            Dimension vocabLabelSize = vocabLabel.getPreferredSize();
            vocabLabel.setBounds(marginX, helpText.getY() + helpTextSize.height + marginY,
                    vocabLabelSize.width, vocabLabelSize.height);
            //chooseVocabFile
            Dimension chooseVocabFileSize = chooseVocabFile.getPreferredSize();
            chooseVocabFile.setBounds(vocabLabel.getX() + vocabLabelSize.width + marginX, helpTextSize.height + marginY,
                    chooseVocabFileSize.width, chooseVocabFileSize.height);
            //vocabFilePathScrollPane
            Dimension vocabFilePathScrollPaneSize = vocabFilePathScrollPane.getPreferredSize();
            vocabFilePathScrollPane.setBounds(chooseVocabFile.getX() + chooseVocabFileSize.width + marginX,
                    helpTextSize.height + marginY, vocabFilePathScrollPaneSize.width, vocabFilePathScrollPaneSize.height);

            //answerLabel
            Dimension answerLabelSize = answerLabel.getPreferredSize();
            answerLabel.setBounds(marginX, vocabLabel.getY() + vocabLabelSize.height + marginY,
                    answerLabelSize.width, answerLabelSize.height);
            //chooseAnswerFile
            Dimension chooseAnswerFileSize = chooseAnswerFile.getPreferredSize();
            chooseAnswerFile.setBounds(vocabLabel.getX() + vocabLabelSize.width + marginX, vocabLabel.getY() + vocabLabelSize.height + marginY,
                    chooseAnswerFileSize.width, chooseAnswerFileSize.height);
            //answerFilePathScrollPane
            Dimension answerFilePathScrollPaneSize = answerFilePathScrollPane.getPreferredSize();
            answerFilePathScrollPane.setBounds(chooseAnswerFile.getX() + chooseVocabFileSize.width + marginX, vocabLabel.getY() + vocabLabelSize.height + marginY,
                    answerFilePathScrollPaneSize.width, answerFilePathScrollPaneSize.height);

            //generate
            Dimension generateSize = new Dimension(width - marginX * 2, generate.getPreferredSize().height);
            generate.setBounds(marginX, answerFilePathScrollPane.getY() + answerFilePathScrollPaneSize.height + marginY * 2,
                    generateSize.width, generateSize.height);
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
