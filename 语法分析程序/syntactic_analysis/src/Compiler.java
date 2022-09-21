import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Compiler {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("testfile.txt"));
        System.setOut(new PrintStream(new FileOutputStream("output.txt")));

        Scanner sc = new Scanner(System.in);
        ArrayList<String> arrayList = new ArrayList<>();
        while (sc.hasNextLine()) {
            arrayList.add(sc.nextLine().split(" ")[0]);
        }
        SyntacticAnalysis sa = new SyntacticAnalysis(arrayList);
        sa.CompUnit();
    }
}
