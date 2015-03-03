package interpreter.codegen;

import interpreter.ast.*;
import interpreter.ast.visitor.NodeVisitor;
import interpreter.lexer.Token;

/**
 *
 * This code will make an attempt to generate java byte code from the given AST.
 *
 *
 * Created by Thomas on 2-3-2015.
 */
public class IntermediateCodeGenerator implements NodeVisitor{


    @Override
    public void visitBinaryExpression(int operator) {

    }

    @Override
    public void visitLiteral(Object value, int type) {

    }

}
