package ru.nsu.ga.grentseva.operations;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.exceptions.DivisionByZeroException;
import ru.nsu.ga.grentseva.exceptions.MissingVariableException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AddTest {

    @Test
    void testEvalWithNumbers() throws DivisionByZeroException, MissingVariableException {
        Expression expr = new Add(new Number(3), new Number(5));
        int result = expr.eval(Map.of());
        assertEquals(8, result);
    }

    @Test
    void testEvalWithVariable()  throws DivisionByZeroException, MissingVariableException {
        Expression expr = new Add(new Variable("x"), new Number(2));
        int result = expr.eval(Map.of("x", 7));
        assertEquals(9, result);
    }

    @Test
    void testDerivative()  throws DivisionByZeroException, MissingVariableException {
        Expression expr = new Add(new Number(3), new Variable("x"));
        Expression derivative = expr.derivative("x");

        assertTrue(derivative instanceof Add);

        int original = expr.eval(Map.of("x", 10));
        int derived = derivative.eval(Map.of("x", 10));

        assertEquals(13, original);
        assertEquals(1, derived);
    }

    @Test
    void testPrint() {
        Expression expr = new Add(new Number(3), new Variable("x"));
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        expr.print();
        System.setOut(System.out);

        assertEquals("(3+x)", out.toString());
    }
}
