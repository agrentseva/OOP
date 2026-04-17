package ru.nsu.ga.grentseva.snake.model;

import java.util.Deque;
import java.util.LinkedList;

public class Snake {

    private final Deque<Cell> body = new LinkedList<>();

    private Direction direction = Direction.RIGHT;
    private Direction nextDirection = direction;

    public Snake(Cell start) {
        body.addFirst(start);
    }

    public void setDirection(Direction newDirection) {
        if (!newDirection.isOpposite(direction)) {
            nextDirection = newDirection;
        }
    }

    public Direction consumeDirection() {
        direction = nextDirection;
        return direction;
    }

    public Direction getCurrentDirection() {
        return direction;
    }

    public MoveResult move(Cell newHead, boolean grow) {
        body.addFirst(newHead);

        if (grow) {
            return new MoveResult(newHead, null);
        }

        Cell removedTail = body.removeLast();
        return new MoveResult(newHead, removedTail);
    }

    public Cell removeTail() {
        return body.removeLast();
    }

    public Cell head() {
        return body.peekFirst();
    }

    public int size() {
        return body.size();
    }

    public boolean contains(Cell cell) {
        return body.contains(cell);
    }

    public Iterable<Cell> getBody() {
        return body;
    }

    public record MoveResult(Cell newHead, Cell removedTail) {}
}