package ru.nsu.ga.grentseva.checker.service.gradle;

import ru.nsu.ga.grentseva.checker.service.logging.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GradleTaskRunner {

    private final Logger logger = new Logger();

    public boolean runTask(File directory, int timeoutSeconds, String taskName) {
        String gradlewName;

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            gradlewName = "gradlew.bat";
        } else {
            gradlewName = "./gradlew";
        }

        File gradlew = new File(directory, gradlewName);
        if (!gradlew.exists()) {
            logger.gradleWrapperNotFound(directory.getAbsolutePath());
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
            try {
                boolean finished;

                if (timeoutSeconds > 0) {
                    finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
                } else {
                    process.waitFor();
                    finished = true;
                }

                if (!finished) {
                    process.destroyForcibly();
                    logger.taskTimeout(taskName);
                    return false;
                }

                int exitCode = process.exitValue();
                if (exitCode != 0) {
                    logger.gradleTaskFailed(taskName, directory.getName());
                }
                return exitCode == 0;

            } finally {
                process.destroy();
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}