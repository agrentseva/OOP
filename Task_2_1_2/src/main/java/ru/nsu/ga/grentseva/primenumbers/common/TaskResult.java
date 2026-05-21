package ru.nsu.ga.grentseva.primenumbers.common;

import java.io.Serializable;

public class TaskResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int taskId;
    private final boolean hasNonPrime;
    private final TaskStatus status;

    public TaskResult(
            int taskId,
            boolean hasNonPrime,
            TaskStatus status) {

        this.taskId = taskId;
        this.hasNonPrime = hasNonPrime;
        this.status = status;
    }

    public boolean hasNonPrime() {
        return hasNonPrime;
    }

    public int getTaskId() {
        return taskId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "taskId=" + taskId +
                ", hasNonPrime=" + hasNonPrime +
                ", status=" + status +
                '}';
    }
}
