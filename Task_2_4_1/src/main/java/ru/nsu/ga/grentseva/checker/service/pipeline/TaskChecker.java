package ru.nsu.ga.grentseva.checker.service.pipeline;

import ru.nsu.ga.grentseva.checker.model.Settings;
import ru.nsu.ga.grentseva.checker.model.Submission;
import ru.nsu.ga.grentseva.checker.model.SubmissionResult;
import ru.nsu.ga.grentseva.checker.model.Task;
import ru.nsu.ga.grentseva.checker.service.git.GitService;
import ru.nsu.ga.grentseva.checker.service.logging.Logger;
import ru.nsu.ga.grentseva.checker.service.scoring.ScoreCalculator;
import ru.nsu.ga.grentseva.checker.service.parser.TestResultParser;
import ru.nsu.ga.grentseva.checker.service.gradle.GradleTaskRunner;

import java.io.File;
import java.time.LocalDate;

public class TaskChecker {

    private final GitService gitService = new GitService();
    private final GradleTaskRunner gradleTaskRunner = new GradleTaskRunner();
    private final TestResultParser testResultParser = new TestResultParser();
    private final ScoreCalculator scoreCalculator = new ScoreCalculator();
    private final Logger logger = new Logger();

    public SubmissionResult checkTask(Task task, Submission submission, File repositoryDirectory, Settings settings) {
        SubmissionResult result = new SubmissionResult();
        File taskDirectory = findTaskDirectory(repositoryDirectory, task.getId());

        try {
            LocalDate submitDate = gitService.getLastCommitDate(repositoryDirectory, taskDirectory);
            submission.setSubmitDate(submitDate);
        } catch (Exception e) {
            logger.commitDateError();
        }

        if (taskDirectory == null) {
            result.setErrorMessage("Папка задачи не найдена");
            return result;
        }

        boolean compiled = gradleTaskRunner.runTask(taskDirectory, 0, "build");
        result.setCompiled(compiled);
        if (!compiled) {
            result.setErrorMessage("Ошибка компиляции");
            return result;
        }

        boolean javadocGenerated = gradleTaskRunner.runTask(taskDirectory, 0, "javadoc");
        result.setJavadocGenerated(javadocGenerated);

        boolean stylePassed = gradleTaskRunner.runTask(taskDirectory, settings.getTestTimeoutSeconds(), "checkstyleMain");
        result.setStylePassed(stylePassed);

        gradleTaskRunner.runTask(taskDirectory, settings.getTestTimeoutSeconds(), "test");

        int[] tests = testResultParser.parse(taskDirectory);
        result.setTestsPassed(tests[0]);
        result.setTestsFailed(tests[1]);
        result.setTestsSkipped(tests[2]);

        double score = scoreCalculator.calculate(task, submission, tests[0], tests[1], tests[2], settings);
        result.setFinalScore(score);

        return result;
    }

    private File findTaskDirectory(File repository, String taskId) {
        String folderName = "Task_" + taskId.replace(".", "_");
        File taskDirectory = new File(repository, folderName);

        if (taskDirectory.exists()) {
            return taskDirectory;
        }
        return null;
    }
}