package ru.nsu.ga.grentseva.checker.model;

public class SubmissionResult {

    private boolean compileSuccess;
    private boolean javadocPassed;
    private boolean stylePassed;

    private int styleErrors;

    private int testsPassed;
    private int testsFailed;
    private int testsSkipped;

    private double bonus;
    private double finalScore;

    public boolean isCompileSuccess() {
        return compileSuccess;
    }

    public void setCompileSuccess(boolean compileSuccess) {
        this.compileSuccess = compileSuccess;
    }

    public boolean isJavadocPassed() {
        return javadocPassed;
    }

    public void setJavadocPassed(boolean javadocPassed) {
        this.javadocPassed = javadocPassed;
    }

    public boolean isStylePassed() {
        return stylePassed;
    }

    public void setStylePassed(boolean stylePassed) {
        this.stylePassed = stylePassed;
    }

    public int getStyleErrors() {
        return styleErrors;
    }

    public void setStyleErrors(int styleErrors) {
        this.styleErrors = styleErrors;
    }

    public int getTestsPassed() {
        return testsPassed;
    }

    public void setTestsPassed(int testsPassed) {
        this.testsPassed = testsPassed;
    }

    public int getTestsFailed() {
        return testsFailed;
    }

    public void setTestsFailed(int testsFailed) {
        this.testsFailed = testsFailed;
    }

    public int getTestsSkipped() {
        return testsSkipped;
    }

    public void setTestsSkipped(int testsSkipped) {
        this.testsSkipped = testsSkipped;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }
}