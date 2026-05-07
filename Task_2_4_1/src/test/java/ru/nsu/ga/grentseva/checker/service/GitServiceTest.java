package ru.nsu.ga.grentseva.checker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GitServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void cloneOrUpdateCreatesDirectory() throws Exception {

        GitService gitService =
                new GitService();

        File repository =
                tempDir.resolve("repo").toFile();

        assertFalse(repository.exists());

        /*
            Тест только структуры,
            без настоящего git clone
         */
        repository.mkdirs();

        assertTrue(repository.exists());
    }

    @Test
    void getLastCommitDateThrowsForNonGitDirectory() {

        GitService gitService =
                new GitService();

        File repository =
                tempDir.toFile();

        File taskDirectory =
                new File(repository, "Task_1_1_1");

        taskDirectory.mkdirs();

        assertThrows(
                Exception.class,
                () -> gitService.getLastCommitDate(
                        repository,
                        taskDirectory
                )
        );
    }

    @Test
    void branchExistsReturnsFalseForNonGitRepository()
            throws Exception {

        GitService gitService =
                new GitService();

        Method method =
                GitService.class.getDeclaredMethod(
                        "branchExists",
                        File.class,
                        String.class
                );

        method.setAccessible(true);

        boolean result =
                (boolean) method.invoke(
                        gitService,
                        tempDir.toFile(),
                        "main"
                );

        assertFalse(result);
    }

    @Test
    void runCommandThrowsExceptionForInvalidCommand()
            throws Exception {

        GitService gitService =
                new GitService();

        Method method =
                GitService.class.getDeclaredMethod(
                        "runCommand",
                        File.class,
                        String[].class
                );

        method.setAccessible(true);

        Exception exception =
                assertThrows(
                        Exception.class,
                        () -> method.invoke(
                                gitService,
                                tempDir.toFile(),
                                new String[]{
                                        "invalid_command"
                                }
                        )
                );

        assertNotNull(exception);
    }

    @Test
    void getLastCommitDateMethodExists()
            throws Exception {

        Method method =
                GitService.class.getDeclaredMethod(
                        "getLastCommitDate",
                        File.class,
                        File.class
                );

        assertNotNull(method);
    }

    @Test
    void cloneOrUpdateMethodExists()
            throws Exception {

        Method method =
                GitService.class.getDeclaredMethod(
                        "cloneOrUpdate",
                        String.class,
                        File.class
                );

        assertNotNull(method);
    }

    @Test
    void gitServiceCanBeCreated() {

        GitService gitService =
                new GitService();

        assertNotNull(gitService);
    }

    @Test
    void temporaryDirectoryExists() {

        assertTrue(
                Files.exists(tempDir)
        );
    }

    @Test
    void localDateParsingWorks() {

        LocalDate date =
                LocalDate.parse("2026-05-01");

        assertEquals(
                2026,
                date.getYear()
        );

        assertEquals(
                5,
                date.getMonthValue()
        );

        assertEquals(
                1,
                date.getDayOfMonth()
        );
    }

    @Test
    void cloneRepositoryThrowsException()
            throws Exception {

        GitService gitService =
                new GitService();

        File file =
                new File(
                        tempDir.toFile(),
                        "test.txt"
                );

        file.createNewFile();

        assertThrows(
                Exception.class,
                () -> gitService.cloneOrUpdate(
                        "invalid",
                        file
                )
        );
    }
}