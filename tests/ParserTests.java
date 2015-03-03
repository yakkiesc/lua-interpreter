import interpreter.ast.Node;
import interpreter.interpreter.ArithmeticInterpreter;
import interpreter.lexer.Lexer;
import interpreter.lexer.LexicalException;
import interpreter.parser.Parser;
import interpreter.parser.SyntaxException;

/**
 * Created by Thomas on 2-3-2015.
 */
public class ParserTests {

    public static void main(String[] args) {
        //doArithmeticTest();
        //doFunctionTest();
        //doIfTests();
        //doFunctionDeclTest();
        //doForTests();
        doTableConstructorTests();
    }

    private static void doForTests() {
        final String source = "function f(z) for i = z, 5 do a(i) end for y in m do end while x do y:z(e) end end";
        final Lexer lexer = new Lexer(source);
        final Parser parser = new Parser(lexer);
        try {
            System.out.println(parser.statement());
        } catch (LexicalException e) {
            e.printStackTrace();
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void doTableConstructorTests(){
        final String source = "function s(a) s({[0]=3, [3]=2, r = f(x), z(e)}) end";
        final Lexer lexer = new Lexer(source);
        final Parser parser = new Parser(lexer);
        try {
            System.out.println(parser.statement());
        } catch (LexicalException e) {
            e.printStackTrace();
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void doFunctionDeclTest() {
        final String source = "function f(a, b) if (x and xx) or x > 4 then return y end return z end";
        final Lexer lexer = new Lexer(source);
        final Parser parser = new Parser(lexer);
        try {
            System.out.println(parser.statement());
        } catch (LexicalException e) {
            e.printStackTrace();
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void doIfTests(){
        final String source = "if x then return y elseif a:f(3,4) == 2 then return z end";
        final Lexer lexer = new Lexer(source);
        final Parser parser = new Parser(lexer);
        try {
            System.out.println(parser.statement());
        } catch (LexicalException e) {
            e.printStackTrace();
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void doFunctionTest() {
        final String source = "a:f(3, 4)";
        final Lexer lexer = new Lexer(source);
        final Parser parser = new Parser(lexer);
        final Node tree = parser.program();
        System.out.println(tree);
    }


    private static void doArithmeticTest() {
        final String source = "-4 * 1 * (5 * 2 + 1)";
        final Lexer lexer = new Lexer(source);
        final Parser parser = new Parser(lexer);
        final Node tree = parser.program();
        ArithmeticInterpreter arithmeticInterpreter = new ArithmeticInterpreter();
        arithmeticInterpreter.interpret(tree);
        arithmeticInterpreter.printResult();
    }

}
