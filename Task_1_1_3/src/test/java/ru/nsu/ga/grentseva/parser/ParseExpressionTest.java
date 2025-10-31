package ru.nsu.ga.grentseva.parser;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.exceptions.DivisionByZeroException;
import ru.nsu.ga.grentseva.exceptions.MissingVariableException;
import ru.nsu.ga.grentseva.exceptions.ParseException;
import ru.nsu.ga.grentseva.operations.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ParseExpressionTest {

    @Test
    void testParseSimpleNumber() throws DivisionByZeroException, ParseException, MissingVariableException {
        ParseExpression parser = new ParseExpression("42");
        Expression expr = parser.parse();
        assertEquals(42, expr.eval("x=5"));
    }

    @Test
    void testParseSimpleVariable() throws DivisionByZeroException, ParseException, MissingVariableException {
        ParseExpression parser = new ParseExpression("x");
        Expression expr = parser.parse();
        assertEquals(7, expr.eval("x=7"));
    }

    @Test
    void testParseAdd() throws DivisionByZeroException, ParseException, MissingVariableException {
        ParseExpression parser = new ParseExpression("3+4");
        Expression expr = parser.parse();
        assertEquals(7, expr.eval("x=10"));
    }

    @Test
    void testParseSub() throws DivisionByZeroException, ParseException, MissingVariableException {
        ParseExpression parser = new ParseExpression("10-3");
        Expression expr = parser.parse();
        assertEquals(7, expr.eval("x=8"));
    }

    @Test
    void testParseMulDiv() throws DivisionByZeroException, ParseException, MissingVariableException {
        ParseExpression parser = new ParseExpression("2*3/6");
        Expression expr = parser.parse();
        assertEquals(1, expr.eval("x=0"));
    }

    @Test
    void testParseComplexExpression() throws DivisionByZeroException, ParseException, MissingVariableException {
        ParseExpression parser = new ParseExpression("(3+(2*x))");
        Expression expr = parser.parse();
        assertEquals(23, expr.eval("x=10"));
    }

    @Test
    void testParseWithMultipleVariables() throws DivisionByZeroException, ParseException, MissingVariableException {
        ParseExpression parser = new ParseExpression("(x*y+z)");
        Expression expr = parser.parse();
        assertEquals(27, expr.eval("x = 3;y = 4;z = 15"));
    }

    @Test
    void testParsePrint() throws ParseException{
        ParseExpression parser = new ParseExpression("(2*(x+3))");
        Expression expr = parser.parse();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        expr.print();
        System.setOut(System.out);
        assertEquals("(2*(x+3))", out.toString());
    }

    @Test
    void testParseInvalidExpression_MissingParenthesis() {
        ParseExpression parser = new ParseExpression("(3+4");
        ParseException exception = assertThrows(
                ParseException.class,
                parser::parse
        );
        assertTrue(exception.getMessage().contains("missing closing parenthesis"));
    }
}
