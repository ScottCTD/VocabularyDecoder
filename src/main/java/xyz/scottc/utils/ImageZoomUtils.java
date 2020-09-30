package xyz.scottc.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class ImageZoomUtils {

    private static int width;
    private static int height;
    private static int finalWidth;
    private static int finalHeight;

    public static void fit(JComponent component, Graphics g, Image image, ImageObserver observer, int xOffset, int yOffset) {
        if (component != null && image != null) {
            ratioHandler(component, image, observer);
            g.drawImage(image, xOffset, yOffset, finalWidth, finalHeight, observer);
        }
    }

    public static void fitCenter(JComponent component, Graphics g, Image image, ImageObserver observer) {
        if (component != null && image != null) {
            ratioHandler(component, image, observer);

            int xOffset = (width - finalWidth) / 2;
            int yOffset = (height - finalHeight) / 2;

            g.drawImage(image, xOffset, yOffset, finalWidth, finalHeight, observer);
        }
    }

    public static void fitTopLeft(JComponent component, Graphics g, Image image, ImageObserver observer) {
        ImageZoomUtils.fit(component, g, image, observer, 0, 0);
    }

    public static void fitTopRight(JComponent component, Graphics g, Image image, ImageObserver observer) {
        if (component != null && image != null) {
            ratioHandler(component, image, observer);

            int xOffset = width - finalWidth;
            int yOffset = 0;

            g.drawImage(image, xOffset, yOffset, finalWidth, finalHeight, observer);
        }
    }

    public static void fitBottomLeft(JComponent component, Graphics g, Image image, ImageObserver observer) {
        if (component != null && image != null) {
            ratioHandler(component, image, observer);

            int xOffset = 0;
            int yOffset = height - finalHeight;

            g.drawImage(image, xOffset, yOffset, finalWidth, finalHeight, observer);
        }
    }

    public static void fitBottomRight(JComponent component, Graphics g, Image image, ImageObserver observer) {
        if (component != null && image != null) {
            ratioHandler(component, image, observer);

            int xOffset = width - finalWidth;
            int yOffset = height - finalHeight;

            g.drawImage(image, xOffset, yOffset, finalWidth, finalHeight, observer);
        }
    }

    private static void ratioHandler(JComponent component, Image image, ImageObserver observer) {
        width = component.getWidth();
        height = component.getHeight();
        int imageWidth = image.getWidth(observer);
        int imageHeight = image.getHeight(observer);

        finalWidth = width;
        finalHeight = finalWidth * imageHeight / imageWidth;
        if (finalHeight > height) {
            finalHeight = height;
            finalWidth = finalHeight * imageWidth / imageHeight;
        }
    }
}
