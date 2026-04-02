package ru.nsu.ga.grentseva.snake.render;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.snake.model.Cell;
import ru.nsu.ga.grentseva.snake.model.FoodType;

import static org.junit.jupiter.api.Assertions.*;

public class RenderCommandTest {

    @Test
    public void testRenderCommandCreation() {
        Cell cell = new Cell(5, 5);
        RenderCommand command = new RenderCommand(cell, RenderType.FOOD, FoodType.BONUS);

        assertEquals(cell, command.cell());
        assertEquals(RenderType.FOOD, command.type());
        assertEquals(FoodType.BONUS, command.foodType());
    }

    @Test
    public void testRenderCommandWithoutFoodType() {
        Cell cell = new Cell(10, 10);
        RenderCommand command = new RenderCommand(cell, RenderType.SNAKE, null);

        assertEquals(cell, command.cell());
        assertEquals(RenderType.SNAKE, command.type());
        assertNull(command.foodType());
    }
}
