package ru.nsu.ga.grentseva.primenumbers.common;

import java.io.Serializable;

public class WorkerMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private final MessageType type;
    private final TaskResult result;

    public WorkerMessage(MessageType type, TaskResult result) {
        this.type = type;
        this.result = result;
    }

    public MessageType getType() {
        return type;
    }

    public TaskResult getResult() {
        return result;
    }
}