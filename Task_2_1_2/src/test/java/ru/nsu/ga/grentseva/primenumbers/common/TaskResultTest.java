package ru.nsu.ga.grentseva.primenumbers.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskResultTest {

    @Test
    void shouldStoreResultDataCorrectly() {
        TaskResult result = new TaskResult(5, true, TaskStatus.COMPLETED);

        assertEquals(5, result.getTaskId());
        assertTrue(result.hasNonPrime());
        assertEquals(TaskStatus.COMPLETED, result.getStatus());
    }
}