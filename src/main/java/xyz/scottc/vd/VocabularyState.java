package xyz.scottc.vd;

public enum VocabularyState {
    CORRECT, INCORRECT, NOT_ANSWERED, ANSWERED;

    @Override
    public String toString() {
        switch (this) {
            case CORRECT:
                return "CORRECT";
            case INCORRECT:
                return "INCORRECT";
            case ANSWERED:
                return "ANSWERED";
            case NOT_ANSWERED:
                return "NOT ANSWERED";
        }
        return null;
    }
}
