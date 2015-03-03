package interpreter.ast.visitor;

import interpreter.ast.*;

/**
 * Created by Thomas on 3-3-2015.
 */
public interface NodeVisitor {

    public void visitArrayAccess (final ArrayAccess arrayAccess);

    public void visitAssignmentStatement(final AssignmentStatement assignmentStatement);

    public void visitBinaryExpression(final BinaryExpression binaryExpression);

    public void visitBlock(final Block block);

    public void visitFunctionCall(final FunctionCall functionCall);

    public void visitFunctionDeclaration(final FunctionDeclaration functionDeclaration);


}
