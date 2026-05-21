package ru.nsu.ga.grentseva.primenumbers.master;

import ru.nsu.ga.grentseva.primenumbers.common.DistributedLogger;
import ru.nsu.ga.grentseva.primenumbers.common.Task;
import ru.nsu.ga.grentseva.primenumbers.common.TaskResult;
import ru.nsu.ga.grentseva.primenumbers.common.TaskStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class WorkerTask implements Runnable {
    private static final int TIMEOUT = 5000;

    private final Task task;
    private final WorkerInfo worker;
    private final TaskManager taskManager;
    private volatile TaskResult result;

    public WorkerTask(Task task, WorkerInfo worker, TaskManager taskManager) {
        this.task = task;
        this.worker = worker;
        this.taskManager = taskManager;
    }

    @Override
    public void run() {
        if (Thread.currentThread().isInterrupted()) {
            markTaskAsFailed();
            return;
        }

        taskManager.setTaskStatus(task.getTaskId(), TaskStatus.IN_PROGRESS);

        try (
                Socket socket = new Socket(worker.getHost(), worker.getPort());
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())
        ) {
            socket.setSoTimeout(TIMEOUT);

            DistributedLogger.info("Sending task " + task.getTaskId() + " to worker " + worker);
            outputStream.writeObject(task);
            outputStream.flush();

            Object response = inputStream.readObject();
            if (!(response instanceof TaskResult)) {
                throw new IOException("Invalid worker response");
            }

            result = (TaskResult) response;
            taskManager.setTaskStatus(task.getTaskId(), TaskStatus.COMPLETED);

        } catch (SocketTimeoutException e) {
            DistributedLogger.error("Worker timeout: " + worker);
            markTaskAsFailed();
        } catch (IOException | ClassNotFoundException e) {
            DistributedLogger.error("Worker failed: " + worker + " | " + e.getMessage());
            markTaskAsFailed();
        }
    }

    private void markTaskAsFailed() {
        result = new TaskResult(task.getTaskId(), false, TaskStatus.FAILED);
        taskManager.setTaskStatus(task.getTaskId(), TaskStatus.FAILED);
    }

    public TaskResult getResult() {
        return result;
    }
}