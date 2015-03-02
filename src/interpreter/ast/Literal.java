package interpreter.ast;

import interpreter.lexer.Token;

/**
 * Created by Thomas on 2-3-2015.
 */
public class Literal extends Expression {

    private final int literalType;

    private final Object value;

    public Literal(int lineIndex, int columnIndex, int literalType, Object value) {
        super(lineIndex, columnIndex);
        this.literalType = literalType;
        this.value = value;
    }

    public boolean getBooleanValue(){
        return (Boolean)value;
    }

    public int getLiteralType() {
        return literalType;
    }

    public Object getValue() {
        return value;
    }

    public double getDoubleValue(){
        return (Double)value;
    }

    public int getIntValue(){
        return (Integer)value;
    }

    public String getStringValue(){
        return (String)value;
    }

    @Override
    public String toString() {
        return "Literal{" +
                "literalType=" + Token.toString(literalType) +
                ", value=" + value +
                '}';
    }
}
