package ru.nsu.ga.grentseva.primenumbers.searchPrimeNum;

public class SequentialSearch implements PrimeSearchMethod {

    @Override
    public boolean hasNonPrime(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return false;
        }

        for (int number : numbers) {
            if (!PrimeUtils.isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}
