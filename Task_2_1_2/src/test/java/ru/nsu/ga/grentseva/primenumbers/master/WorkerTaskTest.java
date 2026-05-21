package ru.nsu.ga.grentseva.primenumbers.master;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.primenumbers.common.Task;
import ru.nsu.ga.grentseva.primenumbers.common.TaskStatus;
import ru.nsu.ga.grentseva.primenumbers.worker.WorkerNode;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTaskTest {
    private WorkerNode workerNode;

    @BeforeEach
    void startWorker() {
        workerNode = new WorkerNode(5005);
        Thread workerThread = new Thread(workerNode::start);
        workerThread.setDaemon(true);
        workerThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    void stopWorker() {
        workerNode.shutdown();
    }

    @Test
    void shouldCompleteTaskSuccessfully() {
        Task task = new Task(1, new int[]{2, 3, 5, 7});
        WorkerInfo workerInfo = new WorkerInfo("localhost", 5005);
        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task);

        WorkerTask workerTask = new WorkerTask(task, workerInfo, taskManager);
        workerTask.run();

        assertNotNull(workerTask.getResult());
        assertEquals(TaskStatus.COMPLETED, workerTask.getResult().getStatus());
        assertFalse(workerTask.getResult().hasNonPrime());
    }

    @Test
    void shouldDetectCompositeNumber() {
        Task task = new Task(2, new int[]{2, 4, 5});
        WorkerInfo workerInfo = new WorkerInfo("localhost", 5005);
        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task);

        WorkerTask workerTask = new WorkerTask(task, workerInfo, taskManager);
        workerTask.run();

        assertTrue(workerTask.getResult().hasNonPrime());
    }

    @Test
    void shouldFailWhenWorkerUnavailable() {
        Task task = new Task(3, new int[]{2, 3, 5});
        WorkerInfo workerInfo = new WorkerInfo("localhost", 5999);
        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task);

        WorkerTask workerTask = new WorkerTask(task, workerInfo, taskManager);
        workerTask.run();

        assertEquals(TaskStatus.FAILED, workerTask.getResult().getStatus());
    }
}