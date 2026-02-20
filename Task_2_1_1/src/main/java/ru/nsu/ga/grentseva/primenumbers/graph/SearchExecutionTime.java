package ru.nsu.ga.grentseva.primenumbers.graph;

import ru.nsu.ga.grentseva.primenumbers.searchPrimeNum.*;

public class SearchExecutionTime {

    private static final int ARRAY_SIZE = 1_000_000;
    private static final int START_VALUE = 5_000_000;
    private static final int MEASURE_RUNS = 3;

    public static void main(String[] args) {
        int[] testData = generatePrimeArray(ARRAY_SIZE, START_VALUE);
        int availableCores = Runtime.getRuntime().availableProcessors();

        measure("Sequential", new SequentialSearch(), testData);
        for (int threads = 1; threads <= availableCores; threads++) {
            measure("Thread (" + threads + ")",
                    new ParallelThreadSearch(threads),
                    testData);
        }
        measure("ParallelStream", new ParallelStreamSearch(), testData);
    }

    private static void measure(
            String name,
            PrimeSearchMethod method,
            int[] data) {

        method.hasNonPrime(data);

        long totalTime = 0;
        for (int i = 0; i < MEASURE_RUNS; i++) {
            long startTime = System.nanoTime();
            method.hasNonPrime(data);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }

        double averageSeconds =
                (totalTime / (double) MEASURE_RUNS) / 1_000_000_000.0;

        System.out.printf(
                "%-25s | %.3f seconds%n",
                name,
                averageSeconds
        );
    }

    private static int[] generatePrimeArray(int size, int startValue) {
        int[] result = new int[size];
        int count = 0;
        int number = startValue;

        while (count < size) {
            if (PrimeUtils.isPrime(number)) {
                result[count++] = number;
            }
            number++;
        }
        return result;
    }
}
