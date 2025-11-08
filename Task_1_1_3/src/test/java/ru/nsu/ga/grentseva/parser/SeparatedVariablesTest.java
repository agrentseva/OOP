package ru.nsu.ga.grentseva.parser;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.exceptions.ParseException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SeparatedVariablesTest {

    @Test
    void testSingleVariable() throws ParseException {
        SeparatedVariables sv = new SeparatedVariables("x=5");
        Map<String, Integer> vars = sv.getVars();
        assertEquals(1, vars.size());
        assertEquals(5, vars.get("x"));
    }

    @Test
    void testMultipleVariables() throws ParseException {
        SeparatedVariables sv = new SeparatedVariables("x=3; y=7; z=10");
        Map<String, Integer> vars = sv.getVars();
        assertEquals(3, vars.size());
        assertEquals(3, vars.get("x"));
        assertEquals(7, vars.get("y"));
        assertEquals(10, vars.get("z"));
    }

    @Test
    void testVariablesWithNoSpaces() throws ParseException {
        SeparatedVariables sv = new SeparatedVariables("a=1;b=2;c=3");
        Map<String, Integer> vars = sv.getVars();
        assertEquals(3, vars.size());
        assertEquals(1, vars.get("a"));
        assertEquals(2, vars.get("b"));
        assertEquals(3, vars.get("c"));
    }

    @Test
    void testEmptyString() {
        ParseException exception = assertThrows(
                ParseException.class,
                () -> new SeparatedVariables("")
        );
        assertEquals("Error: The string is empty", exception.getMessage());
    }

    @Test
    void testNullString() {
        ParseException exception = assertThrows(
                ParseException.class,
                () -> new SeparatedVariables(null)
        );
        assertEquals("Error: The string is empty", exception.getMessage());
    }

    @Test
    void testExtraSemicolons() throws ParseException {
        SeparatedVariables sv = new SeparatedVariables("x=1;;y=2;");
        Map<String, Integer> vars = sv.getVars();
        assertEquals(2, vars.size());
        assertEquals(1, vars.get("x"));
        assertEquals(2, vars.get("y"));
    }
}
