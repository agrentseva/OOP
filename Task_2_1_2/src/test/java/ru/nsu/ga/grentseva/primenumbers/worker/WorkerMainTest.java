package ru.nsu.ga.grentseva.primenumbers.worker;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class WorkerMainTest {

    @Test
    void shouldPrintUsageMessageWhenNoArgumentsProvided() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(outputStream));

        WorkerMain.main(new String[]{});
        System.setErr(originalErr);

        String output = outputStream.toString();
        assertTrue(output.contains("Usage"));
    }

    @Test
    void shouldPrintErrorForInvalidPort() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(outputStream));

        WorkerMain.main(new String[]{"invalid"});
        System.setErr(originalErr);

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid port format"));
    }

    @Test
    void shouldPrintErrorForPortOutOfRange() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(outputStream));

        WorkerMain.main(new String[]{"70000"});
        System.setErr(originalErr);

        String output = outputStream.toString();
        assertTrue(output.contains("Port must be in range"));
    }
}