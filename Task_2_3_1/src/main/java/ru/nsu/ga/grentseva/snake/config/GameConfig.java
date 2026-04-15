package ru.nsu.ga.grentseva.snake.config;

public final class GameConfig {

    public final int width;
    public final int height;
    public final int targetLength;
    public final int foodCount;
    public final double speed;
    public final double foodSpawnChance;
    public final double ticksPerSecond;
    public final int wallCount;

    public static GameConfig level1() {
        return new GameConfig(
                30, 30,
                5,
                3,
                1.0,
                1.0,
                5.0,
                2
        );
    }

    public static GameConfig level2() {
        return new GameConfig(
                30, 30,
                7,
                5,
                1.5,
                0.8,
                7.0,
                10
        );
    }

    public static GameConfig endless() {
        return new GameConfig(
                30, 30,
                Integer.MAX_VALUE,
                5,
                1.0,
                1.0,
                5.0,
                0
        );
    }

    public GameConfig(int width,
                      int height,
                      int targetLength,
                      int foodCount,
                      double speed,
                      double foodSpawnChance,
                      double ticksPerSecond,
                      int wallCount) {

        this.width = width;
        this.height = height;
        this.targetLength = targetLength;
        this.foodCount = foodCount;
        this.speed = speed;
        this.foodSpawnChance = foodSpawnChance;
        this.ticksPerSecond = ticksPerSecond;
        this.wallCount = wallCount;
    }
}