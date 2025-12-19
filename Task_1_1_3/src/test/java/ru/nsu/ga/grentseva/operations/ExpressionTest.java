package ru.nsu.ga.grentseva.operations;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.exceptions.DivisionByZeroException;
import ru.nsu.ga.grentseva.exceptions.MissingVariableException;
import ru.nsu.ga.grentseva.exceptions.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest {

    @Test
    void testEvalWithStringInput_Number() throws DivisionByZeroException, ParseException, MissingVariableException {
        Expression expr = new Number(5);

        int result = expr.eval("x=10");
        assertEquals(5, result);
    }

    @Test
    void testEvalWithStringInput_Variable() throws DivisionByZeroException, ParseException, MissingVariableException {
        Expression expr = new Variable("x");

        int result = expr.eval("x=7;y=20");
        assertEquals(7, result);
    }

    @Test
    void testEvalWithStringInput_Add() throws DivisionByZeroException, ParseException, MissingVariableException {
        Expression expr = new Add(new Variable("x"), new Number(3));

        int result = expr.eval("x=4");
        assertEquals(7, result);
    }

    @Test
    void testEvalWithStringInput_MultipleVars() throws DivisionByZeroException, ParseException, MissingVariableException {
        Expression expr = new Add(new Variable("x"), new Variable("y"));

        int result = expr.eval("x=2;y=5");
        assertEquals(7, result);
    }
}
