package ru.nsu.ga.grentseva.primenumbers.master;

import ru.nsu.ga.grentseva.primenumbers.common.DistributedLogger;
import ru.nsu.ga.grentseva.primenumbers.common.Task;
import ru.nsu.ga.grentseva.primenumbers.common.TaskResult;
import ru.nsu.ga.grentseva.primenumbers.common.TaskStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MasterServer {
    private static final int THREAD_POOL_SIZE = 10;

    private final List<WorkerInfo> workers;
    private final TaskManager taskManager;
    private final ExecutorService executorService;

    public MasterServer(List<WorkerInfo> workers) {
        if (workers.isEmpty()) {
            throw new IllegalStateException("No workers available");
        }
        this.workers = workers;
        this.taskManager = new TaskManager();
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public boolean hasNonPrime(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return false;
        }

        List<Task> tasks = splitTasks(numbers);
        List<Future<?>> futures = new ArrayList<>();
        List<WorkerTask> workerTasks = new ArrayList<>();

        for (Task task : tasks) {
            taskManager.addTask(task);
        }

        for (int i = 0; i < tasks.size(); i++) {
            WorkerInfo worker = workers.get(i % workers.size());
            WorkerTask workerTask = new WorkerTask(tasks.get(i), worker, taskManager);

            workerTasks.add(workerTask);
            Future<?> future = executorService.submit(workerTask);
            futures.add(future);
        }

        waitForTasks(futures);
        reassignFailedTasks();
        printTaskStatuses();

        for (WorkerTask workerTask : workerTasks) {
            TaskResult result = workerTask.getResult();

            if (result != null && result.getStatus() == TaskStatus.COMPLETED && result.hasNonPrime()) {
                DistributedLogger.info("Non-prime number found in task " + result.getTaskId());
                cancelAllTasks(futures);
                return true;
            }
        }

        return false;
    }

    private void waitForTasks(List<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                future.cancel(true);
                DistributedLogger.error("Task execution error: " + e.getMessage());
            }
        }
    }

    private void reassignFailedTasks() {
        List<Task> failedTasks = taskManager.getTasksByStatus(TaskStatus.FAILED);
        if (failedTasks.isEmpty()) {
            return;
        }

        DistributedLogger.info("Reassigning failed tasks...");

        for (Task failedTask : failedTasks) {
            for (WorkerInfo worker : workers) {
                WorkerTask retryTask = new WorkerTask(failedTask, worker, taskManager);
                Future<?> retryFuture = executorService.submit(retryTask);

                try {
                    retryFuture.get();
                } catch (Exception e) {
                    retryFuture.cancel(true);
                    DistributedLogger.error("Retry task failed: " + e.getMessage());
                }

                TaskResult result = retryTask.getResult();
                if (result != null && result.getStatus() == TaskStatus.COMPLETED) {
                    break;
                }
            }
        }
    }

    private void cancelAllTasks(List<Future<?>> futures) {
        for (Future<?> future : futures) {
            if (!future.isDone()) {
                future.cancel(true);
            }
        }
        DistributedLogger.info("Remaining tasks cancelled");
    }

    private void printTaskStatuses() {
        DistributedLogger.info("===== TASK STATES =====");
        for (Map.Entry<Integer, TaskStatus> entry : taskManager.getTaskStatuses().entrySet()) {
            DistributedLogger.info("Task " + entry.getKey() + " -> " + entry.getValue());
        }
        DistributedLogger.info("=======================");
    }

    private List<Task> splitTasks(int[] numbers) {
        List<Task> tasks = new ArrayList<>();
        int workersCount = Math.min(workers.size(), numbers.length);
        int blockSize = numbers.length / workersCount;
        int remainder = numbers.length % workersCount;
        int start = 0;

        for (int i = 0; i < workersCount; i++) {
            int end = start + blockSize + (i < remainder ? 1 : 0);
            int[] chunk = new int[end - start];
            System.arraycopy(numbers, start, chunk, 0, chunk.length);

            Task task = new Task(i, chunk);
            tasks.add(task);
            start = end;
        }

        return tasks;
    }

    public void shutdown() {
        executorService.shutdownNow();
        DistributedLogger.info("Master thread pool stopped");
    }
}