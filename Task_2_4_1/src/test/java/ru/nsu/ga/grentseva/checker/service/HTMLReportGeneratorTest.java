package ru.nsu.ga.grentseva.checker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.ga.grentseva.checker.model.*;
import ru.nsu.ga.grentseva.checker.service.report.HTMLReportGenerator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HTMLReportGeneratorTest {

    @TempDir
    Path tempDir;

    @Test
    void generateCreatesHtmlFile() throws Exception {
        CourseConfig config = createConfig();
        Map<Submission, SubmissionResult> results = createResults(config);
        Path output = tempDir.resolve("report.html");

        HTMLReportGenerator generator = new HTMLReportGenerator();
        generator.generate(config, results, output.toString());

        assertTrue(Files.exists(output));
    }

    @Test
    void generatedHtmlContainsStudentName() throws Exception {
        CourseConfig config = createConfig();
        Map<Submission, SubmissionResult> results = createResults(config);
        Path output = tempDir.resolve("report.html");

        new HTMLReportGenerator().generate(config, results, output.toString());

        String html = Files.readString(output);
        assertTrue(html.contains("Гренцева Алина"));
    }

    @Test
    void generatedHtmlContainsTaskName() throws Exception {
        CourseConfig config = createConfig();
        Map<Submission, SubmissionResult> results = createResults(config);
        Path output = tempDir.resolve("report.html");

        new HTMLReportGenerator().generate(config, results, output.toString());

        String html = Files.readString(output);
        assertTrue(html.contains("Checker"));
    }

    @Test
    void generatedHtmlContainsScore() throws Exception {
        CourseConfig config = createConfig();
        Map<Submission, SubmissionResult> results = createResults(config);
        Path output = tempDir.resolve("report.html");

        new HTMLReportGenerator().generate(config, results, output.toString());

        String html = Files.readString(output);
        assertTrue(html.contains("10.0"));
    }

    @Test
    void generatedHtmlContainsPassedStatus() throws Exception {
        CourseConfig config = createConfig();
        Map<Submission, SubmissionResult> results = createResults(config);
        Path output = tempDir.resolve("report.html");

        new HTMLReportGenerator().generate(config, results, output.toString());

        String html = Files.readString(output);
        assertTrue(html.contains("Passed"));
    }

    @Test
    void generatedHtmlContainsPercent() throws Exception {
        CourseConfig config = createConfig();
        Map<Submission, SubmissionResult> results = createResults(config);
        Path output = tempDir.resolve("report.html");

        new HTMLReportGenerator().generate(config, results, output.toString());

        String html = Files.readString(output);
        assertTrue(html.contains("%"));
    }

    private CourseConfig createConfig() {
        CourseConfig config = new CourseConfig();
        Task task = new Task(
                "2.4.1",
                "Checker",
                10,
                LocalDate.of(2026, 4, 25),
                LocalDate.of(2026, 5, 16)
        );
        config.addTask(task);

        Student student = new Student("agrentseva", "Гренцева Алина", "repo");
        Group group = new Group("24214");
        group.addStudent(student);
        config.addGroup(group);

        Submission submission = new Submission("agrentseva", "2.4.1", LocalDate.of(2026, 4, 20), 0);
        config.addSubmission(submission);

        return config;
    }

    private Map<Submission, SubmissionResult> createResults(CourseConfig config) {
        Map<Submission, SubmissionResult> results = new HashMap<>();
        Submission submission = config.getSubmissions().get(0);

        SubmissionResult result = new SubmissionResult();
        result.setCompiled(true);
        result.setJavadocGenerated(true);
        result.setStylePassed(true);
        result.setTestsPassed(10);
        result.setTestsFailed(0);
        result.setFinalScore(10);

        results.put(submission, result);
        return results;
    }

    @Test
    void generateWithEmptyResultsStillCreatesHtml() throws Exception {
        CourseConfig config = createConfig();
        Path output = tempDir.resolve("empty.html");
        HTMLReportGenerator generator = new HTMLReportGenerator();

        generator.generate(config, new HashMap<>(), output.toString());

        assertTrue(Files.exists(output));
        String html = Files.readString(output);
        assertNotNull(html);
    }
}