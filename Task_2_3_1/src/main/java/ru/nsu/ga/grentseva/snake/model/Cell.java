package ru.nsu.ga.grentseva.snake.model;

import java.util.Objects;

public final class Cell {

    private final int x;
    private final int y;


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public Cell move(Direction d) {
        return switch (d) {
            case UP -> new Cell(x, y - 1);
            case DOWN -> new Cell(x, y + 1);
            case LEFT -> new Cell(x - 1, y);
            case RIGHT -> new Cell(x + 1, y);
        };
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell c)) return false;
        return x == c.x && y == c.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}