import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface Utility {

    static boolean isSpace(char c) {
        return c == ' ';
    }

    static boolean isNewLine(char c) {
        return c == '\n';
    }

    static boolean isTab(char c) {
        return c == '\t';
    }

    static long filesCompareByLine(Path path1, Path path2)
            throws IOException {
        try (BufferedReader bf1 = Files.newBufferedReader(path1);
             BufferedReader bf2 = Files.newBufferedReader(path2)) {
            // 用于标识两文件是否相同，-1是完全相同，
            // 否则是不同的行的行号，某种条件下也是较小文件的最大行号
            long lineNumber = 1;
            String line1 = "", line2 = "";
            while ((line1 = bf1.readLine()) != null) {
                line2 = bf2.readLine();
                if (!line1.equals(line2)) {
                    return lineNumber;
                }
                lineNumber++;
            }
            if (bf2.readLine() == null) {
                return -1;
            }
            else {
                return lineNumber;
            }
        }
    }
}