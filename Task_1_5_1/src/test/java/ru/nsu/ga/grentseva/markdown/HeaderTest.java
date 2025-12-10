package ru.nsu.ga.grentseva.markdown;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeaderTest {

    @Test
    public void testHeaderLevel1() {
        Header h = new Header(1, new Text.Plain("Title"));
        assertEquals("# Title", h.toMarkdown());
    }

    @Test
    public void testHeaderLevel3() {
        Header h = new Header(3, new Text.Plain("Subtitle"));
        assertEquals("### Subtitle", h.toMarkdown());
    }

    @Test
    public void testHeaderLevel6() {
        Header h = new Header(6, new Text.Plain("Lowest"));
        assertEquals("###### Lowest", h.toMarkdown());
    }

    @Test
    public void testHeaderLevelTooLow() {
        Header h = new Header(0, new Text.Plain("Too Low"));
        assertEquals("# Too Low", h.toMarkdown());
    }

    @Test
    public void testHeaderLevelTooHigh() {
        Header h = new Header(10, new Text.Plain("Too High"));
        assertEquals("###### Too High", h.toMarkdown());
    }

    @Test
    public void testHeaderWithStyledText() {
        Header h = new Header(2, new Text.Bold("Bold Title"));
        assertEquals("## **Bold Title**", h.toMarkdown());
    }
}
