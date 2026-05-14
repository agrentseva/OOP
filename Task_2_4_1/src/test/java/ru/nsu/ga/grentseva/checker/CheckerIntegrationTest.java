package ru.nsu.ga.grentseva.checker.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.ga.grentseva.checker.dsl.ConfigDslParser;
import ru.nsu.ga.grentseva.checker.model.CourseConfig;
import ru.nsu.ga.grentseva.checker.model.Submission;
import ru.nsu.ga.grentseva.checker.model.SubmissionResult;
import ru.nsu.ga.grentseva.checker.service.pipeline.PipelineRunner;
import ru.nsu.ga.grentseva.checker.service.report.HTMLReportGenerator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CheckerIntegrationTest {

    @TempDir
    Path tempDir;

    @Test
    void fullPipelineCreatesHtmlReport() throws Exception {
        Path repository = createFakeRepository();
        Path configFile = createConfigFile(repository);

        CourseConfig config = ConfigDslParser.parse(configFile);
        PipelineRunner pipelineRunner = new PipelineRunner();
        Map<Submission, SubmissionResult> results = pipelineRunner.run(config);

        Path report = tempDir.resolve("report.html");
        HTMLReportGenerator generator = new HTMLReportGenerator();

        generator.generate(config, results, report.toString());

        assertTrue(Files.exists(report));
        String html = Files.readString(report);
        assertTrue(html.contains("Integration Student"));
        assertTrue(html.contains("Integration task"));
    }

    private Path createFakeRepository() throws Exception {
        Path repository = tempDir.resolve("fake-repo");
        Path taskDir = repository.resolve("Task_1_1_1");

        Files.createDirectories(taskDir.resolve("build/test-results/test"));

        Files.writeString(taskDir.resolve("gradlew.bat"), """
                @echo off
                exit /B 0
                """);

        Files.writeString(taskDir.resolve("gradlew"), """
                #!/bin/sh
                exit 0
                """);

        Files.writeString(taskDir.resolve("build/test-results/test/TEST-test.xml"), """
                <testsuite tests="10" failures="0" skipped="0">
                </testsuite>
                """);

        return repository;
    }

    private Path createConfigFile(Path repository) throws Exception {
        Path config = tempDir.resolve("config.groovy");
        Files.writeString(config, """
                tasks {
                    task("1.1.1") {
                        name = "Integration task"
                        maxScore = 1
                    }
                }
                groups {
                    group("24214") {
                        student("integration") {
                            name = "Integration Student"
                            repositoryUrl = "%s"
                        }
                    }
                }
                submissions {
                    submission("integration", "1.1.1") { bonus = 0 }
                }
                settings {
                    softDeadlinePenalty = 0.5
                    maxBonus = 1
                    testTimeoutSeconds = 10
                }
                """.formatted(repository.toAbsolutePath().toString().replace("\\", "\\\\")));

        return config;
    }
}