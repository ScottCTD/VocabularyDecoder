package xyz.scottc.vd.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class JSONUtils {

    public static void toFile(Object json, File file) {
        String jsonString;
        //判断是否为json对象
        if (json instanceof JSONObject) {
            jsonString = ((JSONObject) json).toString(2);
        } else if (json instanceof JSONArray) {
            jsonString = ((JSONArray) json).toString(2);
        } else {
            System.out.println("[ERROR] Invalid Json Object! (Shoule be JSONObject or JSONArray)");
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

    public static Object fromFile(File file, String encoding) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            long fileLength = file.length();
            byte[] buffer = new byte[(int) fileLength];
            int length = inputStream.read(buffer);

            int offset = 0;
            String jsonString = new String(buffer, offset, length - offset, encoding);

            if (jsonString.trim().startsWith("{")) {
                return new JSONObject(jsonString);
            }
            if (jsonString.trim().startsWith("[")) {
                return new JSONArray(jsonString);
            }
        } catch (Exception e) {
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
        throw new Exception("Invalid Json File");
    }

}
