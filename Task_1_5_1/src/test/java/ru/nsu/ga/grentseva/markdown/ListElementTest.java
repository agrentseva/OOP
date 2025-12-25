package ru.nsu.ga.grentseva.markdown;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ListElementTest {

    @Test
    public void testEmptyList() {
        ListElement list = new ListElement.Builder().build();
        assertEquals("", list.toMarkdown());
    }

    @Test
    public void testSingleItem() {
        ListElement list = new ListElement.Builder()
                .add(new Text.Plain("Item 1"))
                .build();
        assertEquals("- Item 1", list.toMarkdown());
    }

    @Test
    public void testMultipleItems() {
        ListElement list = new ListElement.Builder()
                .add(new Text.Plain("Item 1"))
                .add(new Text.Bold("Item 2"))
                .add(new Text.Italic("Item 3"))
                .build();

        String expected = """
                - Item 1
                - **Item 2**
                - _Item 3_""";

        assertEquals(expected, list.toMarkdown());
    }

    @Test
    public void testNestedLists() {
        ListElement inner = new ListElement.Builder()
                .add(new Text.Plain("Inner 1"))
                .add(new Text.Plain("Inner 2"))
                .build();

        ListElement outer = new ListElement.Builder()
                .add(new Text.Plain("Outer 1"))
                .add(inner)
                .add(new Text.Plain("Outer 2"))
                .build();

        String expected = """
                - Outer 1
                - - Inner 1
                - Inner 2
                - Outer 2""";

        assertEquals(expected, outer.toMarkdown());
    }
}
