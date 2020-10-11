package xyz.scottc.vd.utils.components;

import xyz.scottc.vd.utils.FileUtils;

import java.awt.*;
import java.io.IOException;

public class VDBackButton extends UtilJButton {

    /**
     * create a back button
     *
     * @param font     the font of "Back"
     * @param iconSize 28 32 38 48 64
     */
    public VDBackButton(Font font, int iconSize) {
        super(font);
        this.setText("Back");
        try {
            switch (iconSize) {
                case 28:
                    this.setIcon(FileUtils.createImageIcon("/images/icons/BackButton/back28.png"));
                    break;
                case 32:
                    this.setIcon(FileUtils.createImageIcon("/images/icons/BackButton/back32.png"));
                    break;
                case 38:
                    this.setIcon(FileUtils.createImageIcon("/images/icons/BackButton/back38.png"));
                    break;
                case 48:
                    this.setIcon(FileUtils.createImageIcon("/images/icons/BackButton/back48.png"));
                    break;
                case 64:
                    this.setIcon(FileUtils.createImageIcon("/images/icons/BackButton/back64.png"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
