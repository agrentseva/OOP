package ru.nsu.ga.grentseva.snake.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameConfigTest {

    @Test
    public void testLevel1Config() {
        GameConfig config = GameConfig.level1();
        assertNotNull(config);
        assertEquals(35, config.width);
        assertEquals(25, config.height);
        assertEquals(5, config.targetLength);
        assertEquals(3, config.foodCount);
        assertEquals(1.0, config.speed, 0.001);
        assertEquals(1.0, config.foodSpawnChance, 0.001);
        assertEquals(5.0, config.ticksPerSecond, 0.001);
    }

    @Test
    public void testLevel2Config() {
        GameConfig config = GameConfig.level2();
        assertNotNull(config);
        assertEquals(35, config.width);
        assertEquals(25, config.height);
        assertEquals(7, config.targetLength);
        assertEquals(5, config.foodCount);
        assertEquals(1.5, config.speed, 0.001);
        assertEquals(0.8, config.foodSpawnChance, 0.001);
        assertEquals(7.0, config.ticksPerSecond, 0.001);
    }

    @Test
    public void testEndlessConfig() {
        GameConfig config = GameConfig.endless();
        assertNotNull(config);
        assertEquals(35, config.width);
        assertEquals(25, config.height);
        assertEquals(Integer.MAX_VALUE, config.targetLength);
        assertEquals(5, config.foodCount);
        assertEquals(1.0, config.speed, 0.001);
        assertEquals(1.0, config.foodSpawnChance, 0.001);
        assertEquals(5.0, config.ticksPerSecond, 0.001);
    }

    @Test
    public void testCustomConfigCreation() {
        GameConfig config = new GameConfig(10, 20, 30, 4, 2.5, 0.5, 10.0, 5);
        assertEquals(10, config.width);
        assertEquals(20, config.height);
        assertEquals(30, config.targetLength);
        assertEquals(4, config.foodCount);
        assertEquals(2.5, config.speed, 0.001);
        assertEquals(0.5, config.foodSpawnChance, 0.001);
        assertEquals(10.0, config.ticksPerSecond, 0.001);
        assertEquals(10.0, config.wallCount, 5);
    }
}
