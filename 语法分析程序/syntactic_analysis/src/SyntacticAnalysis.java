import com.sun.xml.internal.bind.v2.model.core.ID;
import sun.applet.Main;

import java.util.ArrayList;

public class SyntacticAnalysis implements FirstList {
    private final ArrayList<String> voca;
    private Vn vn;
    private static int p = 0;
    private Symbols symbol;
    private boolean inCond = false;

    public SyntacticAnalysis(ArrayList<String> voca) {
        this.voca = voca;
        getVoca();
    }

    private void getVoca() {
        if (p == voca.size()) {
            error();
        }
        symbol = Symbols.valueOf(voca.get(p++));
    }

    private Symbols preView(int num) {
        if (p + num >= voca.size()) {
            return Symbols.NONE;
        }
        return Symbols.valueOf(voca.get(p + num));
    }

    private void error() {
        System.exit(0);
    }

    private void BType() {
        if (symbol != Symbols.INTTK) {
            error();
        }
        getVoca();
    }

    private void Number() {
        if (symbol == Symbols.INTCON) {
            System.out.println("<Number>");
        }

        error();
    }

    private void Exp() {
        /*
        *   表达式
        *   AddExp 已完成
        * */
        AddExp();
    }

    private void Cond() {
        inCond = true;
        LOrExp();
        inCond = false;
        System.out.println("<Cond>");
    }

    private void LVal() {
        /*
        *   左值表达式
        *   Exp 已完成
        * */
        if (symbol == Symbols.IDENFR) {
            getVoca();
            if (symbol == Symbols.LPARENT) { // 可能是一维数组
                getVoca();
                Exp();
                if (symbol == Symbols.RPARENT) {
                    getVoca();
                    if (symbol == Symbols.LPARENT) {    // 可能是二维数组
                        getVoca();
                        Exp();
                        if (symbol == Symbols.RPARENT) {
                            getVoca();
                        } else {
                            error();
                        }
                    }
                } else {
                    error();
                }
            }
        }

        System.out.println("LVal");
    }

    private void PrimaryExp() {
        /*
        *   基本表达式
        *   Exp 已完成
        *   Number 已完成
        *   LVal 已完成
        * */
        // '(' Exp ')'
        if (symbol == Symbols.LPARENT) {
            getVoca();
            Exp();
            if (symbol == Symbols.RPARENT) {
                getVoca();
            } else {
                error();
            }
        } else if (symbol == Symbols.INTCON) {  // Number
            Number();
        } else if (symbol == Symbols.IDENFR) {  // LVal
            LVal();
        } else {
            error();
        }

        System.out.println("<PrimaryExp>");
    }

    private void UnaryOP() {
        if (symbol == Symbols.PLUS || symbol == Symbols.MINU) {
            getVoca();
        } else if (symbol == Symbols.NOT && inCond) {
            // 追加判断在条件表达式中
            getVoca();
        } else {
            error();
        }

        System.out.println("<UnaryOP>");
    }

    private void UnaryExp() {
        /*
        *   一元表达式
        *   FuncRParams
        *   PrimaryExp 已完成
        *   UnaryOp 已完成
         * */
        // Ident '(' [FuncRParams] ') || LVal = Ident { '[' Exp ']' } in PrimaryExp
        if (symbol == Symbols.IDENFR) {
            // Ident '(' [FuncRParams] ')
            if (preView(1) == Symbols.LPARENT) {
                getVoca();
                if (symbol == Symbols.LPARENT) {
                    getVoca();
                    if (symbol == Symbols.RPARENT) {
                        getVoca();
                    } else {
                        FuncRParams();
                        if (symbol == Symbols.RPARENT) {
                            getVoca();
                        } else {
                            error();
                        }
                    }
                } else {
                    error();
                }
            } else {    // LVal = Ident { '[' Exp ']' } in PrimaryExp
                getVoca();
                PrimaryExp();
            }
        } else if (symbol == Symbols.LPARENT) {     // PrimaryExp
            PrimaryExp();
        } else {
            UnaryOP();
            UnaryExp();
        }

        System.out.println("<UnaryExp>");
    }

