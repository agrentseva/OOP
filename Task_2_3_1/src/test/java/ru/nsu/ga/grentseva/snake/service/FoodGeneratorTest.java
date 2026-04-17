package ru.nsu.ga.grentseva.snake.service;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.snake.model.Cell;
import ru.nsu.ga.grentseva.snake.model.Food;
import ru.nsu.ga.grentseva.snake.model.GameField;

import static org.junit.jupiter.api.Assertions.*;

public class FoodGeneratorTest {

    @Test
    public void testGenerateFood() {
        GameField field = new GameField(10, 10);
        FoodGenerator generator = new FoodGenerator();

        Food food = generator.generate(field);

        assertNotNull(food);
        assertNotNull(food.position());
        assertNotNull(food.type());
        assertTrue(food.position().x() >= 0 && food.position().x() < 10);
        assertTrue(food.position().y() >= 0 && food.position().y() < 10);
    }

    @Test
    public void testGenerateFoodWhenFieldFull() {
        GameField field = new GameField(2, 2);
        FoodGenerator generator = new FoodGenerator();

        field.occupy(new Cell(0, 0));
        field.occupy(new Cell(0, 1));
        field.occupy(new Cell(1, 0));
        field.occupy(new Cell(1, 1));

        Food food = generator.generate(field);

        assertNull(food);
    }
}