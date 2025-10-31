package ru.nsu.ga.grentseva.parser;

import ru.nsu.ga.grentseva.exceptions.ParseException;
import ru.nsu.ga.grentseva.operations.*;
import ru.nsu.ga.grentseva.operations.Number;

public class ParseExpression{
    private final String input;
    private int pos;

    public ParseExpression(String str) {
        this.input = str.replaceAll(" ", "");
        this.pos = 0;
    }

    public Expression parse() throws ParseException {
        Expression expr = parseAddSub();
        if (pos != input.length()) {
            throw new ParseException("Error: extra characters");
        }
        return expr;
    }

    private Expression parseAddSub() throws ParseException {
        Expression left = parseMulDiv();

        while (hasMore()) {
            char c = currentChar();
            if (c == '+' || c == '-') {
                consumeChar();
                Expression right = parseMulDiv();
                left = (c == '+') ? new Add(left, right) : new Sub(left, right);
            } else {
                break;
            }
        }

        return left;
    }

    private Expression parseMulDiv() throws ParseException {
        Expression left = parseAtom();

        while (hasMore()) {
            char c = currentChar();
            if (c == '*' || c == '/') {
                consumeChar();
                Expression right = parseAtom();
                left = (c == '*') ? new Mul(left, right) : new Div(left, right);
            } else {
                break;
            }
        }

        return left;
    }

    private Expression parseAtom() throws ParseException {
        if (!hasMore()) {
            throw new ParseException("Error: unexpected end of line");
        }

        char c = currentChar();

        if (c == '(') {
            consumeChar();
            Expression inside = parseAddSub();
            if (!hasMore() || currentChar() != ')') {
                throw new ParseException("Error: missing closing parenthesis");
            }
            consumeChar();
            return inside;
        }

        if (Character.isDigit(c)) {
            return parseNumberLiteral();
        }

        if (Character.isLetter(c)) {
            return parseVariableName();
        }

        throw new ParseException("Error: extra symbol");
    }

    private Expression parseNumberLiteral() {
        int start = pos;
        while (hasMore() && Character.isDigit(currentChar())) {
            consumeChar();
        }
        int value = Integer.parseInt(input.substring(start, pos));
        return new Number(value);
    }

    private Expression parseVariableName() {
        int start = pos;
        while (hasMore() && Character.isLetter(currentChar())) {
            consumeChar();
        }
        String name = input.substring(start, pos);
        return new Variable(name);
    }

    private boolean hasMore() {
        return pos < input.length();
    }

    private char currentChar() {
        return input.charAt(pos);
    }

    private void consumeChar() {
        pos++;
    }
}
