package xyz.scottc.frames.listSelectionFrame;

import xyz.scottc.utils.components.UtilJPanel;

import javax.swing.*;
import java.awt.*;

public class ListSelectionFrame extends JFrame {

    private final UtilJPanel rootPanel = new UtilJPanel();

    public ListSelectionFrame(String title) throws HeadlessException {
        super(title);
        this.setContentPane(this.rootPanel);
    }
}