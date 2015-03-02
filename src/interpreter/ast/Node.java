package interpreter.ast;

/**
 * Created by Thomas on 2-3-2015.
 */
public class Node {

    private final int lineIndex;
    private final int columnIndex;

    public Node(int lineIndex, int columnIndex) {
        this.lineIndex = lineIndex;
        this.columnIndex = columnIndex;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    @Override
    public String toString() {
        return "lineIndex=" + lineIndex +
                ", columnIndex=" + columnIndex;
    }
}
