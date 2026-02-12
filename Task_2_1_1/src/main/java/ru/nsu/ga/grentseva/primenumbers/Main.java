package ru.nsu.ga.grentseva.primenumbers;

import ru.nsu.ga.grentseva.primenumbers.searchPrimeNum.*;

public class Main {

    public static void main(String[] args) {

        PrimeSearchMethod sequential = new SequentialSearch();
        PrimeSearchMethod parallelThread = new ParallelThreadSearch(4);
        PrimeSearchMethod parallelStream = new ParallelStreamSearch();

        int[] input1 = {6, 8, 7, 13, 5, 9, 4};

        System.out.println("Input: [6, 8, 7, 13, 5, 9, 4]");
        System.out.println("Sequential: " + sequential.hasNonPrime(input1));
        System.out.println("Thread: " + parallelThread.hasNonPrime(input1));
        System.out.println("ParallelStream: " + parallelStream.hasNonPrime(input1));
        System.out.println();

        int[] input2 = {
                20319251, 6997901, 6997927, 6997937,
                17858849, 6997967, 6998009, 6998029,
                6998039, 20165149, 6998051, 6998053
        };

        System.out.println("Input: [only primes]");
        System.out.println("Sequential: " + sequential.hasNonPrime(input2));
        System.out.println("Thread: " + parallelThread.hasNonPrime(input2));
        System.out.println("ParallelStream: " + parallelStream.hasNonPrime(input2));
    }
}
