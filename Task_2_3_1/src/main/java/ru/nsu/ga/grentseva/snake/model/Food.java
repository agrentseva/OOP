package ru.nsu.ga.grentseva.snake.model;

public final class Food {

    private final Cell position;
    private final FoodType type;


    public Food(Cell position, FoodType type) {
        this.position = position;
        this.type = type;
    }


    public Cell position() {
        return position;
    }

    public FoodType type() {
        return type;
    }
}