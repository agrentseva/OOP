package ru.nsu.ga.grentseva.substring.search;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubstringSearcherLargeFileTest {

    @Test
    void testLargeFile() throws Exception {
        File tempFile = File.createTempFile("largeTest", ".txt");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile, StandardCharsets.UTF_8)) {
            for (int i = 0; i < 1_000_000; i++) {
                writer.write('a');
            }
            writer.write('b');
        }

        SubstringSearcher searcher = new SubstringSearcher();

        List<Long> positions = searcher.find(tempFile.getAbsolutePath(), "aaab");

        assertEquals(1, positions.size());
        assertEquals(999_997L, positions.get(0));
    }
}
