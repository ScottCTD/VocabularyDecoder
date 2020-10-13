package xyz.scottc.vd.utils.dialogs;

import org.json.JSONObject;
import xyz.scottc.vd.utils.JSONUtils;
import xyz.scottc.vd.utils.VDConstants;
import xyz.scottc.vd.utils.components.UtilJButton;
import xyz.scottc.vd.utils.components.UtilJPanel;
import xyz.scottc.vd.utils.components.VDTextArea;
import xyz.scottc.vd.utils.layouts.AfRowLayout;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class CreateVDFileByInputDialog extends JDialog {

    private final UtilJPanel rootPanel = new UtilJPanel(new AfRowLayout());

    private final VDTextArea QInputArea = new VDTextArea(10, 10, VDConstants.MICROSOFT_YAHEI_PLAIN_15, true, false);
    private final JScrollPane QInputAreaScrollPane = new JScrollPane(this.QInputArea);

    private final VDTextArea AInputArea = new VDTextArea(10, 10, VDConstants.MICROSOFT_YAHEI_PLAIN_15, true, false);
    private final JScrollPane AInputAreaScrollPane = new JScrollPane(this.AInputArea);

    private final UtilJButton confirmButton = new UtilJButton("Confirm", VDConstants.MICROSOFT_YAHEI_BOLD_32);

    public CreateVDFileByInputDialog(Frame owner) {
        super(owner);
        this.setTitle("Create Vocabulary List by Input");
        this.setModal(false);
        this.rootPanelHandler();
    }

    private void rootPanelHandler() {
        this.setContentPane(this.rootPanel);

        this.rootPanel.add(this.QInputAreaScrollPane, "1w");
        this.rootPanel.add(this.AInputAreaScrollPane, "1w");

        this.rootPanel.add(this.confirmButton);
        this.confirmButton.addActionListener(e -> this.generateFile());
    }

    private void generateFile() {
        String Q = this.QInputArea.getText();
        String A = this.AInputArea.getText();
        ArrayList<String> QList = new ArrayList<>();
        ArrayList<String> AList = new ArrayList<>();

        int startValue = 0;
        for (int i = 0; i < Q.length(); i++) {
            if ('\n' == Q.charAt(i)) {
                QList.add(Q.substring(startValue, i).replaceAll("\r", VDConstants.EMPTY)
                        .replaceAll("\n", VDConstants.EMPTY));
                startValue = i;
            }
        }
        startValue = 0;
        for (int i = 0; i < A.length(); i++) {
            if ('\n' == A.charAt(i)) {
                AList.add(A.substring(startValue, i).replaceAll("\r", VDConstants.EMPTY)
                        .replaceAll("\n", VDConstants.EMPTY));
                startValue = i;
            }
        }

        ArrayList<String> VIList = new ArrayList<>();
        for (int i = 0; i < QList.size(); i++) {
            VIList.add(VDConstants.EMPTY);
        }

        ArrayList<String> MIList = new ArrayList<>();
        for (int i = 0; i < QList.size(); i++) {
            MIList.add(VDConstants.EMPTY);
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File output = new File(fileChooser.getSelectedFile().getAbsolutePath().endsWith(".vd") ?
                    fileChooser.getSelectedFile().getAbsolutePath() : fileChooser.getSelectedFile().getAbsolutePath() + ".vd");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(VDConstants.KEY_QUESTIONS, QList);
            jsonObject.put(VDConstants.KEY_ANSWERS, AList);
            jsonObject.put(VDConstants.KEY_INPUT_VOCABULARIES, VIList);
            jsonObject.put(VDConstants.KEY_INPUT_MEANINGS, MIList);
            JSONUtils.toFile(jsonObject, output);
            this.AInputArea.setText(VDConstants.EMPTY);
            this.QInputArea.setText(VDConstants.EMPTY);
        }
    }

}
