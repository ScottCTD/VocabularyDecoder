package xyz.scottc.vd.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import xyz.scottc.vd.exceptions.FileDeletingException;

import java.io.*;
import java.nio.file.FileSystemException;

public class JSONUtils {

    public static void replaceByKey(File target, String key, Object replacement) throws IOException {
        JSONObject jsonObject = (JSONObject) JSONUtils.fromFile(target);
        jsonObject.remove(key);
        jsonObject.put(key, replacement);
        if (!target.delete()) throw new FileDeletingException(target);
        JSONUtils.toFile(jsonObject, target);
    }

    public static void toFile(Object json, File file) {
        String jsonString;
        //判断是否为json对象
        if (json instanceof JSONObject) {
            jsonString = ((JSONObject) json).toString(2);
        } else if (json instanceof JSONArray) {
            jsonString = ((JSONArray) json).toString(2);
        } else {
            System.out.println("[ERROR] Invalid Json Object! (Should be JSONObject or JSONArray)");
            return;
        }

        //存入文件
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            //写入文件
            byte[] buffer = jsonString.getBytes();
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public static Object fromFile(File file) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            long fileLength = file.length();
            byte[] buffer = new byte[(int) fileLength];
            int length = inputStream.read(buffer);

            String jsonString = new String(buffer, 0, length);

            if (jsonString.trim().startsWith("{")) {
                return new JSONObject(jsonString);
            }
            if (jsonString.trim().startsWith("[")) {
                return new JSONArray(jsonString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
        throw new FileSystemException("Invalid Json File");
    }

}
