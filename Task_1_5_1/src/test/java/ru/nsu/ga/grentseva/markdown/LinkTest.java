package ru.nsu.ga.grentseva.markdown;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkTest {

    @Test
    public void testSimpleLink() {
        Link link = new Link("Google", "https://www.google.com");
        String expected = "[Google](https://www.google.com)";
        assertEquals(expected, link.toMarkdown());
    }

    @Test
    public void testEmptyText() {
        Link link = new Link("", "https://www.example.com");
        String expected = "[](https://www.example.com)";
        assertEquals(expected, link.toMarkdown());
    }

    @Test
    public void testEmptyUrl() {
        Link link = new Link("Click here", "");
        String expected = "[Click here]()";
        assertEquals(expected, link.toMarkdown());
    }

    @Test
    public void testEmptyTextAndUrl() {
        Link link = new Link("", "");
        String expected = "[]()";
        assertEquals(expected, link.toMarkdown());
    }

    @Test
    public void testSpecialCharacters() {
        Link link = new Link("Go *bold*", "https://example.com/?q=1&b=2");
        String expected = "[Go *bold*](https://example.com/?q=1&b=2)";
        assertEquals(expected, link.toMarkdown());
    }
}
