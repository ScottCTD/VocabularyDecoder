package xyz.scottc.vd.frames.functional.orderedMode;

import xyz.scottc.vd.frames.functional.FunctionalFrame;
import xyz.scottc.vd.utils.ENText;
import xyz.scottc.vd.utils.VDConstantsUtils;
import xyz.scottc.vd.utils.components.LineSeparator;
import xyz.scottc.vd.utils.components.UtilJButton;
import xyz.scottc.vd.utils.components.UtilJLabel;
import xyz.scottc.vd.utils.components.VDTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OrderedMode extends FunctionalFrame {

    private final UtilJLabel readyLabel = new UtilJLabel(ENText.ORDERED_MODE_READY_TEXT, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_60);

    private final UtilJButton nextButton = new UtilJButton("Next", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);
    private final UtilJButton preButton = new UtilJButton("Previous", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);
    private final UtilJButton answerButton = new UtilJButton("Answer", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);
    private final UtilJButton reviewButton = new UtilJButton("Review", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);

    private final UtilJButton suspendButton = new UtilJButton("Suspend", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);

    private final UtilJLabel preLabel = new UtilJLabel(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);
    private final UtilJLabel nextLabel = new UtilJLabel(VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);

    private final VDTextArea Q = new VDTextArea(2, 10, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_120, false, false);
    private final JScrollPane QView = new JScrollPane(this.Q, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private final VDTextArea I = new VDTextArea(1, 10, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_120, true, false);
    private final JScrollPane IView = new JScrollPane(this.I, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private final VDTextArea A = new VDTextArea(1, 10, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_120, false, false);
    private final JScrollPane AView = new JScrollPane(this.A, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private final LineSeparator lineHelper01 = new LineSeparator(LineSeparator.VERTICAL, VDConstantsUtils.getSreenRectangle().height);

    public OrderedMode() throws HeadlessException {
        super("Ordered Mode");

        this.rootPanelHandler();
        this.layoutHandler();
    }

    @Override
    protected void rootPanelHandler() {
        super.rootPanelHandler();

        super.backButton.addKeyListener(new ReadyPanelKeyBoardListener());

        super.rootPanel.add(this.nextButton);

        super.rootPanel.add(this.preButton);

        super.rootPanel.add(this.answerButton);

        super.rootPanel.add(this.reviewButton);

        super.rootPanel.add(this.suspendButton);

        super.readyPanel.add(this.readyLabel);
        this.readyLabel.setBackground(this.readyLabel.getParent().getBackground());

        this.initPanel.add(this.QView);
        this.Q.setLineWrap(true);
        this.Q.setWrapStyleWord(true);
        this.Q.setBackground(super.rootPanel.getBackground());

        this.initPanel.add(this.IView);
        this.I.grabFocus();

        this.initPanel.add(this.AView);
        this.A.setBackground(super.rootPanel.getBackground());

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                UtilJLabel label = (UtilJLabel) e.getSource();
                label.setBackground(VDConstantsUtils.SELECTED_COLOR);
                if (label.equals(preLabel)) {
                    label.setText("Previous");
                } else if (label.equals(nextLabel)) {
                    label.setText("Next");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                UtilJLabel label = (UtilJLabel) e.getSource();
                label.setBackground(label.getParent().getBackground());
                label.setText(VDConstantsUtils.EMPTY);
            }
        };
        this.initPanel.add(this.preLabel);
        this.preLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.preLabel.addMouseListener(mouseListener);

        this.initPanel.add(this.nextLabel);
        this.nextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.nextLabel.addMouseListener(mouseListener);

        this.initPanel.add(this.lineHelper01);
        this.lineHelper01.setVisible(true);
    }

    @Override
    protected void layoutHandler() {
        super.layoutHandler();

        super.layout.putConstraint(SpringLayout.EAST, this.nextButton, -MARGIN, SpringLayout.EAST, super.rootPanel);
        super.layout.putConstraint(SpringLayout.NORTH, this.nextButton, MARGIN, SpringLayout.NORTH, super.rootPanel);

        super.layout.putConstraint(SpringLayout.NORTH, this.preButton, 0, SpringLayout.NORTH, this.nextButton);
        super.layout.putConstraint(SpringLayout.EAST, this.preButton, -MARGIN, SpringLayout.WEST, this.nextButton);

        super.layout.putConstraint(SpringLayout.NORTH, this.answerButton, 0, SpringLayout.NORTH, this.nextButton);
        super.layout.putConstraint(SpringLayout.EAST, this.answerButton, -MARGIN, SpringLayout.WEST, this.preButton);

        super.layout.putConstraint(SpringLayout.NORTH, this.reviewButton, 0, SpringLayout.NORTH, this.nextButton);
        super.layout.putConstraint(SpringLayout.EAST, this.reviewButton, -MARGIN, SpringLayout.WEST, this.answerButton);

        super.layout.putConstraint(SpringLayout.NORTH, this.suspendButton, 0, SpringLayout.NORTH, super.hideTimerButton);
        super.layout.putConstraint(SpringLayout.EAST, this.suspendButton, -MARGIN, SpringLayout.WEST, super.hideTimerButton);

        //ready panel
        super.readyPanelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.readyLabel, 0, SpringLayout.HORIZONTAL_CENTER, super.readyPanel);
        super.readyPanelLayout.putConstraint(SpringLayout.VERTICAL_CENTER, this.readyLabel, 0, SpringLayout.VERTICAL_CENTER, super.readyPanel);

        //init panel
        super.initPanelLayout.putConstraint(SpringLayout.NORTH, this.preLabel, 0, SpringLayout.NORTH, super.initPanel);
        super.initPanelLayout.putConstraint(SpringLayout.WEST, this.preLabel, 0, SpringLayout.WEST, super.initPanel);
        super.initPanelLayout.putConstraint(SpringLayout.SOUTH, this.preLabel, 0, SpringLayout.SOUTH, super.initPanel);
        super.initPanelLayout.putConstraint(SpringLayout.EAST, this.preLabel, -MARGIN * 2, SpringLayout.WEST, this.QView);

        super.initPanelLayout.putConstraint(SpringLayout.NORTH, this.QView, MARGIN * 4, SpringLayout.NORTH, super.initPanel);
        super.initPanelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.QView, 0, SpringLayout.HORIZONTAL_CENTER, super.initPanel);

        super.initPanelLayout.putConstraint(SpringLayout.VERTICAL_CENTER, this.IView, 0, SpringLayout.VERTICAL_CENTER, this.lineHelper01);
        super.initPanelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.IView, 0, SpringLayout.HORIZONTAL_CENTER, this.QView);

        super.initPanelLayout.putConstraint(SpringLayout.SOUTH, this.AView, -MARGIN, SpringLayout.SOUTH, super.initPanel);
        super.initPanelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.AView, 0, SpringLayout.HORIZONTAL_CENTER, this.QView);

        super.initPanelLayout.putConstraint(SpringLayout.NORTH, this.nextLabel, 0, SpringLayout.NORTH, super.initPanel);
        super.initPanelLayout.putConstraint(SpringLayout.SOUTH, this.nextLabel, 0, SpringLayout.SOUTH, super.initPanel);
        super.initPanelLayout.putConstraint(SpringLayout.EAST, this.nextLabel, 0, SpringLayout.EAST, super.initPanel);
        super.initPanelLayout.putConstraint(SpringLayout.WEST, this.nextLabel, MARGIN * 2, SpringLayout.EAST, this.QView);

        super.layout.putConstraint(SpringLayout.NORTH, this.lineHelper01, 0, SpringLayout.NORTH, super.rootPanel);
        super.layout.putConstraint(SpringLayout.SOUTH, this.lineHelper01, 0, SpringLayout.SOUTH, super.rootPanel);
    }

    private class ReadyPanelKeyBoardListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (!init) {
                readyPanel.setVisible(false);
                initPanel.setVisible(true);
                init = true;
                I.grabFocus();
            }
        }
    }
}
