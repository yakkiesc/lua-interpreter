package interpreter.lexer;

import java.util.HashMap;

/**
 * Created by Thomas on 2-3-2015.
 */
public class Lexer {

    private final char[] buffer;

    private final int length;

    private boolean endOfFile = false;

    private final HashMap<String, Integer> registeredKeywords;

    private int index;

    private int lineIndex;
    private int columnIndex;

    private int markedIndex;

    private boolean markedEndOfFile;

    private int markedLineIndex;
    private int markedColumnIndex;

    public Lexer(final String source){
        this(source.toCharArray());
    }

    public Lexer(char[] buffer) {
        this(buffer, buffer.length);
    }

    public Lexer(char[] buffer, int length) {
        this.buffer = buffer;
        this.length = length;

        index = 0;

        markedColumnIndex = 0;
        markedLineIndex = 0;

        lineIndex = 0;
        columnIndex = 0;

        registeredKeywords = new HashMap<String, Integer>();

        registerKeyword("function", Token.FUNCTION);
        registerKeyword("if", Token.IF);
        registerKeyword("then", Token.THEN);
        registerKeyword("else", Token.ELSE);
        registerKeyword("return", Token.RETURN);
        registerKeyword("do", Token.DO);
        registerKeyword("break", Token.BREAK);
        registerKeyword("while", Token.WHILE);
        registerKeyword("end", Token.END);
        registerKeyword("elseif", Token.ELSEIF);
        registerKeyword("until", Token.UNTIL);
        registerKeyword("or", Token.OR);
        registerKeyword("and", Token.AND);
        registerKeyword("not", Token.NOT);
        registerKeyword("in", Token.IN);
        registerKeyword("goto", Token.GOTO);
        registerKeyword("for", Token.FOR);
        registerKeyword("nil", Token.NIL_LITERAL);

        System.out.println("No duplicate keywords found.");
    }

    /**
     *  the registerKeyword will bind the given keywordName to the given keywordID
     *
     * @param keywordName the name of the keyword
     * @param keywordID the id this keyword will be bound to
     */

    public void registerKeyword(final String keywordName, final int keywordID){
        if(registeredKeywords.containsKey(keywordName)){
            System.err.printf("Duplicate registered keywords for name %s.\n", keywordName);
        } if(registeredKeywords.containsValue(keywordID)){
            System.err.printf("Duplicate registered ID's for id %d\n", keywordID);
        } else {
            registeredKeywords.put(keywordName, keywordID);
        }
    }

    /**
     * The getToken() method will return the next lexeme found in the input buffer at the current index.
     *
     * The id of this token will determine what lexeme was found.
     *
     * If no matching lexemes were found a token with the id Token.ERROR will be returned.
     *
     * @return the next lexeme found in the input buffer.
     */
    public Token getToken() throws LexicalException {
        char ch = getChar();

        //consume all whitespace
        while (Character.isWhitespace(ch)){
            if(ch == '\r' || ch == '\n'){
                if(ch == '\r'){ // handle possible \r\n conventions.
                    accept('\n');
                }
                newLine(); // increment the lineIndex
            }
            ch = nextChar();
        }

        //floating pointer or integer literals
        if(ch == '.' || Character.isDigit(ch)){
            final StringBuilder lexemeBuffer = new StringBuilder();

            boolean isFloatingPointNumber = (ch == '.');

            do {
                lexemeBuffer.append(ch);
                ch = nextChar();
                if(ch == 'e'){
                    if(lexemeBuffer.indexOf("e") > - 1){
                        throwLexicalError("Unexpected character 'e' found in exponential floating point expression.");
                    }
                    isFloatingPointNumber = true;
                }
                if(ch == '-'){
                    if(lexemeBuffer.indexOf("e") != lexemeBuffer.length() - 1){
                        throwLexicalError("Unexpected character '-' found in floating point expression, '-' only expected after 'e'.");
                    }
                }
                if(ch == '.'){
                    if(lexemeBuffer.indexOf(".") > -1){
                        throwLexicalError("Unexpected character '.' found in floating point expression, duplicate use of '.'.");
                    }
                    isFloatingPointNumber = true;
                }
            } while(ch == '.' || ch == 'e' || ch == '-' || Character.isDigit(ch));

            final String lexemeString = lexemeBuffer.toString();

            Token token;

            if(isFloatingPointNumber){
                double floatValue = Double.parseDouble(lexemeString);
                token = createToken(Token.FLOAT_LITERAL);
                token.setFloatValue(floatValue);
            } else {
                int intValue = Integer.parseInt(lexemeString);
                token = createToken(Token.INTEGER_LITERAL);
                token.setIntValue(intValue);
            }

            return token;
        }

        //identifier
        if(ch == '_' || Character.isAlphabetic(ch)){
            final StringBuilder lexemeBuffer = new StringBuilder();

            do {
                lexemeBuffer.append(ch);
                ch = nextChar();
            } while(ch == '_' || Character.isAlphabetic(ch) || Character.isDigit(ch));

            final String lexemeString = lexemeBuffer.toString();

            Token token;

            if(lexemeString.equals("true") || lexemeString.equals("false")){
                final boolean value = Boolean.parseBoolean(lexemeString);
                token = createToken(Token.BOOLEAN_LITERAL);
                token.setBooleanValue(value);
            } else if(registeredKeywords.containsKey(lexemeString)){
                final int tokenID = registeredKeywords.get(lexemeString);
                token = createToken(tokenID);
            } else {
                token = createToken(Token.IDENTIFIER, lexemeString);
            }

            return token;
        }

        //string literal
        if(ch == '\"'){
            final StringBuilder lexemeBuffer = new StringBuilder();

            while((ch = nextChar()) != '\"'){
                if(ch == '\\'){
                    ch = nextChar();
                }
                lexemeBuffer.append(ch);
            }

            expect('\"');

            final String lexemeString = lexemeBuffer.toString();

            Token token = createToken(Token.STRING_LITERAL, lexemeString);
            return token;
        }

        //any symbol token, or an unknown lexeme
        final int tokenID = getSymbolID(ch);
        consume();
        return createToken(tokenID);
    }

