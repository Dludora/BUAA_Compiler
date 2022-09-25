import java.util.ArrayList;

public interface FirstList {
    ArrayList<Symbols> CompUnit = new ArrayList<Symbols>() {{
       add(Symbols.INTTK);
       add(Symbols.CONSTTK);
       add(Symbols.VOIDTK);
    }};
    ArrayList<Symbols> Decl = new ArrayList<Symbols>() {{
        add(Symbols.CONSTTK);
        add(Symbols.INTTK);
    }};
    ArrayList<Symbols> ConstDecl = new ArrayList<Symbols>() {{
        add(Symbols.CONSTTK);
    }};
    ArrayList<Symbols> VarDecl = new ArrayList<Symbols>() {{
        add(Symbols.INTTK);
    }};
    ArrayList<Symbols> FuncDef = new ArrayList<Symbols>() {{
        add(Symbols.INTTK);
        add(Symbols.VOIDTK);
    }};
    ArrayList<Symbols> MainFuncDef = new ArrayList<Symbols>() {{
        add(Symbols.INTTK);
    }};

}
