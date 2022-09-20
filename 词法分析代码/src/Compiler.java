import java.io.*;
import java.util.Scanner;

public class Compiler {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("testfile.txt"));
        System.setOut(new PrintStream(new FileOutputStream("output.txt")));
        Scanner sc = new Scanner(System.in);
        StringBuffer sb = new StringBuffer();
        String s;
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            sb.append(s).append("\n");
        }

        LexicalAnalysis la = new LexicalAnalysis(sb);
        while (la.unReachEnd()) {       // pointer == length()-1
            if(la.getSym() == 0) {
                System.out.println(la.getSymbol() + " " + la.getToken());
            }
        }
    }
}
