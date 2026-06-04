package ru.nsu.ga.grentseva.checker.service.gradle;

import java.io.File;

public class StyleChecker {

    private final GradleTaskRunner gradleTaskRunner = new GradleTaskRunner();

    public boolean check(File taskDirectory, int timeoutSeconds) {
        return gradleTaskRunner.runTask(taskDirectory, timeoutSeconds, "checkstyleMain");
    }
}