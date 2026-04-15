package ru.nsu.ga.grentseva.snake.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimationEffectTest {

    @Test
    void testInitialValues() {
        Cell pos = new Cell(3, 4);
        AnimationEffect effect = new AnimationEffect("БУМ", pos, 2.0);

        assertEquals("БУМ", effect.getText());
        assertEquals(pos, effect.getPosition());
        assertEquals(2.0, effect.getLifetime());
    }

    @Test
    void testUpdateReducesLifetime() {
        AnimationEffect effect = new AnimationEffect("test", new Cell(0, 0), 2.0);

        effect.update(0.5);

        assertEquals(1.5, effect.getLifetime());
    }

    @Test
    void testUpdateMultipleTimes() {
        AnimationEffect effect = new AnimationEffect("test", new Cell(0, 0), 2.0);

        effect.update(0.5);
        effect.update(0.5);

        assertEquals(1.0, effect.getLifetime());
    }

    @Test
    void testIsFinishedFalseInitially() {
        AnimationEffect effect = new AnimationEffect("test", new Cell(0, 0), 1.0);

        assertFalse(effect.isFinished());
    }

    @Test
    void testIsFinishedAfterUpdate() {
        AnimationEffect effect = new AnimationEffect("test", new Cell(0, 0), 1.0);

        effect.update(1.0);

        assertTrue(effect.isFinished());
    }

    @Test
    void testIsFinishedWhenNegativeLifetime() {
        AnimationEffect effect = new AnimationEffect("test", new Cell(0, 0), 0.5);

        effect.update(1.0);

        assertTrue(effect.isFinished());
        assertTrue(effect.getLifetime() < 0);
    }

    @Test
    void testZeroLifetimeImmediatelyFinished() {
        AnimationEffect effect = new AnimationEffect("test", new Cell(0, 0), 0.0);

        assertTrue(effect.isFinished());
    }
}