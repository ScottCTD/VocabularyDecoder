import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FilesWalkTest01 {

    public static void main(String[] args) throws IOException {

        String path = "D:\\Softwares";

        Stream<Path> pathStream = Files.walk(Paths.get(path), 1);
        pathStream.filter(Files::isDirectory).forEach(System.out::println);
    }
}
