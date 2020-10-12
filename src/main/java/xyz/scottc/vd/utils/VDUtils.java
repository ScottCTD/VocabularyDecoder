package xyz.scottc.vd.utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VDUtils {

    public static final String WARNING_TITLE = "Ops";
    public static final String QUESTION_TITLE = "Yes or No?";
    public static final String INFO_TITLE = "Information";
    public static final String ERROR_TITLE = "Oh No!";

    public static Rectangle getScreenRectangle() {
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

    public static int showQuestionMessage(Component parent, String message) {
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

    public static void interconvertList(java.util.List<String> list01, java.util.List<String> list02) {
        java.util.List<String> temp = new ArrayList<>(list01);
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

    public static java.util.List<String> toStringList(java.util.List<Object> objectList) {
        List<String> stringList = new ArrayList<>();
        for (Object o : objectList) {
            stringList.add(o.toString());
        }
        return stringList;
    }
}
