package ru.nsu.ga.grentseva.snake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {

    @Test
    public void testSnakeInitialization() {
        Cell start = new Cell(10, 10);
        Snake snake = new Snake(start);

        assertEquals(1, snake.size());
        assertEquals(start, snake.head());
        assertTrue(snake.contains(start));
    }

    @Test
    public void testMoveWithoutGrowing() {
        Cell start = new Cell(10, 10);
        Snake snake = new Snake(start);

        Cell nextHead = new Cell(11, 10);
        Snake.MoveResult result = snake.move(nextHead, false);

        assertEquals(1, snake.size());
        assertEquals(nextHead, snake.head());
        assertEquals(start, result.removedTail());
        assertTrue(snake.contains(nextHead));
        assertFalse(snake.contains(start));
    }

    @Test
    public void testMoveWithGrowing() {
        Cell start = new Cell(10, 10);
        Snake snake = new Snake(start);

        Cell nextHead = new Cell(11, 10);
        Snake.MoveResult result = snake.move(nextHead, true);

        assertEquals(2, snake.size());
        assertEquals(nextHead, snake.head());
        assertNull(result.removedTail());
        assertTrue(snake.contains(nextHead));
        assertTrue(snake.contains(start));
    }

    @Test
    public void test180DegreeTurnIgnored() {
        Cell start = new Cell(10, 10);
        Snake snake = new Snake(start);
        
        assertEquals(Direction.RIGHT, snake.consumeDirection());

        snake.setDirection(Direction.LEFT);
        
        assertEquals(Direction.RIGHT, snake.consumeDirection());
    }

    @Test
    public void testValidTurn() {
        Cell start = new Cell(10, 10);
        Snake snake = new Snake(start);
        
        snake.setDirection(Direction.UP);
        
        assertEquals(Direction.UP, snake.consumeDirection());
    }
}