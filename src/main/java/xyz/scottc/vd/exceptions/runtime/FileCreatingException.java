package xyz.scottc.vd.exceptions.runtime;

public class FileCreatingException extends RuntimeException {

    public FileCreatingException() {
    }

    public FileCreatingException(String message) {
        super(message);
    }
}
