package ru.nsu.ga.grentseva.checker.model;

public class Settings {

    private double softDeadlinePenalty = 0.5;

    private double maxBonus = 1.0;

    private int testTimeoutSeconds = 60;

    public Settings() {
    }

    public double getSoftDeadlinePenalty() {
        return softDeadlinePenalty;
    }

    public void setSoftDeadlinePenalty(double softDeadlinePenalty) {
        this.softDeadlinePenalty = softDeadlinePenalty;
    }

    public double getMaxBonus() {
        return maxBonus;
    }

    public void setMaxBonus(double maxBonus) {
        this.maxBonus = maxBonus;
    }

    public int getTestTimeoutSeconds() {
        return testTimeoutSeconds;
    }

    public void setTestTimeoutSeconds(int testTimeoutSeconds) {
        this.testTimeoutSeconds = testTimeoutSeconds;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "softDeadlinePenalty=" + softDeadlinePenalty +
                ", maxBonus=" + maxBonus +
                ", testTimeoutSeconds=" + testTimeoutSeconds +
                '}';
    }
}