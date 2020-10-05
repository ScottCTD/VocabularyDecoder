package xyz.scottc.utils;

import javax.swing.*;
import java.awt.*;

public class LineSeparator extends JPanel {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int length;
    private int type;

    private int width;
    private int height;

    public LineSeparator(int type, int length) {
        this.type = type;
        this.length = length;
    }

    @Override
    public Dimension getPreferredSize() {
        if (this.type == HORIZONTAL) {
            return new Dimension(length, 1);
        } else if (this.type == VERTICAL) {
            return new Dimension(1, length);
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.width = this.getWidth();
        this.height = this.getHeight();

        //清空显示
        g.clearRect(0, 0, width, height);
        g.setColor(this.getParent().getBackground());
        g.fillRect(0, 0, width, height);

        int widthCenter = this.width / 2;
        int heightCenter = this.height / 2;

        g.setColor(Color.BLACK);
        switch (this.type) {
            case HORIZONTAL:
                g.drawLine(0, heightCenter, this.length, heightCenter);
                break;
            case VERTICAL:
                g.drawLine(widthCenter, 0, widthCenter, this.length);
                break;
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
        this.repaint();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        this.repaint();
    }
}
