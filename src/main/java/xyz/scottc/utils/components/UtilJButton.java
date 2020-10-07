package xyz.scottc.utils.components;

import xyz.scottc.utils.VDConstantsUtils;

import javax.swing.*;

public class UtilJButton extends JButton {

    public UtilJButton(String text, float size) {
        super(text);
        this.setFont(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30.deriveFont(size));
    }

    public UtilJButton(String text, float size, Icon icon) {
        super(text, icon);
        this.setFont(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30.deriveFont(size));
    }

}
