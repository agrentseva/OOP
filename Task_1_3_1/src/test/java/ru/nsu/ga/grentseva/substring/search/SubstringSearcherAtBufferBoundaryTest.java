package ru.nsu.ga.grentseva.substring.search;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubstringSearcherAtBufferBoundaryTest {

    @Test
    void testSubstringAtBufferBoundary() throws IOException {
        Path tempFile = Files.createTempFile("test", ".txt");
        tempFile.toFile().deleteOnExit();

        int bufferSize = 8192;
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            for (int i = 0; i < bufferSize - 1; i++) {
                writer.write('A');
            }
            writer.write("XYZ");
        }

        SubstringSearcher searcher = new SubstringSearcher();
        List<Long> positions = searcher.find(tempFile.toString(), "XYZ");

        assertEquals(1, positions.size());
        assertEquals((long) bufferSize - 1, positions.get(0));
    }
}
