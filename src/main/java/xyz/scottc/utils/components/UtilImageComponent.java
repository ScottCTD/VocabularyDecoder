package xyz.scottc.utils.components;

import xyz.scottc.utils.ImageZoomUtils;

import javax.swing.*;
import java.awt.*;

public class UtilImageComponent extends JPanel {

    private Image image;
    private Color backgroundColor;
    private FitTypes type;

    public UtilImageComponent() {
        this.setOpaque(false);
    }

    public UtilImageComponent(Image image, FitTypes type) {
        this.image = image;
        this.type = type;
        this.setOpaque(false);
    }

    public void setPreferredSizeInX(int x) {
        int y = x * image.getHeight(null) / image.getWidth(null);
        super.setPreferredSize(new Dimension(x, y));
    }

    public void setPreferredSizeInY(int y) {
        int x = image.getWidth(null) * y / image.getHeight(null);
        super.setPreferredSize(new Dimension(x, y));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = this.getWidth();
        int height = this.getHeight();

        //Background Color
        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, width, height);

        //Image
        if (this.image != null) {
            switch (this.type) {
                case FIT_CENTER:
                    ImageZoomUtils.fitCenter(this, g, this.image, null);
                    break;
                case FIT_TOP_LEFT:
                    ImageZoomUtils.fitTopLeft(this, g, this.image, null);
                    break;
                case FIT_TOP_RIGHT:
                    ImageZoomUtils.fitTopRight(this, g, this.image, null);
                    break;
                case FIT_BOTTOM_LEFT:
                    ImageZoomUtils.fitBottomLeft(this, g, this.image, null);
                    break;
                case FIT_BOTTOM_RIGHT:
                    ImageZoomUtils.fitBottomRight(this, g, this.image, null);
                    break;
            }
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        this.repaint();
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.repaint();
    }

    public FitTypes getType() {
        return type;
    }

    public void setType(FitTypes type) {
        this.type = type;
    }

    public enum FitTypes {
        FIT_CENTER, FIT_TOP_LEFT, FIT_TOP_RIGHT, FIT_BOTTOM_LEFT, FIT_BOTTOM_RIGHT
    }
}
