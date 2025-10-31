package ru.nsu.ga.grentseva.operations;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.exceptions.DivisionByZeroException;
import ru.nsu.ga.grentseva.exceptions.MissingVariableException;
import ru.nsu.ga.grentseva.exceptions.ParseException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NumberTest {

    @Test
    void testEval() {
        Number num = new Number(42);
        assertEquals(42, num.eval(Map.of()));
    }

    @Test
    void testDerivative() throws DivisionByZeroException, MissingVariableException {
        Number num = new Number(5);
        Expression d = num.derivative("x");

        assertTrue(d instanceof Number);
        assertEquals(0, d.eval(Map.of()));
    }

    @Test
    void testPrint() {
        Number num = new Number(7);

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        num.print();

        assertEquals("7", out.toString());
    }
}
