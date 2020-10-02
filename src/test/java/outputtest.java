import java.io.*;

public class outputtest {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Synchronization\\Code\\VocabularyDecoder\\build\\start.bat");
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = new FileOutputStream("C:\\Synchronization\\Code\\VocabularyDecoder\\build\\1");
        byte[] bytes = new byte[8192];
        inputStream.read(bytes);
        outputStream.write(bytes);
    }
}
