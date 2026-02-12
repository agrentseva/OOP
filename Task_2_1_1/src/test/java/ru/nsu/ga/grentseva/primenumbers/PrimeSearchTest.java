package ru.nsu.ga.grentseva.primenumbers;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.primenumbers.searchPrimeNum.*;

import static org.junit.jupiter.api.Assertions.*;

class PrimeSearchTest {

    private final PrimeSearchMethod sequential =
            new SequentialSearch();
    private final PrimeSearchMethod parallelThread =
            new ParallelThreadSearch(4);
    private final PrimeSearchMethod parallelStream =
            new ParallelStreamSearch();

    @Test
    void testArrayWithNonPrimeNumbers() {

        int[] input = {6, 8, 7, 13, 5, 9, 4};

        assertTrue(sequential.hasNonPrime(input));
        assertTrue(parallelThread.hasNonPrime(input));
        assertTrue(parallelStream.hasNonPrime(input));
    }

    @Test
    void testArrayWithOnlyPrimeNumbers() {

        int[] input = {
                20319251, 6997901, 6997927, 6997937,
                17858849, 6997967, 6998009, 6998029,
                6998039, 20165149, 6998051, 6998053
        };

        assertFalse(sequential.hasNonPrime(input));
        assertFalse(parallelThread.hasNonPrime(input));
        assertFalse(parallelStream.hasNonPrime(input));
    }
}
