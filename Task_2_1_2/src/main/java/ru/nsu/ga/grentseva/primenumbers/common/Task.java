package ru.nsu.ga.grentseva.primenumbers.common;

import java.io.Serializable;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int taskId;
    private final int[] numbers;

    public Task(int taskId, int[] numbers) {
        this.taskId = taskId;
        this.numbers = numbers;
    }

    public int getTaskId() {
        return taskId;
    }

    public int[] getNumbers() {
        return numbers;
    }
}