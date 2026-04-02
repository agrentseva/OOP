package ru.nsu.ga.grentseva.snake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameFieldTest {

    @Test
    public void testInitialization() {
        GameField field = new GameField(10, 20);
        
        assertTrue(field.getObstacles().isEmpty());
        assertEquals(200, field.freeCells().size());
    }

    @Test
    public void testOccupyAndFree() {
        GameField field = new GameField(10, 10);
        Cell cell = new Cell(5, 5);
        
        field.occupy(cell);
        assertFalse(field.freeCells().contains(cell));
        
        field.free(cell);
        assertTrue(field.freeCells().contains(cell));
    }

    @Test
    public void testGenerateWalls() {
        GameField field = new GameField(10, 10);
        Cell start = new Cell(5, 5);
        
        field.generateWalls(10, start);
        
        assertEquals(10, field.getObstacles().size());
        
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                Cell safeCell = new Cell(start.x() + dx, start.y() + dy);
                assertFalse(field.getObstacles().contains(safeCell));
            }
        }
    }
}