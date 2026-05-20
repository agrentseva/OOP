package ru.nsu.ga.grentseva.checker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckerIntegrationTest {

    private final File reportFile = new File("report.html");

    @AfterEach
    void cleanUp() throws Exception {
        Files.deleteIfExists(reportFile.toPath());
        Files.deleteIfExists(Path.of("integration-test.groovy"));
    }

    @Test
    void mainRunsFullPipelineAndCreatesReport() throws Exception {
        Path config = Path.of("integration-test.groovy");

        Files.writeString(config, """
                tasks {
                    task("1.1.1") {
                        name = "Пирамидальная сортировка"
                        maxScore = 1
                        softDeadline = "13/09/2025"
                        hardDeadline = "13/09/2025"
                    }
                }

                groups {
                    group("24214") {

                        student("agrentseva") {
                            name = "Гренцева Алина Олеговна"
                            repositoryUrl = "https://github.com/agrentseva/OOP.git"
                        }

                        student("VlanAni") {
                            name = "Анисимов Владимир Сергеевич"
                            repositoryUrl = "https://github.com/VlanAni/OOP.git"
                        }
                    }
                }

                submissions {
                    submission("agrentseva", "1.1.1") {
                        bonus = 0
                    }

                    submission("VlanAni", "1.1.1") {
                        bonus = 0
                    }
                }

                settings {
                    softDeadlinePenalty = 0.5
                    maxBonus = 1.0
                    testTimeoutSeconds = 30
                }
                """);

        assertDoesNotThrow(() -> Main.main(new String[]{"check", config.toString()}));

        assertTrue(reportFile.exists());

        String html = Files.readString(reportFile.toPath());
        assertTrue(html.contains("Гренцева Алина Олеговна"));
        assertTrue(html.contains("Анисимов Владимир Сергеевич"));
        assertTrue(html.contains("Пирамидальная сортировка"));
    }
}