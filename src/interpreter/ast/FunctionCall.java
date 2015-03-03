package interpreter.ast;

import interpreter.ast.visitor.NodeVisitor;

import java.util.ArrayList;

/**
 * Created by Thomas on 2-3-2015.
 */
public class FunctionCall extends Expression {

    private Expression functionValue;
    private ArrayList<Expression> arguments;

    public FunctionCall(int lineIndex, int columnIndex, Expression functionValue, ArrayList<Expression> arguments) {
        super(lineIndex, columnIndex);
        this.functionValue = functionValue;
        this.arguments = arguments;
    }

    public Expression getFunctionValue() {
        return functionValue;
    }

    public ArrayList<Expression> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "FunctionCall{" +
                "functionValue=" + functionValue +
                ", arguments=" + arguments +
                '}';
    }

    @Override
    public void visit(NodeVisitor visitor) {

    }
}
