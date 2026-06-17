package ru.nsu.ga.grentseva.primenumbers.master;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.primenumbers.common.Task;
import ru.nsu.ga.grentseva.primenumbers.common.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    @Test
    void shouldAddTaskWithCreatedStatus() {
        TaskManager taskManager = new TaskManager();
        Task task = new Task(1, new int[]{2, 3, 5});

        taskManager.addTask(task);

        assertEquals(TaskStatus.CREATED, taskManager.getTaskStatuses().get(1));
    }

    @Test
    void shouldUpdateTaskStatus() {
        TaskManager taskManager = new TaskManager();
        Task task = new Task(1, new int[]{2, 3, 5});

        taskManager.addTask(task);
        taskManager.setTaskStatus(1, TaskStatus.COMPLETED);

        assertEquals(TaskStatus.COMPLETED, taskManager.getTaskStatuses().get(1));
    }

    @Test
    void shouldReturnTasksByStatus() {
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task(1, new int[]{2, 3});
        Task task2 = new Task(2, new int[]{4, 5});

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.setTaskStatus(2, TaskStatus.FAILED);

        List<Task> failedTasks = taskManager.getTasksByStatus(TaskStatus.FAILED);

        assertEquals(1, failedTasks.size());
        assertEquals(2, failedTasks.get(0).getTaskId());
    }
}