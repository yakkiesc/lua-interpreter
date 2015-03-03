package interpreter.ast;

/**
 * Created by Thomas on 3-3-2015.
 */
public class StatementExpression extends Statement {

    private final Expression expression;

    public StatementExpression(int lineIndex, int columnIndex, Expression expression) {
        super(lineIndex, columnIndex);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "StatementExpression{" +
                "expression=" + expression +
                '}';
    }
}
