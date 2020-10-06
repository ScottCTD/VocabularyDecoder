package xyz.scottc.utils.components;

import javax.swing.*;
import java.awt.*;

public class LineSeparator extends JPanel {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int type;
    private int thickness;
    private int length;
    private float[] dash;
    private Paint color;

    public LineSeparator(int type, int length) {
        this(type, length, 1, new float[]{1F, 0F});
    }

    public LineSeparator(int type, int length, int thickness) {
        this(type, length, thickness, new float[]{1F, 0F});
    }

    public LineSeparator(int type, int length, int thickness, float[] dash) {
        this(type, length, thickness, dash, Color.BLACK);
    }

    public LineSeparator(int type, int length, int thickness, float[] dash, Paint color) {
        this.type = type;
        this.thickness = thickness;
        this.length = length;
        this.dash = dash;
        this.color = color;
    }

    @Override
    public Dimension getPreferredSize() {
        if (this.type == HORIZONTAL) {
            return new Dimension(this.length, this.thickness);
        } else if (this.type == VERTICAL) {
            return new Dimension(this.thickness, length);
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = this.getWidth();
        int height = this.getHeight();
        Graphics2D g2D = (Graphics2D) g;

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //清空显示
        g2D.clearRect(0, 0, width, height);
        g2D.setColor(this.getParent().getBackground());
        g2D.fillRect(0, 0, width, height);

        /*int widthCenter = width / 2;
        int heightCenter = height / 2;*/

        g2D.setStroke(new BasicStroke(this.thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND,
                1F, this.dash, 0F));
        g2D.setPaint(this.color);

        switch (this.type) {
            case HORIZONTAL:
                g.drawLine(0, 0, this.length, 0);
                break;
            case VERTICAL:
                g.drawLine(0, 0, 0, this.length);
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

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public float[] getDash() {
        return dash;
    }

    public void setDash(float[] dash) {
        this.dash = dash;
    }

    public Paint getColor() {
        return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }
}
