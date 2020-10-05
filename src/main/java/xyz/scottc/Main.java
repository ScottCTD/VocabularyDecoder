package xyz.scottc;

import xyz.scottc.frames.EntryFrame;
import xyz.scottc.utils.VDConstantsUtils;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        //Initiate the Frame
        Main.initFrame();
    }

    private static void initFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                IllegalAccessException e) {
            e.printStackTrace();
        }
        //MainFrame frame = new MainFrame("Vocabulary Decoder");
        EntryFrame frame = new EntryFrame("Vocabulary Decoder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(VDConstantsUtils.getSreenRectangle());
        frame.setVisible(true);
    }
}
