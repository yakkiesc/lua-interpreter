package interpreter.ast.visitor;

import interpreter.ast.*;

/**
 * Created by Thomas on 3-3-2015.
 */
public interface NodeVisitor {

    public void visitBinaryExpression(final int operator);

    public void visitLiteral(final Object value, final int type);


}
