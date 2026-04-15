package ru.nsu.ga.grentseva.snake.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.snake.config.GameConfig;

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
}