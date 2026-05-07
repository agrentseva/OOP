package ru.nsu.ga.grentseva.checker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.ga.grentseva.checker.model.*;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PipelineRunnerTest {

    @TempDir
    Path tempDir;

    @Test
    void pipelineRunnerCanBeCreated() {

        PipelineRunner runner =
                new PipelineRunner();

        assertNotNull(runner);
    }

    @Test
    void runReturnsMap() {

        PipelineRunner runner =
                new PipelineRunner();

        CourseConfig config =
                new CourseConfig();

        Map<Submission,
                SubmissionResult> results =
                runner.run(config);

        assertNotNull(results);

        assertTrue(results.isEmpty());
    }

    @Test
    void findTaskReturnsCorrectTask()
            throws Exception {

        PipelineRunner runner =
                new PipelineRunner();

        CourseConfig config =
                new CourseConfig();

        Task task =
                new Task(
                        "2.2.1",
                        "Pizza",
                        1,
                        null,
                        null
                );

        config.addTask(task);

        Method method =
                PipelineRunner.class
                        .getDeclaredMethod(
                                "findTask",
                                CourseConfig.class,
                                String.class
                        );

        method.setAccessible(true);

        Task found =
                (Task) method.invoke(
                        runner,
                        config,
                        "2.2.1"
                );

        assertEquals(task, found);
    }

    @Test
    void findTaskReturnsNullForMissingTask()
            throws Exception {

        PipelineRunner runner =
                new PipelineRunner();

        CourseConfig config =
                new CourseConfig();

        Method method =
                PipelineRunner.class
                        .getDeclaredMethod(
                                "findTask",
                                CourseConfig.class,
                                String.class
                        );

        method.setAccessible(true);

        Object result =
                method.invoke(
                        runner,
                        config,
                        "missing"
                );

        assertNull(result);
    }

    @Test
    void findTaskDirectoryFindsExistingDirectory()
            throws Exception {

        PipelineRunner runner =
                new PipelineRunner();

        File repository =
                tempDir.toFile();

        File taskFolder =
                new File(
                        repository,
                        "Task_2_2_1"
                );

        taskFolder.mkdirs();

        Method method =
                PipelineRunner.class
                        .getDeclaredMethod(
                                "findTaskDirectory",
                                File.class,
                                String.class
                        );

        method.setAccessible(true);

        File result =
                (File) method.invoke(
                        runner,
                        repository,
                        "2.2.1"
                );

        assertNotNull(result);

        assertEquals(
                taskFolder.getName(),
                result.getName()
        );
    }

    @Test
    void findTaskDirectoryReturnsNull()
            throws Exception {

        PipelineRunner runner =
                new PipelineRunner();

        Method method =
                PipelineRunner.class
                        .getDeclaredMethod(
                                "findTaskDirectory",
                                File.class,
                                String.class
                        );

        method.setAccessible(true);

        Object result =
                method.invoke(
                        runner,
                        tempDir.toFile(),
                        "1.1.1"
                );

        assertNull(result);
    }

    @Test
    void parseTestResultsReturnsZeros()
            throws Exception {

        PipelineRunner runner =
                new PipelineRunner();

        Method method =
                PipelineRunner.class
                        .getDeclaredMethod(
                                "parseTestResults",
                                File.class
                        );

        method.setAccessible(true);

        int[] result =
                (int[]) method.invoke(
                        runner,
                        tempDir.toFile()
                );

        assertEquals(0, result[0]);

        assertEquals(0, result[1]);

        assertEquals(0, result[2]);
    }

    @Test
    void runGradleTaskReturnsFalseForMissingGradle()
            throws Exception {

        PipelineRunner runner =
                new PipelineRunner();

        Method method =
                PipelineRunner.class
                        .getDeclaredMethod(
                                "runGradleTask",
                                File.class,
                                int.class,
                                String.class
                        );

        method.setAccessible(true);

        boolean result =
                (boolean) method.invoke(
                        runner,
                        tempDir.toFile(),
                        1,
                        "build"
                );

        assertFalse(result);
    }

    @Test
    void checkTaskReturnsErrorWhenTaskFolderMissing()
            throws Exception {

        PipelineRunner runner =
                new PipelineRunner();

        Task task =
                new Task(
                        "2.2.1",
                        "Pizza",
                        1,
                        LocalDate.now(),
                        LocalDate.now()
                );

        Submission submission =
                new Submission(
                        "agrentseva",
                        "2.2.1",
                        LocalDate.now(),
                        0
                );

        Settings settings =
                new Settings();

        Method method =
                PipelineRunner.class
                        .getDeclaredMethod(
                                "checkTask",
                                Task.class,
                                Submission.class,
                                File.class,
                                Settings.class
                        );

        method.setAccessible(true);

        SubmissionResult result =
                (SubmissionResult) method.invoke(
                        runner,
                        task,
                        submission,
                        tempDir.toFile(),
                        settings
                );

        assertNotNull(result);

        assertEquals(
                "Папка задачи не найдена",
                result.getErrorMessage()
        );
    }

    @Test
    void runWithEmptyConfig() {

        PipelineRunner runner =
                new PipelineRunner();

        CourseConfig config =
                new CourseConfig();

        Map<?, ?> result =
                runner.run(config);

        assertTrue(result.isEmpty());
    }

    @Test
    void findTaskDirectoryWorks()
            throws Exception {

        PipelineRunner runner =
                new PipelineRunner();

        File repository =
                tempDir.toFile();

        File folder =
                new File(
                        repository,
                        "Task_1_1_1"
                );

        folder.mkdirs();

        Method method =
                PipelineRunner.class
                        .getDeclaredMethod(
                                "findTaskDirectory",
                                File.class,
                                String.class
                        );

        method.setAccessible(true);

        File result =
                (File) method.invoke(
                        runner,
                        repository,
                        "1.1.1"
                );

        assertNotNull(result);
    }

}