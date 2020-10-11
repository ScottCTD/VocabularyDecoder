package xyz.scottc.vd.utils.components;

import xyz.scottc.vd.utils.FileUtils;

import java.awt.*;
import java.io.IOException;

public class VDCancelButton extends UtilJButton {

    public VDCancelButton(Font font, int iconSize) {
        super(font);
        this.setText("Cancel");
        try {
            switch (iconSize) {
                case 16:
                    this.setIcon(FileUtils.createImageIcon("/images/icons/cancel/cancel16.png"));
                    break;
                case 32:
                    this.setIcon(FileUtils.createImageIcon("/images/icons/cancel/cancel32.png"));
                    break;
                case 48:
                    this.setIcon(FileUtils.createImageIcon("/images/icons/cancel/cancel48.png"));
                    break;
                case 96:
                    this.setIcon(FileUtils.createImageIcon("/images/icons/cancel/cancel96.png"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
