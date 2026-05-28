package ru.nsu.ga.grentseva.primenumbers.worker;

import ru.nsu.ga.grentseva.primenumbers.common.DistributedLogger;
import ru.nsu.ga.grentseva.primenumbers.common.MessageType;
import ru.nsu.ga.grentseva.primenumbers.common.PrimeUtils;
import ru.nsu.ga.grentseva.primenumbers.common.Task;
import ru.nsu.ga.grentseva.primenumbers.common.TaskResult;
import ru.nsu.ga.grentseva.primenumbers.common.TaskStatus;
import ru.nsu.ga.grentseva.primenumbers.common.WorkerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WorkerHandler implements Runnable {
    private static final Map<Integer, Boolean> PRIME_CACHE = new ConcurrentHashMap<>();
    private static final long HEARTBEAT_INTERVAL = 500;
    private final Socket socket;

    public WorkerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())
        ) {
            Object request = inputStream.readObject();

            if (!(request instanceof Task task)) {
                DistributedLogger.error("Unknown request received");
                return;
            }

            DistributedLogger.info("Task received: " + task.getTaskId());

            TaskResult result = executeTask(task, outputStream);

            outputStream.writeObject(new WorkerMessage(MessageType.RESULT, result));
            outputStream.flush();

            DistributedLogger.info("Task completed: " + task.getTaskId());

        } catch (IOException | ClassNotFoundException e) {
            DistributedLogger.error("Worker handler error: " + e.getMessage());
        }
    }

    private TaskResult executeTask(Task task, ObjectOutputStream outputStream) {
        int[] numbers = task.getNumbers();
        long lastHeartbeat = System.currentTimeMillis();

        for (int number : numbers) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                DistributedLogger.error("Task interrupted: " + task.getTaskId());
                return new TaskResult(task.getTaskId(), false, TaskStatus.FAILED);
            }

            if (Thread.currentThread().isInterrupted()) {
                DistributedLogger.error("Task interrupted: " + task.getTaskId());
                return new TaskResult(task.getTaskId(), false, TaskStatus.FAILED);
            }

            long currentTime = System.currentTimeMillis();

            if (currentTime - lastHeartbeat >= HEARTBEAT_INTERVAL) {
                try {
                    outputStream.writeObject(new WorkerMessage(MessageType.HEARTBEAT, null));
                    outputStream.flush();
                    DistributedLogger.info("Heartbeat sent for task " + task.getTaskId());
                    lastHeartbeat = currentTime;
                } catch (IOException e) {
                    DistributedLogger.error("Heartbeat send failed: " + e.getMessage());
                    return new TaskResult(task.getTaskId(), false, TaskStatus.FAILED);
                }
            }

            boolean isPrime = PRIME_CACHE.computeIfAbsent(number, PrimeUtils::isPrime);

            if (!isPrime) {
                return new TaskResult(task.getTaskId(), true, TaskStatus.COMPLETED);
            }
        }

        return new TaskResult(task.getTaskId(), false, TaskStatus.COMPLETED);
    }

    private void closeSocket() {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            DistributedLogger.error("Socket close error: " + e.getMessage());
        }
    }
}