package ru.nsu.ga.grentseva.snake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    @Test
    public void testCellCreation() {
        Cell cell = new Cell(5, 10);
        assertEquals(5, cell.x());
        assertEquals(10, cell.y());
    }

    @Test
    public void testMoveUp() {
        Cell cell = new Cell(5, 10);
        Cell moved = cell.move(Direction.UP);
        assertEquals(5, moved.x());
        assertEquals(9, moved.y());
    }

    @Test
    public void testMoveDown() {
        Cell cell = new Cell(5, 10);
        Cell moved = cell.move(Direction.DOWN);
        assertEquals(5, moved.x());
        assertEquals(11, moved.y());
    }

    @Test
    public void testMoveLeft() {
        Cell cell = new Cell(5, 10);
        Cell moved = cell.move(Direction.LEFT);
        assertEquals(4, moved.x());
        assertEquals(10, moved.y());
    }

    @Test
    public void testMoveRight() {
        Cell cell = new Cell(5, 10);
        Cell moved = cell.move(Direction.RIGHT);
        assertEquals(6, moved.x());
        assertEquals(10, moved.y());
    }

    @Test
    public void testEqualsAndHashCode() {
        Cell cell1 = new Cell(5, 10);
        Cell cell2 = new Cell(5, 10);
        Cell cell3 = new Cell(6, 10);

        assertEquals(cell1, cell2);
        assertNotEquals(cell1, cell3);
        assertEquals(cell1.hashCode(), cell2.hashCode());
    }
}
