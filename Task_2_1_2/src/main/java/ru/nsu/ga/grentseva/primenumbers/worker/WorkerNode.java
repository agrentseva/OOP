package ru.nsu.ga.grentseva.primenumbers.worker;

import ru.nsu.ga.grentseva.primenumbers.common.DistributedLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WorkerNode {
    private static final int THREAD_POOL_SIZE = 10;

    private final int port;
    private final ExecutorService executorService;
    private ServerSocket serverSocket;

    private volatile boolean running;

    public WorkerNode(int port) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.running = true;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            DistributedLogger.info("Worker started on port: " + port);

            while (running) {
                try {
                    Socket socket = serverSocket.accept();
                    DistributedLogger.info("Master connected: " + socket.getInetAddress());
                    executorService.submit(new WorkerHandler(socket, this));
                } catch (SocketException e) {
                    if (running) {
                        DistributedLogger.error("Socket error: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            DistributedLogger.error("Worker server error: " + e.getMessage());
        } finally {
            stopExecutor();
            DistributedLogger.info("Worker stopped");
        }
    }

    private void stopExecutor() {
        executorService.shutdownNow();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                DistributedLogger.error("Worker pool did not terminate");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        if (!running) {
            return;
        }
        running = false;

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            DistributedLogger.error("Server socket close error: " + e.getMessage());
        }
    }
}