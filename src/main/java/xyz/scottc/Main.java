package xyz.scottc;

import xyz.scottc.frames.mainFrame.MainFrame;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        //Initiate the Frame
        Main.initFrame();
    }

    private static void initFrame() {
/*        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                IllegalAccessException e) {
            e.printStackTrace();
        }*/
        MainFrame frame = new MainFrame("Vocabulary Decoder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1000, 1000));
        frame.setVisible(true);
    }
}
