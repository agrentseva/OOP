package ru.nsu.ga.grentseva.primenumbers.worker;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.primenumbers.common.MessageType;
import ru.nsu.ga.grentseva.primenumbers.common.Task;
import ru.nsu.ga.grentseva.primenumbers.common.TaskResult;
import ru.nsu.ga.grentseva.primenumbers.common.TaskStatus;
import ru.nsu.ga.grentseva.primenumbers.common.WorkerMessage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        try (
                Socket clientSocket = new Socket("localhost", 6000);
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            Task task = new Task(1, new int[]{2, 3, 4});
            outputStream.writeObject(task);
            outputStream.flush();

            boolean resultReceived = false;

            while (!resultReceived) {
                Object response = inputStream.readObject();
                assertTrue(response instanceof WorkerMessage);

                WorkerMessage message = (WorkerMessage) response;

                if (message.getType() == MessageType.HEARTBEAT) {
                    continue;
                }

                if (message.getType() == MessageType.RESULT) {
                    TaskResult result = message.getResult();

                    assertEquals(TaskStatus.COMPLETED, result.getStatus());
                    assertTrue(result.hasNonPrime());

                    resultReceived = true;
                }
            }
        }

        serverSocket.close();
    }
}