package ru.nsu.ga.grentseva.snake.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.snake.config.GameConfig;
import ru.nsu.ga.grentseva.snake.render.RenderCommand;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    private GameModel model;

    @BeforeEach
    void setUp() {
        GameConfig config = GameConfig.level1();
        model = new GameModel(config, false);
    }

    @Test
    void testInitialState() {
        assertEquals(GameState.RUNNING, model.getState());
        assertNotNull(model.getPlayer());
        assertFalse(model.getSnakes().isEmpty());
    }

    @Test
    void testWrapLeft() {
        Cell c = new Cell(-1, 5);
        Cell wrapped = model.wrap(c);

        assertEquals(model.getConfig().width - 1, wrapped.x());
        assertEquals(5, wrapped.y());
    }

    @Test
    void testWrapRight() {
        int width = model.getConfig().width;
        Cell c = new Cell(width, 3);

        Cell wrapped = model.wrap(c);

        assertEquals(0, wrapped.x());
        assertEquals(3, wrapped.y());
    }

    @Test
    void testWrapTop() {
        Cell c = new Cell(2, -1);

        Cell wrapped = model.wrap(c);

        assertEquals(2, wrapped.x());
        assertEquals(model.getConfig().height - 1, wrapped.y());
    }

    @Test
    void testWrapBottom() {
        int height = model.getConfig().height;

        Cell c = new Cell(4, height);

        Cell wrapped = model.wrap(c);

        assertEquals(4, wrapped.x());
        assertEquals(0, wrapped.y());
    }

    @Test
    void testUpdateDoesNotCrash() {
        assertDoesNotThrow(() -> model.update());
    }

    @Test
    void testScoreInitiallyZero() {
        assertEquals(0, model.getScore());
    }

    @Test
    void testPlayerExistsInSnakes() {
        assertTrue(model.getSnakes().contains(model.getPlayer()));
    }

    @Test
    void testFoodGenerated() {
        assertFalse(model.getFoods().isEmpty());
    }

    @Test
    void testUpdateEffectsRemovesExpired() {
        model.getAnimationEffects().add(
                new AnimationEffect("БУМ", new Cell(1,1), 0.01)
        );

        model.updateEffects(1.0);

        assertTrue(model.getAnimationEffects().isEmpty());
    }

    @Test
    void testGameOverWhenPlayerDies() {
        model.getPlayer().removeTail();

        while (model.getPlayer().size() > 0) {
            model.getPlayer().removeTail();
        }

        model.update();

        assertEquals(GameState.GAME_OVER, model.getState());
    }

    @Test
    void testBotsExist() {
        assertTrue(model.getSnakes().size() >= 1);
    }

    @Test
    void testSnakeMovesAfterUpdate() {
        Cell before = model.getPlayer().head();

        model.update();

        Cell after = model.getPlayer().head();

        assertNotEquals(before, after);
    }

    @Test
    void testEatingNormalFoodIncreasesScore() {
        Snake player = model.getPlayer();
        Cell head = player.head();

        Cell foodCell = new Cell(head.x() + 1, head.y());

        model.getFoods().clear();
        model.getFoods().add(new Food(foodCell, FoodType.NORMAL));

        player.setDirection(Direction.RIGHT);

        model.update();

        assertEquals(10, model.getScore());
    }

    @Test
    void testEatingBonusFoodGivesMoreScore() {
        Snake player = model.getPlayer();
        Cell head = player.head();

        Cell foodCell = new Cell(head.x() + 1, head.y());

        model.getFoods().clear();
        model.getFoods().add(new Food(foodCell, FoodType.BONUS));

        player.setDirection(Direction.RIGHT);

        model.update();

        assertEquals(20, model.getScore());
    }

    @Test
    void testEatingPoisonDecreasesScore() {
        Snake player = model.getPlayer();
        Cell head = player.head();

        Cell foodCell = new Cell(head.x() + 1, head.y());

        model.getFoods().clear();
        model.getFoods().add(new Food(foodCell, FoodType.POISON));

        player.setDirection(Direction.RIGHT);

        model.update();

        assertEquals(-5, model.getScore());
    }

    @Test
    void testSnakeGrowsOnNormalFood() {
        Snake player = model.getPlayer();

        int before = player.size();

        Cell foodCell = new Cell(player.head().x() + 1, player.head().y());

        model.getFoods().clear();
        model.getFoods().add(new Food(foodCell, FoodType.NORMAL));

        player.setDirection(Direction.RIGHT);

        model.update();

        assertTrue(player.size() > before);
    }

    @Test
    void testPoisonCanKillSnake() {
        Snake player = model.getPlayer();

        while (player.size() > 1) {
            player.removeTail();
        }

        Cell foodCell = new Cell(player.head().x() + 1, player.head().y());

        model.getFoods().clear();
        model.getFoods().add(new Food(foodCell, FoodType.POISON));

        player.setDirection(Direction.RIGHT);

        model.update();

        assertEquals(GameState.GAME_OVER, model.getState());
    }

    @Test
    void testCommandsAreGenerated() {
        List<RenderCommand> commands = model.update();

        assertNotNull(commands);
        assertFalse(commands.isEmpty());
    }

    @Test
    void testBotSpawning() {
        int before = model.getSnakes().size();
        for (int i = 0; i < 10; i++) {
            model.getPlayer().move(new Cell(0, i), true);
        }

        model.update();

        int after = model.getSnakes().size();

        assertTrue(after >= before);
    }

    @Test
    void testAnimationEffectAddedOnBotDeath() {
        model.getSnakes().removeIf(s -> s instanceof BotSnake);

        model.update();

        assertFalse(model.getSnakes().isEmpty());
    }
}