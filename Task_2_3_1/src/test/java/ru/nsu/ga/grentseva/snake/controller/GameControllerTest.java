package ru.nsu.ga.grentseva.snake.controller;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    @BeforeAll
    public static void initJFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void testSetEndlessMode() {
        GameController controller = new GameController();
        assertDoesNotThrow(() -> controller.setEndlessMode(true));
        assertDoesNotThrow(() -> controller.setEndlessMode(false));
    }
}