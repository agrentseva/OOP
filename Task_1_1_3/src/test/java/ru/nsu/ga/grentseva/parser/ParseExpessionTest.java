package ru.nsu.ga.grentseva.parser;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.operations.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ParseExpessionTest {

    @Test
    void testParseSimpleNumber() {
        ParseExpession parser = new ParseExpession("42");
        Expression expr = parser.parse();
        assertEquals(42, expr.eval("x=5"));
    }

    @Test
    void testParseSimpleVariable() {
        ParseExpession parser = new ParseExpession("x");
        Expression expr = parser.parse();
        assertEquals(7, expr.eval("x=7"));
    }

    @Test
    void testParseAdd() {
        ParseExpession parser = new ParseExpession("3+4");
        Expression expr = parser.parse();
        assertEquals(7, expr.eval("x=10"));
    }

    @Test
    void testParseSub() {
        ParseExpession parser = new ParseExpession("10-3");
        Expression expr = parser.parse();
        assertEquals(7, expr.eval("x=8"));
    }

    @Test
    void testParseMulDiv() {
        ParseExpession parser = new ParseExpession("2*3/6");
        Expression expr = parser.parse();
        assertEquals(1, expr.eval("x=0"));
    }

    @Test
    void testParseComplexExpression() {
        ParseExpession parser = new ParseExpession("(3+(2*x))");
        Expression expr = parser.parse();
        assertEquals(23, expr.eval("x=10"));
    }

    @Test
    void testParseWithMultipleVariables() {
        ParseExpession parser = new ParseExpession("(x*y+z)");
        Expression expr = parser.parse();
        assertEquals(27, expr.eval("x = 3;y = 4;z = 15"));
    }

    @Test
    void testParsePrint() {
        ParseExpession parser = new ParseExpession("(2*(x+3))");
        Expression expr = parser.parse();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        expr.print();
        System.setOut(System.out);
        assertEquals("(2*(x+3))", out.toString());
    }

    @Test
    void testParseInvalidExpression_MissingParenthesis() {
        ParseExpession parser = new ParseExpession("(3+4");
        Exception exception = assertThrows(IllegalArgumentException.class, parser::parse);
        assertTrue(exception.getMessage().contains("missing closing parenthesis"));
    }
}
