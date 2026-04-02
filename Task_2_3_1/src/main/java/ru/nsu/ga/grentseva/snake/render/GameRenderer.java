package ru.nsu.ga.grentseva.snake.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.ga.grentseva.snake.model.Cell;
import ru.nsu.ga.grentseva.snake.model.FoodType;

import java.util.List;

public class GameRenderer {

    private final int cellSize;

    private static final Color BG_LIGHT = Color.web("#AAD751");
    private static final Color BG_DARK = Color.web("#A2D149");
    private static final Color SNAKE = Color.web("#E3973B");
    private static final Color WALL = Color.web("#578A34");


    public GameRenderer(int cellSize) {
        this.cellSize = cellSize;
    }


    public void drawBackground(GraphicsContext gc, int width, int height) {
        int cols = width / cellSize;
        int rows = height / cellSize;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                gc.setFill(isLightCell(x, y) ? BG_LIGHT : BG_DARK);
                gc.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }
    }

    public void render(GraphicsContext gc, List<RenderCommand> commands) {
        for (RenderCommand cmd : commands) {
            draw(gc, cmd);
        }
    }


    private void draw(GraphicsContext gc, RenderCommand cmd) {
        Cell c = cmd.cell();

        int x = c.x() * cellSize;
        int y = c.y() * cellSize;

        switch (cmd.type()) {
            case SNAKE -> drawSnake(gc, x, y);
            case FOOD -> drawFood(gc, x, y, cmd.foodType());
            case OBSTACLE -> drawWall(gc, x, y);
            case EMPTY -> clearCell(gc, x, y, c.x(), c.y());
        }
    }


    private void drawSnake(GraphicsContext gc, int x, int y) {
        gc.setFill(SNAKE);
        gc.fillRoundRect(
                x,
                y,
                cellSize,
                cellSize,
                cellSize * 0.4,
                cellSize * 0.4
        );
    }

    private void drawFood(GraphicsContext gc, int x, int y, FoodType type) {
        gc.setFill(getFoodColor(type));
        gc.fillOval(
                x + cellSize * 0.1,
                y + cellSize * 0.1,
                cellSize * 0.8,
                cellSize * 0.8
        );
    }

    private void drawWall(GraphicsContext gc, int x, int y) {
        gc.setFill(WALL);
        gc.fillRect(x, y, cellSize, cellSize);
    }

    private void clearCell(GraphicsContext gc, int pixelX, int pixelY, int gridX, int gridY) {
        gc.setFill(isLightCell(gridX, gridY) ? BG_LIGHT : BG_DARK);
        gc.fillRect(pixelX, pixelY, cellSize, cellSize);
    }


    private boolean isLightCell(int x, int y) {
        return (x + y) % 2 == 0;
    }

    private Color getFoodColor(FoodType type) {
        return switch (type) {
            case NORMAL -> Color.web("#E7471D");
            case BONUS -> Color.web("#3C78D8");
            case POISON -> Color.web("#6A1B9A");
        };
    }
}