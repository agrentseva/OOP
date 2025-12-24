package ru.nsu.ga.grentseva.substring.search;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubstringSearcherLargeFileTest {

    @Test
    void testLargeFile() throws Exception {
        File tempFile = File.createTempFile("giganticTest", ".txt");
        tempFile.deleteOnExit();

        String chunk = "a".repeat(1024 * 1024);
        int iterations = 2000;
        String pattern = "special_pattern";

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(tempFile), StandardCharsets.UTF_8))) {

            for (int i = 0; i < iterations; i++) {
                writer.write(chunk);
                if (i == 1500) {
                    writer.write(pattern);
                }
            }
        }
        System.out.println("Размер созданного файла: " + (tempFile.length() / (1024 * 1024)) + " МБ");

        SubstringSearcher searcher = new SubstringSearcher();
        List<Long> positions = searcher.find(tempFile.getAbsolutePath(), pattern);

        long expectedPosition = 1501L * 1024 * 1024;

        assertEquals(1, positions.size());
        assertEquals(expectedPosition, positions.get(0));
    }
}