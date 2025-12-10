package ru.nsu.ga.grentseva.markdown;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TableTest {

    @Test
    void testSimpleTable() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .addRow("Index", "Random")
                .addRow(1, 5)
                .addRow(2, 8)
                .build();

        String expected =
                "| Index | Random | \n" +
                        "| ---:  | :---   | \n" +
                        "| 1     | 5      | \n" +
                        "| 2     | 8      | \n";

        assertEquals(expected, table.toMarkdown());
    }

    @Test
    void testBoldCells() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .addRow("Index", "Value")
                .addRow(1, new Text.Bold("10"))
                .addRow(2, new Text.Bold("20"))
                .build();

        String expected =
                "| Index | Value  | \n" +
                        "| ---:  | :---   | \n" +
                        "| 1     | **10** | \n" +
                        "| 2     | **20** | \n";

        assertEquals(expected, table.toMarkdown());
    }

    @Test
    void testRowLimit() {
        Table table = new Table.Builder()
                .withRowLimit(2)
                .addRow("A", "B")
                .addRow(1, 2)
                .addRow(3, 4)
                .build();

        String expected =
                "| A | B | \n" +
                        "| :--- | :--- | \n" +
                        "| 1 | 2 | \n";

        assertEquals(expected, table.toMarkdown());
    }

    @Test
    void testSingleCellTable() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT)
                .addRow("Only")
                .build();

        String expected =
                "| Only | \n" +
                        "| :--- | \n";

        assertEquals(expected, table.toMarkdown());
    }

    @Test
    void testEmptyTable() {
        Table table = new Table.Builder().build();
        assertEquals("", table.toMarkdown());
    }
}
