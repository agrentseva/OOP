package ru.nsu.ga.grentseva.operations;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.exceptions.DivisionByZeroException;
import ru.nsu.ga.grentseva.exceptions.MissingVariableException;
import ru.nsu.ga.grentseva.exceptions.ParseException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VariableTest {

    @Test
    void testEvalWithMap() throws DivisionByZeroException, MissingVariableException {
        Expression expr = new Variable("x");
        assertEquals(10, expr.eval(Map.of("x", 10)));
        assertEquals(5, expr.eval(Map.of("x", 5, "y", 3)));
    }

    @Test
    void testEvalWithString() throws DivisionByZeroException, ParseException, MissingVariableException {
        Expression expr = new Variable("x");
        assertEquals(7, expr.eval("x=7"));
        assertEquals(15, expr.eval("x=15;y=20"));
    }

    @Test
    void testEvalMissingVariable() {
        Expression expr = new Variable("z");
        MissingVariableException exception = assertThrows(
                MissingVariableException.class,
                () -> expr.eval(Map.of("x", 5, "y", 10))
        );
        assertEquals("Error: No value for variable", exception.getMessage());
    }

    @Test
    void testDerivative() throws DivisionByZeroException, MissingVariableException {
        Expression expr = new Variable("x");

        Expression derivative1 = expr.derivative("x");
        assertEquals(1, derivative1.eval(Map.of("x", 5)));

        Expression derivative2 = expr.derivative("y");
        assertEquals(0, derivative2.eval(Map.of("x", 5, "y", 10)));
    }

    @Test
    void testPrint() {
        Expression expr = new Variable("x");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        expr.print();
        System.setOut(System.out);
        assertEquals("x", out.toString());
    }
}
