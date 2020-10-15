package xyz.scottc.vd.utils;

import xyz.scottc.vd.core.Input;
import xyz.scottc.vd.core.VDList;

public class ChineseCorrection {

    private final String answer;
    private final String input;

    private final Input.InputState state;

    public ChineseCorrection(String answer, String input) {
        this.answer = answer;
        this.input = input;
        this.state = this.judge();
    }

    private Input.InputState judge() {

        // EMPTY -> NOT_ANSWERED
        if (this.input.equals(VDConstants.EMPTY)) {
            return Input.InputState.NOT_ANSWERED;
        }

        // completely equal -> CORRECT
        if (this.answer.replaceAll(VDConstants.SPACE, VDConstants.EMPTY)
                .equals(this.input.replaceAll(VDConstants.SPACE, VDConstants.EMPTY))) {
            return Input.InputState.CORRECT;
        }

        // answer > input but partly equal -> CORRECT
        if (this.answer.contains(VDConstants.SPACE)) {
            int index01 = 0;
            int startValue01 = 0;
            while ((index01 = this.answer.indexOf(VDConstants.SPACE, index01)) != -1) {
                String part01 = this.answer.substring(startValue01, index01).replaceAll(VDConstants.SPACE, VDConstants.EMPTY);
                String part02 = this.answer.substring(index01).replaceAll(VDConstants.SPACE, VDConstants.EMPTY);
                if (!this.input.contains(VDConstants.SPACE)) {
                    String input = this.input.trim().replaceAll(VDConstants.SPACE, VDConstants.EMPTY);
                    if (part01.equals(input) || part02.equals(input)) {
                        return Input.InputState.CORRECT;
                    }
                } else {
                    int index02 = 0;
                    int startValue02 = 0;
                    while ((index02 = this.input.indexOf(VDConstants.SPACE, index02)) != -1) {
                        String input01 = this.input.substring(startValue02, index02).replaceAll(VDConstants.SPACE, VDConstants.EMPTY);
                        String input02 = this.input.substring(index02).replaceAll(VDConstants.SPACE, VDConstants.EMPTY);
                        if (input01.equals(part01) || input02.equals(part01) || input01.equals(part02) || input02.equals(part02)) {
                            return Input.InputState.CORRECT;
                        }
                        startValue02 = index02;
                        index02++;
                    }
                }
                startValue01 = index01;
                index01++;
            }
        } else {
            int index = 0;
            int startValue = 0;
            while ((index = this.input.indexOf(VDConstants.SPACE, index)) != -1) {
                String input01 = this.input.substring(startValue, index).replaceAll(VDConstants.SPACE, VDConstants.EMPTY);
                String input02 = this.input.substring(index).replaceAll(VDConstants.SPACE, VDConstants.EMPTY);
                if (input01.equals(this.answer) || input02.equals(this.answer)) {
                    return Input.InputState.CORRECT;
                }
                startValue = index;
                index++;
            }
        }

        // two or above amount of characters in the answer are equal to that in input
        int equal = 0;
        for (char c : this.answer.replaceAll(VDConstants.SPACE, VDConstants.EMPTY).toCharArray()) {
            if (equal >= 2) {
                return Input.InputState.CORRECT;
            } else {
                for (char i : this.input.replaceAll(VDConstants.SPACE, VDConstants.EMPTY).toCharArray()) {
                    if (i == c) {
                        equal++;
                    }
                }
            }
        }

        return Input.InputState.UNSURE;
    }

    public static Input.InputState judge(VDList vdlist, int index) {
        ChineseCorrection chineseCorrection = new ChineseCorrection(vdlist.getAnswer(index),
                vdlist.getInput(index).getContent());
        return chineseCorrection.state;
    }

}
