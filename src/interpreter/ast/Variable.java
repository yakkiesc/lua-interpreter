package interpreter.ast;

import interpreter.ast.visitor.NodeVisitor;

/**
 * Created by Thomas on 2-3-2015.
 */
public class Variable extends Expression {

    private final String variableName;

    public Variable(int lineIndex, int columnIndex, String variableName) {
        super(lineIndex, columnIndex);
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "variableName='" + variableName + '\'' +
                '}';
    }

    @Override
    public void visit(NodeVisitor visitor) {

    }
}
