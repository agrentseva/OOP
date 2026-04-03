package ru.nsu.ga.grentseva.snake.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.snake.config.GameConfig;

import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {

    private GameConfig config;

    @BeforeEach
    public void setup() {
        config = new GameConfig(10, 10, 20, 1, 1.0, 1.0, 1.0);
    }

    @Test
    public void testInitialization() {
        GameModel model = new GameModel(config, false);

        assertEquals(GameState.RUNNING, model.getState());
        assertEquals(0, model.getScore());
        assertEquals(1, model.getSnake().size());
        assertEquals(config.foodCount, model.getFoods().size());
        assertNotNull(model.getField());
    }

    @Test
    public void testWrapAroundScreen() {
        GameModel model = new GameModel(config, false);
        model.getField().getObstacles().clear();
        model.getFoods().clear();
        Snake snake = model.getSnake();

        for (int i = 0; i < 5; i++) {
            model.update();
        }

        assertEquals(new Cell(0, 5), snake.head());
        assertEquals(GameState.RUNNING, model.getState());
    }

    @Test
    public void testCollisionWithWallGameOver() {
        GameModel model = new GameModel(config, false);
        Snake snake = model.getSnake();

        Cell nextPos = snake.head().move(Direction.RIGHT);
        model.getField().getObstacles().add(nextPos);

        model.update();

        assertEquals(GameState.GAME_OVER, model.getState());
    }

    @Test
    public void testEatFoodAndWin() {
        GameConfig winConfig = new GameConfig(10, 10, 2, 1, 1.0, 1.0, 1.0);
        GameModel model = new GameModel(winConfig, false);
        model.getField().getObstacles().clear();
        Snake snake = model.getSnake();

        Cell nextPos = snake.head().move(Direction.RIGHT);

        model.getFoods().clear();
        model.getFoods().add(new Food(nextPos, FoodType.NORMAL));

        model.update();

        assertEquals(2, snake.size());
        assertEquals(1, model.getScore());
        assertEquals(GameState.WIN, model.getState());
    }

    @Test
    public void testCollisionWithSelf() {
        GameModel model = new GameModel(config, false);
        model.getField().getObstacles().clear();
        model.getFoods().clear();
        Snake snake = model.getSnake();

        Cell h = snake.head();
        snake.move(h.move(Direction.RIGHT), true);
        h = snake.head();
        snake.move(h.move(Direction.RIGHT), true);
        h = snake.head();
        snake.move(h.move(Direction.RIGHT), true);
        h = snake.head();
        snake.move(h.move(Direction.RIGHT), true);

        snake.consumeDirection();

        snake.setDirection(Direction.DOWN);
        model.update();

        snake.setDirection(Direction.LEFT);
        model.update();

        snake.setDirection(Direction.UP);
        model.update();

        assertEquals(GameState.GAME_OVER, model.getState());
    }

    @Test
    public void testBonusFoodGivesExtraScoreAndGrowth() {
        GameModel model = new GameModel(config, false);
        model.getField().getObstacles().clear();

        Snake snake = model.getSnake();
        Cell next = snake.head().move(Direction.RIGHT);

        model.getFoods().clear();
        model.getFoods().add(new Food(next, FoodType.BONUS));

        model.update();

        assertEquals(2, model.getScore());
        assertTrue(snake.size() >= 2);
    }

    @Test
    public void testPoisonFoodDecreasesScore() {
        GameModel model = new GameModel(config, false);
        model.getField().getObstacles().clear();

        Snake snake = model.getSnake();
        Cell next = snake.head().move(Direction.RIGHT);

        model.getFoods().clear();
        model.getFoods().add(new Food(next, FoodType.POISON));

        model.update();

        assertEquals(-1, model.getScore());
    }

    @Test
    public void testPoisonFoodGameOverWhenNegativeScore() {
        GameModel model = new GameModel(config, false);
        model.getField().getObstacles().clear();

        Snake snake = model.getSnake();
        Cell next = snake.head().move(Direction.RIGHT);

        model.getFoods().clear();
        model.getFoods().add(new Food(next, FoodType.POISON));

        model.update();

        assertEquals(GameState.GAME_OVER, model.getState());
    }

    @Test
    public void testFoodRespawnsAfterEating() {
        GameModel model = new GameModel(config, false);
        model.getField().getObstacles().clear();

        Snake snake = model.getSnake();
        Cell next = snake.head().move(Direction.RIGHT);

        model.getFoods().clear();
        model.getFoods().add(new Food(next, FoodType.NORMAL));

        model.update();

        assertEquals(config.foodCount, model.getFoods().size());
    }

    @Test
    public void testSnakeGrowsOnNormalFood() {
        GameModel model = new GameModel(config, false);
        model.getField().getObstacles().clear();

        Snake snake = model.getSnake();
        Cell next = snake.head().move(Direction.RIGHT);

        model.getFoods().clear();
        model.getFoods().add(new Food(next, FoodType.NORMAL));

        model.update();

        assertEquals(2, snake.size());
    }
}