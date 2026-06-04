package ru.nsu.ga.grentseva.checker.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class SubmissionTest {

    @Test
    void emptyConstructorCreatesObject() {
        Submission submission = new Submission();

        assertNotNull(submission);
    }

    @Test
    void constructorSetsFields() {
        LocalDate date = LocalDate.of(2026, 4, 20);
        Submission submission = new Submission("agrentseva", "2.4.1", date, 1.5);

        assertEquals("agrentseva", submission.getStudentId());
        assertEquals("2.4.1", submission.getTaskId());
        assertEquals(date, submission.getSubmitDate());
        assertEquals(1.5, submission.getBonus());
    }

    @Test
    void settersWorkCorrectly() {
        Submission submission = new Submission();
        LocalDate date = LocalDate.of(2025, 9, 15);

        submission.setStudentId("VlanAni");
        submission.setTaskId("1.1.2");
        submission.setSubmitDate(date);
        submission.setBonus(2);

        assertEquals("VlanAni", submission.getStudentId());
        assertEquals("1.1.2", submission.getTaskId());
        assertEquals(date, submission.getSubmitDate());
        assertEquals(2, submission.getBonus());
    }

    @Test
    void equalsReturnsTrueForSameStudentAndTask() {
        Submission first = new Submission("agrentseva", "2.2.1", LocalDate.now(), 0);
        Submission second = new Submission("agrentseva", "2.2.1", LocalDate.now().plusDays(1), 5);

        assertEquals(first, second);
    }

    @Test
    void equalsReturnsFalseForDifferentStudents() {
        Submission first = new Submission("agrentseva", "2.2.1", LocalDate.now(), 0);
        Submission second = new Submission("VlanAni", "2.2.1", LocalDate.now(), 0);

        assertNotEquals(first, second);
    }

    @Test
    void equalsReturnsFalseForDifferentTasks() {
        Submission first = new Submission("agrentseva", "1.1.1", LocalDate.now(), 0);
        Submission second = new Submission("agrentseva", "1.1.2", LocalDate.now(), 0);

        assertNotEquals(first, second);
    }

    @Test
    void hashCodeSameForEqualObjects() {
        Submission first = new Submission("agrentseva", "2.3.1", LocalDate.now(), 0);
        Submission second = new Submission("agrentseva", "2.3.1", LocalDate.now().plusDays(2), 10);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void toStringContainsFields() {
        Submission submission = new Submission("agrentseva", "2.4.1", LocalDate.of(2026, 4, 20), 1);
        String text = submission.toString();

        assertTrue(text.contains("agrentseva"));
        assertTrue(text.contains("2.4.1"));
        assertTrue(text.contains("2026-04-20"));
    }
}