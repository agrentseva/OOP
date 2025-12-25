package ru.nsu.ga.grentseva.markdown;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ImageTest {

    @Test
    public void testSimpleImage() {
        Image img = new Image("Alt text", "https://example.com/image.png");
        String expected = "![Alt text](https://example.com/image.png)";
        assertEquals(expected, img.toMarkdown());
    }

    @Test
    public void testEmptyAlt() {
        Image img = new Image("", "https://example.com/image.png");
        String expected = "![](https://example.com/image.png)";
        assertEquals(expected, img.toMarkdown());
    }

    @Test
    public void testEmptySrc() {
        Image img = new Image("Alt text", "");
        String expected = "![Alt text]()";
        assertEquals(expected, img.toMarkdown());
    }

    @Test
    public void testEmptyAltAndSrc() {
        Image img = new Image("", "");
        String expected = "![]()";
        assertEquals(expected, img.toMarkdown());
    }
}
