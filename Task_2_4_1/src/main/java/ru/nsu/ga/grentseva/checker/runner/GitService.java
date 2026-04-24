package ru.nsu.ga.grentseva.checker.runner;

import java.io.File;
import java.io.IOException;

public class GitService {

    public void cloneOrUpdate(String repoUrl, File directory) throws Exception {
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new IOException("Не удалось создать папку: " + directory.getAbsolutePath());
            }

            System.out.println("Скачиваем репозиторий: " + repoUrl);
            runGitCommand(directory.getParentFile(), "git", "clone", repoUrl, directory.getName());
        } else {
            System.out.println("Обновляем репозиторий в: " + directory.getName());
            runGitCommand(directory, "git", "fetch", "origin");

            try {
                runGitCommand(directory, "git", "checkout", "main");
                runGitCommand(directory, "git", "pull", "origin", "main");
            } catch (Exception e) {
                System.out.println("Ветка main не найдена, пробуем master...");
                runGitCommand(directory, "git", "checkout", "master");
                runGitCommand(directory, "git", "pull", "origin", "master");
            }
        }
    }

    private void runGitCommand(File workingDir, String... command) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(workingDir);
        builder.environment().put("GIT_TERMINAL_PROMPT", "0");
        builder.inheritIO();

        Process process = builder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            String errorMsg = new String(process.getErrorStream().readAllBytes());
            throw new Exception("Ошибка при выполнении команды Git: " + String.join(" ", command) + "\n" + errorMsg);
        }
    }
}