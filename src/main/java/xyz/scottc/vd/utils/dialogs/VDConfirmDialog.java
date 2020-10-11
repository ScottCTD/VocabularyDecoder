package xyz.scottc.vd.utils.dialogs;

import xyz.scottc.vd.utils.FileUtils;
import xyz.scottc.vd.utils.VDConstants;
import xyz.scottc.vd.utils.components.VDCancelButton;
import xyz.scottc.vd.utils.components.VDConfirmButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VDConfirmDialog extends VDDialog {

    public static final int CONFIRM = 0;
    public static final int CANCEL = 1;

    private final VDConfirmButton confirmButton = new VDConfirmButton(VDConstants.MICROSOFT_YAHEI_PLAIN_20, 32);
    private final VDCancelButton cancelButton = new VDCancelButton(VDConstants.MICROSOFT_YAHEI_PLAIN_20, 32);

    private int value = 1;

    public VDConfirmDialog(Frame owner, String title, String info, Font font) {
        super(owner, title, info, font);
        try {
            super.setIcon(FileUtils.createImageIcon("/images/icons/question.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.setSize(width, height - (135 - MARGIN));
        this.rootPanelHandler();
        this.layoutHandler();
    }

    public static int show(Frame owner, String title, String info, Font font) {
        VDConfirmDialog dialog = new VDConfirmDialog(owner, title, info, font);
        dialog.setVisible(true);
        return dialog.value;
    }

    @Override
    protected void rootPanelHandler() {
        super.rootPanelHandler();

        super.rootPanel.add(this.confirmButton);
        this.confirmButton.addActionListener(e -> {
            this.value = 0;
            this.setVisible(false);
        });

        super.rootPanel.add(this.cancelButton);
        this.cancelButton.addActionListener(e -> this.setVisible(false));
    }

    @Override
    protected void layoutHandler() {
        super.layoutHandler();

        super.layout.putConstraint(SpringLayout.NORTH, this.confirmButton, MARGIN, SpringLayout.SOUTH, super.separator01);
        super.layout.putConstraint(SpringLayout.WEST, this.confirmButton, 0, SpringLayout.WEST, super.info);
        super.layout.putConstraint(SpringLayout.EAST, this.confirmButton, -MARGIN, SpringLayout.HORIZONTAL_CENTER, super.separator01);

        super.layout.putConstraint(SpringLayout.NORTH, this.cancelButton, 0, SpringLayout.NORTH, this.confirmButton);
        super.layout.putConstraint(SpringLayout.WEST, this.cancelButton, MARGIN, SpringLayout.HORIZONTAL_CENTER, super.separator01);
        super.layout.putConstraint(SpringLayout.EAST, this.cancelButton, 0, SpringLayout.EAST, super.info);
    }
}
