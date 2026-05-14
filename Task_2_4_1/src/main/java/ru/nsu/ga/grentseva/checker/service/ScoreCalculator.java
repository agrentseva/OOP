package ru.nsu.ga.grentseva.checker.service;

import ru.nsu.ga.grentseva.checker.model.Settings;
import ru.nsu.ga.grentseva.checker.model.Submission;
import ru.nsu.ga.grentseva.checker.model.Task;

import java.time.LocalDate;

public class ScoreCalculator {

    public double calculate(Task task, Submission submission, int passedTests,
                            int failedTests, int skippedTests, Settings settings) {

        if (passedTests == 0 && failedTests == 0 && skippedTests == 0) {
            return 0.0;
        }

        int totalTests = passedTests + failedTests + skippedTests;
        if (totalTests == 0) {
            return 0.0;
        }

        double successRate = (double) passedTests / totalTests;

        double score = task.getMaxScore() * successRate;

        LocalDate softDeadline = task.getSoftDeadline();

        LocalDate submitDate = submission.getSubmitDate();

        if (softDeadline != null && submitDate != null && submitDate.isAfter(softDeadline)) {
            score -= settings.getSoftDeadlinePenalty();
        }

        score += submission.getBonus();
        if (score < 0) {
            score = 0;
        }
        
        double maxScore = task.getMaxScore() + settings.getMaxBonus();
        if (score > maxScore) {
            score = maxScore;
        }

        return score;
    }
}