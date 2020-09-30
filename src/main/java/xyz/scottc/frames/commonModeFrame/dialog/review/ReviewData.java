package xyz.scottc.frames.commonModeFrame.dialog.review;

public class ReviewData {

    private int serialNumber;
    private String question;
    private String answer;
    private String input;
    private boolean correct;

    public ReviewData() {
    }

    public ReviewData(int serialNumber, String question, String answer, String input, boolean correct) {
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

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
