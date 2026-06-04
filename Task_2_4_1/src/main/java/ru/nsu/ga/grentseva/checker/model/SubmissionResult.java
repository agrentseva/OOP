package ru.nsu.ga.grentseva.checker.model;

public class SubmissionResult {

    private boolean compiled;
    private boolean javadocGenerated;
    private boolean stylePassed;
    private int styleErrors;
    private int testsPassed;
    private int testsFailed;
    private int testsSkipped;
    private double finalScore;
    private String errorMessage;

    public boolean isCompiled() {
        return compiled;
    }

    public void setCompiled(boolean compiled) {
        this.compiled = compiled;
    }

    public boolean isJavadocGenerated() {
        return javadocGenerated;
    }

    public void setJavadocGenerated(boolean javadocGenerated) {
        this.javadocGenerated = javadocGenerated;
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

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}