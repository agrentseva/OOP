package ru.nsu.ga.grentseva.checker.model;

public class Settings {
    private double penalty = 0.5;
    private double maxBonus = 1.0;
    private int testTimeoutSeconds = 60;

    public double getPenalty() { return penalty; }

    public void setPenalty(double penalty) { this.penalty = penalty; }

    public double getMaxBonus() { return maxBonus; }

    public void setMaxBonus(double maxBonus) { this.maxBonus = maxBonus; }

    public int getTestTimeoutSeconds() { return testTimeoutSeconds; }

    public void setTestTimeoutSeconds(int testTimeoutSeconds) { this.testTimeoutSeconds = testTimeoutSeconds; }
}