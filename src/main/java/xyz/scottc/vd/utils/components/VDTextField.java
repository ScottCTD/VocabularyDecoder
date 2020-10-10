package xyz.scottc.vd.utils.components;

import javax.swing.*;
import java.awt.*;

public class VDTextField extends JTextField {

    public VDTextField(Font font) {
        super();
        this.setFont(font);
    }

    public VDTextField(int columns) {
        super(columns);
    }

    public VDTextField(String text) {
        super(text);
    }

    public VDTextField(String text, int columns) {
        super(text, columns);
    }
}
