package xyz.scottc.utils.components;

import xyz.scottc.utils.VDConstantsUtils;

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
