package ru.nsu.ga.grentseva.checker.service.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class TestResultParserTest {

    @TempDir
    Path tempDir;

    @Test
    void parseValidXml() throws Exception {
        Path resultsDirectory = tempDir.resolve("build/test-results/test");
        Files.createDirectories(resultsDirectory);

        Path xml = resultsDirectory.resolve("TEST-test.xml");
        Files.writeString(xml, """
                <testsuite tests="10" failures="2" skipped="1">
                </testsuite>
                """);

        TestResultParser parser = new TestResultParser();
        int[] result = parser.parse(tempDir.toFile());

        assertEquals(7, result[0]);
        assertEquals(2, result[1]);
        assertEquals(1, result[2]);
    }

    @Test
    void parseWithoutSkippedTests() throws Exception {
        Path resultsDirectory = tempDir.resolve("build/test-results/test");
        Files.createDirectories(resultsDirectory);

        Path xml = resultsDirectory.resolve("TEST-test.xml");
        Files.writeString(xml, """
                <testsuite tests="5" failures="1" skipped="0">
                </testsuite>
                """);

        TestResultParser parser = new TestResultParser();
        int[] result = parser.parse(tempDir.toFile());

        assertEquals(4, result[0]);
        assertEquals(1, result[1]);
        assertEquals(0, result[2]);
    }
}