    private void MulExp() {
        UnaryExp();
        while (symbol == Symbols.MULT || symbol == Symbols.DIV || symbol == Symbols.MOD) {
            getVoca();
            UnaryExp();
        }

        System.out.println("<MulExp>");
    }

    private void AddExp() {
        MulExp();
        while (symbol == Symbols.PLUS) {
            getVoca();
            MulExp();
        }

        System.out.println("<AddExp>");
    }

    private void RelExp() {
        AddExp();
        while (symbol == Symbols.LSS || symbol == Symbols.LEQ || symbol == Symbols.GRE || symbol == Symbols.GEQ) {
            getVoca();
            AddExp();
        }

        System.out.println("<RelExp>");
    }

    private void EqExp() {
        RelExp();
        while (symbol == Symbols.EQL || symbol == Symbols.NEQ) {
            getVoca();
            RelExp();
        }

        System.out.println("<EqExp>");
    }

    private void LAndExp() {
        EqExp();
        while (symbol == Symbols.AND) {
            getVoca();
            EqExp();
        }

        System.out.println("<LAndExp>");
    }

    private void LOrExp() {
        LAndExp();
        while (symbol == Symbols.OR) {
            getVoca();
            LAndExp();
        }

        System.out.println("<LOrExp>");
    }

    private void FuncRParams() {
        Exp();
        while (symbol == Symbols.COMMA) {
            getVoca();
            Exp();
        }
    }

    private void ConstExp() {
        AddExp();
    }

    private void BlockItem() {
        /*
        *   语句块项
        *   Decl
        *   Stmt
        * */
    }

    private void Block() {
        /*
        *   语句块
        *   BlockItem
        * */
        if (symbol == Symbols.LBRACE) {
            getVoca();
            BlockItem();
            if (symbol == Symbols.RBRACE) {
                getVoca();
            } else {
                error();
            }
        } else {
            error();
        }

        System.out.println("<Block>");
    }

    private void MainFuncDef() {
//        getVoca();
//        getVoca();
//        if (symbol == Symbols.LPARENT) {
//            getVoca();
//            if (symbol == Symbols.RPARENT) {
//                Block();
//            } else {
//                error();
//                // error
//            }
//        } else {
//            error();
//            // error
//        }
    }

    private void FuncFParam() {
        /*
        *   函数形参
        *   BType 完成
        *   ConstExp
        * */
        BType();
        if (symbol == Symbols.IDENFR) {
            getVoca();
        } else {
            error();
        }
        if (symbol == Symbols.LBRACK) {
            getVoca();
            if (symbol == Symbols.RBRACK) {
                getVoca();
                if (symbol == Symbols.LBRACK) {
                    getVoca();
                    ConstExp();
                    if (symbol == Symbols.RBRACK) {
                        getVoca();
                    } else {
                        error();
                    }
                }
            } else {
                error();
            }
        } else {
            error();
        }
    }

    private void FuncFParams() {
        /*
        *   函数形参表
        *   FuncFParam 已完成
        * */
        do {
            FuncFParam();
            getVoca();
        } while (symbol == Symbols.COMMA);

        System.out.println("<FuncFParams>");
    }

    private void ConstDef() {
        if (symbol == Symbols.IDENFR) {
            getVoca();
            if (symbol == Symbols.LBRACK) {
                getVoca();
                ConstExp();
                if (symbol == Symbols.RBRACK) {
                    getVoca();
                    if (symbol == Symbols.LBRACK) { // 可能是二维
                        getVoca();
                        ConstExp();
                        if (symbol == Symbols.RBRACK) {
                            getVoca();
                        } else {
                            error();
                        }
                    }
                } else {
                    error();
                }
            }
            if (symbol == Symbols.ASSIGN) {
                getVoca();
                ConstInitVal();
            } else {
                error();
            }
        } else {
            error();
        }

        System.out.println("<ConstInitVal>");
    }

