package xyz.scottc.frames.old.commonModeFrame.dialog.review;


import xyz.scottc.utils.AfColumnLayout;
import xyz.scottc.utils.components.UtilJPanel;
import xyz.scottc.utils.VDConstantsUtils;

import javax.swing.*;
import java.awt.*;

public class Review extends JDialog {

    private final UtilJPanel rootPanel = new UtilJPanel(new CardLayout());
    public final DataPanel dataPanel = new DataPanel();

    private final JComboBox<String> comboBox = new JComboBox<>();

    public Review(Frame owner) {
        super(owner, "Review", false);
        this.setLayout(new AfColumnLayout());

        this.add(this.comboBox, "50px");
        this.comboBox.setFont(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);
        this.comboBox.addItem("All Vocabulary List");
        this.comboBox.addItem("Incorrect Vocabulary List");
        this.comboBox.addActionListener(e -> this.choosePanel());

        this.add(this.rootPanel, "1w");
        this.rootPanel.add(this.dataPanel, "dataPanel");
    }

    private void choosePanel() {
        CardLayout cardLayout = (CardLayout) rootPanel.getLayout();
        switch (this.comboBox.getSelectedItem().toString()) {
            case "All Vocabulary List":
                this.dataPanel.reviewTable.setModel(this.dataPanel.allDataModel);
                this.dataPanel.addRenderers();
                cardLayout.show(rootPanel, "dataPanel");
                break;
            case "Incorrect Vocabulary List":
                this.dataPanel.reviewTable.setModel(this.dataPanel.incorrectDataModel);
                this.dataPanel.addRenderers();
        }
    }
}
