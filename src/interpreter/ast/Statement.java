package interpreter.ast;

/**
 * Created by Thomas on 2-3-2015.
 */
public abstract class Statement extends Node {

    public Statement(int lineIndex, int columnIndex) {
        super(lineIndex, columnIndex);
    }

}
