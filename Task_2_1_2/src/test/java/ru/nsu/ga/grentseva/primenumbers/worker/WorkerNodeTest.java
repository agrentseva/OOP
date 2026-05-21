package ru.nsu.ga.grentseva.primenumbers.worker;

import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class WorkerNodeTest {

    @Test
    void shouldAcceptConnection() throws Exception {
        WorkerNode workerNode = new WorkerNode(7000);
        Thread workerThread = new Thread(workerNode::start);
        workerThread.setDaemon(true);
        workerThread.start();

        Thread.sleep(1000);

        Socket socket = new Socket("localhost", 7000);
        assertTrue(socket.isConnected());

        socket.close();
        workerNode.shutdown();
    }

    @Test
    void shouldShutdownCorrectly() throws Exception {
        WorkerNode workerNode = new WorkerNode(7001);
        Thread workerThread = new Thread(workerNode::start);
        workerThread.setDaemon(true);
        workerThread.start();

        Thread.sleep(1000);
        workerNode.shutdown();
        Thread.sleep(500);

        assertFalse(workerThread.isInterrupted());
    }

    @Test
    void shouldHandleMultipleConnections() throws Exception {
        WorkerNode workerNode = new WorkerNode(7002);
        Thread workerThread = new Thread(workerNode::start);
        workerThread.setDaemon(true);
        workerThread.start();

        Thread.sleep(1000);

        Socket socket1 = new Socket("localhost", 7002);
        Socket socket2 = new Socket("localhost", 7002);

        assertTrue(socket1.isConnected());
        assertTrue(socket2.isConnected());

        socket1.close();
        socket2.close();
        workerNode.shutdown();
    }
}