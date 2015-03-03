package interpreter.parser;

import interpreter.ast.*;
import interpreter.lexer.Lexer;
import interpreter.lexer.LexicalException;
import interpreter.lexer.Token;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Thomas on 2-3-2015.
 */
public class Parser {

    private final HashMap<Class<? extends Node>, Constructor<? extends Node>> cachedConstructors;

    private Lexer lexer;

    private Token currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;

        cachedConstructors = new HashMap<Class<? extends Node>, Constructor<? extends Node>>();
    }

    public Node program(){
        try {
            return expression();
        } catch (LexicalException e) {
            e.printStackTrace();
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Expression expression() throws LexicalException, SyntaxException {
        return conditional();
    }

    /**
     * The conditional method will parse productions with the form of:
     *
     * expression ('>' | '>=' | '<' | '<=' | '==') expression
     */
    private Expression conditional() throws LexicalException, SyntaxException {
        Expression expression = term();
        if(accept(Token.OR) || accept(Token.AND) ||  accept(Token.GREATER_THAN) || accept(Token.GREATER_THAN_EQUALS) || accept(Token.LESS_THAN) || accept(Token.LESS_THAN_EQUALS) || accept(Token.EQUALS)){
            final int operator = currentToken.getId();
            final Expression right = conditional();
            expression = createExpression(BinaryExpression.class, expression, operator, right);
        }
        return expression;
    }

    /**
     * The term method will parse productions with the form of:
     *
     * expression ('+' | '-') expression
     */
    private Expression term() throws LexicalException, SyntaxException {
        Expression expression = factor();
        if(accept(Token.PLUS) || accept(Token.MINUS)){
            final int operator = currentToken.getId();
            final Expression right = term();
            expression = createExpression(BinaryExpression.class, expression, operator, right);
        }
        return expression;
    }

    /**
     * The factor method will parse productions with the form of:
     *
     * expression ('*' | '/' | '%') expression
     */
    private Expression factor() throws LexicalException, SyntaxException {
        Expression expression = unary();
        if(accept(Token.MULTIPLY) || accept(Token.DIVIDE) || accept(Token.MODULUS)){
            final int operator = currentToken.getId();
            final Expression right = factor();
            expression = createExpression(BinaryExpression.class, expression, operator, right);
        }
        return expression;
    }


    /**
     * The unary method will parse productions with the form of:
     *
     *  ( '#' | '!' | 'not') expression
     *
     * @return
     * @throws LexicalException
     * @throws SyntaxException
     */
    private Expression unary() throws LexicalException, SyntaxException {
        if(accept(Token.NOT) || accept(Token.STRING_SIZE) || accept(Token.MINUS)){
            final int operator = currentToken.getId();
            Expression expression = postfix();
            return createExpression(UnaryExpression.class, operator, expression);
        }
        return postfix();
    }


    /**
     *  The postfix expression will generate 'fake' left recursive trees for variable access and function calls.
     *
     *  This method will parse productions with the form of:
     *
     *  identifier . postfix |
     *  identifier . identifier |
     *  postfix '[' expression ']' |
     *  postfix '(' args ')'
     *
     *
     * @return
     * @throws LexicalException
     * @throws SyntaxException
     */
    private Expression postfix() throws LexicalException, SyntaxException {
        Expression expression = null;
        if(accept(Token.IDENTIFIER)){
            final String variableName = currentToken.getLexeme();
            final Expression variable = createExpression(Variable.class, variableName);
            expression = variable;
            //evil loop >:), i'm so sorry

            while(true){
                if(accept(Token.DOT)){ // variable access: x.y
                    expect(Token.IDENTIFIER);
                    final String accessName = currentToken.getLexeme();
                    final Expression accessVariable = createExpression(Variable.class, accessName);
                    expression = createExpression(VariableAccess.class, expression, accessVariable);
                    continue;
                }
                if(accept(Token.PARENTHESIS_OPEN)){ // function call: x()
                    final ArrayList<Expression> arguments = expressionList();
                    expect(Token.PARENTHESIS_CLOSE);
                    expression = createExpression(FunctionCall.class, expression, arguments);
                    continue;
                }
                if(accept(Token.BRACKET_OPEN)){ // array access: x[y]
                    final Expression index = expression();
                    expect(Token.BRACKET_CLOSE);
                    expression = createExpression(ArrayAccess.class, expression, index);
                    continue;
                }
                if(accept(Token.COLON)){ // object calls: x:f(y) = x.f(x,y)
                    expect(Token.IDENTIFIER);
                    final String accessName = currentToken.getLexeme();
                    final Expression accessVariable = createExpression(Variable.class, accessName);
                    final Expression variableAccess = createExpression(VariableAccess.class, expression, accessVariable);
                    expect(Token.PARENTHESIS_OPEN);
                    final ArrayList<Expression> arguments = expressionList();
                    expect(Token.PARENTHESIS_CLOSE);
                    arguments.add(0, expression);
                    expression = createExpression(FunctionCall.class, variableAccess, arguments);
                    continue;
                }
                break;
            }

        } else {
            expression = literal();
        }
        return expression;
    }

    /**
     * This method will parse productions with the form of:
     *
     * 'nil' | 'true' | 'false' | integer | float | string | '(' expression ')'
     *
     *
     * @return
     * @throws LexicalException
     * @throws SyntaxException
     */
    private Expression literal() throws LexicalException, SyntaxException {
        Object value;

        if(accept(Token.PARENTHESIS_OPEN)){
            Expression expression = expression();
            expect(Token.PARENTHESIS_CLOSE);
            return expression;
        }

        if(accept(Token.BOOLEAN_LITERAL)){
            value = currentToken.getBooleanValue();
        } else if(accept(Token.INTEGER_LITERAL)){
            value = currentToken.getIntValue();
        } else if(accept(Token.FLOAT_LITERAL)){
            value = currentToken.getFloatValue();
        } else if(accept(Token.STRING_LITERAL)){
            value = currentToken.getLexeme();
        } else {
            throw new SyntaxException("Unexpected token " + currentToken.getId(), currentToken.getLineIndex(), currentToken.getColumnIndex());
        }

        final int literalType = currentToken.getId();

        return createExpression(Literal.class, literalType, value);
    }

    /**
     * This method will parse productions with the form of:
     *
     * expression ',' arguments | expression ',' expression | expression ')'
     *
     * @return
     * @throws LexicalException
     * @throws SyntaxException
     */
    private ArrayList<Expression> expressionList() throws LexicalException, SyntaxException {
        if(accept(Token.PARENTHESIS_CLOSE)){
            return null;
        }
        final ArrayList<Expression> arguments = new ArrayList<Expression>();
        //another evil loop! REFACTOR //TODO:: REFACTOR!!!
        while(true){
            final Expression argument = expression();
            arguments.add(argument);
            if(!accept(Token.COMMA)){
                return arguments;
            }
        }
    }


    public Statement statement() throws LexicalException, SyntaxException {
        if(accept(Token.IF)){
            return ifStatement();
        } else if(accept(Token.FUNCTION)){
            return functionDeclaration();
        } else if(accept(Token.FOR)){
            return forStatement();
        } else if(accept(Token.WHILE)){
            return whileStatement();
        }

        Expression expression = expression();

        if(accept(Token.ASSIGNMENT)){

        }

        return createStatement(StatementExpression.class, expression);
    }

    private Statement whileStatement() throws SyntaxException, LexicalException {
        final Expression condition = expression();
        expect(Token.DO);
        final Statement body = block();
        return createStatement(WhileStatement.class, condition, body);
    }

    private Statement forStatement() throws SyntaxException, LexicalException {
        expect(Token.IDENTIFIER);
        final String initialVariableName = currentToken.getLexeme();
        if(accept(Token.ASSIGNMENT)){ // numerical for loop
            final Expression initialVariable = createExpression(Variable.class, initialVariableName);
            final Expression value = expression();
            final Statement var = createStatement(AssignmentStatement.class, initialVariable, value);
            Expression limit, step;
            limit = step = null;
            if(accept(Token.COMMA)){
                limit = expression();
                if(accept(Token.COMMA)){
                    step = expression();
                }
            }
            expect(Token.DO);
            final Statement body = block();
            return createStatement(NumericForStatement.class, var, limit, step, body);
        } else { // iterative for loop
            ArrayList<String> nameList = new ArrayList<String>();
            nameList.add(initialVariableName);
            if(accept(Token.COMMA)) {
                nameList.addAll(nameList());
            }
            expect(Token.IN);
            final ArrayList<Expression> expressionsList = expressionList();
            expect(Token.DO);
            final Statement body = block();
            return createStatement(IterativeForStatement.class, nameList, expressionsList, body);
        }
    }

    private Statement ifStatement() throws SyntaxException, LexicalException {
        Statement body;
        Statement elseStatement = null;
        final Expression condition = expression();

        expect(Token.THEN);

        body = ifBody();

        switch(currentToken.getId()){
            case Token.ELSE:
                elseStatement = elseStatement();
                break;
            case Token.ELSEIF:
                elseStatement = ifStatement();
                break;
            case Token.END:
                elseStatement = null;
                break;
        }

        return createStatement(IfStatement.class, condition, body, elseStatement);
    }

    private Statement elseStatement() throws SyntaxException, LexicalException {
        final Statement elseBody = ifBody();
        if(currentToken.getId() != Token.END){
            throw new SyntaxException("Expected end keyword after else body.", currentToken.getLineIndex(), currentToken.getColumnIndex());
        }
        return createStatement(IfStatement.class, null, elseBody, null);
    }

    private Statement block() throws LexicalException, SyntaxException {
        Expression returnValue = null;
        final ArrayList<Statement> statements = new ArrayList<Statement>();
        while(!accept(Token.END)){
            if(accept(Token.RETURN)){
                returnValue = expression();
                break;
            }
            final Statement statement = statement();
            statements.add(statement);
        }
        return createStatement(Block.class, statements, returnValue);
    }

    private Statement ifBody() throws LexicalException, SyntaxException {
        Expression returnValue = null;
        final ArrayList<Statement> statements = new ArrayList<Statement>();
        while(!accept(Token.ELSE) && !accept(Token.ELSEIF) && !accept(Token.END)){
            if(accept(Token.RETURN)){
                returnValue = expression();
                accept(Token.ELSE);
                accept(Token.ELSEIF);
                accept(Token.END);
                break;
            }
            if(accept(Token.BREAK)){
                accept(Token.ELSE);
                accept(Token.ELSEIF);
                accept(Token.END);
                break;
            }
            final Statement statement = statement();
            statements.add(statement);
        }
        return createStatement(Block.class, statements, returnValue);
    }

    private Statement assignment(){
        return null;
    }

    private Statement functionDeclaration() throws SyntaxException, LexicalException {
        expect(Token.IDENTIFIER);
        final String functionName = currentToken.getLexeme();
        expect(Token.PARENTHESIS_OPEN);

        final ArrayList<String> parameterNames = nameList();

        expect(Token.PARENTHESIS_CLOSE);

        final Statement functionBody = block();

        return createStatement(FunctionDeclaration.class, functionName, parameterNames, functionBody);
    }

    private ArrayList<String> nameList() throws SyntaxException, LexicalException {
        final ArrayList<String> nameList = new ArrayList<String>();
        while(true){
            expect(Token.IDENTIFIER);
            final String parameterName = currentToken.getLexeme();
            nameList.add(parameterName);
            if(!accept(Token.COMMA)){
                break;
            }
        }
        return nameList;
    }

    private Statement createStatement(final Class<? extends Statement> nodeType, final Object...arguments) {
        return nodeType.cast(createNode(nodeType, arguments));
    }

    private Expression createExpression(final Class<? extends Expression> nodeType, final Object...arguments) {
        return nodeType.cast(createNode(nodeType, arguments));
    }

    private Node createNode(final Class<? extends Node> nodeType, final Object...arguments) {
        final Object[] args = new Object[arguments.length + 2];
        args[0] = currentToken.getLineIndex();
        args[1] = currentToken.getColumnIndex();
        for (int i = 0; i < arguments.length; i++) {
            args[i + 2] = arguments[i];
        }

        if(!cachedConstructors.containsKey(nodeType)){
            cachedConstructors.put(nodeType, (Constructor<? extends Node>) nodeType.getConstructors()[0]);
        }

        try {
            return cachedConstructors.get(nodeType).newInstance(args);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean expect(final int expectedTokenID) throws LexicalException, SyntaxException {
        if(accept(expectedTokenID)){
            return true;
        } else {
            Token token = lexer.getToken();
            throw new SyntaxException(String.format("Unexpected token %d, expected %d", token.getId(), expectedTokenID), token.getLineIndex(), token.getColumnIndex());
        }
    }

    private boolean accept(final int expectedTokenID) throws LexicalException {
        if(lexer.peekToken().getID() == expectedTokenID){
            currentToken = lexer.getToken();
            return true;
        } else {
            return false;
        }
    }

}
