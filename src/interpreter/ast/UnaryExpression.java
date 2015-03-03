package interpreter.ast;

import interpreter.ast.visitor.NodeVisitor;
import interpreter.lexer.Token;

/**
 * Created by Thomas on 2-3-2015.
 *
 * An unary expression has the form of:
 *
 * operator Expression
 *
 * Where Expression is 'expression'.
 * Where operator is 'operator'
 */
public class UnaryExpression extends Expression {

    private final int operator;
    private final Expression expression;

    public UnaryExpression(int lineIndex, int columnIndex, int operator, Expression expression) {
        super(lineIndex, columnIndex);
        this.operator = operator;
        this.expression = expression;
    }

    public int getOperator() {
        return operator;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "UnaryExpression{" +
                "operator=" + Token.toString(operator) +
                ", expression=" + expression +
                '}';
    }

    @Override
    public void visit(NodeVisitor visitor) {

    }

}
