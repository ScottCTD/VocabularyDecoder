package xyz.scottc.vd.utils.components;

import xyz.scottc.vd.utils.FileUtils;

import java.awt.*;
import java.io.IOException;

public class VDConfirmButton extends UtilJButton {

    public VDConfirmButton(Font font, int iconSize) {
        super(font);
        this.setText("Confirm");
        try {
            switch (iconSize) {
                case 16:
                    this.setIcon(FileUtils.createImageIcon("/images/icons/confirm/confirm16.png"));
                    break;
                case 32:
                    this.setIcon(FileUtils.createImageIcon("/images/icons/confirm/confirm32.png"));
                    break;
                case 48:
                    this.setIcon(FileUtils.createImageIcon("/images/icons/confirm/confirm48.png"));
                    break;
                case 96:
                    this.setIcon(FileUtils.createImageIcon("/images/icons/confirm/confirm96.png"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
