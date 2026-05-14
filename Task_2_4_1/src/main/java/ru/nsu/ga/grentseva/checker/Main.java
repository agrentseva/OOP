package ru.nsu.ga.grentseva.checker;

import ru.nsu.ga.grentseva.checker.controller.CheckerController;
import ru.nsu.ga.grentseva.checker.service.logging.Logger;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {

    private static final Logger logger = new Logger();

    public static void main(String[] args) {
        setupUtf8();

        CheckerController controller = new CheckerController();
        controller.run(args);
    }

    private static void setupUtf8() {
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
            System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8.name()));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}