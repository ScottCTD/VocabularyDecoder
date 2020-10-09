package xyz.scottc.vd.frames.functional;

import xyz.scottc.vd.frames.transitional.ListSelection;
import xyz.scottc.vd.utils.VDConstantsUtils;
import xyz.scottc.vd.utils.components.*;

import javax.swing.*;
import java.awt.*;

public class FunctionalFrame extends JFrame {

    protected static final int MARGIN = VDConstantsUtils.getSreenRectangle().width / 100;
    protected static final int SUB_MARGIN = MARGIN / 10;

    protected final UtilJPanel rootPanel = new UtilJPanel();
    protected final SpringLayout layout = new SpringLayout();

    protected final UtilJPanel initPanel = new UtilJPanel();
    protected final SpringLayout initPanelLayout = new SpringLayout();

    protected final UtilJPanel readyPanel = new UtilJPanel();
    protected final SpringLayout readyPanelLayout = new SpringLayout();

    protected final UtilJButton backButton = new UtilJButton("Back", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30, false);
    protected final UtilJButton hideTimerButton = new UtilJButton("Hide Timer", VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_20);

    protected final VDAmountDisplay amount = new VDAmountDisplay(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30.deriveFont(Font.PLAIN));
    protected final VDTimer timer = new VDTimer();

    protected final LineSeparator separator01 = new LineSeparator(LineSeparator.HORIZONTAL, VDConstantsUtils.getSreenRectangle().width);
    protected final LineSeparator separator02 = new LineSeparator(LineSeparator.HORIZONTAL, VDConstantsUtils.getSreenRectangle().width);

    protected boolean init = false;
    protected boolean suspend = false;

    public FunctionalFrame(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(VDConstantsUtils.getSreenRectangle());
    }

    protected void rootPanelHandler() {
        this.setContentPane(this.rootPanel);

        this.rootPanel.add(this.backButton);
        this.backButton.addActionListener(e -> VDConstantsUtils.switchFrame(this, new ListSelection()));

        this.rootPanel.add(this.separator01);

        this.rootPanel.add(this.timer);
        this.timer.setFont(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30.deriveFont(Font.PLAIN));

        this.rootPanel.add(this.hideTimerButton);
        this.hideTimerButton.setFont(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30.deriveFont(Font.PLAIN));
        this.hideTimerButton.addActionListener(e -> {
            String hide = "Hide Timer";
            String display = "Display Timer";
            if (this.timer.isVisible()) {
                this.timer.setVisible(false);
                this.hideTimerButton.setText(display);
            } else {
                this.timer.setVisible(true);
                this.hideTimerButton.setText(hide);
            }
        });

        this.rootPanel.add(this.amount);

        this.rootPanel.add(this.separator02);

        this.rootPanel.add(this.readyPanel);
        this.readyPanel.setLayout(this.readyPanelLayout);
        this.readyPanel.setBackGround(VDConstantsUtils.SELECTED_COLOR);

        this.rootPanel.add(this.initPanel);
        this.initPanel.setLayout(initPanelLayout);

        if (init) {
            this.readyPanel.setVisible(true);
        } else {
            this.initPanel.setVisible(false);
        }
    }

    protected void layoutHandler() {
        this.rootPanel.setLayout(this.layout);

        this.layout.putConstraint(SpringLayout.NORTH, this.backButton, MARGIN, SpringLayout.NORTH, this.rootPanel);
        this.layout.putConstraint(SpringLayout.WEST, this.backButton, MARGIN, SpringLayout.WEST, this.rootPanel);

        this.layout.putConstraint(SpringLayout.NORTH, this.separator01, MARGIN, SpringLayout.SOUTH, this.backButton);

        this.layout.putConstraint(SpringLayout.EAST, this.timer, -MARGIN * 2, SpringLayout.EAST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.NORTH, this.timer, SUB_MARGIN + 6, SpringLayout.SOUTH, this.separator01);

        this.layout.putConstraint(SpringLayout.EAST, this.hideTimerButton, -MARGIN * 2, SpringLayout.WEST, this.timer);
        this.layout.putConstraint(SpringLayout.NORTH, this.hideTimerButton, SUB_MARGIN, SpringLayout.SOUTH, this.separator01);

        this.layout.putConstraint(SpringLayout.WEST, this.amount, MARGIN, SpringLayout.WEST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.NORTH, this.amount, 0, SpringLayout.NORTH, this.timer);

        this.layout.putConstraint(SpringLayout.NORTH, this.separator02, SUB_MARGIN, SpringLayout.SOUTH, this.hideTimerButton);

        this.layout.putConstraint(SpringLayout.NORTH, this.initPanel, 0, SpringLayout.SOUTH, this.separator02);
        this.layout.putConstraint(SpringLayout.SOUTH, this.initPanel, 0, SpringLayout.SOUTH, this.rootPanel);
        this.layout.putConstraint(SpringLayout.WEST, this.initPanel, 0, SpringLayout.WEST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.EAST, this.initPanel, 0, SpringLayout.EAST, this.rootPanel);

        this.layout.putConstraint(SpringLayout.NORTH, this.readyPanel, 0, SpringLayout.SOUTH, this.separator02);
        this.layout.putConstraint(SpringLayout.SOUTH, this.readyPanel, 0, SpringLayout.SOUTH, this.rootPanel);
        this.layout.putConstraint(SpringLayout.WEST, this.readyPanel, 0, SpringLayout.WEST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.EAST, this.readyPanel, 0, SpringLayout.EAST, this.rootPanel);
    }

}
