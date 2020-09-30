package xyz.scottc.utils;

import java.io.*;
import java.net.FileNameMap;
import java.net.URLDecoder;

public class FileUtils {

    public static File getDirectoryFile(Object object) {
        if (object != null) {
            String path = object.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            try {
                path = URLDecoder.decode(path, "UTF-8");
                File file = new File(path);
                return file.getParentFile();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        System.out.println("FileUtils.getDirectory null");
        return null;
    }

    public static void copyFile(File file, File targetDirectory) {
        copyFile(file, targetDirectory.getAbsolutePath());
    }

    public static void copyFile(File file, String targetDirectory) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(file);
            outputStream = new FileOutputStream(targetDirectory + "/" + file.getName());
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void copyFile(InputStream inputStream, File targetDir, String fileName) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(targetDir + "/" + fileName);
            byte[] buffer = new byte[1024];
            int length = 0;
            while((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
