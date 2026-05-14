package ru.nsu.ga.grentseva.checker.service.gradle;

import ru.nsu.ga.grentseva.checker.service.parser.TestResultParser;

import java.io.File;

public class TestChecker {

    private final GradleTaskRunner gradleTaskRunner = new GradleTaskRunner();
    private final TestResultParser testResultParser = new TestResultParser();

    public int[] check(File taskDirectory, int timeoutSeconds) {
        gradleTaskRunner.runTask(taskDirectory, timeoutSeconds, "test");
        return testResultParser.parse(taskDirectory);
    }
}