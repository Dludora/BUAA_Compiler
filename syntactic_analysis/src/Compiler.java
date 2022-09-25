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
        StringBuffer sb = new StringBuffer();

        while (sc.hasNextLine()) {
            str = sc.nextLine();
            sb.append(str).append("\n");
        }

        LexicalAnalysis la = new LexicalAnalysis(sb);
        while (la.unReachEnd()) {       // pointer == length()-1
            if(la.getSym() == 0) {
                arrayList1.add(la.getSymbol().toString());
                arrayList2.add(la.getToken().toString());
            }
        }
        SyntacticAnalysis sa = new SyntacticAnalysis(arrayList1, arrayList2);
        sa.CompUnit();
    }
}
