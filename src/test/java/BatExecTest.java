
import java.io.IOException;

public class BatExecTest {

    public static void main(String[] args) {
        String exec = "C:\\Synchronization\\Code\\VocabularyDecoder\\target\\start.bat";
        try {
            Process process = Runtime
                    .getRuntime()
                    .exec("cmd /c start java -Dfile.encoding=utf-8 -jar VocabularyDecoder-v0.7.jar");

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
