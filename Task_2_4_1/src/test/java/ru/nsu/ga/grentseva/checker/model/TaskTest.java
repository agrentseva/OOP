package ru.nsu.ga.grentseva.checker.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void emptyConstructorCreatesObject() {
        Task task = new Task();

        assertNotNull(task);
    }

    @Test
    void constructorSetsFields() {
        LocalDate soft = LocalDate.of(2026, 4, 25);
        LocalDate hard = LocalDate.of(2026, 5, 16);
        Task task = new Task("2.4.1", "Checker", 10, soft, hard);

        assertEquals("2.4.1", task.getId());
        assertEquals("Checker", task.getName());
        assertEquals(10, task.getMaxScore());
        assertEquals(soft, task.getSoftDeadline());
        assertEquals(hard, task.getHardDeadline());
    }

    @Test
    void settersWorkCorrectly() {
        Task task = new Task();
        LocalDate soft = LocalDate.of(2025, 9, 20);
        LocalDate hard = LocalDate.of(2025, 9, 27);

        task.setId("1.1.2");
        task.setName("Blackjack");
        task.setMaxScore(2);
        task.setSoftDeadline(soft);
        task.setHardDeadline(hard);

        assertEquals("1.1.2", task.getId());
        assertEquals("Blackjack", task.getName());
        assertEquals(2, task.getMaxScore());
        assertEquals(soft, task.getSoftDeadline());
        assertEquals(hard, task.getHardDeadline());
    }

    @Test
    void equalsReturnsTrueForSameIds() {
        Task first = new Task("2.2.1", "Pizza", 1, null, null);
        Task second = new Task("2.2.1", "Another", 5, null, null);

        assertEquals(first, second);
    }

    @Test
    void equalsReturnsFalseForDifferentIds() {
        Task first = new Task("1.1.1", "Heap", 1, null, null);
        Task second = new Task("1.1.2", "Blackjack", 2, null, null);

        assertNotEquals(first, second);
    }

    @Test
    void hashCodeSameForEqualObjects() {
        Task first = new Task("2.3.1", "Snake", 1, null, null);
        Task second = new Task("2.3.1", "Another Snake", 5, null, null);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void toStringContainsFields() {
        Task task = new Task("2.4.1", "Checker", 10, LocalDate.of(2026, 4, 25), LocalDate.of(2026, 5, 16));
        String text = task.toString();

        assertTrue(text.contains("2.4.1"));
        assertTrue(text.contains("Checker"));
        assertTrue(text.contains("10"));
        assertTrue(text.contains("2026-04-25"));
    }
}