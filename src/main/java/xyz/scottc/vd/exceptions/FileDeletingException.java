package xyz.scottc.vd.exceptions;

import java.io.File;
import java.nio.file.FileSystemException;

public class FileDeletingException extends FileSystemException {

    public FileDeletingException(File file) {
        super("Failed to delete: " + file.getAbsolutePath());
    }
}
