package interpreter.ast;

import interpreter.lexer.Token;

/**
 * Created by Thomas on 2-3-2015.
 *
 * A binary expression has the form of:
 *
 *  Expression operator Expression.
 *
 *  The lefthand expression will be referred to as 'left'.
 *  The righthand expression will be referred to as 'right'.
 *  The operator will be referred to as 'operator'.
 *
 */
public class BinaryExpression extends Expression {

    private final int operator;
    private final Expression leftExpression;
    private final Expression rightExpression;

    public BinaryExpression(int lineIndex, int columnIndex, Expression leftExpression, int operator, Expression rightExpression) {
        super(lineIndex, columnIndex);
        this.leftExpression = leftExpression;
        this.operator = operator;
        this.rightExpression = rightExpression;
    }

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public int getOperator() {
        return operator;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

    @Override
    public String toString() {
        return "BinaryExpression{" +
                "leftExpression=" + leftExpression +
                ", operator=" + Token.toString(operator) +
                ", rightExpression=" + rightExpression +
                '}';
    }
}
