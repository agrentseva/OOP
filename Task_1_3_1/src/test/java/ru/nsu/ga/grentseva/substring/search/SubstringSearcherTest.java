package ru.nsu.ga.grentseva.substring.search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SubstringSearcherTest {

    private Path tempFile;
    private final SubstringSearcher searcher = new SubstringSearcher();

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("substring_test_", ".txt");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testBasicAscii() throws IOException {
        Files.writeString(tempFile, "abacaba");
        List<Long> result = searcher.find(tempFile.toString(), "aba");
        assertEquals(List.of(0L, 4L), result);
    }

    @Test
    void testCyrillic() throws IOException {
        Files.writeString(tempFile, "Привет мир, большой мир!");
        List<Long> result = searcher.find(tempFile.toString(), "мир");
        assertEquals(List.of(7L, 20L), result);
    }

    @Test
    void testArabic() throws IOException {
        Files.writeString(tempFile, "مرحبا سلام، كيف حالك؟ سلام للجميع");
        List<Long> result = searcher.find(tempFile.toString(), "سلام");
        assertEquals(List.of(6L, 22L), result);
    }

    @Test
    void testChinese() throws IOException {
        Files.writeString(tempFile, "我喜欢北京，我在北京学习。");
        List<Long> result = searcher.find(tempFile.toString(), "北京");
        assertEquals(List.of(3L, 8L), result);
    }

    @Test
    void testEmoji() throws IOException {
        Files.writeString(tempFile, "🙂🙂😊😊🙂");
        List<Long> result = searcher.find(tempFile.toString(), "😊");
        assertEquals(List.of(2L, 3L), result);
    }

    @Test
    void testEmptySubstring() throws IOException {
        Files.writeString(tempFile, "Любой текст здесь");
        List<Long> result = searcher.find(tempFile.toString(), "");
        assertEquals(List.of(), result);
    }
}
