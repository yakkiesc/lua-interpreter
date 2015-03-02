package interpreter.ast;

import java.util.ArrayList;

/**
 * Created by Thomas on 2-3-2015.
 */
public class Block extends Statement {

    private final ArrayList<Statement> statements;

    private final Expression returnStatement;

    public Block(int lineIndex, int columnIndex, ArrayList<Statement> statements, Expression returnStatement) {
        super(lineIndex, columnIndex);
        this.statements = statements;
        this.returnStatement = returnStatement;
    }

    public boolean hasReturnStatement(){
        return returnStatement != null;
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }

    public Expression getReturnStatement() {
        return returnStatement;
    }

    @Override
    public String toString() {
        return "Block{" +
                "statements=" + statements +
                ", returnStatement=" + returnStatement +
                '}';
    }
}
