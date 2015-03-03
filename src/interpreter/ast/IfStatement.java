package interpreter.ast;

import interpreter.ast.visitor.NodeVisitor;

/**
 * Created by Thomas on 2-3-2015.
 */
public class IfStatement extends Statement {

    private final  Expression condition;
    private final  Statement body;
    private final  IfStatement elseStatement;

    public IfStatement(int lineIndex, int columnIndex,  Expression condition, Statement body, IfStatement elseStatement) {
        super(lineIndex, columnIndex);
        this.condition = condition;
        this.body = body;
        this.elseStatement = elseStatement;
    }

    public Statement getBody() {
        return body;
    }

    public Expression getCondition() {
        return condition;
    }

    public IfStatement getElseStatement() {
        return elseStatement;
    }

    @Override
    public String toString() {
        return "IfStatement{" +
                "condition=" + condition +
                ", body=" + body +
                ", elseStatement=" + elseStatement +
                '}';
    }

    @Override
    public void visit(NodeVisitor visitor) {

    }
}
