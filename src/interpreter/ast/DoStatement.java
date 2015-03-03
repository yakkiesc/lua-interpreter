package interpreter.ast;

import interpreter.ast.visitor.NodeVisitor;

/**
 * Created by Thomas on 3-3-2015.
 */
public class DoStatement extends Statement {

    private final Statement body;

    public DoStatement(int lineIndex, int columnIndex, Statement body) {
        super(lineIndex, columnIndex);
        this.body = body;
    }

    public Statement getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "DoStatement{" +
                "body=" + body +
                '}';
    }

    @Override
    public void visit(NodeVisitor visitor) {

    }
}
