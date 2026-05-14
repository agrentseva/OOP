package ru.nsu.ga.grentseva.checker.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class GitService {

    public void cloneOrUpdate(String repositoryUrl, File repositoryDirectory) throws Exception {
        if (!repositoryDirectory.exists()) {
            cloneRepository(repositoryUrl, repositoryDirectory);
        } else {
            updateRepository(repositoryDirectory);
        }
    }

    private void cloneRepository(String repositoryUrl, File repositoryDirectory) throws Exception {
        File parentDirectory = repositoryDirectory.getParentFile();
        if (parentDirectory != null && !parentDirectory.exists()) {
            boolean created = parentDirectory.mkdirs();
            if (!created) {
                throw new IOException("Не удалось создать папку: " + parentDirectory.getAbsolutePath());
            }
        }

        System.out.println("Клонируем: " + repositoryUrl);
        runCommand(parentDirectory, "git", "clone", repositoryUrl, repositoryDirectory.getName());
    }

    private void updateRepository(File repositoryDirectory) throws Exception {
        System.out.println("Обновляем репозиторий: " + repositoryDirectory.getName());
        runCommand(repositoryDirectory, "git", "fetch", "origin");

        String branch = branchExists(repositoryDirectory, "main") ? "main" : "master";
        runCommand(repositoryDirectory, "git", "checkout", branch);
        runCommand(repositoryDirectory, "git", "reset", "--hard");
        runCommand(repositoryDirectory, "git", "clean", "-fd");
        runCommand(repositoryDirectory, "git", "pull", "origin", branch);
    }

    private boolean branchExists(File repositoryDirectory, String branch) {
        try {
            runCommand(repositoryDirectory, "git", "rev-parse", "--verify", branch);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void runCommand(File workingDirectory, String... command) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(workingDirectory);
        builder.environment().put("GIT_TERMINAL_PROMPT", "0");

        Process process = builder.start();
        boolean finished = process.waitFor(30, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            throw new RuntimeException("Git команда зависла");
        }

        int exitCode = process.exitValue();
        if (exitCode != 0) {
            String error = new String(process.getErrorStream().readAllBytes());
            throw new Exception("Ошибка git команды:\n" + String.join(" ", command) + "\n" + error);
        }
    }

    public LocalDate getLastCommitDate(File repository, File taskDirectory) throws Exception {
        ProcessBuilder builder = new ProcessBuilder("git", "log", "-1",
                "--format=%cd", "--date=short", "--", taskDirectory.getName());
        builder.directory(repository);

        Process process = builder.start();
        boolean finished = process.waitFor(15, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            throw new RuntimeException("git log завис");
        }

        String output = new String(process.getInputStream().readAllBytes()).trim();
        if (output.isEmpty()) {
            throw new RuntimeException("Не удалось получить дату commit");
        }

        return LocalDate.parse(output);
    }
}