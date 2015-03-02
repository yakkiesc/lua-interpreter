package interpreter.parser;

/**
 * Created by Thomas on 2-3-2015.
 */
public class SyntaxException extends Exception {

    public SyntaxException(String message, final int lineIndex, final int columnIndex) {
        super(String.format("%s @ %d:%d", message, lineIndex, columnIndex));
    }
}
