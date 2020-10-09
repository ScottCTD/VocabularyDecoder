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

    protected static final int MARGIN = VDConstantsUtils.getSreenRectangle().width / 100;

    public TransitionalFrame(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(VDConstantsUtils.getSreenRectangle());
        this.setContentPane(this.rootPanel);
    }

    protected void rootPanelHandler() {

        this.rootPanel.add(this.titleLabel);

        this.rootPanel.add(this.aboutLabel);

        this.rootPanel.add(this.separator01);

        this.rootPanel.add(this.separator02);

        this.rootPanel.add(this.separator03);

        this.rootPanel.add(this.introLabel);
    }

    protected void layoutHandler() {
        this.rootPanel.setLayout(this.layout);

        this.layout.putConstraint(SpringLayout.WEST, this.titleLabel, MARGIN, SpringLayout.WEST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.NORTH, this.titleLabel, MARGIN, SpringLayout.NORTH, this.rootPanel);

        this.layout.putConstraint(SpringLayout.WEST, this.aboutLabel, MARGIN, SpringLayout.WEST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.SOUTH, this.aboutLabel, -MARGIN, SpringLayout.SOUTH, this.rootPanel);

        this.layout.putConstraint(SpringLayout.WEST, this.separator01, MARGIN, SpringLayout.EAST, this.titleLabel);

        this.layout.putConstraint(SpringLayout.NORTH, this.separator02, MARGIN, SpringLayout.SOUTH, this.titleLabel);
        this.layout.putConstraint(SpringLayout.EAST, this.separator02, 0, SpringLayout.WEST, this.separator01);

        this.layout.putConstraint(SpringLayout.SOUTH, this.separator03, -MARGIN, SpringLayout.NORTH, this.aboutLabel);
        this.layout.putConstraint(SpringLayout.EAST, this.separator03, 0, SpringLayout.WEST, this.separator01);

        this.layout.putConstraint(SpringLayout.NORTH, this.introLabel, MARGIN, SpringLayout.SOUTH, this.separator02);
        this.layout.putConstraint(SpringLayout.SOUTH, this.introLabel, -MARGIN, SpringLayout.NORTH, this.separator03);
        this.layout.putConstraint(SpringLayout.WEST, this.introLabel, MARGIN, SpringLayout.WEST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.EAST, this.introLabel, -MARGIN, SpringLayout.WEST, this.separator01);
    }
}
