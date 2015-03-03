package interpreter.ast;

import interpreter.ast.visitor.NodeVisitor;

/**
 * Created by Thomas on 3-3-2015.
 */
public class ReapetUntilStatement extends Statement {

    private final Expression condition;
    private final Statement body;

    public ReapetUntilStatement(int lineIndex, int columnIndex, Expression condition, Statement body) {
        super(lineIndex, columnIndex);
        this.condition = condition;
        this.body = body;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "doUntilStatement{" +
                "condition=" + condition +
                ", body=" + body +
                '}';
    }

    @Override
    public void visit(NodeVisitor visitor) {

    }

}
