package ru.nsu.ga.grentseva.operations;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MulTest {

    @Test
    void testEvalWithNumbers() {
        Expression expr = new Mul(new Number(3), new Number(4));
        assertEquals(12, expr.eval(Map.of()));
    }

    @Test
    void testEvalWithVariable_Map() {
        Expression expr = new Mul(new Variable("x"), new Number(5));
        assertEquals(20, expr.eval(Map.of("x", 4)));
    }

    @Test
    void testEvalWithVariable_String() {
        Expression expr = new Mul(new Variable("x"), new Number(2));
        assertEquals(14, expr.eval("x=7"));
    }

    @Test
    void testDerivative_Number() {
        Expression expr = new Mul(new Number(3), new Number(4));
        Expression derivative = expr.derivative("x");
        assertEquals(0, derivative.eval(Map.of()));
    }

    @Test
    void testDerivative_Variable() {
        Expression expr = new Mul(new Variable("x"), new Number(5));
        Expression derivative = expr.derivative("x");
        assertEquals(5, derivative.eval("x=10")); // x неважен
    }

    @Test
    void testDerivative_MultipleVars() {
        Expression expr = new Mul(new Variable("x"), new Variable("y"));
        Expression derivativeX = expr.derivative("x");
        assertEquals(7, derivativeX.eval("x=3;y=7"));

        Expression derivativeY = expr.derivative("y");
        assertEquals(3, derivativeY.eval("x=3;y=7"));
    }

    @Test
    void testPrint() {
        Expression expr = new Mul(new Variable("x"), new Number(5));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        expr.print();
        System.setOut(System.out);
        assertEquals("(x*5)", out.toString());
    }
}
