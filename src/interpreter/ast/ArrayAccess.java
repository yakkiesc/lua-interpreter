package interpreter.ast;

import interpreter.ast.visitor.NodeVisitor;

/**
 * Created by Thomas on 2-3-2015.
 */
public class ArrayAccess extends Expression{

    private final Expression value;
    private final Expression index;

    public ArrayAccess(int lineIndex, int columnIndex, Expression value, Expression index) {
        super(lineIndex, columnIndex);
        this.value = value;
        this.index = index;
    }

    public Expression getValue() {
        return value;
    }

    public Expression getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "ArrayAccess{" +
                "value=" + value +
                ", index=" + index +
                '}';
    }

    @Override
    public void visit(NodeVisitor visitor) {

    }
}
