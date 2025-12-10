package ru.nsu.ga.grentseva.markdown;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TextTest {

    @Test
    public void testPlain() {
        Text.Plain t = new Text.Plain("Hello");
        assertEquals("Hello", t.toMarkdown());
    }

    @Test
    public void testBold() {
        Text.Bold t = new Text.Bold("Hello");
        assertEquals("**Hello**", t.toMarkdown());
    }

    @Test
    public void testItalic() {
        Text.Italic t = new Text.Italic("Hello");
        assertEquals("_Hello_", t.toMarkdown());
    }

    @Test
    public void testStrike() {
        Text.Strike t = new Text.Strike("Hello");
        assertEquals("~~Hello~~", t.toMarkdown());
    }

    @Test
    public void testInlineCode() {
        Text.InlineCode t = new Text.InlineCode("Hello");
        assertEquals("`Hello`", t.toMarkdown());
    }

    @Test
    public void testCombined() {
        Text.Bold bold = new Text.Bold("Bold");
        Text.Italic italic = new Text.Italic("Italic");
        assertEquals("**Bold**", bold.toMarkdown());
        assertEquals("_Italic_", italic.toMarkdown());
    }
}
