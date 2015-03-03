package interpreter.lexer;

/**
 * Created by Thomas on 2-3-2015.
 */
public class Token {

    public static final int EOF = -2;
    public static final int ERROR = -1;

    /* literals & identifiers */
    public static final int IDENTIFIER = 0;
    public static final int INTEGER_LITERAL = 1;
    public static final int FLOAT_LITERAL = 2;
    public static final int BOOLEAN_LITERAL = 3;
    public static final int STRING_LITERAL = 4;
    public static final int NIL_LITERAL = 5;

    /* symbols */
    public static final int GREATER_THAN = 10;
    public static final int GREATER_THAN_EQUALS = 11;
    public static final int LESS_THAN = 12;
    public static final int LESS_THAN_EQUALS = 13;
    public static final int EQUALS = 14;
    public static final int ASSIGNMENT = 15;
    public static final int PLUS = 16;
    public static final int MINUS = 17;
    public static final int MULTIPLY = 18;
    public static final int DIVIDE = 19;
    public static final int PARENTHESIS_OPEN = 20;
    public static final int PARENTHESIS_CLOSE = 21;
    public static final int BRACKET_OPEN = 22;
    public static final int BRACKET_CLOSE = 23;
    public static final int CURLY_BRACKET_OPEN = 28;
    public static final int CURLY_BRACKET_CLOSE = 29;
    public static final int DOT = 24;
    public static final int COMMA = 25;
    public static final int MODULUS = 26;
    public static final int STRING_SIZE = 27;
    public static final int VARARG = 28;
    public static final int CONCATENATION = 29;
    public static final int POWER = 30;
    public static final int LABEL = 31;
    public static final int COLON = 32;
    public static final int SEMICOLON = 33;

    /* keywords */
    public static final int IF = 40;
    public static final int ELSE = 41;
    public static final int THEN = 42;
    public static final int FUNCTION = 43;
    public static final int END = 44;
    public static final int UNTIL = 45;
    public static final int WHILE = 46;
    public static final int DO = 47;
    public static final int AND = 48;
    public static final int OR = 49;
    public static final int ELSEIF = 50;
    public static final int BREAK = 51;
    public static final int FOR = 52;
    public static final int NOT = 53;
    public static final int GOTO = 54;
    public static final int RETURN = 55;
    public static final int IN = 56;
    public static final int REPEAT = 57;

    private final int columnIndex;

    private final int lineIndex;

    private final int id;

    private final String lexeme;

    private int intValue;

    private double floatValue;
    private boolean booleanValue;

    public Token(int lineIndex, int columnIndex, int id) {
        this(columnIndex, lineIndex, id, null);
    }

    public Token(int lineIndex, int columnIndex, int id, String lexeme) {
        this.lineIndex = lineIndex;
        this.columnIndex = columnIndex;
        this.id = id;
        this.lexeme = lexeme;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public int getId() {
        return id;
    }

    public String getLexeme() {
        return lexeme;
    }


    public double getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(double floatValue) {
        this.floatValue = floatValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public static String toString(final int tokenID){
        switch (tokenID){
            case Token.PLUS:
                return "plus";
            case Token.MINUS:
                return "minus";
            case Token.MULTIPLY:
                return "multiply";
            case Token.DIVIDE:
                return "divide";

            default:
                return "unknown";
        }
    }

    public int getID() {
        return id;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }
}
