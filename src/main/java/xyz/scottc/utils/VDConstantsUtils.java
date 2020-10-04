package xyz.scottc.utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VDConstantsUtils {

    public static final String HELP = "Common Mode:\nAnswer the meaning of a vocabulary \nor the correspond vocabulary of it's \nmeaning. You can check the answer \nwhen you are answering.";
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String WARNING_TITLE = "Ops";
    public static final String QUESTION_TITLE = "Yes or No?";
    public static final String INFO_TITLE = "Information";

    public static final Font MICROSOFT_YAHEI_PLAIN_15 = new Font("微软雅黑", Font.PLAIN, 15);
    public static final Font MICROSOFT_YAHEI_PLAIN_20 = new Font("微软雅黑", Font.PLAIN, 20);
    public static final Font MICROSOFT_YAHEI_BOLD_30 = new Font("微软雅黑", Font.BOLD, 30);
    public static final Font MICROSOFT_YAHEI_BOLD_40 = new Font("微软雅黑", Font.BOLD, 40);
    public static final Font MICROSOFT_YAHEI_BOLD_60 = new Font("微软雅黑", Font.BOLD, 60);
    public static final Font MICROSOFT_YAHEI_BOLD_80 = new Font("微软雅黑", Font.BOLD, 80);

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

    public static void interconvertList(List<Object> list01, List<Object> list02) {
        List<Object> temp = new ArrayList<>(list01);
        list01.clear();
        list01.addAll(list02);
        list02.clear();
        list02.addAll(temp);
    }

}
