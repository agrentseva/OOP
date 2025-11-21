package ru.nsu.ga.grentseva.substring;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubstringSearcherTest {

    private SubstringSearcher searcher;

    @BeforeEach
    void setup() {
        searcher = new SubstringSearcher();
    }

    /**
     * Создаёт временный файл с указанным текстом.
     * Файл автоматически удаляется системой после теста.
     */
    private Path createTempFile(String content) throws IOException {
        Path temp = Files.createTempFile("substring-test-", ".txt");
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        return temp;
    }

    /**
     * Проверяет простой случай: подстрока встречается несколько раз.
     * В тексте "ababa" подстрока "aba" находится с позиций 0 и 2.
     */
    @Test
    void testSimpleSearch() throws IOException {
        Path file = createTempFile("ababa");
        List<Long> result = searcher.findOccurrences(file.toString(), "aba");
        assertEquals(List.of(0L, 2L), result);
    }

    /**
     * Проверяет ситуацию, когда подстрока не встречается в файле.
     * Ожидается пустой список найденных позиций.
     */
    @Test
    void testNoOccurrences() throws IOException {
        Path file = createTempFile("hello world");
        List<Long> result = searcher.findOccurrences(file.toString(), "abc");
        assertTrue(result.isEmpty());
    }

    /**
     * Проверяет поиск с пересекающимися совпадениями.
     * В строке "aaaaa" подстрока "aaa" встречается с позиций 0, 1 и 2.
     */
    @Test
    void testOverlappingOccurrences() throws IOException {
        Path file = createTempFile("aaaaa");
        List<Long> result = searcher.findOccurrences(file.toString(), "aaa");
        assertEquals(List.of(0L, 1L, 2L), result);
    }

    /**
     * Проверяет случай, когда подстрока длиннее содержимого файла.
     * В таком случае совпадений быть не может.
     */
    @Test
    void testSubstringLongerThanText() throws IOException {
        Path file = createTempFile("short");
        List<Long> result = searcher.findOccurrences(file.toString(), "verylongsubstring");
        assertTrue(result.isEmpty());
    }

    /**
     * Проверяет корректную работу с Unicode-текстом.
     * Подстрока "мир" должна находиться в строке "Привет мир, мир!"
     * на двух позициях: 7 и 12.
     */
    @Test
    void testUnicodeSearch() throws IOException {
        Path file = createTempFile("Сначала пряник, потом сухарик и еще пряник!");
        List<Long> result = searcher.findOccurrences(file.toString(), "пряник");
        assertEquals(List.of(8L, 36L), result);
    }

    /**
     * Проверяет обработку больших данных.
     * Создаётся длинная строка из повторяющегося блока.
     * Ожидается, что подстрока встретится один раз в каждом блоке.
     */
    @Test
    void testLargeGeneratedText() throws IOException {
        String block = "abcxabcdabcdabcy";
        String substring = "abcdabcy";

        StringBuilder builder = new StringBuilder();
        int repeat = 5000;
        for (int i = 0; i < repeat; i++) {
            builder.append(block);
        }

        Path file = createTempFile(builder.toString());
        List<Long> result = searcher.findOccurrences(file.toString(), substring);

        assertEquals(repeat, result.size());
    }
}
