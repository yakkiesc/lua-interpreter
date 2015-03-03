package interpreter.ast;

/**
 * Created by Thomas on 3-3-2015.
 */
public class WhileStatement extends Statement{

    private final Expression condition;
    private final Statement statement;

    public WhileStatement(int lineIndex, int columnIndex, Expression condition, Statement statement) {
        super(lineIndex, columnIndex);
        this.condition = condition;
        this.statement = statement;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getStatement() {
        return statement;
    }

    @Override
    public String toString() {
        return "WhileStatement{" +
                "condition=" + condition +
                ", statement=" + statement +
                '}';
    }
}
