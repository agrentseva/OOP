package ru.nsu.ga.grentseva.checker.service.gradle;

import java.io.File;

public class JavadocChecker {

    private final GradleTaskRunner gradleTaskRunner = new GradleTaskRunner();

    public boolean check(File taskDirectory) {
        return gradleTaskRunner.runTask(taskDirectory, 0, "javadoc");
    }
}