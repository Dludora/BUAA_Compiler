import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Compiler {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("testfile.txt"));
        System.setOut(new PrintStream(new FileOutputStream("output.txt")));

        Scanner sc = new Scanner(System.in);
        ArrayList<String> arrayList1 = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        String str;
        while (sc.hasNextLine()) {
            str = sc.nextLine();
            arrayList1.add(str.split(" ")[0]);
            arrayList2.add(str.split(" ")[1]);
        }
        SyntacticAnalysis sa = new SyntacticAnalysis(arrayList1, arrayList2);
        sa.CompUnit();
    }
}
