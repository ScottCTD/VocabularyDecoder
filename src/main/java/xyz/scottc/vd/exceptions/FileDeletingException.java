package xyz.scottc.vd.exceptions;

import java.nio.file.FileSystemException;

public class FileDeletingException extends FileSystemException {

    public FileDeletingException(String file) {
        super(file);
    }
}
