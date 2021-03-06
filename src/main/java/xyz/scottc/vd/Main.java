package xyz.scottc.vd;

import xyz.scottc.vd.exceptions.runtime.FileCreatingException;
import xyz.scottc.vd.frames.transitional.Entry;
import xyz.scottc.vd.utils.FileUtils;
import xyz.scottc.vd.utils.VDConstants;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Main {

    public static File library;
    public static File internalLibrary;
    public static File externalLibrary;

    public static final List<File> INTERNAL_LISTS = new ArrayList<>();
    public static final List<File> EXTERNAL_LISTS = new ArrayList<>();

    public static void main(String[] args) {
        //Initialize the Directories (Libraries)
        initDirectory();

        //Copy the internal lists in the jar to library
        try {
            if (Main.class.getResource("/internalLibrary").getPath().contains("!")) {
                initInternalLibraryInJar();
            } else {
                initLibraryInIDE();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Initiate the Frame
        initFrame();
    }

    private static void initFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                IllegalAccessException e) {
            e.printStackTrace();
        }
        Entry frame = new Entry();
        //ListSelection frame = new ListSelection();
        //FunctionalFrame frame = new FunctionalFrame("Functional Frame");
        //OrderedMode frame = new OrderedMode();
        frame.setVisible(true);
    }

    private static void initDirectory() {
        //Create the library files
        File directory = FileUtils.getDirectoryFile(Main.class);
        if (directory != null) {
            File lib = new File(directory.getAbsolutePath() + "/" + VDConstants.LIBRARY_NAME);
            if (!lib.exists()) {
                if (!lib.mkdir()) throw new FileCreatingException("Fail to create" + VDConstants.LIBRARY_NAME);
            }
            File exLibrary = new File(lib.getAbsolutePath() + "/" + VDConstants.EXTERNAL_LIBRARY_NAME);
            if (!exLibrary.exists()) {
                if (!exLibrary.mkdir())
                    throw new FileCreatingException("Failed to create " + VDConstants.EXTERNAL_LIBRARY_NAME);
            }
            File inLibrary = new File(lib.getAbsolutePath() + "/" + VDConstants.INTERNAL_LIBRARY_NAME);
            if (!inLibrary.exists()) {
                if (!inLibrary.mkdir())
                    throw new FileCreatingException("Failed to create " + VDConstants.INTERNAL_LIBRARY_NAME);
            }
            library = lib;
            internalLibrary = inLibrary;
            externalLibrary = exLibrary;
        }
    }

    private static void initInternalLibraryInJar() throws IOException {
        String sourceDir = "internalLibrary/";
        String jarPath = URLDecoder.decode(FileUtils.getJarFilePath(Main.class), "UTF-8");
        JarFile jarFile = new JarFile(jarPath);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String filePath = entry.getName();
            if (filePath.startsWith(sourceDir) && !filePath.equals(sourceDir)) {
                InputStream inputStream = Main.class.getResourceAsStream("/" + filePath);
                String fileName = filePath.substring(sourceDir.length());
                File targetFile = new File(internalLibrary.getAbsolutePath() + "/" + fileName);
                if (fileName.contains(".")) {
                    //ignore whether the file exists, add it to the list if it is a regular file
                    if (!targetFile.exists()) {
                        Files.copy(inputStream, targetFile.toPath());
                    }
                    INTERNAL_LISTS.add(targetFile);
                } else {
                    if (!targetFile.exists()) {
                        if (!targetFile.mkdir()) throw new FileCreatingException("Failed to create " + targetFile);
                    }
                }
            }
        }
        Main.updateExternalFiles();
    }

    private static void initLibraryInIDE() throws IOException {
        File source = new File(Main.class.getResource("/internalLibrary").getPath());

        //create the directory first
        Files.walk(source.toPath()).filter(Files::isDirectory).forEach(path -> {
            if (!path.toString().equals(source.getAbsolutePath())) {
                String temp = path.toString().substring(source.getAbsolutePath().length());
                File target = new File(internalLibrary.getAbsolutePath() + temp);
                if (!target.exists())
                    if (!target.mkdirs()) throw new FileCreatingException("Failed to create " + target);
            }
        });

        //copy the files and add them to list next
        Files.walk(source.toPath()).filter(Files::isRegularFile).forEach(path -> {
            String temp = path.toString().substring(source.getAbsolutePath().length());
            File target = new File(internalLibrary.getAbsolutePath() + temp);
            if (!target.exists()) {
                try {
                    Files.copy(path, target.toPath());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            INTERNAL_LISTS.add(target);
        });
        Main.updateExternalFiles();
    }

    public static void updateExternalFiles() throws IOException {
        //Files.walk(externalLibrary.toPath()).filter(Files::isRegularFile).forEach(path -> EXTERNAL_LISTS.add(path.toFile()));
        EXTERNAL_LISTS.clear();
        Files.walk(externalLibrary.toPath()).filter(Files::isRegularFile).forEach(path -> {
            if (!path.toString().equals(externalLibrary.getAbsolutePath())) {
                EXTERNAL_LISTS.add(path.toFile());
            }
        });
    }

    public static void updateInternalFiles() throws IOException {
        INTERNAL_LISTS.clear();
        Files.walk(internalLibrary.toPath()).filter(Files::isRegularFile).forEach(path -> {
            if (!path.toString().equals(externalLibrary.getAbsolutePath())) {
                INTERNAL_LISTS.add(path.toFile());
            }
        });
    }

}
