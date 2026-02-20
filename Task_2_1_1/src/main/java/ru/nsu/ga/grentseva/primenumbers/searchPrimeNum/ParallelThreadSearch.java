package ru.nsu.ga.grentseva.primenumbers.searchPrimeNum;

public class ParallelThreadSearch implements PrimeSearchMethod {

    private final int threadsCount;
    private volatile boolean foundNonPrime = false;

    public ParallelThreadSearch(int threadsCount) {
        if (threadsCount <= 0) {
            throw new IllegalArgumentException("the number of threads is zero");
        }
        this.threadsCount = threadsCount;
    }

    @Override
    public boolean hasNonPrime(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return false;
        }
        foundNonPrime = false;

        int actualThreadsCount = Math.min(threadsCount, numbers.length);
        Thread[] threads = new Thread[actualThreadsCount];

        int blockSize = numbers.length / actualThreadsCount;
        int remainder = numbers.length % actualThreadsCount;

        int start = 0;
        for (int i = 0; i < actualThreadsCount; i++) {
            int end = start + blockSize + (i < remainder ? 1 : 0);
            threads[i] = new Thread(new MyThread(numbers, start, end));
            threads[i].start();
            start = end;
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return foundNonPrime;
    }

    private class MyThread implements Runnable {
        private final int[] numbers;
        private final int start;
        private final int end;

        public MyThread(int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i < end && !foundNonPrime; i++) {
                if (!PrimeUtils.isPrime(numbers[i])) {
                    foundNonPrime = true;
                    break;
                }
            }
        }
    }
}
