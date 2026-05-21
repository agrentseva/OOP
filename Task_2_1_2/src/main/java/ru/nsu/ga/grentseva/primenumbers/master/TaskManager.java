package ru.nsu.ga.grentseva.primenumbers.master;

import ru.nsu.ga.grentseva.primenumbers.common.Task;
import ru.nsu.ga.grentseva.primenumbers.common.TaskStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskManager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, TaskStatus> taskStatuses;

    public TaskManager() {
        tasks = new ConcurrentHashMap<>();
        taskStatuses = new ConcurrentHashMap<>();
    }

    public void addTask(Task task) {
        tasks.put(task.getTaskId(), task);
        taskStatuses.put(task.getTaskId(), TaskStatus.CREATED);
    }

    public void setTaskStatus(int taskId, TaskStatus status) {
        taskStatuses.put(taskId, status);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        List<Task> result = new ArrayList<>();

        for (Map.Entry<Integer, TaskStatus> entry : taskStatuses.entrySet()) {
            if (entry.getValue() == status) {
                result.add(tasks.get(entry.getKey()));
            }
        }

        return result;
    }

    public Map<Integer, TaskStatus> getTaskStatuses() {
        return new ConcurrentHashMap<>(taskStatuses);
    }
}