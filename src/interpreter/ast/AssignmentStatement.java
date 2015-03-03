package interpreter.ast;

import interpreter.ast.visitor.NodeVisitor;

import java.util.ArrayList;

/**
 * Created by Thomas on 2-3-2015.
 */
public class AssignmentStatement extends Statement {

    private final ArrayList<Expression> variables;
    private final ArrayList<Expression> values;

    public AssignmentStatement(int lineIndex, int columnIndex, Expression variable, Expression value){
        super(lineIndex, columnIndex);
        this.variables = new ArrayList<Expression>();
        this.values = new ArrayList<Expression>();

        variables.add(variable);
        values.add(value);
    }

    public AssignmentStatement(int lineIndex, int columnIndex, ArrayList<Expression> variables, ArrayList<Expression> values) {
        super(lineIndex, columnIndex);
        this.variables = variables;
        this.values = values;
    }

    public ArrayList<Expression> getVariables() {
        return variables;
    }

    public ArrayList<Expression> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "AssignmentStatement{" +
                "variables=" + variables +
                ", values=" + values +
                '}';
    }

    @Override
    public void visit(NodeVisitor visitor) {

    }
}
