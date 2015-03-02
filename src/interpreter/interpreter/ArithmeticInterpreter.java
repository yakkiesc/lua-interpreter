package interpreter.interpreter;

import interpreter.ast.BinaryExpression;
import interpreter.ast.Literal;
import interpreter.ast.Node;
import interpreter.ast.UnaryExpression;
import interpreter.lexer.Token;

import java.util.Objects;
import java.util.Stack;

/**
 * Created by Thomas on 2-3-2015.
 */
public class ArithmeticInterpreter implements Interpreter {

    private Stack<Object> stack = new Stack<Object>();

    @Override
    public void interpret(Node node) {
        if(node instanceof Literal){
            stack.push(((Literal) node).getValue());
        } else if(node instanceof BinaryExpression){
            interpret(((BinaryExpression) node).getLeftExpression());
            interpret(((BinaryExpression) node).getRightExpression());

            int operator = ((BinaryExpression) node).getOperator();

            //this is gonna get ugly ;(
            Object left = pop();
            Object right = pop();


            //i'm sooo sorry :(
            double d1 = Double.valueOf(left.toString());
            double d2 = Double.valueOf(right.toString());

            if(left instanceof Double || right instanceof Double){
                push(evalOperator(operator, d1, d2));
            } else {
                push((int)evalOperator(operator, d1, d2));
            }
        } else if(node instanceof UnaryExpression){
            interpret(((UnaryExpression) node).getExpression());
            Object pop = pop();
            if(pop instanceof Integer){
                int i = (Integer)pop;
                push(-i);
            }
            if(pop instanceof Double){
                double d = (Double)pop;
                push(-d);
            }
        }
    }

    public void printResult(){
        System.out.println("Result = " + pop());
    }

    private double evalOperator(final int operator, final double left, final double right){
        switch(operator){
            case Token.PLUS:
                return left + right;
            case Token.MINUS:
                return left - right;
            case Token.MULTIPLY:
                return left * right;
            case Token.DIVIDE:
                return left / right;

            default:
                System.err.println("No operator");
                return 0;
        }
    }

    private void push(Object object){
        stack.push(object);
    }

    private Object pop(){
        return stack.pop();
    }

}
