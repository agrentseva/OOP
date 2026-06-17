package ru.nsu.ga.grentseva.primenumbers.common;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class DistributedLoggerTest {

    @Test
    void shouldPrintInfoMessage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        DistributedLogger.info("Test info");
        System.setOut(originalOut);

        String output = outputStream.toString();
        assertTrue(output.contains("INFO"));
        assertTrue(output.contains("Test info"));
    }

    @Test
    void shouldPrintErrorMessage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(outputStream));

        DistributedLogger.error("Test error");
        System.setErr(originalErr);

        String output = outputStream.toString();
        assertTrue(output.contains("ERROR"));
        assertTrue(output.contains("Test error"));
    }
}