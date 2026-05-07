package ru.nsu.ga.grentseva.checker.dsl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.ga.grentseva.checker.model.CourseConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ConfigScriptTest {

    @TempDir
    Path tempDir;

    @Test
    void parseTask() throws Exception {

        Path file =
                tempDir.resolve("config.groovy");

        Files.writeString(
                file,
                """
                tasks {
                    task("1.1.1") {
                        name = "Heap sort"
                        maxScore = 2
                        softDeadline = "14/09/2025"
                        hardDeadline = "21/09/2025"
                    }
                }
                """
        );

        CourseConfig config =
                ConfigDslParser.parse(file);

        assertEquals(
                1,
                config.getTasks().size()
        );

        assertEquals(
                "1.1.1",
                config.getTasks().getFirst().getId()
        );

        assertEquals(
                "Heap sort",
                config.getTasks().getFirst().getName()
        );

        assertEquals(
                2,
                config.getTasks().getFirst().getMaxScore()
        );

        assertEquals(
                LocalDate.of(2025, 9, 14),
                config.getTasks().getFirst().getSoftDeadline()
        );
    }

    @Test
    void parseGroupAndStudent() throws Exception {

        Path file =
                tempDir.resolve("config.groovy");

        Files.writeString(
                file,
                """
                groups {
                    group("24214") {
                        student("agrentseva") {
                            name = "Alina"
                            repositoryUrl = "https://github.com/test/repo.git"
                        }
                    }
                }
                """
        );

        CourseConfig config =
                ConfigDslParser.parse(file);

        assertEquals(
                1,
                config.getGroups().size()
        );

        assertEquals(
                "24214",
                config.getGroups().getFirst().getName()
        );

        assertEquals(
                1,
                config.getGroups().getFirst().getStudents().size()
        );

        assertEquals(
                "agrentseva",
                config.getGroups()
                        .getFirst()
                        .getStudents()
                        .getFirst()
                        .getGithubId()
        );
    }

    @Test
    void parseSubmission() throws Exception {

        Path file =
                tempDir.resolve("config.groovy");

        Files.writeString(
                file,
                """
                submissions {
                    submission("agrentseva", "1.1.1") {
                        submitDate = "14/09/2025"
                        bonus = 1
                    }
                }
                """
        );

        CourseConfig config =
                ConfigDslParser.parse(file);

        assertEquals(
                1,
                config.getSubmissions().size()
        );

        assertEquals(
                "agrentseva",
                config.getSubmissions()
                        .getFirst()
                        .getStudentId()
        );

        assertEquals(
                "1.1.1",
                config.getSubmissions()
                        .getFirst()
                        .getTaskId()
        );

        assertEquals(
                1,
                config.getSubmissions()
                        .getFirst()
                        .getBonus()
        );
    }

    @Test
    void parseCheckpoint() throws Exception {

        Path file =
                tempDir.resolve("config.groovy");

        Files.writeString(
                file,
                """
                checkpoints {
                    checkpoint("Checkpoint 1") {
                        date = "01/03/2026"
                    }
                }
                """
        );

        CourseConfig config =
                ConfigDslParser.parse(file);

        assertEquals(
                1,
                config.getCheckpoints().size()
        );

        assertEquals(
                "Checkpoint 1",
                config.getCheckpoints()
                        .getFirst()
                        .getName()
        );
    }

    @Test
    void parseSettings() throws Exception {

        Path file =
                tempDir.resolve("config.groovy");

        Files.writeString(
                file,
                """
                settings {
                    softDeadlinePenalty = 0.5
                    maxBonus = 2
                    testTimeoutSeconds = 60
                }
                """
        );

        CourseConfig config =
                ConfigDslParser.parse(file);

        assertEquals(
                0.5,
                config.getSettings()
                        .getSoftDeadlinePenalty()
        );

        assertEquals(
                2,
                config.getSettings()
                        .getMaxBonus()
        );

        assertEquals(
                60,
                config.getSettings()
                        .getTestTimeoutSeconds()
        );
    }

    @Test
    void includeFileWorks() throws Exception {

        Path included =
                tempDir.resolve("included.groovy");

        Files.writeString(
                included,
                """
                tasks {
                    task("1.1.1") {
                        name = "Included task"
                        maxScore = 1
                    }
                }
                """
        );

        Path main =
                tempDir.resolve("main.groovy");

        Files.writeString(
                main,
                """
                include("included.groovy")
                """
        );

        CourseConfig config =
                ConfigDslParser.parse(main);

        assertEquals(
                1,
                config.getTasks().size()
        );

        assertEquals(
                "Included task",
                config.getTasks()
                        .getFirst()
                        .getName()
        );
    }

    @Test
    void parseMissingFileThrowsException() {

        Path file =
                tempDir.resolve("missing.groovy");

        assertThrows(
                IOException.class,
                () -> ConfigDslParser.parse(file)
        );
    }
}