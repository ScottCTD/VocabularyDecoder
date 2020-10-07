package xyz.scottc;

import xyz.scottc.exceptions.FileCreatingException;
import xyz.scottc.frames.listSelectionFrame.ListSelectionFrame;
import xyz.scottc.utils.FileUtils;
import xyz.scottc.utils.VDConstantsUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Main {

    public static File library;
    public static File internalLibrary;
    public static File externalLibrary;

    public static final List<File> INTERNAL_LISTS = new ArrayList<>();

    public static void main(String[] args) {
        //Initiate the Frame
        initFrame();

        //Initialize the Directories (Libraries)
        initDirectory();

        //Copy the internal lists in the jar to library
        if (Main.class.getResource("/internalLibrary").getPath().contains("!")) {
            try {
                initInternalLibraryInJar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            initInternalLibraryInIDE();
        }
    }

    private static void initFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                IllegalAccessException e) {
            e.printStackTrace();
        }
        //MainFrame frame = new MainFrame("Vocabulary Decoder");
        //EntryFrame frame = new EntryFrame("Vocabulary Decoder");
        ListSelectionFrame frame = new ListSelectionFrame("List Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(VDConstantsUtils.getSreenRectangle());
        frame.setVisible(true);
    }

    private static void initDirectory() {
        //Create the library files
        File directory = FileUtils.getDirectoryFile(Main.class);
        if (directory != null) {
            File lib = new File(directory.getAbsolutePath() + "/" + VDConstantsUtils.LIBRARY_NAME);
            if (!lib.exists()) {
                if (!lib.mkdir()) throw new FileCreatingException("Fail to create" + VDConstantsUtils.LIBRARY_NAME);
            }
            File exLibrary = new File(lib.getAbsolutePath() + "/" + VDConstantsUtils.EXTERNAL_LIBRARY_NAME);
            if (!exLibrary.exists()) {
                if (!exLibrary.mkdir())
                    throw new FileCreatingException("Failed to create " + VDConstantsUtils.EXTERNAL_LIBRARY_NAME);
            }
            File inLibrary = new File(lib.getAbsolutePath() + "/" + VDConstantsUtils.INTERNAL_LIBRARY_NAME);
            if (!inLibrary.exists()) {
                if (!inLibrary.mkdir())
                    throw new FileCreatingException("Failed to create " + VDConstantsUtils.INTERNAL_LIBRARY_NAME);
            }
            library = lib;
            internalLibrary = inLibrary;
            externalLibrary = exLibrary;
        }
    }

    private static void initInternalLibraryInJar() throws IOException {
        String targetDir = "internalLibrary/";
        String jarPath = FileUtils.getJarFilePath(Main.class);
        JarFile jarFile = new JarFile(jarPath);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String filePath = entry.getName();
            if (filePath.startsWith(targetDir) && !filePath.equals(targetDir)) {
                InputStream inputStream = Main.class.getResourceAsStream("/" + filePath);
                String fileName = filePath.replace(targetDir, VDConstantsUtils.EMPTY);
                String target = internalLibrary.getAbsolutePath() + "/" + fileName;
                File targetFile = new File(target);
                if (!targetFile.exists()) {
                    if (target.contains(".")) {
                        Files.copy(inputStream, Paths.get(target));
                    } else {
                        if (!targetFile.mkdir()) throw new FileCreatingException("Failed to create " + target);
                    }
                }
                INTERNAL_LISTS.add(targetFile);
            }
        }
    }

    private static void initInternalLibraryInIDE() {
        File directory = new File(Main.class.getResource("/internalLibrary").getPath());
        File[] files = directory.listFiles();
        if (files != null) {
            INTERNAL_LISTS.addAll(Arrays.asList(files));
        }
    }
}
