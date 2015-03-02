package interpreter.lexer;

/**
 * Created by Thomas on 2-3-2015.
 */
public class LexicalException extends Exception {

    private final int lineIndex;
    private final int columnIndex;
    private final String message;

    public LexicalException(final String message, final int lineIndex, final int columnIndex) {
        super(String.format("%s @ %d:%d", message, lineIndex, columnIndex));
        this.lineIndex = lineIndex;
        this.columnIndex = columnIndex;
        this.message = message;
    }



}
