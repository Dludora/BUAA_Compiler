public class LexicalAnalysis implements Reserves {

    private final StringBuffer token = new StringBuffer();
    private Character c;
    private Symbols symbol;
    private int pointer = 0;
    private final StringBuffer file;

    static {
        resSymbols.put("main", "MAINTK");
        resSymbols.put("const", "CONSTTK");
        resSymbols.put("int", "INTTK");
        resSymbols.put("break", "BREAKTK");
        resSymbols.put("continue", "CONTINUETK");
        resSymbols.put("if", "IFTK");
        resSymbols.put("else", "ELSETK");
        resSymbols.put("while", "WHILETK");
        resSymbols.put("getint", "GETINTTK");
        resSymbols.put("printf", "PRINTFTK");
        resSymbols.put("return", "RETURNTK");
        resSymbols.put("void", "VOIDTK");
    }

    public StringBuffer getToken() {
        return token;
    }

    public Symbols getSymbol() {
        return symbol;
    }

    public LexicalAnalysis(StringBuffer file) {
        this.file = file;
    }

    public void clearToken() {
        token.delete(0, token.length());
    }

    public boolean unReachEnd() {
        return pointer < file.length() - 1;
    }

    public void catToken() {
        token.append(c);
    }

    public void getChar() {
        c = file.charAt(pointer);
        pointer++;
    }

    public void retract() {
        if (pointer > 0) {
            pointer--;
        }
    }

    public int getSym() {
        clearToken();
        getChar();
        while ((Utility.isSpace(c) || Utility.isNewLine(c) || Utility.isTab(c))) {
            if (pointer == file.length()) {
                return -1;
            }
            getChar();
        }
        if (Character.isLetter(c) || c == '_') {
            while (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
                catToken();
                getChar();
            }
            retract();
            boolean resultValue = resSymbols.containsKey(token.toString());

            if (!resultValue) {
                symbol = Symbols.IDENFR;
            } else {
                symbol = Symbols.valueOf(resSymbols.get(token.toString()));
            }
        } else if (Character.isDigit(c)) {
            while (Character.isDigit(c)) {
                catToken();
                getChar();
            }
            retract();
            symbol = Symbols.INTCON;
        } else if (c == '\"') {
            do {
                catToken();
                getChar();
            } while (c != '\"');
            catToken();
            symbol = Symbols.STRCON;
        } else if (c == '+') {
            catToken();
            symbol = Symbols.PLUS;
        } else if (c == '-') {
            catToken();
            symbol = Symbols.MINU;
        } else if (c == '*') {
            catToken();
            symbol = Symbols.MULT;
        } else if (c == '(') {
            catToken();
            symbol = Symbols.LPARENT;
        } else if (c == ')') {
            catToken();
            symbol = Symbols.RPARENT;
        } else if (c == '[') {
            catToken();
            symbol = Symbols.LBRACK;
        } else if (c == ']') {
            catToken();
            symbol = Symbols.RBRACK;
        } else if (c == '{') {
            catToken();
            symbol = Symbols.LBRACE;
        } else if (c == '}') {
            catToken();
            symbol = Symbols.RBRACE;
        } else if (c == ',') {
            catToken();
            symbol = Symbols.COMMA;
        } else if (c == ';') {
            catToken();
            symbol = Symbols.SEMICN;
        } else if (c == '/') {
            catToken();
            getChar();
            if (c == '*') {
                do {
                    do {
                        getChar();
                    } while (c != '*');
                    do {
                        getChar();
                        if (c == '/') {
                            catToken();
                            return -1;
                        }
                    } while (c == '*');
                } while (pointer < file.length());
            } else if (c == '/') {
                do {
                    getChar();
                } while (!Utility.isNewLine(c));

                return -1;
            } else {
                retract();
                symbol = Symbols.DIV;
            }
        } else if (c == '%') {
            catToken();
            symbol = Symbols.MOD;
        } else if (c == '<') {
            catToken();
            getChar();
            if (c == '=') {
                catToken();
                symbol = Symbols.LEQ;
            } else {
                retract();
                symbol = Symbols.LSS;
            }
        } else if (c == '>') {
            catToken();
            getChar();
            if (c == '=') {
                catToken();
                symbol = Symbols.GEQ;
            } else {
                retract();
                symbol = Symbols.GRE;
            }
        } else if (c == '=') {
            catToken();
            getChar();
            if (c == '=') {
                catToken();
                symbol = Symbols.EQL;
            } else {
                retract();
                symbol = Symbols.ASSIGN;
            }
        } else if (c == '!') {
            catToken();
            getChar();
            if (c == '=') {
                catToken();
                symbol = Symbols.NEQ;
            } else {
                retract();
                symbol = Symbols.NOT;
            }
        } else if (c == '&') {
            catToken();
            getChar();
            if (c == '&') {
                catToken();
                symbol = Symbols.AND;
            } else {
                retract();

                return -1;
            }
        } else if (c == '|') {
            catToken();
            getChar();
            if (c == '|') {
                catToken();
                symbol = Symbols.OR;
            } else {
                retract();

                return -1;
            }
        }

        return 0;
    }

}