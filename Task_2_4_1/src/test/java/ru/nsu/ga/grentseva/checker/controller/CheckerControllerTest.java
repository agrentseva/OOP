package ru.nsu.ga.grentseva.checker.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CheckerControllerTest {

    @Test
    void controllerCreatesSuccessfully() {
        CheckerController controller = new CheckerController();
        assertNotNull(controller);
    }

    @Test
    void runWithInvalidArgumentsDoesNotThrow() {
        CheckerController controller = new CheckerController();
        assertDoesNotThrow(() -> controller.run(new String[]{"invalid"}));
    }

    @Test
    void runWithEmptyArgumentsDoesNotThrow() {
        CheckerController controller = new CheckerController();
        assertDoesNotThrow(() -> controller.run(new String[]{}));
    }
}