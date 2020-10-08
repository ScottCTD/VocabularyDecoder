package xyz.scottc.vd.exceptions;

public class FileCreatingException extends RuntimeException {

    public FileCreatingException() {
    }

    public FileCreatingException(String message) {
        super(message);
    }
}
