package ru.nsu.ga.grentseva.primenumbers.worker;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.primenumbers.common.Task;
import ru.nsu.ga.grentseva.primenumbers.common.TaskResult;
import ru.nsu.ga.grentseva.primenumbers.common.TaskStatus;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class WorkerHandlerTest {

    @Test
    void shouldProcessTaskCorrectly() throws Exception {
        ServerSocket serverSocket = new ServerSocket(6000);

        Thread serverThread = new Thread(() -> {
            try {
                Socket workerSocket = serverSocket.accept();
                WorkerHandler handler = new WorkerHandler(workerSocket);
                handler.run();
            } catch (Exception ignored) {
            }
        });
        serverThread.start();

        Socket clientSocket = new Socket("localhost", 6000);
        ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

        Task task = new Task(1, new int[]{2, 3, 4});
        outputStream.writeObject(task);
        outputStream.flush();

        Object response = inputStream.readObject();
        assertTrue(response instanceof TaskResult);

        TaskResult result = (TaskResult) response;
        assertEquals(TaskStatus.COMPLETED, result.getStatus());
        assertTrue(result.hasNonPrime());

        clientSocket.close();
        serverSocket.close();
    }
}