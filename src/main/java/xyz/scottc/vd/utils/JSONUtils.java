package xyz.scottc.vd.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JSONUtils {

    public static void toFile(Object json, File file, String encoding) {
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
            // UTF-8写入BOM头部
            encoding = encoding.toUpperCase();
            if ("UTF-8".equals(encoding)) {
                byte[] bom = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
                outputStream.write(bom);
            }
            outputStream.flush();
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
            encoding = encoding.toUpperCase();
            if (length > 3 && "UTF-8".equals(encoding)) {
                if (buffer[0] == (byte) 0xEF && buffer[1] == (byte) 0xBB && buffer[2] == (byte) 0xBF)
                    offset = 3; // 前3个字节是BOM
            }
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

    public static List<String> JSONArrayToArrayList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for (Object object : jsonArray) {
            list.add(object.toString());
        }
        return list;
    }
}
