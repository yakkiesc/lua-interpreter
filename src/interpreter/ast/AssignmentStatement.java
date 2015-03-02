package interpreter.ast;

import java.util.List;

/**
 * Created by Thomas on 2-3-2015.
 */
public class AssignmentStatement extends Statement {

    private final List<Expression> variables;
    private final List<Expression> values;

    public AssignmentStatement(int lineIndex, int columnIndex, List<Expression> variables, List<Expression> values) {
        super(lineIndex, columnIndex);
        this.variables = variables;
        this.values = values;
    }

    public List<Expression> getVariables() {
        return variables;
    }

    public List<Expression> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "AssignmentStatement{" +
                "variables=" + variables +
                ", values=" + values +
                '}';
    }
}
