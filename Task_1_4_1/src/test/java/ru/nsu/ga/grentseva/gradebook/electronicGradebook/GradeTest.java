package ru.nsu.ga.grentseva.gradebook.electronicGradebook;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GradeTest {

    @Test
    void testValidGradeCreation() {
        Grade g1 = new Grade("Math", GradeType.EXAM, 5, 1);
        assertEquals("Math", g1.getSubject());
        assertEquals(GradeType.EXAM, g1.getType());
        assertEquals(5, g1.getValue());
        assertEquals(1, g1.getSemester());

        Grade g2 = new Grade("Physics", GradeType.CREDIT, 2, 2);
        assertEquals(2, g2.getValue());
    }

    @Test
    void testInvalidCreditValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Grade("Chemistry", GradeType.CREDIT, 3, 1);
        });
        assertTrue(exception.getMessage().contains("Для зачета значение оценки должно быть 5 (зачтено) или 2 (не зачтено)"));
    }

    @Test
    void testInvalidExamValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Grade("Biology", GradeType.EXAM, 6, 1);
        });
        assertTrue(exception.getMessage().contains("значение оценки должно быть от 2 до 5"));
    }

    @Test
    void testInvalidSemester() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Grade("History", GradeType.EXAM, 4, 0);
        });
        assertTrue(exception.getMessage().contains("Номер семестра должен быть от 1 до 8."));
    }
}
