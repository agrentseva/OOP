package ru.nsu.ga.grentseva.primenumbers.master;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.primenumbers.common.Task;
import ru.nsu.ga.grentseva.primenumbers.common.TaskStatus;
import ru.nsu.ga.grentseva.primenumbers.worker.WorkerNode;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTaskTest {
    private static final int TEST_PORT = 5005;
    private WorkerNode workerNode;

    @BeforeEach
    void startWorker() {
        workerNode = new WorkerNode(TEST_PORT);

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
        WorkerInfo workerInfo = new WorkerInfo("localhost", TEST_PORT);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task);

        AtomicBoolean foundComposite = new AtomicBoolean(false);
        WorkerTask workerTask = new WorkerTask(task, workerInfo, taskManager, foundComposite);

        workerTask.run();

        assertNotNull(workerTask.getResult());
        assertEquals(TaskStatus.COMPLETED, workerTask.getResult().getStatus());
        assertFalse(workerTask.getResult().hasNonPrime());
        assertFalse(foundComposite.get());
    }

    @Test
    void shouldDetectCompositeNumber() {
        Task task = new Task(2, new int[]{2, 4, 5});
        WorkerInfo workerInfo = new WorkerInfo("localhost", TEST_PORT);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task);

        AtomicBoolean foundComposite = new AtomicBoolean(false);
        WorkerTask workerTask = new WorkerTask(task, workerInfo, taskManager, foundComposite);

        workerTask.run();

        assertNotNull(workerTask.getResult());
        assertEquals(TaskStatus.COMPLETED, workerTask.getResult().getStatus());
        assertTrue(workerTask.getResult().hasNonPrime());
        assertTrue(foundComposite.get());
    }

    @Test
    void shouldFailWhenWorkerUnavailable() {
        Task task = new Task(3, new int[]{2, 3, 5});
        WorkerInfo workerInfo = new WorkerInfo("localhost", 5999);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task);

        AtomicBoolean foundComposite = new AtomicBoolean(false);
        WorkerTask workerTask = new WorkerTask(task, workerInfo, taskManager, foundComposite);

        workerTask.run();

        assertNotNull(workerTask.getResult());
        assertEquals(TaskStatus.FAILED, workerTask.getResult().getStatus());
        assertFalse(foundComposite.get());
    }

    @Test
    void shouldHandleHeartbeatDuringLongTask() {
        int[] numbers = new int[2_000_000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.MAX_VALUE;
        }

        Task task = new Task(4, numbers);
        WorkerInfo workerInfo = new WorkerInfo("localhost", TEST_PORT);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task);

        AtomicBoolean foundComposite = new AtomicBoolean(false);
        WorkerTask workerTask = new WorkerTask(task, workerInfo, taskManager, foundComposite);

        long startTime = System.currentTimeMillis();
        workerTask.run();
        long endTime = System.currentTimeMillis();

        assertNotNull(workerTask.getResult());
        assertEquals(TaskStatus.COMPLETED, workerTask.getResult().getStatus());
        assertFalse(workerTask.getResult().hasNonPrime());
        assertFalse(foundComposite.get());
        assertTrue(endTime - startTime > 1000, "Task finished too quickly, heartbeat probably not tested");
    }
}