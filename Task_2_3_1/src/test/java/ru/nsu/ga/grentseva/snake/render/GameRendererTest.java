package ru.nsu.ga.grentseva.snake.render;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.snake.model.Cell;
import ru.nsu.ga.grentseva.snake.model.FoodType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GameRendererTest {

    @BeforeAll
    public static void initJFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void testRendererCreation() {
        assertDoesNotThrow(() -> {
            GameRenderer renderer = new GameRenderer(20);
        });
    }

    @Test
    public void testDrawMethods() {
        GameRenderer renderer = new GameRenderer(20);
        Canvas canvas = new Canvas(100, 100);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        assertDoesNotThrow(() -> {
            renderer.drawBackground(gc, 100, 100);
        });

        List<RenderCommand> commands = List.of(
                new RenderCommand(new Cell(1, 1), RenderType.SNAKE, null),
                new RenderCommand(new Cell(2, 2), RenderType.FOOD, FoodType.NORMAL),
                new RenderCommand(new Cell(3, 3), RenderType.OBSTACLE, null),
                new RenderCommand(new Cell(4, 4), RenderType.EMPTY, null)
        );

        assertDoesNotThrow(() -> {
            renderer.render(gc, commands);
        });
    }
}