package interpreter.ast;

import interpreter.ast.visitor.NodeVisitor;

/**
 * Created by Thomas on 3-3-2015.
 */
public class NumericForStatement extends Statement {

    private final AssignmentStatement var;
    private final Expression limit;
    private final Expression step;
    private final Statement body;

    public NumericForStatement(int lineIndex, int columnIndex, AssignmentStatement var, Expression limit, Expression step, Statement body) {
        super(lineIndex, columnIndex);
        this.var = var;
        this.limit = limit;
        this.step = step;
        this.body = body;
    }

    public AssignmentStatement getVar() {
        return var;
    }

    public Expression getLimit() {
        return limit;
    }

    public Expression getStep() {
        return step;
    }

    public Statement getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "NumericForStatement{" +
                "var=" + var +
                ", limit=" + limit +
                ", step=" + step +
                ", body=" + body +
                '}';
    }

    @Override
    public void visit(NodeVisitor visitor) {

    }
}
