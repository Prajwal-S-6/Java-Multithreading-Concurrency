package multithreading.course260711.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileProcess {

    public static void main(String[] args) throws IOException {
        Files.lines(Paths.get("D:\\dev\\Java-Multithreading-Concurrency\\MultiThreadingAndConcurrency\\Java-Multithreading-Concurrency\\src\\main\\java\\multithreading\\course260711\\stream\\string.txt"))
                .forEach(System.out::println);
    }
}
