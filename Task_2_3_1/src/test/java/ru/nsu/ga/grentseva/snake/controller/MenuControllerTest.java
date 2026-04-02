package ru.nsu.ga.grentseva.snake.controller;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MenuControllerTest {

    @BeforeAll
    public static void initJFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void testControllerCreation() {
        assertDoesNotThrow(() -> {
            MenuController controller = new MenuController();
        });
    }
}