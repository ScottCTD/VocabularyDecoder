package xyz.scottc.frames.old.mainFrame.dialog;

import org.json.JSONObject;
import xyz.scottc.utils.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class CreateVDFileByInputDialog extends JDialog {

    private final UtilJPanel rootPanel = new UtilJPanel(new AfRowLayout());

    private final VDTextArea QInputArea = new VDTextArea(10, 10, VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_15, true, false);
    private final JScrollPane QInputAreaScrollPane = new JScrollPane(this.QInputArea);

    private final VDTextArea AInputArea = new VDTextArea(10, 10, VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_15, true, false);
    private final JScrollPane AInputAreaScrollPane = new JScrollPane(this.AInputArea);

    private final UtilJButton confirmButton = new UtilJButton("Confirm", 30);

    public CreateVDFileByInputDialog(Frame owner) {
        super(owner);
        this.setTitle("Create Vocabulary List by Input");
        this.setModal(false);
        this.rootPanelHandler();
    }

    private void rootPanelHandler() {
        this.setContentPane(this.rootPanel);

        this.rootPanel.add(this.QInputAreaScrollPane, "2w");
        this.rootPanel.add(this.AInputAreaScrollPane, "2w");

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
                QList.add(Q.substring(startValue, i).replaceAll("\r", VDConstantsUtils.EMPTY)
                        .replaceAll("\n", VDConstantsUtils.EMPTY));
                startValue = i;
            }
        }
        startValue = 0;
        for (int i = 0; i < A.length(); i++) {
            if ('\n' == A.charAt(i)) {
                AList.add(A.substring(startValue, i).replaceAll("\r", VDConstantsUtils.EMPTY)
                        .replaceAll("\n", VDConstantsUtils.EMPTY));
                startValue = i;
            }
        }
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File output = new File(fileChooser.getSelectedFile().getAbsolutePath().endsWith(".json") ?
                    fileChooser.getSelectedFile().getAbsolutePath() : fileChooser.getSelectedFile().getAbsolutePath() + ".json");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("questions", QList);
            jsonObject.put("answers", AList);
            JSONUtils.toFile(jsonObject, output, "UTF-8");
            this.AInputArea.setText(VDConstantsUtils.EMPTY);
            this.QInputArea.setText(VDConstantsUtils.EMPTY);
        }
    }

}
