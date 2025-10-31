package ru.nsu.ga.grentseva.console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class ConsoleInputTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testAskPlayerChoiceRussian() {
        String simulatedInput = "1\n"; // пользователь вводит 1
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ConsoleInput input = new ConsoleInput();

        int choice = input.askPlayerChoice();

        String output = testOut.toString();
        assertTrue(output.contains("Введите 1 - взять карту"));
        assertEquals(1, choice);
    }

    @Test
    void testAskPlayerChoiceEnglish() {
        String simulatedInput = "0\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ConsoleInput input = new ConsoleInput();
        input.setUseEnglish(true);

        int choice = input.askPlayerChoice();

        String output = testOut.toString();
        assertTrue(output.contains("Enter 1 to take a card"));
        assertEquals(0, choice);
    }

    @Test
    void testAskContinueYes() {
        String simulatedInput = "1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ConsoleInput input = new ConsoleInput();

        boolean result = input.askContinue();

        String output = testOut.toString();
        assertTrue(output.contains("Хотите сыграть"));
        assertTrue(result);
    }

    @Test
    void testAskContinueNo() {
        String simulatedInput = "0\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ConsoleInput input = new ConsoleInput();

        boolean result = input.askContinue();

        String output = testOut.toString();
        assertTrue(output.contains("Хотите сыграть"));
        assertFalse(result);
    }

    @Test
    void testAskContinueInvalidThenValid() {
        String simulatedInput = "5\n1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ConsoleInput input = new ConsoleInput();

        boolean result = input.askContinue();

        String output = testOut.toString();
        assertTrue(output.contains("Неверный ввод"));
        assertTrue(result);
    }
}
