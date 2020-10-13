package xyz.scottc.vd.utils.dialogs;

import org.json.JSONObject;
import xyz.scottc.vd.utils.*;
import xyz.scottc.vd.utils.components.UtilJButton;
import xyz.scottc.vd.utils.components.VDConfirmButton;
import xyz.scottc.vd.utils.components.VDTextField;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Convert .txt file to .vd file
 */
public class VDFileConverter01Dialog extends VDDialog {

    private final VDTextField path = new VDTextField(VDConstants.MICROSOFT_YAHEI_PLAIN_20);
    private final JScrollPane pathView = new JScrollPane(this.path);
    private final UtilJButton importButton = new UtilJButton("Import", VDConstants.MICROSOFT_YAHEI_PLAIN_20);
    private final VDConfirmButton confirmButton = new VDConfirmButton(VDConstants.MICROSOFT_YAHEI_BOLD_32, 32);

    private String[] txts = null;
    private String[] fileNames = null;

    public VDFileConverter01Dialog(Frame owner) {
        super(owner, "Convert .txt file to .vd file", ENText.CONVERTER_01_INFO, VDConstants.MICROSOFT_YAHEI_PLAIN_20);
        this.setSize(width, height - (90 - MARGIN));
        try {
            this.setIcon(FileUtils.createImageIcon("/images/icons/ConverterDialog/converter.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.rootPanelHandler();
        this.layoutHandler();
    }

    public static void show(Frame owner) {
        VDFileConverter01Dialog dialog = new VDFileConverter01Dialog(owner);
        dialog.setVisible(true);
    }

    @Override
    protected void rootPanelHandler() {
        super.rootPanelHandler();

        super.rootPanel.add(this.pathView);
        this.path.setEnabled(false);

        super.rootPanel.add(this.importButton);
        this.importButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File[] files = fileChooser.getSelectedFiles();
                StringBuilder fileNames = new StringBuilder(VDConstants.EMPTY);
                this.fileNames = new String[files.length];
                this.txts = new String[files.length];
                for (int i = 0; i < files.length; i++) {
                    try {
                        byte[] buffer = FileUtils.readFromFile(files[i]);
                        txts[i] = new String(buffer);
                        fileNames.append(files[i].getName()).append(";");
                        this.fileNames[i] = files[i].getName();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
                this.path.setText(fileNames.toString());
            }
        });

        super.rootPanel.add(this.confirmButton);
        this.confirmButton.addActionListener(e -> {
            if (this.txts != null) {
                for (int i = 0; i < this.txts.length; i++) {
                    Map<String, List<String>> map = this.parseTxtFormat(this.txts[i]);
                    List<String> Qs = map.get(VDConstants.KEY_QUESTIONS);
                    List<String> As = map.get(VDConstants.KEY_ANSWERS);

                    ArrayList<String> VIList = new ArrayList<>();
                    for (int j = 0; j < Qs.size(); j++) {
                        VIList.add(VDConstants.EMPTY);
                    }

                    ArrayList<String> MIList = new ArrayList<>();
                    for (int j = 0; j < Qs.size(); j++) {
                        MIList.add(VDConstants.EMPTY);
                    }

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(VDConstants.KEY_QUESTIONS, Qs);
                    jsonObject.put(VDConstants.KEY_ANSWERS, As);
                    jsonObject.put(VDConstants.KEY_INPUT_VOCABULARIES, VIList);
                    jsonObject.put(VDConstants.KEY_INPUT_MEANINGS, MIList);

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setMultiSelectionEnabled(false);
                    fileChooser.setFileFilter(new FileNameExtensionFilter("VD File", "vd"));
                    int result = fileChooser.showSaveDialog(this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File target = fileChooser.getSelectedFile();
                        if (target.getName().equals("origin")) {
                            target = new File(fileChooser.getCurrentDirectory().getAbsolutePath()
                                    + "\\" + this.fileNames[i] + VDConstants.VD_FILE_EXTENTION);
                        }
                        if (!target.getName().contains(VDConstants.VD_FILE_EXTENTION)) {
                            target = new File(target.getAbsolutePath() + VDConstants.VD_FILE_EXTENTION);
                        }
                        JSONUtils.toFile(jsonObject, target);
                    }
                }
            }
        });

    }

    /**
     * Convert the [vocabulary meaning] format to a map
     *
     * @param txt [vocabulary meaning] format String
     * @return the Map contains key vocabularies and key meanings and the corresponding values
     */
    private Map<String, List<String>> parseTxtFormat(String txt) {
        Map<String, List<String>> map = new HashMap<>(220);
        List<String> temp = new ArrayList<>(110);
        int startValue = 0;
        int index = 0;
        while ((index = txt.indexOf("\n", index)) != -1) {
            temp.add(txt.substring(startValue, index));
            startValue = index;
            index++;
        }
        List<String> Qs = new ArrayList<>(110);
        List<String> As = new ArrayList<>(110);
        for (String string : temp) {
            if (string.contains("\t")) {
                int subIndex = string.indexOf("\t");
                Qs.add(VDUtils.removeALLESC(string.substring(0, subIndex)));
                As.add(VDUtils.removeALLESC(string.substring(subIndex)));
            }
        }
        map.put(VDConstants.KEY_QUESTIONS, Qs);
        map.put(VDConstants.KEY_ANSWERS, As);
        return map;
    }

    @Override
    protected void layoutHandler() {
        super.layoutHandler();

        super.layout.putConstraint(SpringLayout.WEST, this.pathView, MARGIN, SpringLayout.EAST, this.importButton);
        super.layout.putConstraint(SpringLayout.NORTH, this.pathView, MARGIN, SpringLayout.SOUTH, super.separator01);
        super.layout.putConstraint(SpringLayout.EAST, this.pathView, -MARGIN, SpringLayout.EAST, super.rootPanel);

        super.layout.putConstraint(SpringLayout.NORTH, this.importButton, 0, SpringLayout.NORTH, this.pathView);
        super.layout.putConstraint(SpringLayout.WEST, this.importButton, MARGIN, SpringLayout.WEST, super.rootPanel);

        super.layout.putConstraint(SpringLayout.NORTH, this.confirmButton, MARGIN, SpringLayout.SOUTH, this.pathView);
        super.layout.putConstraint(SpringLayout.WEST, this.confirmButton, MARGIN, SpringLayout.WEST, super.rootPanel);
        super.layout.putConstraint(SpringLayout.EAST, this.confirmButton, -MARGIN, SpringLayout.EAST, super.rootPanel);

    }
}
