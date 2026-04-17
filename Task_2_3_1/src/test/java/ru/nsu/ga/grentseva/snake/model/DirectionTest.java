package ru.nsu.ga.grentseva.snake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    public void testIsOpposite() {
        assertTrue(Direction.UP.isOpposite(Direction.DOWN));
        assertTrue(Direction.DOWN.isOpposite(Direction.UP));
        assertTrue(Direction.LEFT.isOpposite(Direction.RIGHT));
        assertTrue(Direction.RIGHT.isOpposite(Direction.LEFT));
        
        assertFalse(Direction.UP.isOpposite(Direction.LEFT));
        assertFalse(Direction.UP.isOpposite(Direction.RIGHT));
        assertFalse(Direction.UP.isOpposite(Direction.UP));
        
        assertFalse(Direction.DOWN.isOpposite(Direction.LEFT));
        assertFalse(Direction.DOWN.isOpposite(Direction.RIGHT));
        assertFalse(Direction.DOWN.isOpposite(Direction.DOWN));
    }

    @Test
    public void testNoReverseDirection() {
        Snake snake = new Snake(new Cell(5, 5));

        snake.setDirection(Direction.RIGHT);
        snake.consumeDirection();

        snake.setDirection(Direction.LEFT);

        Direction result = snake.consumeDirection();

        assertEquals(Direction.RIGHT, result);
    }
}
