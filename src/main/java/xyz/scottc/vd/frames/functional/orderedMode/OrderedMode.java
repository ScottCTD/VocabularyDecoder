package xyz.scottc.vd.frames.functional.orderedMode;

import xyz.scottc.vd.Input;
import xyz.scottc.vd.VDList;
import xyz.scottc.vd.frames.functional.FunctionalFrame;
import xyz.scottc.vd.utils.ENText;
import xyz.scottc.vd.utils.VDConstantsUtils;
import xyz.scottc.vd.utils.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OrderedMode extends FunctionalFrame {

    private final UtilJPanel suspendPanel = new UtilJPanel();
    private final SpringLayout suspendPanelLayout = new SpringLayout();
    private final UtilJLabel suspendLabel = new UtilJLabel(ENText.ORDERED_MODE_SUSPEND_TEXT, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_60);

    private final UtilJLabel readyLabel = new UtilJLabel(ENText.ORDERED_MODE_READY_TEXT, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);
    private final UtilJButton VQButton = new UtilJButton("Vocabularies as Questions", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);
    private final UtilJButton MQButton = new UtilJButton("Meanings as Questions", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);

    private final UtilJButton nextButton = new UtilJButton("Next", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);
    private final UtilJButton preButton = new UtilJButton("Previous", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);
    private final UtilJButton answerButton = new UtilJButton("Answer", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);
    private final UtilJButton reviewButton = new UtilJButton("Review", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);
    private final UtilJLabel currentListLabel = new UtilJLabel("Current VD List: ", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);

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

    private final VDList vdList;

    public static boolean vocabularyQ;
    private boolean isAnswerShown;

    public OrderedMode(VDList vdList) throws HeadlessException {
        this.vdList = vdList;
        this.setTitle("Ordered Mode - " + this.vdList.getSimpleName());
        this.currentListLabel.setText("Current VD List: " + this.vdList.getSimpleName());
        this.rootPanelHandler();
        this.layoutHandler();
    }

    @Override
    protected void rootPanelHandler() {
        super.rootPanelHandler();

        //root panel
        super.rootPanel.add(this.nextButton);
        this.nextButton.addActionListener(e -> this.next());

        super.rootPanel.add(this.preButton);
        this.preButton.addActionListener(e -> this.pre());

        super.rootPanel.add(this.answerButton);
        this.answerButton.addActionListener(e -> this.answer());

        super.rootPanel.add(this.reviewButton);
        this.reviewButton.addActionListener(e -> this.review());

        super.rootPanel.add(this.suspendButton);
        this.suspendButton.addActionListener(e -> this.suspend());

        super.rootPanel.add(this.currentListLabel);
        this.currentListLabel.setBackground(this.rootPanel.getBackground());

        //suspend panel
        super.rootPanel.add(this.suspendPanel);
        this.suspendPanel.setLayout(this.suspendPanelLayout);
        this.suspendPanel.setBackGround(VDConstantsUtils.SELECTED_COLOR);
        this.suspendPanel.setVisible(false);
        this.suspendPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (init && suspend) {
                    suspendPanel.setVisible(false);
                    initPanel.setVisible(true);
                    timer.continu();
                    suspend = false;
                }
            }
        });

        this.suspendPanel.add(this.suspendLabel);
        this.suspendLabel.setBackground(VDConstantsUtils.SELECTED_COLOR);

        //ready panel
        super.readyPanel.add(this.readyLabel);
        this.readyLabel.setBackground(this.readyLabel.getParent().getBackground());

        super.readyPanel.add(this.VQButton);
        this.VQButton.addActionListener(e -> this.init());

        super.readyPanel.add(this.MQButton);
        this.MQButton.addActionListener(e -> {
            this.vdList.interconvertQAList();
            this.init();
        });

        //init panel
        this.initPanel.add(this.QView);
        this.Q.setLineWrap(true);
        this.Q.setWrapStyleWord(true);
        this.Q.setBackground(super.rootPanel.getBackground());

        this.initPanel.add(this.IView);
        this.I.addKeyListener(new InputKeyListener());

        this.initPanel.add(this.AView);
        this.A.setBackground(super.rootPanel.getBackground());

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UtilJLabel label = (UtilJLabel) e.getSource();
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if (label.equals(preLabel)) {
                    pre();
                } else if (label.equals(nextLabel)) {
                    next();
                }
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
                label.setBorder(BorderFactory.createEmptyBorder());
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

    private void init() {
        readyPanel.setVisible(false);
        initPanel.setVisible(true);

        Q.setText(vdList.getQuestion());

        timer.startFromZero();
        amount.setTotalAmount(vdList.getQs().size());
        amount.setCurrentAmount(vdList.getIndex() + 1);

        I.grabFocus();

        init = true;
    }

    private void next() {
        if (super.init && !super.suspend) {
            //save
            this.vdList.setInputContent(this.I.getText());
            if (this.vdList.next()) {
                this.updateUI();
            }
        }
    }

    private void pre() {
        if (super.init && !super.suspend) {
            //save
            this.vdList.setInputContent(this.I.getText());
            if (this.vdList.pre()) {
                this.updateUI();
            }
        }
    }

    private void updateUI() {
        this.Q.setText(this.vdList.getQuestion());
        this.I.setText(this.vdList.getInput().toString());
        this.I.grabFocus();

        if (this.isAnswerShown) {
            if (this.vdList.getInput().getState() != Input.InputState.NOT_ANSWERED) {
                this.updateAnswer();
            } else {
                this.disableAnswer();
            }
        }

        amount.setCurrentAmount(vdList.getIndex() + 1);
    }

    private void answer() {
        if (super.init && !super.suspend) {
            if (this.isAnswerShown) {
                this.disableAnswer();
            } else {
                this.updateAnswer();
            }
            this.I.grabFocus();
        }
    }

    private void disableAnswer() {
        this.A.setText(VDConstantsUtils.EMPTY);
        this.IView.setBorder(BorderFactory.createEmptyBorder());
        this.isAnswerShown = false;
    }

    private void updateAnswer() {
        this.A.setText(this.vdList.getAnswer());
        switch (this.vdList.getInput().getState()) {
            case CORRECT:
                this.IView.setBorder(BorderFactory.createLineBorder(Input.CORRECT, 3));
                break;
            case INCORRECT:
                this.IView.setBorder(BorderFactory.createLineBorder(Input.INCORRECT, 3));
                break;
        }
        this.isAnswerShown = true;
    }

    private void review() {
        if (super.init) {
            System.out.println("review");
        }
    }

    private void suspend() {
        if (super.init && !super.suspend) {
            this.timer.suspend();
            this.initPanel.setVisible(false);
            this.suspendPanel.setVisible(true);
            super.suspend = true;
        }
    }

    @Override
    protected void layoutHandler() {
        super.layoutHandler();

        //root panel
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

        super.layout.putConstraint(SpringLayout.WEST, this.currentListLabel, MARGIN, SpringLayout.EAST, super.backButton);
        super.layout.putConstraint(SpringLayout.NORTH, this.currentListLabel, MARGIN + 4, SpringLayout.NORTH, super.rootPanel);

        super.layout.putConstraint(SpringLayout.NORTH, this.suspendPanel, 0, SpringLayout.SOUTH, super.separator02);
        super.layout.putConstraint(SpringLayout.SOUTH, this.suspendPanel, 0, SpringLayout.SOUTH, super.rootPanel);
        super.layout.putConstraint(SpringLayout.WEST, this.suspendPanel, 0, SpringLayout.WEST, super.rootPanel);
        super.layout.putConstraint(SpringLayout.EAST, this.suspendPanel, 0, SpringLayout.EAST, super.rootPanel);

        //ready panel
        super.readyPanelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.readyLabel, 0, SpringLayout.HORIZONTAL_CENTER, super.readyPanel);
        super.readyPanelLayout.putConstraint(SpringLayout.VERTICAL_CENTER, this.readyLabel, 0, SpringLayout.VERTICAL_CENTER, super.readyPanel);

        super.readyPanelLayout.putConstraint(SpringLayout.SOUTH, this.VQButton, -MARGIN, SpringLayout.NORTH, this.readyLabel);
        super.readyPanelLayout.putConstraint(SpringLayout.EAST, this.VQButton, -MARGIN, SpringLayout.HORIZONTAL_CENTER, super.readyPanel);

        super.readyPanelLayout.putConstraint(SpringLayout.NORTH, this.MQButton, 0, SpringLayout.NORTH, this.VQButton);
        super.readyPanelLayout.putConstraint(SpringLayout.WEST, this.MQButton, MARGIN, SpringLayout.HORIZONTAL_CENTER, super.readyPanel);

        //suspend panel
        this.suspendPanelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.suspendLabel, 0, SpringLayout.HORIZONTAL_CENTER, this.suspendPanel);
        this.suspendPanelLayout.putConstraint(SpringLayout.VERTICAL_CENTER, this.suspendLabel, 0, SpringLayout.VERTICAL_CENTER, this.suspendPanel);

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

    private class InputKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (init && !suspend) {
                //单键判断： enter = 10  下箭头 = 40  上箭头 38
                switch (e.getKeyCode()) {
                    case 10:
                    case 40:
                        try {
                            next();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        break;
                    case 38:
                        try {
                            pre();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        break;
                }

                //save
                vdList.getInput().setContent(I.getText());

                //judge
                if (!OrderedMode.vocabularyQ) {
                    if (!I.getText().equals(VDConstantsUtils.EMPTY)) {
                        vdList.setInput(vdList.judgeEn());
                    }
                } else {
                    if (!I.getText().equals(VDConstantsUtils.EMPTY)) {
                    }
                }

                //update answer if needed
                if (isAnswerShown) updateAnswer();
            }
        }
    }
}
