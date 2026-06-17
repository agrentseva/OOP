package ru.nsu.ga.grentseva.primenumbers.master;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.primenumbers.worker.WorkerNode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MasterServerTest {
    private final List<WorkerNode> workerNodes = new ArrayList<>();

    @BeforeEach
    void startWorkers() {
        startWorker(5000);
        startWorker(5001);
        startWorker(5002);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    void stopWorkers() {
        for (WorkerNode workerNode : workerNodes) {
            workerNode.shutdown();
        }
    }

    @Test
    void shouldReturnTrueWhenArrayContainsCompositeNumber() {
        MasterServer masterServer = createMasterServer();
        int[] numbers = {2, 3, 5, 7, 9, 11};

        boolean result = masterServer.hasNonPrime(numbers);
        assertTrue(result);

        masterServer.shutdown();
    }

    @Test
    void shouldReturnFalseWhenAllNumbersArePrime() {
        MasterServer masterServer = createMasterServer();
        int[] numbers = {2, 3, 5, 7, 11, 13};

        boolean result = masterServer.hasNonPrime(numbers);
        assertFalse(result);

        masterServer.shutdown();
    }

    @Test
    void shouldReturnFalseForEmptyArray() {
        MasterServer masterServer = createMasterServer();

        boolean result = masterServer.hasNonPrime(new int[]{});
        assertFalse(result);

        masterServer.shutdown();
    }

    private void startWorker(int port) {
        WorkerNode workerNode = new WorkerNode(port);
        Thread workerThread = new Thread(workerNode::start);
        workerThread.setDaemon(true);
        workerThread.start();
        workerNodes.add(workerNode);
    }

    private MasterServer createMasterServer() {
        List<WorkerInfo> workers = new ArrayList<>();
        workers.add(new WorkerInfo("localhost", 5000));
        workers.add(new WorkerInfo("localhost", 5001));
        workers.add(new WorkerInfo("localhost", 5002));
        return new MasterServer(workers);
    }
}