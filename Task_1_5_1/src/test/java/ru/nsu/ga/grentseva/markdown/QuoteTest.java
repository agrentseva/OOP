package ru.nsu.ga.grentseva.markdown;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuoteTest {

    @Test
    public void testPlainQuote() {
        Quote q = new Quote(new Text.Plain("Hello world"));
        assertEquals("> Hello world", q.toMarkdown());
    }

    @Test
    public void testBoldQuote() {
        Quote q = new Quote(new Text.Bold("Important"));
        assertEquals("> **Important**", q.toMarkdown());
    }

    @Test
    public void testItalicQuote() {
        Quote q = new Quote(new Text.Italic("Note"));
        assertEquals("> _Note_", q.toMarkdown());
    }

    @Test
    public void testNestedQuote() {
        Quote inner = new Quote(new Text.Plain("Inner quote"));
        Quote outer = new Quote(inner);
        assertEquals("> > Inner quote", outer.toMarkdown());
    }

    @Test
    public void testEmptyQuote() {
        Quote q = new Quote(new Text.Plain(""));
        assertEquals("> ", q.toMarkdown());
    }
}
