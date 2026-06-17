package ru.nsu.ga.grentseva.primenumbers.master;

import ru.nsu.ga.grentseva.primenumbers.common.DistributedLogger;
import ru.nsu.ga.grentseva.primenumbers.common.MessageType;
import ru.nsu.ga.grentseva.primenumbers.common.Task;
import ru.nsu.ga.grentseva.primenumbers.common.TaskResult;
import ru.nsu.ga.grentseva.primenumbers.common.TaskStatus;
import ru.nsu.ga.grentseva.primenumbers.common.WorkerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorkerTask implements Runnable {
    private static final int SOCKET_TIMEOUT = 1000;
    private static final long HEARTBEAT_TIMEOUT = 5000;

    private final Task task;
    private final WorkerInfo worker;
    private final TaskManager taskManager;
    private final AtomicBoolean foundComposite;
    private volatile TaskResult result;
    private volatile int heartbeatCount;

    public WorkerTask(Task task, WorkerInfo worker, TaskManager taskManager, AtomicBoolean foundComposite) {
        this.task = task;
        this.worker = worker;
        this.taskManager = taskManager;
        this.foundComposite = foundComposite;
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
            socket.setSoTimeout(SOCKET_TIMEOUT);

            DistributedLogger.info("Sending task " + task.getTaskId() + " to worker " + worker);

            outputStream.writeObject(task);
            outputStream.flush();

            long lastHeartbeat = System.currentTimeMillis();

            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    DistributedLogger.info("Task interrupted: " + task.getTaskId());
                    markTaskAsFailed();
                    return;
                }

                if (foundComposite.get()) {
                    DistributedLogger.info("Task cancelled because composite already found");
                    markTaskAsFailed();
                    return;
                }

                if (System.currentTimeMillis() - lastHeartbeat > HEARTBEAT_TIMEOUT) {
                    DistributedLogger.error("Heartbeat timeout from worker " + worker);
                    markTaskAsFailed();
                    return;
                }

                try {
                    Object response = inputStream.readObject();

                    if (!(response instanceof WorkerMessage message)) {
                        DistributedLogger.error("Invalid worker response");
                        markTaskAsFailed();
                        return;
                    }

                    if (message.getType() == MessageType.HEARTBEAT) {
                        heartbeatCount++;
                        lastHeartbeat = System.currentTimeMillis();
                        DistributedLogger.info("Heartbeat received from " + worker + " for task " + task.getTaskId());
                    }

                    if (message.getType() == MessageType.RESULT) {
                        result = message.getResult();
                        if (result.hasNonPrime()) {
                            foundComposite.set(true);
                        }

                        taskManager.setTaskStatus(task.getTaskId(), result.getStatus());
                        DistributedLogger.info("Result received for task " + task.getTaskId());
                        return;
                    }
                } catch (SocketTimeoutException e) {
                }
            }
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

    public int getHeartbeatCount() {
        return heartbeatCount;
    }
}