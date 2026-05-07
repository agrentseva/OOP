package ru.nsu.ga.grentseva.checker.dsl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.ga.grentseva.checker.model.CourseConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ConfigDslParserTest {

    @TempDir
    Path tempDir;

    @Test
    void parseValidConfig() throws Exception {

        Path file =
                tempDir.resolve("config.groovy");

        Files.writeString(
                file,
                """
                tasks {
                    task("1.1.1") {
                        name = "Test task"
                        maxScore = 1
                    }
                }
                """
        );

        CourseConfig config =
                ConfigDslParser.parse(file);

        assertNotNull(config);

        assertEquals(
                1,
                config.getTasks().size()
        );

        assertEquals(
                "1.1.1",
                config.getTasks().getFirst().getId()
        );

        assertEquals(
                "Test task",
                config.getTasks().getFirst().getName()
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

    @Test
    void loadFileAddsTaskToExistingConfig() throws Exception {

        Path file =
                tempDir.resolve("config.groovy");

        Files.writeString(
                file,
                """
                tasks {
                    task("2.2.1") {
                        name = "Pizza"
                        maxScore = 2
                    }
                }
                """
        );

        CourseConfig config =
                new CourseConfig();

        ConfigDslParser.loadFile(
                file,
                config
        );

        assertEquals(
                1,
                config.getTasks().size()
        );

        assertEquals(
                "2.2.1",
                config.getTasks().getFirst().getId()
        );
    }

    @Test
    void loadFileWithInvalidPathThrowsException() {

        Path file =
                tempDir.resolve("invalid.groovy");

        CourseConfig config =
                new CourseConfig();

        assertThrows(
                IOException.class,
                () -> ConfigDslParser.loadFile(file, config)
        );
    }
}