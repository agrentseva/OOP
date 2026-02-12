package ru.nsu.ga.grentseva.primenumbers.searchPrimeNum;

import java.util.Arrays;

public class ParallelStreamSearch implements PrimeSearchMethod {

    @Override
    public boolean hasNonPrime(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return false;
        }

        return Arrays.stream(numbers)
                .parallel()
                .anyMatch(number -> !PrimeUtils.isPrime(number));
    }
}
