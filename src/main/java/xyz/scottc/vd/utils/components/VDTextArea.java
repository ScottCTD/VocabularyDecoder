package xyz.scottc.vd.utils.components;

import xyz.scottc.vd.utils.VDConstantsUtils;

import javax.swing.*;
import java.awt.*;

public class VDTextArea extends JTextArea {

    public VDTextArea(String text, Font font) {
        this(text, 0, 0, font, true, false);
    }

    public VDTextArea(int rows, int columns, Font font, boolean enable, boolean enter) {
        this(VDConstantsUtils.EMPTY, rows, columns, font, enable, enter);
    }

    public VDTextArea(String text, int rows, int columns, Font font) {
        this(text, rows, columns, font, true, false);
    }

    public VDTextArea(String text, int rows, int columns, Font font, boolean enable, boolean enter) {
        super(text, rows, columns);
        this.setFont(font);
        this.setEnabled(enable);
        this.setDisabledTextColor(Color.BLACK);

        if (!enter) {
            //prevent enter from wrapping line
            KeyStroke enterKey = KeyStroke.getKeyStroke("ENTER");
            this.getInputMap().put(enterKey, "none");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