    private void ConstInitVal() {
        if (symbol == Symbols.LBRACE) {
            getVoca();
            if (symbol == Symbols.RBRACE) {
                getVoca();
            } else {
                ConstInitVal();
                while (symbol == Symbols.COMMA) {
                    getVoca();
                    ConstInitVal();
                }
                if (symbol == Symbols.RBRACE) {
                    getVoca();
                } else {
                    error();
                }
            }
        } else {
            ConstExp();
        }

        System.out.println("<ConstInitVal>");
    }

    private void VarDecl() {
        BType();
        VarDef();
        while (symbol == Symbols.COMMA) {
            getVoca();
            VarDef();
        }
        if (symbol == Symbols.SEMICN) {
            getVoca();
        } else {
            error();
        }

        System.out.println("<VarDecl>");
    }

    private void VarDef() {
        if (symbol == Symbols.IDENFR) {
            getVoca();
            if (symbol == Symbols.LBRACK) {
                getVoca();
                ConstExp();
                if (symbol == Symbols.RBRACK) {
                    getVoca();
                    if (symbol == Symbols.EQL) {
                        getVoca();
                        InitVal();
                    }
                } else {
                    error();
                }
            } else {
                error();
            }
        } else {
            error();
        }

        System.out.println("<VarDef>");
    }

    private void InitVal() {
        if (symbol == Symbols.LBRACE) {
            getVoca();
            if (symbol == Symbols.RBRACE) {
                getVoca();
            } else {
                InitVal();
                while (symbol == Symbols.COMMA) {
                    getVoca();
                    InitVal();
                }
                if (symbol == Symbols.RBRACE) {
                    getVoca();
                } else {
                    error();
                }
            }
        } else {
            Exp();
        }

        System.out.println("<InitVal>");
    }

    private void FuncType() {
        /*
        *   函数类型
        *   已完成
        * */
        if (symbol == Symbols.VOIDTK || symbol == Symbols.INTTK) {
            System.out.println("<FuncType>");
            return;
        }
        error();
    }

    private void FuncDef() {
        /*
        *   函数定义
        *   FuncType
        *   FuncFParams
        *   Block
        * */
        FuncType();
        if (symbol == Symbols.IDENFR) {
            getVoca();
        } else {
            error();
        }
        if (symbol == Symbols.LPARENT) {
            getVoca();
            if (symbol == Symbols.RPARENT) {
                getVoca();
                Block();
            } else {
                FuncFParams();
                if (symbol == Symbols.RPARENT) {
                    getVoca();
                    Block();
                } else {
                    error();
                }
            }
        } else {
            error();
        }

        System.out.println("<FuncDef>");
    }

    private void ConstDecl() {
        if (symbol == Symbols.CONSTTK) {
            getVoca();
            BType();
            ConstDef();
            while (symbol == Symbols.COMMA) {
                getVoca();
                ConstDef();
            }
            if (symbol == Symbols.SEMICN) {
                getVoca();
            } else {
                error();
            }
        } else {
            error();
        }

        System.out.println("<ConstDecl>");
    }

    private void Decl() {
        switch (symbol) {
            case CONSTTK -> {
                ConstDecl();
            }
            case INTTK -> {
                VarDecl();
            }
            default -> {
                error();
            }
        }
    }

    public void CompUnit() {
        if (symbol == Symbols.INTTK) {
            if (preView(1) == Symbols.MAINTK) {
                MainFuncDef();
            } else {
                if (preView(2) == Symbols.LPARENT) {     // == '('
                    FuncDef();
                } else {
                    Decl();
                }
            }
        } else if (symbol == Symbols.CONSTTK) {
            Decl();
        } else if (symbol == Symbols.VOIDTK) {
            FuncDef();
        } else {
            error();
        }

        System.out.println("<CompUnit>");
    }
}
