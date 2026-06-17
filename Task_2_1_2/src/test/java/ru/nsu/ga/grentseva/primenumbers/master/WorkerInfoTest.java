package ru.nsu.ga.grentseva.primenumbers.master;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerInfoTest {

    @Test
    void shouldStoreHostCorrectly() {
        WorkerInfo workerInfo = new WorkerInfo("localhost", 5000);
        assertEquals("localhost", workerInfo.getHost());
    }

    @Test
    void shouldStorePortCorrectly() {
        WorkerInfo workerInfo = new WorkerInfo("localhost", 5000);
        assertEquals(5000, workerInfo.getPort());
    }

    @Test
    void shouldReturnCorrectToString() {
        WorkerInfo workerInfo = new WorkerInfo("127.0.0.1", 8080);
        assertEquals("127.0.0.1:8080", workerInfo.toString());
    }
}