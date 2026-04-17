package ru.nsu.ga.grentseva.snake.service;

import ru.nsu.ga.grentseva.snake.model.Cell;
import ru.nsu.ga.grentseva.snake.model.Food;
import ru.nsu.ga.grentseva.snake.model.FoodType;
import ru.nsu.ga.grentseva.snake.model.GameField;

import java.util.List;
import java.util.Random;

public class FoodGenerator {

    private final Random random = new Random();


    public Food generate(GameField field) {
        List<Cell> free = field.freeCells();
        if (free.isEmpty()) return null;

        Cell pos = free.get(random.nextInt(free.size()));
        FoodType type = randomType();

        return new Food(pos, type);
    }


    private FoodType randomType() {
        double r = random.nextDouble();

        if (r < 0.6) return FoodType.NORMAL;
        if (r < 0.8) return FoodType.BONUS;
        return FoodType.POISON;
    }
}