package xyz.scottc.frames.commonModeFrame.dialog.review;

import xyz.scottc.VocabularyState;
import xyz.scottc.utils.VDConstantsUtils;

public class ReviewData {

    private int serialNumber;
    private String question;
    private String answer;
    private String input;
    private VocabularyState correct;

    public ReviewData() {
    }

    public ReviewData(int serialNumber, String question, String answer, String input, VocabularyState correct) {
        this.serialNumber = serialNumber;
        this.question = question;
        this.answer = answer;
        this.input = input;
        this.correct = correct;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public VocabularyState getCorrect() {
        return correct;
    }

    public void setCorrect(VocabularyState correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        if (this.input.equals(VDConstantsUtils.EMPTY)) {
            this.input = VDConstantsUtils.SPACE;
        }
        return this.serialNumber + VDConstantsUtils.SPACE + this.question + VDConstantsUtils.SPACE +
                this.answer + VDConstantsUtils.SPACE +
                this.input + VDConstantsUtils.SPACE +
                this.correct;
    }
}
