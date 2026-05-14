package ru.nsu.ga.grentseva.checker.service;

import ru.nsu.ga.grentseva.checker.model.*;

import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PipelineRunner {

    private final GitService gitService = new GitService();
    private final ScoreCalculator scoreCalculator = new ScoreCalculator();

    public Map<Submission, SubmissionResult> run(CourseConfig config) {
        Map<Submission, SubmissionResult> results = new ConcurrentHashMap<>();

        File workspace = new File("checker_workspace");
        if (!workspace.exists()) {
            workspace.mkdirs();
        }

        int threads = 3;
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        List<Future<?>> futures = new ArrayList<>();

        for (Group group : config.getGroups()) {
            for (Student student : group.getStudents()) {
                Future<?> future = executor.submit(() -> {processStudent(config, student, workspace, results);});
                futures.add(future);
            }
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                System.out.println("Ошибка потока проверки");
            }
        }
        executor.shutdown();

        return results;
    }
    
    private void processStudent(CourseConfig config, Student student,
                                File workspace, Map<Submission, SubmissionResult> results) {

        File repositoryDirectory = new File(workspace, student.getGithubId());
        try {
            gitService.cloneOrUpdate(student.getRepositoryUrl(), repositoryDirectory);
        } catch (Exception e) {
            System.out.println("Ошибка Git для " + student.getGithubId());
            return;
        }

        for (Submission submission : config.getSubmissions()) {
            if (!submission.getStudentId().equals(student.getGithubId())) {
                continue;
            }

            Task task = findTask(config, submission.getTaskId());
            if (task == null) {
                continue;
            }

            SubmissionResult result = checkTask(task, submission, repositoryDirectory, config.getSettings());
            results.put(submission, result);
        }
    }

    private SubmissionResult checkTask(Task task, Submission submission,
                                       File repositoryDirectory, Settings settings) {

        SubmissionResult result = new SubmissionResult();

        File taskDirectory = findTaskDirectory(repositoryDirectory, task.getId());
        try {
            LocalDate submitDate = gitService.getLastCommitDate(repositoryDirectory, taskDirectory);
            submission.setSubmitDate(submitDate);
        } catch (Exception e) {
            System.out.println("Не удалось получить дату commit");
        }

        if (taskDirectory == null) {
            result.setErrorMessage("Папка задачи не найдена");
            return result;
        }

        boolean compiled = runGradleTask(taskDirectory, 0, "build");
        result.setCompiled(compiled);
        if (!compiled) {
            result.setErrorMessage("Ошибка компиляции");
            return result;
        }

        boolean javadocGenerated = runGradleTask(taskDirectory, 0, "javadoc");
        result.setJavadocGenerated(javadocGenerated);

        boolean stylePassed = runGradleTask(taskDirectory, settings.getTestTimeoutSeconds(), "checkstyleMain");
        result.setStylePassed(stylePassed);

        runGradleTask(taskDirectory, settings.getTestTimeoutSeconds(), "test");
        int[] tests = parseTestResults(taskDirectory);
        result.setTestsPassed(tests[0]);
        result.setTestsFailed(tests[1]);
        result.setTestsSkipped(tests[2]);

        double score = scoreCalculator.calculate(task, submission, tests[0], tests[1], tests[2], settings);
        result.setFinalScore(score);

        return result;
    }

    private boolean runGradleTask(File directory, int timeoutSeconds, String taskName) {
        String gradlewName;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            gradlewName = "gradlew.bat";
        } else {
            gradlewName = "./gradlew";
        }

        File gradlew = new File(directory, gradlewName);
        if (!gradlew.exists()) {
            System.out.println("gradlew не найден: " + directory.getAbsolutePath());
            return false;
        }

        List<String> command = new ArrayList<>();
        command.add(gradlew.getAbsolutePath());
        command.add("--no-daemon");
        command.add(taskName);

        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.directory(directory);
            builder.inheritIO();

            Process process = builder.start();
            boolean finished;
            if (timeoutSeconds > 0) {
                finished = process.waitFor(timeoutSeconds, java.util.concurrent.TimeUnit.SECONDS);
            } else {
                process.waitFor();
                finished = true;
            }

            if (!finished) {
                process.destroyForcibly();
                System.out.println("Timeout: " + taskName);
                return false;
            }

            int exitCode = process.exitValue();
            if (exitCode != 0) {
                System.out.println("Gradle task failed: " + taskName + " in " + directory.getName());
            }
            return exitCode == 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int[] parseTestResults(File directory) {
        File resultsFolder = new File(directory, "build/test-results/test");
        if (!resultsFolder.exists()) {
            return new int[]{0, 0, 0};
        }

        int passed = 0;
        int failed = 0;
        int skipped = 0;

        File[] files = resultsFolder.listFiles((dir, name) -> name.endsWith(".xml"));
        if (files == null) {
            return new int[]{0, 0, 0};
        }

        for (File file : files) {
            try {
                var document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
                var root = document.getDocumentElement();
                int tests = Integer.parseInt(root.getAttribute("tests"));
                int failures = Integer.parseInt(root.getAttribute("failures"));
                int skippedTests = Integer.parseInt(root.getAttribute("skipped"));
                passed += tests - failures - skippedTests;
                failed += failures;
                skipped += skippedTests;
            } catch (Exception ignored) {
            }
        }

        return new int[]{passed, failed, skipped};
    }

    private File findTaskDirectory(File repository, String taskId) {
        String folderName = "Task_" + taskId.replace(".", "_");

        File taskDirectory = new File(repository, folderName);
        if (taskDirectory.exists()) {
            return taskDirectory;
        }

        return null;
    }

    private Task findTask(CourseConfig config, String taskId) {
        for (Task task : config.getTasks()) {
            if (task.getId().equals(taskId)) {
                return task;
            }
        }

        return null;
    }
}