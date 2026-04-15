package ru.nsu.ga.grentseva.snake.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.snake.config.GameConfig;

import static org.junit.jupiter.api.Assertions.*;

class BotSnakeTest {

    private GameModel model;

    @BeforeEach
    void setUp() {
        model = new GameModel(GameConfig.level1(), false);
    }

    @Test
    void testRandomBotDoesNotCrash() {
        BotSnake bot = new BotSnake(new Cell(5, 5), BotType.RANDOM);

        Direction dir = bot.decideDirection(model);

        assertNotNull(dir);
    }

    @Test
    void testGreedyBotMovesTowardFood() {
        BotSnake bot = new BotSnake(new Cell(5, 5), BotType.GREEDY);
        model.getSnakes().add(bot);

        model.getFoods().clear();
        model.getFoods().add(new Food(new Cell(6, 5), FoodType.NORMAL));

        Direction dir = bot.decideDirection(model);

        assertEquals(Direction.RIGHT, dir);
    }

    @Test
    void testBotAvoidsObstacle() {
        BotSnake bot = new BotSnake(new Cell(5, 5), BotType.GREEDY);
        model.getSnakes().add(bot);

        model.getFoods().clear();
        model.getFoods().add(new Food(new Cell(5, 4), FoodType.NORMAL));

        model.getField().getObstacles().add(new Cell(5, 4));

        Direction dir = bot.decideDirection(model);

        assertNotEquals(Direction.UP, dir);
    }

    @Test
    void testFallbackWhenAllDanger() {
        BotSnake bot = new BotSnake(new Cell(5, 5), BotType.GREEDY);
        model.getSnakes().add(bot);

        model.getField().getObstacles().add(new Cell(5, 4));
        model.getField().getObstacles().add(new Cell(5, 6));
        model.getField().getObstacles().add(new Cell(4, 5));
        model.getField().getObstacles().add(new Cell(6, 5));

        Direction dir = bot.decideDirection(model);

        assertEquals(bot.getCurrentDirection(), dir);
    }

    @Test
    void testBotDoesNotGoBackwards() {
        BotSnake bot = new BotSnake(new Cell(5, 5), BotType.RANDOM);
        model.getSnakes().add(bot);

        bot.setDirection(Direction.RIGHT);
        bot.consumeDirection();

        Direction dir = bot.decideDirection(model);

        assertNotEquals(Direction.LEFT, dir);
    }

    @Test
    void testDistanceCalculationIndirectly() {
        BotSnake bot = new BotSnake(new Cell(5, 5), BotType.GREEDY);
        model.getSnakes().add(bot);

        model.getFoods().clear();
        model.getFoods().add(new Food(new Cell(7, 5), FoodType.NORMAL)); // дальше
        model.getFoods().add(new Food(new Cell(6, 5), FoodType.NORMAL)); // ближе

        Direction dir = bot.decideDirection(model);

        assertEquals(Direction.RIGHT, dir);
    }
}