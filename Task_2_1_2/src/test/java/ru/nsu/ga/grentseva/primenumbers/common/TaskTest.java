package ru.nsu.ga.grentseva.primenumbers.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void shouldStoreTaskDataCorrectly() {
        int[] numbers = {2, 3, 5};

        Task task = new Task(10, numbers);

        assertEquals(10, task.getTaskId());
        assertArrayEquals(numbers, task.getNumbers());
    }
}