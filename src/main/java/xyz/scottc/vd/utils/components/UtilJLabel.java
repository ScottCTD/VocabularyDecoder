package xyz.scottc.vd.utils.components;

import xyz.scottc.vd.utils.VDConstantsUtils;

import javax.swing.*;
import java.awt.*;

public class UtilJLabel extends JLabel {

    public UtilJLabel() {
        this(VDConstantsUtils.EMPTY, null);
        this.setOpaque(true);
    }

    public UtilJLabel(Font font) {
        this(VDConstantsUtils.EMPTY, font);
        this.setOpaque(true);
    }

    public UtilJLabel(String text) {
        this(text, null);
        this.setOpaque(true);
    }

    public UtilJLabel(String text, Font font) {
        super(text);
        this.setFont(font);
        this.setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
