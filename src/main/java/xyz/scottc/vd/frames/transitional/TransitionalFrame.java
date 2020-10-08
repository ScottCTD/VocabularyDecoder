package xyz.scottc.vd.frames.transitional;

import xyz.scottc.vd.utils.VDConstantsUtils;
import xyz.scottc.vd.utils.components.LineSeparator;
import xyz.scottc.vd.utils.components.UtilJLabel;
import xyz.scottc.vd.utils.components.UtilJPanel;

import javax.swing.*;
import java.awt.*;

public class TransitionalFrame extends JFrame {

    protected final UtilJPanel rootPanel = new UtilJPanel();
    protected final SpringLayout layout = new SpringLayout();

    protected final UtilJLabel titleLabel = new UtilJLabel(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_100);
    protected final UtilJLabel introLabel = new UtilJLabel(VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_20);
    protected final UtilJLabel aboutLabel = new UtilJLabel(VDConstantsUtils.ABOUT, VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_20);

    protected final LineSeparator separator01 = new LineSeparator(LineSeparator.VERTICAL, VDConstantsUtils.getSreenRectangle().height);
    protected final LineSeparator separator02 = new LineSeparator(LineSeparator.HORIZONTAL, VDConstantsUtils.getSreenRectangle().width);
    protected final LineSeparator separator03 = new LineSeparator(LineSeparator.HORIZONTAL, VDConstantsUtils.getSreenRectangle().width);

    protected static final int MARGIN = 20;

    public TransitionalFrame(String title) throws HeadlessException {
        super(title);
        this.setContentPane(this.rootPanel);
    }

    protected void rootPanelHandler() {
        this.rootPanel.setLayout(layout);

        this.rootPanel.add(this.titleLabel);

        this.rootPanel.add(this.aboutLabel);

        this.rootPanel.add(this.separator01);

        this.rootPanel.add(this.separator02);

        this.rootPanel.add(this.separator03);

        this.rootPanel.add(this.introLabel);
    }

    protected void layoutHandler() {
        layout.putConstraint(SpringLayout.WEST, this.titleLabel, MARGIN, SpringLayout.WEST, this.rootPanel);
        layout.putConstraint(SpringLayout.NORTH, this.titleLabel, MARGIN, SpringLayout.NORTH, this.rootPanel);

        layout.putConstraint(SpringLayout.WEST, this.aboutLabel, MARGIN, SpringLayout.WEST, this.rootPanel);
        layout.putConstraint(SpringLayout.SOUTH, this.aboutLabel, -MARGIN, SpringLayout.SOUTH, this.rootPanel);

        layout.putConstraint(SpringLayout.WEST, this.separator01, MARGIN, SpringLayout.EAST, this.titleLabel);

        layout.putConstraint(SpringLayout.NORTH, this.separator02, MARGIN, SpringLayout.SOUTH, this.titleLabel);
        layout.putConstraint(SpringLayout.EAST, this.separator02, 0, SpringLayout.WEST, this.separator01);

        layout.putConstraint(SpringLayout.SOUTH, this.separator03, -MARGIN, SpringLayout.NORTH, this.aboutLabel);
        layout.putConstraint(SpringLayout.EAST, this.separator03, 0, SpringLayout.WEST, this.separator01);

        layout.putConstraint(SpringLayout.NORTH, this.introLabel, MARGIN, SpringLayout.SOUTH, this.separator02);
        layout.putConstraint(SpringLayout.SOUTH, this.introLabel, -MARGIN, SpringLayout.NORTH, this.separator03);
        layout.putConstraint(SpringLayout.WEST, this.introLabel, MARGIN, SpringLayout.WEST, this.rootPanel);
        layout.putConstraint(SpringLayout.EAST, this.introLabel, -MARGIN, SpringLayout.WEST, this.separator01);
    }
}
