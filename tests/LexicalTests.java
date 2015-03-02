import interpreter.lexer.Lexer;
import interpreter.lexer.LexicalException;

/**
 * Created by Thomas on 2-3-2015.
 */
public class LexicalTests {

    public static void main(String[] args) {
        try {
            doNumberTests();
        } catch (LexicalException e) {
            e.printStackTrace();
        }
    }

    private static void doNumberTests() throws LexicalException {
        final String source = "123e3 12e-3 0.31e3 .12 134 1651 .3e-32";
        Lexer lexer = new Lexer(source);
        assertLexerDouble(123e3, lexer);
        assertLexerDouble(12e-3, lexer);
        assertLexerDouble(0.31e3, lexer);
        assertLexerDouble(.12, lexer);
        assertLexerInt(134, lexer);
        assertLexerInt(1651, lexer);
        assertLexerDouble(.3e-32, lexer);
        System.out.println("Done");
    }

    private static void assertLexerInt(final int expected, final Lexer lexer) throws LexicalException {
        if(lexer.getToken().getIntValue() != expected){
            System.err.println("Unexpected value expected: " + expected);
        } else {
            System.out.println("Passed");
        }
    }

    private static void assertLexerDouble(final double expected, final Lexer lexer) throws LexicalException {
        if(lexer.getToken().getFloatValue() != expected){
            System.err.println("Unexpected value expected: " + expected);
        } else {
            System.out.println("Passed");
        }
    }


}
