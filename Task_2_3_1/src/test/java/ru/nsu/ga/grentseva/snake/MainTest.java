package ru.nsu.ga.grentseva.snake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainTest {

    @Test
    public void testMainCreation() {
        assertDoesNotThrow(() -> {
            Main main = new Main();
        });
    }
}