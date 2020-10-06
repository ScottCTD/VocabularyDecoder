package xyz.scottc.utils.components;

import javax.swing.*;
import java.awt.*;

public class UtilColorfulLabel extends JLabel {

    public UtilColorfulLabel(String text, Color background, int fontSize) {
        super(text);
        this.setOpaque(true);
        this.setBackground(background);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setFont(new Font("", Font.BOLD, fontSize));
    }
}
