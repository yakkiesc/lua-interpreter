package interpreter.ast;

/**
 * Created by Thomas on 2-3-2015.
 */
public abstract class Expression extends  Node {

    public Expression(int lineIndex, int columnIndex) {
        super(lineIndex, columnIndex);
    }

}
