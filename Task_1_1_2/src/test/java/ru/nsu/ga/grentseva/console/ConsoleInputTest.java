package ru.nsu.ga.grentseva.console;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.console.localization.EnglishLocalization;
import ru.nsu.ga.grentseva.console.localization.RussianLocalization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleInputTest {

    private ByteArrayOutputStream outContent;
    private ConsoleOutput output;

    private void setupOutput(boolean english) {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        output = english ?
                new ConsoleOutput(new EnglishLocalization()) :
                new ConsoleOutput(new RussianLocalization());
    }

    private String getOutput() {
        return outContent.toString().trim();
    }

    @Test
    void testAskPlayerChoiceValid() {
        setupOutput(true);
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ConsoleInput consoleInput = new ConsoleInput(output);
        int choice = consoleInput.askPlayerToStopOrTakeCard();

        assertEquals(1, choice);
    }

    @Test
    void testAskContinueYes() {
        setupOutput(true);
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ConsoleInput consoleInput = new ConsoleInput(output);
        boolean cont = consoleInput.askPlayerWantsToContinueGame();

        assertTrue(cont);
    }

    @Test
    void testAskContinueNo() {
        setupOutput(true);
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ConsoleInput consoleInput = new ConsoleInput(output);
        boolean cont = consoleInput.askPlayerWantsToContinueGame();

        assertFalse(cont);
    }

    @Test
    void testCloseScanner() {
        setupOutput(true);
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ConsoleInput consoleInput = new ConsoleInput(output);

        consoleInput.closeInput();
        assertTrue(true);
    }
}
