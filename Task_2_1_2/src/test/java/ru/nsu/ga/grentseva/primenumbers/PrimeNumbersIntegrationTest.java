package ru.nsu.ga.grentseva.primenumbers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.primenumbers.Main;
import ru.nsu.ga.grentseva.primenumbers.worker.WorkerNode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PrimeNumbersIntegrationTest {
    private final List<WorkerNode> workers = new ArrayList<>();

    @BeforeEach
    void startWorkers() {
        startWorker(5005);
        startWorker(5006);
        startWorker(5007);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    void stopWorkers() {
        for (WorkerNode worker : workers) {
            worker.shutdown();
        }
    }

    @Test
    void shouldRunWholeApplication() {
        assertDoesNotThrow(Main::runApplication);
    }

    private void startWorker(int port) {
        WorkerNode workerNode = new WorkerNode(port);
        Thread workerThread = new Thread(workerNode::start);
        workerThread.setDaemon(true);
        workerThread.start();
        workers.add(workerNode);
    }
}