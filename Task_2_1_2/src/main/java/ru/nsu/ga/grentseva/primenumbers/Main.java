package ru.nsu.ga.grentseva.primenumbers;

import ru.nsu.ga.grentseva.primenumbers.common.DistributedLogger;
import ru.nsu.ga.grentseva.primenumbers.common.PrimeUtils;
import ru.nsu.ga.grentseva.primenumbers.master.MasterServer;
import ru.nsu.ga.grentseva.primenumbers.master.WorkerInfo;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int ARRAY_SIZE = 1_000_000;
    private static final int START_VALUE = 5_000_000;

    public static void main(String[] args) {
        runApplication();
        System.exit(0);
    }

    public static void runApplication() {
        List<WorkerInfo> workers = createWorkers();
        MasterServer master = new MasterServer(workers);

        runHeartbeatDemo(master);

        runExampleTest(master);
        runBenchmark(master);

        master.shutdown();
    }

    private static List<WorkerInfo> createWorkers() {
        List<WorkerInfo> workers = new ArrayList<>();
        workers.add(new WorkerInfo("localhost", 5000));
        workers.add(new WorkerInfo("localhost", 5001));
        workers.add(new WorkerInfo("localhost", 5002));
        return workers;
    }

    private static void runExampleTest(MasterServer master) {
        int[] numbers = {6, 8, 7, 13, 5, 9, 4};
        boolean result = master.hasNonPrime(numbers);

        DistributedLogger.info("Input: [6, 8, 7, 13, 5, 9, 4]");
        DistributedLogger.info("Output: " + result);
    }

    private static void runHeartbeatDemo(MasterServer master) {
        DistributedLogger.info("===== HEARTBEAT DEMO =====");

        int[] numbers = new int[50_000_000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.MAX_VALUE;
        }

        long start = System.nanoTime();
        boolean result = master.hasNonPrime(numbers);
        long end = System.nanoTime();

        DistributedLogger.info("Result: " + result);
        DistributedLogger.info("Time: " + (end - start) / 1_000_000_000.0 + " sec");
        DistributedLogger.info("==========================");
    }

    private static void runBenchmark(MasterServer master) {
        DistributedLogger.info("Generating large prime array...");
        int[] numbers = generatePrimeArray(ARRAY_SIZE, START_VALUE);
        DistributedLogger.info("Array generated");

        long startTime = System.nanoTime();
        boolean result = master.hasNonPrime(numbers);
        long endTime = System.nanoTime();

        double executionTime = (endTime - startTime) / 1_000_000_000.0;

        DistributedLogger.info("Has non-prime: " + result);
        DistributedLogger.info(String.format("Execution time: %.3f seconds", executionTime));
    }

    private static int[] generatePrimeArray(int size, int startValue) {
        int[] result = new int[size];
        int count = 0;
        int number = startValue;

        while (count < size) {
            if (PrimeUtils.isPrime(number)) {
                result[count] = number;
                count++;
            }
            number++;
        }
        return result;
    }
}