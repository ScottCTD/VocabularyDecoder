package xyz.scottc.vd.utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class BorderUtils {

    public static void addMargin(JComponent component, int size) {
        BorderUtils.addMargin(component, size);
    }

    public static void addMargin(JComponent component, int top, int left, int bottom, int right) {
        BorderUtils.addOuterBorder(component, new EmptyBorder(top, left, bottom, right));
    }

    public static void addPadding(JComponent component, int size) {
        BorderUtils.addPadding(component, size, size, size, size);
    }

    public static void addPadding(JComponent component, int top, int left, int bottom, int right) {
        BorderUtils.addInnerBorder(component, new EmptyBorder(top, left, bottom, right));
    }

    public static void addOuterBorder(JComponent component, Border outerBorder) {
        Border border = component.getBorder();
        if (border != null) {
            component.setBorder(new CompoundBorder(border, outerBorder));
        } else {
            component.setBorder(outerBorder);
        }
    }

    public static void addInnerBorder(JComponent component, Border innerBorder) {
        Border border = component.getBorder();
        if (border != null) {
            component.setBorder(new CompoundBorder(border, innerBorder));
        } else {
            component.setBorder(innerBorder);
        }
    }

}
