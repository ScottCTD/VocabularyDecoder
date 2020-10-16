package xyz.scottc.vd.utils.dialogs;

import xyz.scottc.vd.utils.FileUtils;
import xyz.scottc.vd.utils.VDConstants;
import xyz.scottc.vd.utils.components.VDConfirmButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VDErrorDialog extends VDDialog {

    public static final String DEFAULT_MESSAGE = "<html>An Error Happened!<br>" +
            "Please contact with ScottCTD@outlook.com!</html>";

    private final VDConfirmButton confirmButton = new VDConfirmButton(VDConstants.MICROSOFT_YAHEI_PLAIN_20, 32);

    public VDErrorDialog(Frame owner, String info, Font font) {
        super(owner, "Error!", info, font);
        this.setSize(width, height - (135 - MARGIN));
        try {
            super.setIcon(FileUtils.createImageIcon("/images/icons/ErrorDialog/error.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.rootPanelHandler();
        this.layoutHandler();

    }

    public static void show(Frame owner, String info, Font font) {
        VDErrorDialog dialog = new VDErrorDialog(owner, info, font);
        dialog.setVisible(true);
    }

    @Override
    protected void rootPanelHandler() {
        super.rootPanelHandler();

        super.rootPanel.add(this.confirmButton);
        this.confirmButton.addActionListener(e -> this.dispose());
    }

    @Override
    protected void layoutHandler() {
        super.layoutHandler();

        super.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.confirmButton, 0, SpringLayout.HORIZONTAL_CENTER, super.info);
        super.layout.putConstraint(SpringLayout.NORTH, this.confirmButton, MARGIN, SpringLayout.SOUTH, super.separator01);
    }
}
