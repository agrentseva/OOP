package ru.nsu.ga.grentseva.checker.service.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void applicationStarted() {
        info(Messages.APPLICATION_STARTED);
    }

    public void applicationFinished() {
        success(Messages.APPLICATION_FINISHED);
    }

    public void htmlReportGenerated(String fileName) {
        success(Messages.HTML_REPORT_GENERATED + fileName);
    }

    public void cloningRepository(String repositoryUrl) {
        info(Messages.CLONING_REPOSITORY + repositoryUrl);
    }

    public void updatingRepository(String repositoryName) {
        info(Messages.UPDATING_REPOSITORY + repositoryName);
    }

    public void gitError(String githubId) {
        error(Messages.GIT_ERROR + githubId);
    }

    public void threadError() {
        error(Messages.THREAD_ERROR);
    }

    public void commitDateError() {
        warning(Messages.COMMIT_DATE_ERROR);
    }

    public void gradleWrapperNotFound(String path) {
        error(Messages.GRADLE_WRAPPER_NOT_FOUND + path);
    }

    public void taskTimeout(String taskName) {
        warning(Messages.GRADLE_TASK_TIMEOUT + taskName);
    }

    public void gradleTaskFailed(String taskName, String directoryName) {
        warning(Messages.GRADLE_TASK_FAILED
                + taskName
                + Messages.IN_DIRECTORY
                + directoryName);
    }

    public void htmlGenerationError() {
        error(Messages.HTML_GENERATION_ERROR);
    }

    public void applicationError(Exception exception) {
        error(Messages.APPLICATION_ERROR);

        if (exception != null) {
            exception.printStackTrace();
        }
    }

    public void repositoryPrepared(String githubId) {
        success(Messages.REPOSITORY_PREPARED + githubId);
    }

    public void printUsage() {
        System.out.println();

        System.out.println(Messages.USAGE);
        System.out.println(Messages.USAGE_COMMAND);

        System.out.println();

        System.out.println(Messages.EXAMPLE);
        System.out.println(Messages.EXAMPLE_COMMAND);
    }

    public void info(String message) {
        log("INFO", message);
    }

    public void success(String message) {
        log("SUCCESS", message);
    }

    public void warning(String message) {
        log("WARNING", message);
    }

    public void error(String message) {
        log("ERROR", message);
    }

    private void log(String level, String message) {
        String time = LocalDateTime.now().format(TIME_FORMAT);

        System.out.println("[" + time + "] " + "[" + level + "] " + message);
    }

    private static class Messages {

        private static final String APPLICATION_STARTED =
                "Application started";

        private static final String APPLICATION_FINISHED =
                "Checking completed";

        private static final String HTML_REPORT_GENERATED =
                "HTML report generated: ";

        private static final String CLONING_REPOSITORY =
                "Cloning repository: ";

        private static final String UPDATING_REPOSITORY =
                "Updating repository: ";

        private static final String GIT_ERROR =
                "Git error for student: ";

        private static final String THREAD_ERROR =
                "Thread execution error";

        private static final String COMMIT_DATE_ERROR =
                "Failed to get last commit date";

        private static final String GRADLE_WRAPPER_NOT_FOUND =
                "Gradle wrapper not found: ";

        private static final String GRADLE_TASK_TIMEOUT =
                "Gradle task timeout: ";

        private static final String GRADLE_TASK_FAILED =
                "Gradle task failed: ";

        private static final String REPOSITORY_PREPARED =
                "Repository prepared: ";

        private static final String IN_DIRECTORY =
                " in directory: ";

        private static final String HTML_GENERATION_ERROR =
                "Failed to generate HTML report";

        private static final String APPLICATION_ERROR =
                "Application execution error";

        private static final String USAGE =
                "Usage:";

        private static final String USAGE_COMMAND =
                "java -jar checker.jar check [config.groovy]";

        private static final String EXAMPLE =
                "Example:";

        private static final String EXAMPLE_COMMAND =
                "java -jar checker.jar check oop-check.groovy";
    }
}