package ru.nsu.ga.grentseva.checker.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubmissionResultTest {

    @Test
    void defaultValuesAreCorrect() {

        SubmissionResult result =
                new SubmissionResult();

        assertFalse(result.isCompiled());

        assertFalse(result.isJavadocGenerated());

        assertFalse(result.isStylePassed());

        assertEquals(
                0,
                result.getStyleErrors()
        );

        assertEquals(
                0,
                result.getTestsPassed()
        );

        assertEquals(
                0,
                result.getTestsFailed()
        );

        assertEquals(
                0,
                result.getTestsSkipped()
        );

        assertEquals(
                0,
                result.getFinalScore()
        );

        assertNull(
                result.getErrorMessage()
        );
    }

    @Test
    void compiledSetterWorks() {

        SubmissionResult result =
                new SubmissionResult();

        result.setCompiled(true);

        assertTrue(result.isCompiled());
    }

    @Test
    void javadocSetterWorks() {

        SubmissionResult result =
                new SubmissionResult();

        result.setJavadocGenerated(true);

        assertTrue(
                result.isJavadocGenerated()
        );
    }

    @Test
    void stylePassedSetterWorks() {

        SubmissionResult result =
                new SubmissionResult();

        result.setStylePassed(true);

        assertTrue(
                result.isStylePassed()
        );
    }

    @Test
    void styleErrorsSetterWorks() {

        SubmissionResult result =
                new SubmissionResult();

        result.setStyleErrors(5);

        assertEquals(
                5,
                result.getStyleErrors()
        );
    }

    @Test
    void testsPassedSetterWorks() {

        SubmissionResult result =
                new SubmissionResult();

        result.setTestsPassed(10);

        assertEquals(
                10,
                result.getTestsPassed()
        );
    }

    @Test
    void testsFailedSetterWorks() {

        SubmissionResult result =
                new SubmissionResult();

        result.setTestsFailed(2);

        assertEquals(
                2,
                result.getTestsFailed()
        );
    }

    @Test
    void testsSkippedSetterWorks() {

        SubmissionResult result =
                new SubmissionResult();

        result.setTestsSkipped(1);

        assertEquals(
                1,
                result.getTestsSkipped()
        );
    }

    @Test
    void finalScoreSetterWorks() {

        SubmissionResult result =
                new SubmissionResult();

        result.setFinalScore(9.5);

        assertEquals(
                9.5,
                result.getFinalScore()
        );
    }

    @Test
    void errorMessageSetterWorks() {

        SubmissionResult result =
                new SubmissionResult();

        result.setErrorMessage(
                "Compilation error"
        );

        assertEquals(
                "Compilation error",
                result.getErrorMessage()
        );
    }
}