package xyz.scottc.utils;

import javax.swing.*;
import java.awt.*;

public class UtilJPanel extends JPanel {

    private Image backgroundImage;
    private Color backgroundImageMask;

    public int width;
    public int height;

    public UtilJPanel() {
        this.setOpaque(false);
    }

    public UtilJPanel(LayoutManager layout) {
        super(layout);
        this.setOpaque(false);
    }

    public UtilJPanel(LayoutManager layout, Image backgroundImage, Color backgroundImageMask) {
        super(layout);
        this.backgroundImage = backgroundImage;
        this.backgroundImageMask = backgroundImageMask;
    }

    public UtilJPanel addPadding(int top, int left, int bottom, int right) {
        BorderUtils.addPadding(this, top, left, bottom, right);
        return this;
    }

    public UtilJPanel addPadding(int size) {
        return this.addPadding(size, size, size, size);
    }

    public UtilJPanel addMargin(int top, int left, int bottom, int right) {
        BorderUtils.addMargin(this, top, left, bottom, right);
        return this;
    }

    public UtilJPanel addMargin(int size) {
        return this.addMargin(size, size, size, size);
    }

    public UtilJPanel setBackGround(Color color) {
        this.setOpaque(true);
        this.setBackground(color);
        return this;
    }

    public UtilJPanel setPreferredWidth(int width) {
        Dimension size = this.getPreferredSize();
        if (size == null) size = new Dimension(0, 0);
        size.width = width;
        this.setPreferredSize(size);
        return this;
    }

    public UtilJPanel setPreferredHeight(int height) {
        Dimension size = this.getPreferredSize();
        if (size == null) size = new Dimension(0, 0);
        size.height = height;
        this.setPreferredSize(size);
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //如果传入了背景图片就添加背景图片
        this.addBackgroundImage(g);

        this.width = this.getWidth();
        this.height = this.getHeight();
    }

    private void addBackgroundImage(Graphics g) {
        if (this.backgroundImage != null) {
            int width = this.getWidth();
            int height = this.getHeight();
            g.clearRect(0, 0, width, height);
            g.drawImage(this.backgroundImage, 0, 0, width, height, null);

            //背景图片蒙版
            g.setColor(backgroundImageMask);
            g.fillRect(0, 0, width, height);
        }
    }
}
