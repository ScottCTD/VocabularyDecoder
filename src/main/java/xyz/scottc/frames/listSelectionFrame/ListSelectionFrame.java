package xyz.scottc.frames.listSelectionFrame;

import xyz.scottc.frames.ATransitionalFrame;
import xyz.scottc.utils.ENText;
import xyz.scottc.utils.VDConstantsUtils;
import xyz.scottc.utils.components.UtilJButton;

import javax.swing.*;
import java.awt.*;

public class ListSelectionFrame extends ATransitionalFrame {

    private final UtilJButton backButton = new UtilJButton("Back", 40);

    public ListSelectionFrame(String title) throws HeadlessException {
        super(title);
        this.rootPanelHandler();
    }

    @Override
    protected void rootPanelHandler() {
        super.rootPanelHandler();

        super.titleLabel.setFont(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_60);
        super.titleLabel.setText(ENText.LIST_SELECTION);
        super.introLabel.setText(ENText.LIST_SELECTION_INTRODUCTION);

        super.rootPanel.add(this.backButton);
        super.layout.putConstraint(SpringLayout.WEST, this.backButton, MARGIN, SpringLayout.WEST, super.rootPanel);
        super.layout.putConstraint(SpringLayout.SOUTH, this.backButton, -MARGIN, SpringLayout.NORTH, super.separator03);
    }
}