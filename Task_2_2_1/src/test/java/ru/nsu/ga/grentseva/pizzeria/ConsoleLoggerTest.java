package ru.nsu.ga.grentseva.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.pizzeria.util.ConsoleLogger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsoleLoggerTest {

    @Test
    public void testLogPrintsMessage() {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(output));

        ConsoleLogger logger = new ConsoleLogger();
        logger.log("Hello");

        System.setOut(originalOut);

        assertTrue(output.toString().contains("Hello"));
    }
}