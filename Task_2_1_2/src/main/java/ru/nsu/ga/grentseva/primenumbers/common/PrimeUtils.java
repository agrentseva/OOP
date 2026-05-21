package ru.nsu.ga.grentseva.primenumbers.common;

public final class PrimeUtils {

    private PrimeUtils() {

    }

    public static boolean isPrime(int number) {
        if (number < 2) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;

        for (int i = 3; i <= number / i; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
