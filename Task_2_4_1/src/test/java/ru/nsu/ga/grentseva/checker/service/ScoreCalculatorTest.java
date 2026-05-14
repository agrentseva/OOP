package ru.nsu.ga.grentseva.checker.service;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.checker.model.Settings;
import ru.nsu.ga.grentseva.checker.model.Submission;
import ru.nsu.ga.grentseva.checker.model.Task;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ScoreCalculatorTest {

    @Test
    void returnsZeroWhenNoTestsExecuted() {
        ScoreCalculator calculator = new ScoreCalculator();
        Task task = createTask();
        Submission submission = createSubmission();
        Settings settings = new Settings();

        double score = calculator.calculate(task, submission, 0, 0, 0, settings);

        assertEquals(0, score);
    }

    @Test
    void returnsFullScoreWhenAllTestsPassed() {
        ScoreCalculator calculator = new ScoreCalculator();
        Task task = createTask();
        Submission submission = createSubmission();
        Settings settings = new Settings();

        double score = calculator.calculate(task, submission, 10, 0, 0, settings);

        assertEquals(10, score);
    }

    @Test
    void returnsHalfScoreWhenHalfTestsPassed() {
        ScoreCalculator calculator = new ScoreCalculator();
        Task task = createTask();
        Submission submission = createSubmission();
        Settings settings = new Settings();

        double score = calculator.calculate(task, submission, 5, 5, 0, settings);

        assertEquals(5, score);
    }

    @Test
    void appliesSoftDeadlinePenalty() {
        ScoreCalculator calculator = new ScoreCalculator();
        Task task = createTask();
        task.setSoftDeadline(LocalDate.of(2025, 9, 10));

        Submission submission = createSubmission();
        submission.setSubmitDate(LocalDate.of(2025, 9, 20));

        Settings settings = new Settings();
        settings.setSoftDeadlinePenalty(1);

        double score = calculator.calculate(task, submission, 10, 0, 0, settings);

        assertEquals(9, score);
    }

    @Test
    void addsBonusToScore() {
        ScoreCalculator calculator = new ScoreCalculator();
        Task task = createTask();
        Submission submission = createSubmission();
        submission.setBonus(2);

        Settings settings = new Settings();
        settings.setMaxBonus(5);

        double score = calculator.calculate(task, submission, 10, 0, 0, settings);

        assertEquals(12, score);
    }

    @Test
    void scoreCannotBeNegative() {
        ScoreCalculator calculator = new ScoreCalculator();
        Task task = createTask();
        task.setSoftDeadline(LocalDate.of(2025, 9, 1));

        Submission submission = createSubmission();
        submission.setSubmitDate(LocalDate.of(2025, 9, 20));

        Settings settings = new Settings();
        settings.setSoftDeadlinePenalty(100);

        double score = calculator.calculate(task, submission, 1, 9, 0, settings);

        assertEquals(0, score);
    }

    @Test
    void scoreCannotExceedMaximum() {
        ScoreCalculator calculator = new ScoreCalculator();
        Task task = createTask();
        Submission submission = createSubmission();
        submission.setBonus(100);

        Settings settings = new Settings();
        settings.setMaxBonus(3);

        double score = calculator.calculate(task, submission, 10, 0, 0, settings);

        assertEquals(13, score);
    }

    @Test
    void skippedTestsAffectSuccessRate() {
        ScoreCalculator calculator = new ScoreCalculator();
        Task task = createTask();
        Submission submission = createSubmission();
        Settings settings = new Settings();

        double score = calculator.calculate(task, submission, 5, 0, 5, settings);

        assertEquals(5, score);
    }

    private Task createTask() {
        Task task = new Task();
        task.setId("2.4.1");
        task.setName("Checker");
        task.setMaxScore(10);
        return task;
    }

    private Submission createSubmission() {
        Submission submission = new Submission();
        submission.setStudentId("agrentseva");
        submission.setTaskId("2.4.1");
        submission.setSubmitDate(LocalDate.of(2025, 9, 1));
        submission.setBonus(0);
        return submission;
    }
}