package interpreter.ast;

import interpreter.ast.visitor.NodeVisitor;

import java.util.ArrayList;

/**
 * Created by Thomas on 3-3-2015.
 */
public class IterativeForStatement extends Statement{

    private final ArrayList<String> vars;
    private final ArrayList<Expression> expressions;
    private final Statement body;


    public IterativeForStatement(int lineIndex, int columnIndex, ArrayList<String> vars, ArrayList<Expression> expressions, Statement body) {
        super(lineIndex, columnIndex);
        this.vars = vars;
        this.expressions = expressions;
        this.body = body;
    }

    public ArrayList<String> getVars() {
        return vars;
    }

    public ArrayList<Expression> getExpressions() {
        return expressions;
    }

    public Statement getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "IterationForStatement{" +
                "vars=" + vars +
                ", expressions=" + expressions +
                ", body=" + body +
                '}';
    }

    @Override
    public void visit(NodeVisitor visitor) {

    }
}
