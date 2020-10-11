package xyz.scottc.vd.utils.dialogs;

import xyz.scottc.vd.utils.FileUtils;
import xyz.scottc.vd.utils.VDConstants;
import xyz.scottc.vd.utils.components.VDCancelButton;
import xyz.scottc.vd.utils.components.VDConfirmButton;
import xyz.scottc.vd.utils.components.VDTextField;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VDInputDialog extends VDDialog {

    private final VDTextField inputField = new VDTextField(VDConstants.MICROSOFT_YAHEI_PLAIN_20);
    private final JScrollPane inputFieldView = new JScrollPane(this.inputField);

    private final VDConfirmButton confirmButton = new VDConfirmButton(VDConstants.MICROSOFT_YAHEI_PLAIN_20, 32);
    private final VDCancelButton cancelButton = new VDCancelButton(VDConstants.MICROSOFT_YAHEI_PLAIN_20, 32);

    private String inputValue = VDConstants.EMPTY;

    public VDInputDialog(Frame owner, String title, String info, Font font) {
        super(owner, title, info, font);
        this.setSize(width, height - (100 - MARGIN));
        try {
            Icon icon = FileUtils.createImageIcon("/images/icons/edit.png");
            super.info.setIcon(icon);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.rootPanelHandler();
        this.layoutHandler();
    }

    /**
     * Show a VDInputDialog (Modal) with given parameters.
     *
     * @param owner the parent of the dialog
     * @param title the title of the dialog
     * @param info  the information displayed in the dialog
     * @param font  the font of the information
     * @return the String that user input
     */
    public static String show(Frame owner, String title, String info, Font font) {
        VDInputDialog inputDialog = new VDInputDialog(owner, title, info, font);
        inputDialog.setVisible(true);
        return inputDialog.inputValue;
    }

    @Override
    protected void rootPanelHandler() {
        super.rootPanelHandler();

        super.rootPanel.add(this.inputFieldView);

        super.rootPanel.add(this.confirmButton);
        this.confirmButton.addActionListener(e -> {
            this.inputValue = this.inputField.getText();
            this.dispose();
        });

        super.rootPanel.add(this.cancelButton);
        this.cancelButton.addActionListener(e -> this.dispose());

    }

    @Override
    protected void layoutHandler() {
        super.layoutHandler();

        super.layout.putConstraint(SpringLayout.NORTH, this.inputFieldView, MARGIN, SpringLayout.SOUTH, super.separator01);
        super.layout.putConstraint(SpringLayout.WEST, this.inputFieldView, MARGIN, SpringLayout.WEST, super.rootPanel);
        super.layout.putConstraint(SpringLayout.EAST, this.inputFieldView, -MARGIN, SpringLayout.EAST, super.rootPanel);

        super.layout.putConstraint(SpringLayout.NORTH, this.confirmButton, MARGIN, SpringLayout.SOUTH, this.inputFieldView);
        super.layout.putConstraint(SpringLayout.WEST, this.confirmButton, 0, SpringLayout.WEST, this.inputFieldView);
        super.layout.putConstraint(SpringLayout.EAST, this.confirmButton, -MARGIN, SpringLayout.HORIZONTAL_CENTER, this.inputFieldView);

        super.layout.putConstraint(SpringLayout.NORTH, this.cancelButton, 0, SpringLayout.NORTH, this.confirmButton);
        super.layout.putConstraint(SpringLayout.WEST, this.cancelButton, MARGIN, SpringLayout.HORIZONTAL_CENTER, this.inputFieldView);
        super.layout.putConstraint(SpringLayout.EAST, this.cancelButton, 0, SpringLayout.EAST, this.inputFieldView);

    }
}
