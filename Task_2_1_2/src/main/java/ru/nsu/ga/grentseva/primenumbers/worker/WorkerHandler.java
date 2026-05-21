package ru.nsu.ga.grentseva.primenumbers.worker;

import ru.nsu.ga.grentseva.primenumbers.common.DistributedLogger;
import ru.nsu.ga.grentseva.primenumbers.common.PrimeUtils;
import ru.nsu.ga.grentseva.primenumbers.common.Task;
import ru.nsu.ga.grentseva.primenumbers.common.TaskResult;
import ru.nsu.ga.grentseva.primenumbers.common.TaskStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WorkerHandler implements Runnable {
    private static final Map<Integer, Boolean> PRIME_CACHE = new ConcurrentHashMap<>();

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

            TaskResult result = executeTask(task);
            outputStream.writeObject(result);
            outputStream.flush();

            DistributedLogger.info("Task completed: " + task.getTaskId());
        } catch (IOException | ClassNotFoundException e) {
            DistributedLogger.error("Worker handler error: " + e.getMessage());
        } finally {
            closeSocket();
        }
    }

    private TaskResult executeTask(Task task) {
        int[] numbers = task.getNumbers();

        for (int number : numbers) {
            if (Thread.currentThread().isInterrupted()) {
                DistributedLogger.error("Task interrupted: " + task.getTaskId());
                return new TaskResult(task.getTaskId(), false, TaskStatus.FAILED);
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
            socket.close();
        } catch (IOException e) {
            DistributedLogger.error("Socket close error: " + e.getMessage());
        }
    }
}