    /**
     * The peekToken method will return the next lexeme found in the input without consuming the input buffer.
     *
     * @return the next token in input
     */
    public Token peekToken() throws LexicalException {
        mark();
        final Token peekedToken = getToken();
        reset();
        return peekedToken;
    }

    private int getSymbolID(char ch) {
        if(endOfFile){
            return Token.EOF;
        }
        switch(ch){
            case '=':
                if(accept('=')){
                    return Token.EQUALS;
                }
                return Token.ASSIGNMENT;
            case '>':
                if(accept('=')){
                    return Token.GREATER_THAN_EQUALS;
                }
                return Token.GREATER_THAN;
            case '<':
                if(accept('=')){
                    return Token.LESS_THAN_EQUALS;
                }
                return Token.LESS_THAN;
            case ',':
                return Token.COMMA;
            case ':':
                if(accept(':')){
                    return Token.LABEL;
                }
                return Token.COLON;
            case '.':
                if(accept('.')){
                    if(accept('.')){
                        return Token.VARARG;
                    }
                    return Token.CONCATENATION;
                }
                return Token.DOT;
            case '%':
                return Token.MODULUS;
            case '*':
                return Token.MULTIPLY;
            case '/':
                return Token.DIVIDE;
            case '-':
                return Token.MINUS;
            case '+':
                return Token.PLUS;
            case ')':
                return Token.PARENTHESIS_CLOSE;
            case '(':
                return Token.PARENTHESIS_OPEN;
            case ']':
                return Token.BRACKET_CLOSE;
            case '[':
                return Token.BRACKET_OPEN;
            case '{':
                return Token.CURLY_BRACKET_OPEN;
            case '}':
                return Token.CURLY_BRACKET_CLOSE;
            case '^':
                return Token.POWER;
            case '#':
                return Token.STRING_SIZE;

            default: return Token.ERROR;
        }
    }

    public void throwLexicalError(final String message) throws LexicalException {
        throw new LexicalException(message, lineIndex, columnIndex);
    }

    public void mark(){
        markedIndex = index;
        markedLineIndex = lineIndex;
        markedColumnIndex = columnIndex;
        markedEndOfFile = endOfFile;
    }

    public void reset(){
        index = markedIndex;
        lineIndex = markedLineIndex;
        columnIndex = markedColumnIndex;
        endOfFile = markedEndOfFile;
    }

    private void newLine(){
        lineIndex ++;
        columnIndex = 0;
    }

    public char getChar(){
        if(index < length){
            return buffer[index];
        } else {
            endOfFile = true;
            return 0;
        }
    }

    /**
     * Create a token with the given id and lexeme.
     * This method is created to eliminate code redundancy.
     *
     * @param id the id of the token
     * @return the created token with the current lineIndex, columnIndex and the given id.
     */
    private Token createToken(final int id){
        return new Token(lineIndex, columnIndex, id);
    }

    /**
     * Create a token with the given id and lexeme.
     * This method is created to eliminate code redundancy.
     *
     * @param id the id of the token.
     * @param lexeme the lexeme of the token.
     * @return the created token with the current lineIndex, columnIndex and the given id and lexeme.
     */
    private Token createToken(final int id, final String lexeme){
        return new Token(lineIndex, columnIndex, id, lexeme);
    }


    /**
     * The accept method will return true if the next character in input is equal to the given expected character.
     *
     * The lexer will consume one character if the peeked character is equal to the expected character.
     *
     * @param expectedCharacter
     * @return true if the next character in input matches the given expected character
     */
    private boolean accept(final char expectedCharacter){
        char peekedChar = peekChar();
        if(peekedChar == expectedCharacter){
            consume();
            return true;
        } else {
            return false;
        }
    }

    private boolean expect(final char expectedCharacter) throws LexicalException{
        if(accept(expectedCharacter)){
            return true;
        } else {
            final char unexpectedChar = peekChar();
            throwLexicalError(String.format("Unexpected character %c, expected %c", unexpectedChar, expectedCharacter));
            return false;
        }
    }

    /**
     * The peekChar method will return the next character in the input buffer without consuming it.
     *
     * @return the next character in input
     */
    public char peekChar(){
        final char peekedChar = nextChar();
        consume(-1);
        return peekedChar;
    }

    /**
     * The nextChar method will return the next character in the input buffer, this will consume the input.
     *
     * @return the next character in input.
     */
    private char nextChar(){
        consume();
        return getChar();
    }

    /**
     * The consume method will increment the current index of the input buffer.
     */
    private void consume() {
        consume(1);
    }

    /**
     * The consume method will increment the current index of the input buffer by the given amount.
     */
    private void consume(int amount) {
        index += amount;
        columnIndex += amount;
    }

}
