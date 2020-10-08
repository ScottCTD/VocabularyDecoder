package xyz.scottc.vd.frames.functional;

import xyz.scottc.vd.utils.VDConstantsUtils;
import xyz.scottc.vd.utils.components.*;

import javax.swing.*;
import java.awt.*;

public class FunctionalFrame extends JFrame {

    protected static final int MARGIN = VDConstantsUtils.getSreenRectangle().width / 100;

    protected final UtilJPanel rootPanel = new UtilJPanel();
    protected final SpringLayout layout = new SpringLayout();

    protected final UtilJButton backButton = new UtilJButton("Back", 40);
    protected final UtilJButton hideTimerButton = new UtilJButton("Hide Timer", 30);

    protected final VDAmountDisplay amount = new VDAmountDisplay(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30.deriveFont(Font.PLAIN));
    protected final VDTimer timer = new VDTimer();

    protected final LineSeparator separator01 = new LineSeparator(LineSeparator.HORIZONTAL, VDConstantsUtils.getSreenRectangle().width);
    protected final LineSeparator separator02 = new LineSeparator(LineSeparator.HORIZONTAL, VDConstantsUtils.getSreenRectangle().width);

    public FunctionalFrame(String title) throws HeadlessException {
        super(title);
        this.rootPanelHandler();
        this.layoutHandler();
    }

    protected void rootPanelHandler() {
        this.setContentPane(this.rootPanel);

        this.rootPanel.add(this.backButton);

        this.rootPanel.add(this.separator01);

        this.rootPanel.add(this.timer);
        this.timer.setFont(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30.deriveFont(Font.PLAIN));

        this.rootPanel.add(this.hideTimerButton);
        this.hideTimerButton.setFont(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30.deriveFont(Font.PLAIN));

        this.rootPanel.add(this.amount);

        this.rootPanel.add(this.separator02);
    }

    protected void layoutHandler() {
        this.rootPanel.setLayout(this.layout);

        this.layout.putConstraint(SpringLayout.NORTH, this.backButton, MARGIN, SpringLayout.NORTH, this.rootPanel);
        this.layout.putConstraint(SpringLayout.WEST, this.backButton, MARGIN, SpringLayout.WEST, this.rootPanel);

        this.layout.putConstraint(SpringLayout.NORTH, this.separator01, MARGIN, SpringLayout.SOUTH, this.backButton);

        this.layout.putConstraint(SpringLayout.EAST, this.timer, -MARGIN * 2, SpringLayout.EAST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.NORTH, this.timer, MARGIN + 6, SpringLayout.SOUTH, this.separator01);

        this.layout.putConstraint(SpringLayout.EAST, this.hideTimerButton, -MARGIN * 2, SpringLayout.WEST, this.timer);
        this.layout.putConstraint(SpringLayout.NORTH, this.hideTimerButton, MARGIN, SpringLayout.SOUTH, this.separator01);

        this.layout.putConstraint(SpringLayout.WEST, this.amount, MARGIN, SpringLayout.WEST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.NORTH, this.amount, 0, SpringLayout.NORTH, this.timer);

        this.layout.putConstraint(SpringLayout.NORTH, this.separator02, MARGIN, SpringLayout.SOUTH, this.hideTimerButton);
    }
}
