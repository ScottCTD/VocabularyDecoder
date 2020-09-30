package xyz.scottc.utils;

import javax.swing.*;
import java.awt.*;

public class UtilJLabel extends JLabel {

    public UtilJLabel() {
        this(VDConstantsUtils.EMPTY, null);
    }

    public UtilJLabel(Font font) {
        this(VDConstantsUtils.EMPTY, font);
    }

    public UtilJLabel(String text) {
        this(text, null);
    }

    public UtilJLabel(String text, Font font) {
        super(text);
        this.setFont(font);
    }
}
