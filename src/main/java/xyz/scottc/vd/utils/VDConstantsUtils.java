package xyz.scottc.vd.utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VDConstantsUtils {

    public static final String LIBRARY_NAME = "VocabularyDecoder";
    public static final String EXTERNAL_LIBRARY_NAME = "ExternalLibrary";
    public static final String INTERNAL_LIBRARY_NAME = "InternalLibrary";
    public static final String NAME = "Vocabulary Decoder";
    public static final String ABOUT = "Email -> ScottCTD@outlook.com";
    public static final String HELP = "Common Mode:\nAnswer the meaning of a vocabulary \nor the correspond vocabulary of it's \nmeaning. You can check the answer \nwhen you are answering.";
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String WARNING_TITLE = "Ops";
    public static final String QUESTION_TITLE = "Yes or No?";
    public static final String INFO_TITLE = "Information";
    public static final String ERROR_TITLE = "Oh No!";

    public static final Font MICROSOFT_YAHEI_PLAIN_15 = new Font("Microsoft YaHei UI", Font.PLAIN, 15);
    public static final Font MICROSOFT_YAHEI_PLAIN_20 = new Font("Microsoft YaHei UI", Font.PLAIN, 20);
    public static final Font MICROSOFT_YAHEI_BOLD_30 = new Font("Microsoft YaHei UI", Font.BOLD, 30);
    public static final Font MICROSOFT_YAHEI_BOLD_40 = new Font("Microsoft YaHei UI", Font.BOLD, 40);
    public static final Font MICROSOFT_YAHEI_BOLD_60 = new Font("Microsoft YaHei UI", Font.BOLD, 60);
    public static final Font MICROSOFT_YAHEI_BOLD_80 = new Font("Microsoft YaHei UI", Font.BOLD, 80);
    public static final Font MICROSOFT_YAHEI_BOLD_100 = new Font("Microsoft YaHei UI", Font.BOLD, 100);
    public static final Font MICROSOFT_YAHEI_BOLD_120 = new Font("Microsoft YaHei UI", Font.BOLD, 120);

    public static final Color SELECTED_COLOR = new Color(0xCCCCCC);

    public static Rectangle getSreenRectangle() {
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
                        getDefaultConfiguration());
        return new Rectangle(screenInsets.left, screenInsets.top, screenDimension.width -
                screenInsets.left - screenInsets.right, screenDimension.height -
                screenInsets.top - screenInsets.bottom);
    }

    public static void showWarningMessage(Component parent, String message) {
        JOptionPane.showConfirmDialog(parent, message, WARNING_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    public static int showQuestionMessgae(Component parent, String message) {
        return JOptionPane.showConfirmDialog(parent, message, QUESTION_TITLE, JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    public static void showInfoMessage(Component parent, String message) {
        JOptionPane.showConfirmDialog(parent, message, INFO_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorMessage(Component parent, String message) {
        JOptionPane.showConfirmDialog(parent, message, ERROR_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    public static void switchFrame(JFrame current, JFrame target) {
        current.dispose();
        target.setVisible(true);
    }

    public static void interconvertList(List<String> list01, List<String> list02) {
        List<String> temp = new ArrayList<>(list01);
        list01.clear();
        list01.addAll(list02);
        list02.clear();
        list02.addAll(temp);
    }

    public static int getExistCount(String subject, String key) {
        int count = 0;
        int index = 0;
        while ((index = subject.indexOf(key, index)) != -1) {
            index += key.length();
            count++;
        }
        return count;
    }
}
