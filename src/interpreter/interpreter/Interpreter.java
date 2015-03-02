package interpreter.interpreter;

import interpreter.ast.Node;

/**
 * Created by Thomas on 2-3-2015.
 */
public interface Interpreter {

    public void interpret(final Node node);

}
