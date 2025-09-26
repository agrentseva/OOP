package ru.nsu.ga.grentseva.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;

class ConsoleInputTest {

    @Test
    void testPlayerChoice() {
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        ConsoleInput input = new ConsoleInput();
        int choice = input.askPlayerChoice();
        assertEquals(1, choice);
    }

    @Test
    void testAskContinueYes() {
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        ConsoleInput input = new ConsoleInput();
        boolean result = input.askContinue();
        assertTrue(result);
    }

    @Test
    void testAskContinueNo() {
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        ConsoleInput input = new ConsoleInput();
        boolean result = input.askContinue();
        assertEquals(false, result);
    }

    @Test
    void testAskContinueInvalidThenYes() {
        System.setIn(new ByteArrayInputStream("5\n1\n".getBytes()));
        ConsoleInput input = new ConsoleInput();
        boolean result = input.askContinue();
        assertTrue(result);
    }
}
