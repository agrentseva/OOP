package ru.nsu.ga.grentseva.checker.runner;

import ru.nsu.ga.grentseva.checker.model.Task;

public class ScoreCalculator {

    public double calculate(Task task, int passed, int failed, double bonus) {
        if (passed == 0 && failed == 0) {
            return 0.0;
        }

        if (failed > 0) {
            return task.getMaxScore() * 0.5 + bonus;
        }

        return task.getMaxScore() + bonus;
    }
}