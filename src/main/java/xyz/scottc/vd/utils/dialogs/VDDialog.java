package xyz.scottc.vd.utils.dialogs;

import xyz.scottc.vd.utils.VDUtils;
import xyz.scottc.vd.utils.components.LineSeparator;
import xyz.scottc.vd.utils.components.UtilJLabel;
import xyz.scottc.vd.utils.components.UtilJPanel;

import javax.swing.*;
import java.awt.*;

public class VDDialog extends JDialog {

    protected final UtilJPanel rootPanel = new UtilJPanel();
    protected final SpringLayout layout = new SpringLayout();

    /**
     * InfoLabel occupy 1 / 3 of the whole dialog by default
     * the icon should be 96 * 96 maximum
     */
    protected final UtilJLabel info = new UtilJLabel();

    protected final LineSeparator separator01 = new LineSeparator(LineSeparator.HORIZONTAL, width);

    protected static int width = VDUtils.getScreenRectangle().width / 3;
    protected static int height = VDUtils.getScreenRectangle().height / 3;

    protected static final int MARGIN = height / 50;

    protected VDDialog(Frame owner, String title) {
        super(owner, title);
        this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.setSize(width, height);
        this.setLocation(owner.getX() + (owner.getWidth() - width) / 2,
                owner.getY() + (owner.getHeight() - height) / 2);
        this.info.setVerticalTextPosition(JLabel.TOP);
        this.info.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    protected VDDialog(Frame owner, String title, String info, Font font) {
        this(owner, title);
        this.info.setText(info);
        this.info.setFont(font);
    }

    protected VDDialog(Frame owner, String title, String info, Font font, Icon icon) {
        this(owner, title, info, font);
        this.info.setIcon(icon);
    }

    protected void setIcon(Icon icon) {
        this.info.setIcon(icon);
    }

    protected void rootPanelHandler() {
        this.setContentPane(this.rootPanel);
        this.rootPanel.add(this.info);

        this.rootPanel.add(this.separator01);
        this.separator01.setVisible(false);
    }

    protected void layoutHandler() {
        this.rootPanel.setLayout(this.layout);

        this.layout.putConstraint(SpringLayout.NORTH, this.info, MARGIN, SpringLayout.NORTH, this.rootPanel);
        this.layout.putConstraint(SpringLayout.SOUTH, this.info, height / 3, SpringLayout.NORTH, this.rootPanel);
        this.layout.putConstraint(SpringLayout.WEST, this.info, MARGIN, SpringLayout.WEST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.EAST, this.info, -MARGIN, SpringLayout.EAST, this.rootPanel);

        this.layout.putConstraint(SpringLayout.NORTH, this.separator01, MARGIN, SpringLayout.SOUTH, this.info);
    }
}
