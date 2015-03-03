package interpreter.ast;

import interpreter.ast.visitor.NodeVisitor;

import java.util.ArrayList;

/**
 * Created by Thomas on 2-3-2015.
 */
public class FunctionDeclaration extends Statement {

    private final String functionName;
    private final ArrayList<String> parameterNames;
    private final Statement body;

    public FunctionDeclaration(int lineIndex, int columnIndex, String functionName, ArrayList<String> parameterNames, Statement body) {
        super(lineIndex, columnIndex);
        this.functionName = functionName;
        this.parameterNames = parameterNames;
        this.body = body;
    }

    public String getFunctionName() {
        return functionName;
    }

    public ArrayList<String> getParameterNames() {
        return parameterNames;
    }

    public Statement getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "functionDeclaration{" +
                "functionName='" + functionName + '\'' +
                ", parameterNames=" + parameterNames +
                ", body=" + body +
                '}';
    }

    @Override
    public void visit(NodeVisitor visitor) {

    }

}
