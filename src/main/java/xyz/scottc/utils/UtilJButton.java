package xyz.scottc.utils;

import javax.swing.*;
import java.awt.*;

public class UtilJButton extends JButton {

    public UtilJButton(String text, int size) {
        super(text);
        this.setFont(new Font("微软雅黑", Font.BOLD, size));
    }

    public UtilJButton(String text, int size, Icon icon) {
        super(text, icon);
        this.setFont(new Font("微软雅黑", Font.BOLD, size));
    }

}
