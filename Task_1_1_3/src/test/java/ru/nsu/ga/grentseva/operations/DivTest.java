package ru.nsu.ga.grentseva.operations;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.exceptions.DivisionByZeroException;
import ru.nsu.ga.grentseva.exceptions.MissingVariableException;
import ru.nsu.ga.grentseva.exceptions.ParseException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DivTest {

    @Test
    void testEvalWithNumbers() throws DivisionByZeroException, MissingVariableException {
        Expression expr = new Div(new Number(10), new Number(2));
        assertEquals(5, expr.eval(Map.of()));
    }

    @Test
    void testEvalWithVariable() throws DivisionByZeroException, MissingVariableException {
        Expression expr = new Div(new Variable("x"), new Number(2));
        assertEquals(3, expr.eval(Map.of("x", 6)));
    }

    @Test
    void testEvalWithVariable_String() throws DivisionByZeroException, ParseException, MissingVariableException {
        Expression expr = new Div(new Variable("x"), new Number(2));
        assertEquals(4, expr.eval("x=8"));
    }

    @Test
    void testDivisionByZero() {
        Expression expr = new Div(new Number(5), new Number(0));
        DivisionByZeroException exception = assertThrows(
                DivisionByZeroException.class,
                () -> expr.eval(Map.of())
        );
        assertEquals("Error: division by zero", exception.getMessage());
    }

    @Test
    void testDerivative() throws DivisionByZeroException, ParseException, MissingVariableException {
        Expression expr = new Div(new Variable("x"), new Number(2));
        Expression derivative = expr.derivative("x");

        int result = derivative.eval("x=5");
        assertEquals(1, expr.eval("x=2"));
        assertEquals(0, result);
    }

    @Test
    void testPrint() {
        Expression expr = new Div(new Number(6), new Variable("y"));
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        expr.print();
        System.setOut(System.out);

        assertEquals("(6/y)", out.toString());
    }
}
