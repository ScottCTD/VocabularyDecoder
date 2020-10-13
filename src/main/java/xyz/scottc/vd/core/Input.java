package xyz.scottc.vd.core;

import xyz.scottc.vd.utils.VDConstants;

import java.awt.*;
import java.util.Objects;

public class Input {

    private String content;
    private InputState state;

    public static final Color CORRECT_COLOR = new Color(0x00A74A);
    public static final Color INCORRECT_COLOR = new Color(0xFE4365);

    public Input(String content) {
        if (content.equals(VDConstants.EMPTY)) {
            this.content = content;
            this.state = InputState.NOT_ANSWERED;
        } else {
            this.content = content;
            this.state = InputState.UNSURE;
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public InputState getState() {
        return state;
    }

    public void setState(InputState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.getContent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Input)) return false;
        Input input1 = (Input) o;
        return getContent().equals(input1.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent());
    }

    public enum InputState {
        CORRECT, INCORRECT, NOT_ANSWERED, UNSURE;

        @Override
        public String toString() {
            switch (this) {
                case CORRECT:
                    return "CORRECT";
                case INCORRECT:
                    return "INCORRECT";
                case UNSURE:
                    return "UNSURE";
                case NOT_ANSWERED:
                    return "NOT ANSWERED";
            }
            return null;
        }
    }
}
