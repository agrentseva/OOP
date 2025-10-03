package ru.nsu.ga.grentseva.operations;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SubTest {

    @Test
    void testEvalWithNumbers() {
        Expression expr = new Sub(new Number(10), new Number(4));
        assertEquals(6, expr.eval(Map.of()));
    }

    @Test
    void testEvalWithVariable_Map() {
        Expression expr = new Sub(new Variable("x"), new Number(5));
        assertEquals(7, expr.eval(Map.of("x", 12)));
    }

    @Test
    void testEvalWithVariable_String() {
        Expression expr = new Sub(new Number(10), new Variable("y"));
        assertEquals(3, expr.eval("y=7"));
    }

    @Test
    void testDerivative_Number() {
        Expression expr = new Sub(new Number(8), new Number(3));
        Expression derivative = expr.derivative("x");
        // (8-3)' = 0
        assertEquals(0, derivative.eval(Map.of()));
    }

    @Test
    void testDerivative_Variable() {
        Expression expr = new Sub(new Variable("x"), new Number(5));
        Expression derivative = expr.derivative("x");
        assertEquals(1, derivative.eval("x=10"));
    }

    @Test
    void testDerivative_VariableVariable() {
        Expression expr = new Sub(new Variable("x"), new Variable("y"));
        Expression derivativeX = expr.derivative("x");
        assertEquals(1, derivativeX.eval("x=3;y=7"));

        Expression derivativeY = expr.derivative("y");
        assertEquals(-1, derivativeY.eval("x=3;y=7"));
    }

    @Test
    void testPrint() {
        Expression expr = new Sub(new Variable("x"), new Number(5));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        expr.print();
        System.setOut(System.out);
        assertEquals("(x-5)", out.toString());
    }
}
