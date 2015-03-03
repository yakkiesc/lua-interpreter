package interpreter.ast;

import interpreter.ast.visitor.NodeVisitor;

/**
 * Created by Thomas on 2-3-2015.
 */
public class VariableAccess extends Expression {

    private final Expression variable;
    private final Expression access;

    public VariableAccess(int lineIndex, int columnIndex, Expression variable, Expression access) {
        super(lineIndex, columnIndex);
        this.variable = variable;
        this.access = access;
    }

    public Expression getVariable() {
        return variable;
    }

    public Expression getAccess() {
        return access;
    }

    @Override
    public String toString() {
        return "VariableAccess{" +
                "variable=" + variable +
                ", access=" + access +
                '}';
    }

    @Override
    public void visit(NodeVisitor visitor) {

    }

}
