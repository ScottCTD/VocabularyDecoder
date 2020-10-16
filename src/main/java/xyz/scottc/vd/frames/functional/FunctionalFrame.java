package xyz.scottc.vd.frames.functional;

import xyz.scottc.vd.frames.transitional.ListSelection;
import xyz.scottc.vd.utils.FileUtils;
import xyz.scottc.vd.utils.VDConstants;
import xyz.scottc.vd.utils.VDUtils;
import xyz.scottc.vd.utils.components.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class FunctionalFrame extends JFrame {

    protected static final int MARGIN = VDUtils.getScreenRectangle().width / 100;
    protected static final int SUB_MARGIN = MARGIN / 10;

    protected final UtilJPanel rootPanel = new UtilJPanel();
    protected final SpringLayout layout = new SpringLayout();

    protected final UtilJPanel initPanel = new UtilJPanel();
    protected final SpringLayout initPanelLayout = new SpringLayout();

    protected final UtilJPanel readyPanel = new UtilJPanel();
    protected final SpringLayout readyPanelLayout = new SpringLayout();

    protected final VDBackButton backButton = new VDBackButton(VDConstants.MICROSOFT_YAHEI_BOLD_32, 32);
    protected final UtilJButton saveButton = new UtilJButton("Save", VDConstants.MICROSOFT_YAHEI_BOLD_32);
    protected final UtilJButton clearButton = new UtilJButton("Clear", VDConstants.MICROSOFT_YAHEI_BOLD_32);

    protected final UtilJButton hideTimerButton = new UtilJButton("Hide Timer", VDConstants.MICROSOFT_YAHEI_PLAIN_20);
    protected final UtilJLabel currentListLabel = new UtilJLabel(VDConstants.MICROSOFT_YAHEI_BOLD_32);
    protected final VDAmountDisplay amount = new VDAmountDisplay(VDConstants.MICROSOFT_YAHEI_BOLD_32.deriveFont(Font.PLAIN));
    protected final VDTimer timer = new VDTimer();

    protected final LineSeparator separator01 = new LineSeparator(LineSeparator.HORIZONTAL, VDUtils.getScreenRectangle().width);
    protected final LineSeparator separator02 = new LineSeparator(LineSeparator.HORIZONTAL, VDUtils.getScreenRectangle().width);

    protected boolean init = false;
    protected boolean suspend = false;

    public FunctionalFrame() throws HeadlessException {
        try {
            this.setIconImage(ImageIO.read(FileUtils.getInputStream("/images/icons/WindowsIcon.png")));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(VDUtils.getScreenRectangle());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    protected void rootPanelHandler() {
        this.setContentPane(this.rootPanel);

        this.rootPanel.add(this.backButton);
        this.backButton.addActionListener(e -> VDUtils.switchFrame(this, new ListSelection()));

        this.rootPanel.add(this.saveButton);

        this.rootPanel.add(this.clearButton);

        this.rootPanel.add(this.currentListLabel);

        this.currentListLabel.setBackground(this.rootPanel.getBackground());

        this.rootPanel.add(this.separator01);

        this.rootPanel.add(this.timer);
        this.timer.setFont(VDConstants.MICROSOFT_YAHEI_BOLD_32.deriveFont(Font.PLAIN));

        this.rootPanel.add(this.hideTimerButton);
        this.hideTimerButton.setFont(VDConstants.MICROSOFT_YAHEI_BOLD_32.deriveFont(Font.PLAIN));
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
        this.readyPanel.setBackGround(VDConstants.SELECTED_COLOR);

        this.rootPanel.add(this.initPanel);
        this.initPanel.setLayout(initPanelLayout);

        if (init) {
            this.readyPanel.setVisible(true);
        } else {
            this.initPanel.setVisible(false);
        }
    }

    protected void iconHandler() {
        try {
            this.saveButton.setIcon(FileUtils.createImageIcon("/images/icons/OrderedMode/save.png"));
            this.clearButton.setIcon(FileUtils.createImageIcon("/images/icons/OrderedMode/clear.png"));
            this.currentListLabel.setIcon(FileUtils.createImageIcon("/images/icons/FunctionalFrame/vdList.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    protected void layoutHandler() {
        this.rootPanel.setLayout(this.layout);

        this.layout.putConstraint(SpringLayout.NORTH, this.backButton, MARGIN, SpringLayout.NORTH, this.rootPanel);
        this.layout.putConstraint(SpringLayout.WEST, this.backButton, MARGIN, SpringLayout.WEST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.WEST, this.clearButton, MARGIN, SpringLayout.EAST, this.backButton);
        this.layout.putConstraint(SpringLayout.NORTH, this.clearButton, 0, SpringLayout.NORTH, this.backButton);
        this.layout.putConstraint(SpringLayout.WEST, this.saveButton, MARGIN, SpringLayout.EAST, this.clearButton);
        this.layout.putConstraint(SpringLayout.NORTH, this.saveButton, 0, SpringLayout.NORTH, this.backButton);

        this.layout.putConstraint(SpringLayout.NORTH, this.separator01, MARGIN, SpringLayout.SOUTH, this.backButton);

        this.layout.putConstraint(SpringLayout.WEST, this.currentListLabel, MARGIN, SpringLayout.WEST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.NORTH, this.currentListLabel, 0, SpringLayout.NORTH, this.timer);

        this.layout.putConstraint(SpringLayout.EAST, this.timer, -MARGIN * 2, SpringLayout.EAST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.NORTH, this.timer, SUB_MARGIN + 6, SpringLayout.SOUTH, this.separator01);

        this.layout.putConstraint(SpringLayout.EAST, this.hideTimerButton, -MARGIN * 2, SpringLayout.WEST, this.timer);
        this.layout.putConstraint(SpringLayout.NORTH, this.hideTimerButton, SUB_MARGIN, SpringLayout.SOUTH, this.separator01);

        this.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.amount, 0, SpringLayout.HORIZONTAL_CENTER, this.rootPanel);
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
