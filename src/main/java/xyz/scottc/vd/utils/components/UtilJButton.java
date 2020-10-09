package xyz.scottc.vd.utils.components;

import javax.swing.*;
import java.awt.*;

public class UtilJButton extends JButton {

    public UtilJButton() {
    }

    public UtilJButton(String text, Font font) {
        super(text);
        this.setFont(font);
    }

    public UtilJButton(String text, Font font, boolean space) {
        this(text, font);
        if (!space) {
            KeyStroke spaceKey = KeyStroke.getKeyStroke("SPACE");
            this.getInputMap().put(spaceKey, "none");
        }
    }

    public UtilJButton(String text, Font font, Icon icon) {
        this(text, font, icon, true);
    }

    public UtilJButton(String text, Font font, Icon icon, boolean space) {
        super(text, icon);
        this.setFont(font);
        if (!space) {
            KeyStroke spaceKey = KeyStroke.getKeyStroke("SPACE");
            this.getInputMap().put(spaceKey, "none");
        }
    }

}
