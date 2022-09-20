import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class Clap {
    public static void main(String[] args) throws IOException {
        long num = Utility.filesCompareByLine(Paths.get("output.txt"), Paths.get("standard.txt"));
        System.out.println(num);
    }
}
