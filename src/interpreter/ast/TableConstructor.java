package interpreter.ast;

import interpreter.ast.visitor.NodeVisitor;

import java.util.ArrayList;

/**
 * Created by Thomas on 3-3-2015.
 */
public class TableConstructor extends Expression {

    private final ArrayList<Statement> statements;

    public TableConstructor(int lineIndex, int columnIndex, ArrayList<Statement> statements) {
        super(lineIndex, columnIndex);
        this.statements = statements;
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        return "TableConstructor{" +
                "statements=" + statements +
                '}';
    }

    @Override
    public void visit(NodeVisitor visitor) {

    }

}
