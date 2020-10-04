package xyz.scottc.frames.commonModeFrame.component;

import xyz.scottc.VocabularyState;
import xyz.scottc.frames.commonModeFrame.CommonModeFrame;
import xyz.scottc.frames.commonModeFrame.dialog.review.Review;
import xyz.scottc.frames.commonModeFrame.dialog.review.ReviewData;
import xyz.scottc.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class FunctionPanel extends UtilJPanel {

    private final CommonModeFrame parentFrame;
    private final TopPanel topPanel;
    private final Review review;

    private final UtilJLabel previousQLabel = new UtilJLabel("Previous Question", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);
    private final VDTextArea previousQ = new VDTextArea(1, 11, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30, false, true);
    private final JScrollPane previousQScrollPane = new JScrollPane(this.previousQ);

    private final UtilJLabel previousALabel = new UtilJLabel("Your Previous Answer", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30);
    private final VDTextArea previousA = new VDTextArea(1, 11, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_30, false, true);
    private final JScrollPane previousAScrollPane = new JScrollPane(this.previousA);

    private final UtilJLabel QLabel = new UtilJLabel("Question", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_60);
    private final VDTextArea Q = new VDTextArea(1, 8, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_80, false, true);
    private final JScrollPane QScrollPane = new JScrollPane(this.Q);

    private final UtilJLabel ALabel = new UtilJLabel("Your Answer", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_60);
    private final VDTextArea A = new VDTextArea(1, 8, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_80, false, true);
    private final JScrollPane AScrollPane = new JScrollPane(this.A);

    private final UtilJLabel standardALabel = new UtilJLabel("Standard Answer", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_40);
    private final VDTextArea standardA = new VDTextArea(1, 11, VDConstantsUtils.MICROSOFT_YAHEI_BOLD_60, false, true);
    private final JScrollPane standardAScrollPane = new JScrollPane(this.standardA);

    private final UtilJButton previousButton = new UtilJButton("Previous", 35);
    private final UtilJButton nextButton = new UtilJButton("Next", 35);
    private final UtilJButton answerButton = new UtilJButton("Answer", 35);
    private final UtilJButton reviewButton = new UtilJButton("Review", 35);

    public boolean isListLoaded = false;
    public boolean init = false;
    private boolean suspend = false;
    private boolean isAnswerShown = false;

    private int index = 0;

    public List<Object> questionList;
    public List<Object> answerList;
    public List<String> inputList = new ArrayList<>();

    private final Object[] selectionValues = {"Vocabulary - Meaning", "Meaning - Vocabulary"};
    private Object selectionValue;

    public FunctionPanel(CommonModeFrame parent, TopPanel topPanel) {
        this.parentFrame = parent;
        this.topPanel = topPanel;
        this.review = new Review(this.parentFrame);
        this.setLayout(new FunctionPanelLayout());
        this.rootPanelHandler();
        this.scrollBarPolicyHandler();
        this.topPanelHandler();
    }

    private void rootPanelHandler() {
        this.add(this.previousQLabel);
        this.add(this.previousQScrollPane);

        this.add(this.previousALabel);
        this.add(this.previousAScrollPane);

        this.add(this.QLabel);
        this.add(this.QScrollPane);

        this.add(this.ALabel);
        this.add(this.AScrollPane);
        this.A.addKeyListener(new TextAreaKeyListener());

        this.add(this.standardALabel);
        this.add(this.standardAScrollPane);

        this.add(this.previousButton);
        this.previousButton.addActionListener(e -> {
            try {
                this.previous();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        this.add(this.nextButton);
        this.nextButton.addActionListener(e -> {
            try {
                this.next();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        this.add(this.answerButton);
        this.answerButton.addActionListener(e -> this.answer());

        this.add(this.reviewButton);
        this.reviewButton.addActionListener(e -> this.review());
        this.review.dataPanel.reviewTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    jumpToSelectedReviewItem(review.dataPanel.reviewTable);
                }
            }
        });
    }

    private void topPanelHandler() {
        this.topPanel.startButton.addActionListener(e -> this.start());
        this.topPanel.stopButton.addActionListener(e -> this.stop());
        this.topPanel.suspendButton.addActionListener(e -> this.suspend());
        this.topPanel.continueButton.addActionListener(e -> this.continu());
    }

    private void start() {
        if (this.isListLoaded) {
            //if not init, start the process
            if (!this.init) {
                //let user choose whether VM or MV
                Object input = JOptionPane.showInputDialog(this.parentFrame,
                        "V-M = You need to answer the meaning of a vocabulary\n" +
                                "M-V = You need to answer the vocabulary of corresponding meaning", "V-M or M-V?",
                        JOptionPane.QUESTION_MESSAGE, null, selectionValues, selectionValues[0]);
                switch (input.toString()) {
                    case "Vocabulary - Meaning":
                        this.selectionValue = this.selectionValues[0];
                        break;
                    case "Meaning - Vocabulary":
                        //默认单词列表是questionList，所以如果选这个模式就将questionList和answerList互换
                        VDConstantsUtils.interconvertList(this.questionList, this.answerList);
                        this.selectionValue = this.selectionValues[1];
                        break;
                }

                //init the index
                this.index = 0;

                //init the inputList
                this.initInputList();

                //init the Q
                this.Q.setText(this.questionList.get(this.index).toString());

                //init the A
                this.A.setEnabled(true);
                this.A.setText(this.inputList.get(this.index));
                this.A.grabFocus();

                //init the timer
                this.topPanel.timer.startFromZero();

                //init the amount display
                this.topPanel.amountDisplay.setCurrentAmount(this.index + 1);

                //init the review data
                this.initReviewData();

                //update the sign
                this.init = true;
            } else {
                VDConstantsUtils.showWarningMessage(this.parentFrame, "The process was already started!");
            }
        } else {
            VDConstantsUtils.showWarningMessage(this.parentFrame,
                    "Please first double click the vocabulary list you want to memorize to load it!");
        }
    }

    private void initInputList() {
        this.inputList.clear();
        for (int i = 0; i < this.questionList.size(); i++) {
            this.inputList.add(VDConstantsUtils.EMPTY);
        }
    }

    private void stop() {
        if (this.init) {
            int result = VDConstantsUtils.showQuestionMessgae(this.parentFrame, "Do you really want to stop?");
            if (result == JOptionPane.OK_OPTION) {
                //update the index
                this.index = 0;

                //clear all the display area
                this.previousQ.setText(VDConstantsUtils.EMPTY);
                this.previousA.setText(VDConstantsUtils.EMPTY);
                this.Q.setText(VDConstantsUtils.EMPTY);
                this.A.setText(VDConstantsUtils.EMPTY);
                this.standardA.setText(VDConstantsUtils.EMPTY);

                //disable the input area
                this.A.setEnabled(false);

                //clear all the lists
                this.questionList.clear();
                this.answerList.clear();
                this.initInputList();

                //stop the timer
                this.topPanel.timer.stop();

                //stop the amount display
                this.topPanel.amountDisplay.stop();

                //clear the review
                this.review.dataPanel.clear();

                //update the sign
                this.suspend = false;
                this.init = false;
                this.isListLoaded = false;
            }
        } else {
            VDConstantsUtils.showWarningMessage(this.parentFrame, "You had not stated it yet!");
        }
    }

    private void suspend() {
        if (this.init && !this.suspend) {
            //suspend the functional components
            this.A.setEnabled(false);
            this.review.setEnabled(false);
            this.previousButton.setEnabled(false);
            this.answerButton.setEnabled(false);
            this.nextButton.setEnabled(false);

            //pause the timer
            this.topPanel.timer.suspend();

            //update the sign
            this.suspend = true;
        }
    }

    private void continu() {
        if (this.init && this.suspend) {
            //enable the functional components
            this.A.setEnabled(true);
            this.review.setEnabled(true);
            this.previousButton.setEnabled(true);
            this.answerButton.setEnabled(true);
            this.nextButton.setEnabled(true);

            //continue the timer
            this.topPanel.timer.continu();

            //update the sign
            this.suspend = false;
        }
    }

    private void save() {
        this.inputList.set(this.index, this.A.getText());
    }

    private void next() throws Exception {
        if (this.init) {
            //index have not increase yet -> current question
            //save current input answer
            this.save();

            //update the previousA&Q
            this.previousQ.setText(this.questionList.get(this.index).toString());
            this.previousA.setText(this.inputList.get(this.index));

            //set the review data
            review.dataPanel.setData(review.dataPanel.allDataModel, generateReviewData(), index);

            //increase the index by 1 -> next vocabulary
            index++;

            //set Q and A
            this.Q.setText(this.questionList.get(this.index).toString());
            this.A.setText(this.inputList.get(this.index));

            //handle the answer
            if (this.isAnswerShown) {
                if (this.inputList.get(this.index).equals(VDConstantsUtils.EMPTY)) {
                    //if the answer had shown, and the next vocab is unknown to user, then make the answer disappear
                    this.disableAnswer();
                } else {
                    //if the answer had shown, and the user knows next vocab
                    //replace the current answer with the answer of next vocab
                    this.updateAnswer();
                }
            }

            //do some common stuff
            this.doTransition();
        }
    }

    private void previous() throws Exception {
        if (this.init && this.index > 0) {
            //save the current input
            this.save();

            //set the review data
            review.dataPanel.setData(review.dataPanel.allDataModel, generateReviewData(), index);

            //back to the previous index
            index--;

            //set the Q&A
            this.Q.setText(this.questionList.get(this.index).toString());
            this.A.setText(this.inputList.get(this.index));

            //index = 0 means that there is no previous one anymore
            //index = 1 means that the previous one of index = 1 that is index = 0 have no previous one
            if (index >= 1) {
                //set the previous Q&A
                this.previousQ.setText(this.questionList.get(this.index - 1).toString());
                this.previousA.setText(this.inputList.get(this.index - 1));
            } else {
                this.previousQ.setText(VDConstantsUtils.EMPTY);
                this.previousA.setText(VDConstantsUtils.EMPTY);
            }

            //handle the answer
            if (this.isAnswerShown) {
                //if the answer is shown, just update it if the previous button is hit
                this.updateAnswer();
            }

            //do common stuff
            this.doTransition();
        }
    }

    private void answer() {
        if (this.init) {
            if (!this.isAnswerShown) {
                //if the answer did not shown yet, then make it appear
                this.updateAnswer();
            } else {
                //if the answer is shown, just make it disappear
                this.disableAnswer();
            }
            this.A.grabFocus();
        }
    }

    private void updateAnswer() {
        this.standardA.setText(this.answerList.get(this.index).toString());

        //update the border of A to signify whether the input is correct
        this.updateBorder();

        //update the sign
        this.isAnswerShown = true;
    }

    private void disableAnswer() {
        this.standardA.setText(VDConstantsUtils.EMPTY);

        //make the border of A back to default state
        this.AScrollPane.setBorder(BorderFactory.createEmptyBorder());

        //update the sign
        this.isAnswerShown = false;
    }

    private VocabularyState judgeEnglish() {
        if (this.inputList.get(this.index).equals(this.answerList.get(this.index).toString())) {
            return VocabularyState.CORRECT;
        } else if (this.inputList.get(this.index).equals(VDConstantsUtils.EMPTY)) {
            return VocabularyState.EMPTY;
        } else {
            return VocabularyState.INCORRECT;
        }
    }

    private VocabularyState judgeChinese() {
        return VocabularyState.EMPTY;
    }

    private void updateBorder() {
        if (this.selectionValues[0].equals(this.selectionValue)) {
            //judge the meaning
            //WIP
            System.out.println("WIP");
        } else {
            //judge the vocabulary
            switch (this.judgeEnglish()) {
                case CORRECT:
                    this.AScrollPane.setBorder(BorderFactory.createLineBorder(new Color(0x00A74A), 5, false));
                    break;
                case INCORRECT:
                    this.AScrollPane.setBorder(BorderFactory.createLineBorder(new Color(0xFE4365), 5, false));
                    break;
                case EMPTY:
                    break;
            }
        }
    }

    private void review() {
        if (this.init) {
            this.review.setSize(1000, 700);
            this.review.setLocation(this.parentFrame.getLocation());
            this.review.setVisible(true);
        }
    }

    private void initReviewData() {
        for (int i = 0; i < this.questionList.size(); i++) {
            this.review.dataPanel.addData(new ReviewData(i + 1,
                    this.questionList.get(i).toString(), this.answerList.get(i).toString(),
                    this.inputList.get(i), VocabularyState.EMPTY));
        }
    }

    private void jumpToSelectedReviewItem(JTable table) {
        int row = table.getSelectedRow();
        int index = (int) table.getModel().getValueAt(row, 0);

        this.index = index - 1;

        //update the Q and A
        this.Q.setText(this.questionList.get(this.index).toString());
        this.A.setText(this.inputList.get(this.index));

        //update the previousQ and A
        if (this.index >= 1) {
            //set the previous Q&A
            this.previousQ.setText(this.questionList.get(this.index - 1).toString());
            this.previousA.setText(this.inputList.get(this.index - 1));
        } else {
            this.previousQ.setText(VDConstantsUtils.EMPTY);
            this.previousA.setText(VDConstantsUtils.EMPTY);
        }

        //handle the answer
        if (this.isAnswerShown) {
            if (!VDConstantsUtils.EMPTY.equals(this.inputList.get(this.index))) {
                /*if the answer is already shown and the selected vocabulary was answered,
                just update the answer! */
                this.updateAnswer();
            } else {
                /*if the answer is already shown but the selected vocabulary was not answered,
                just disable the answer! */
                this.disableAnswer();
            }
        }

        //close the review panel
        this.review.setVisible(false);
    }

    private ReviewData generateReviewData() throws Exception {
        switch (this.selectionValue.toString()) {
            case "Vocabulary - Meaning":
                return new ReviewData(this.index + 1, this.questionList.get(this.index).toString(),
                        this.answerList.get(this.index).toString(), this.inputList.get(index), this.judgeChinese());
            case "Meaning - Vocabulary":
                return new ReviewData(this.index + 1, this.questionList.get(this.index).toString(),
                        this.answerList.get(this.index).toString(), this.inputList.get(index), this.judgeEnglish());
        }
        throw new Exception("Invalid Mode Selection");
    }

    private void doTransition() {
        this.topPanel.amountDisplay.setCurrentAmount(this.index + 1);
        this.A.grabFocus();
    }

    private class TextAreaKeyListener implements KeyListener {

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
                            previous();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        break;
                }

                //save the input every time user input a char
                save();

                //if the answer is shown, update the border of A everytime the user modify it
                if (isAnswerShown) {
                    updateAnswer();
                }

                //set the review data
                try {
                    review.dataPanel.setData(review.dataPanel.allDataModel, generateReviewData(), index);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                //add incorrect data
                review.dataPanel.incorrectDataHandler();
            }
        }
    }

    private void scrollBarPolicyHandler() {
        this.previousQScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.previousAScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.QScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.AScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.standardAScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    }

    private class FunctionPanelLayout implements LayoutManager {

        @Override
        public void layoutContainer(Container parent) {
            int width = parent.getWidth();
            int height = parent.getHeight();
            int marginX = width / 100;
            int marginY = height / 100;

            //previousQLabel
            Dimension previousQLabelSize = previousQLabel.getPreferredSize();
            previousQLabel.setBounds(0, 0, previousQLabelSize.width, previousQLabelSize.height);
            //previousQ
            Dimension previousQSize = previousQScrollPane.getPreferredSize();
            previousQScrollPane.setBounds(0, previousQLabel.getY() + previousQSize.height + marginY,
                    previousQSize.width, previousQSize.height + 10);

            Dimension previousASize = previousAScrollPane.getPreferredSize();
            Dimension previousALabelSize = previousALabel.getPreferredSize();
            //previousA
            previousAScrollPane.setBounds(previousQScrollPane.getX() + previousQSize.width + marginX, previousQScrollPane.getY(),
                    previousASize.width, previousQSize.height + 10);
            //previousALabel
            previousALabel.setBounds(previousAScrollPane.getX(), 0,
                    previousALabelSize.width, previousALabelSize.height);

            //QLabel
            Dimension QLabelSize = QLabel.getPreferredSize();
            QLabel.setBounds(0, previousQScrollPane.getY() + previousQSize.height + marginY,
                    QLabelSize.width, QLabelSize.height);
            //Q
            Dimension QSize = QScrollPane.getPreferredSize();
            QScrollPane.setBounds(0, QLabel.getY() + QLabelSize.height + marginY,
                    QSize.width, QSize.height);

            //ALabel
            Dimension ALabelSize = ALabel.getPreferredSize();
            ALabel.setBounds(0, QScrollPane.getY() + QSize.height + marginY,
                    ALabelSize.width, ALabelSize.height);
            //A
            Dimension ASize = AScrollPane.getPreferredSize();
            AScrollPane.setBounds(0, ALabel.getY() + ALabelSize.height + marginY,
                    ASize.width, ASize.height);

            //standardA
            Dimension standardASize = standardAScrollPane.getPreferredSize();
            standardAScrollPane.setBounds(0, parentFrame.separator03.getY() - standardASize.height - marginY - 70,
                    standardASize.width, standardASize.height);
            //standardALabel
            Dimension standardALabelSize = standardALabel.getPreferredSize();
            standardALabel.setBounds(0, standardAScrollPane.getY() - standardALabelSize.height - marginY,
                    standardALabelSize.width, standardALabelSize.height);

            //nextButton
            Dimension nextButtonSize = nextButton.getPreferredSize();
            nextButton.setBounds(width - nextButtonSize.width - marginX * 4,
                    height - nextButtonSize.height - marginY * 3,
                    nextButtonSize.width, nextButtonSize.height);
            //previousButton
            Dimension previousButtonSize = previousButton.getPreferredSize();
            previousButton.setBounds(width - (width - nextButton.getX()) - previousButtonSize.width - marginX, nextButton.getY(),
                    previousButtonSize.width, previousButtonSize.height);
            //answerButton
            Dimension answerButtonSize = answerButton.getPreferredSize();
            answerButton.setBounds(width - (width - previousButton.getX()) - answerButtonSize.width - marginX, nextButton.getY(),
                    answerButtonSize.width, answerButtonSize.height);
            //reviewButton
            Dimension reviewButtonSize = reviewButton.getPreferredSize();
            reviewButton.setBounds(0, nextButton.getY(), reviewButtonSize.width, reviewButtonSize.height);
        }

        @Override
        public void addLayoutComponent(String name, Component comp) {

        }

        @Override
        public void removeLayoutComponent(Component comp) {

        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return null;
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return null;
        }
    }
}