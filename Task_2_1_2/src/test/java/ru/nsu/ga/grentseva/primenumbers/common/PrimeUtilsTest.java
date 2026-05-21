package ru.nsu.ga.grentseva.primenumbers.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimeUtilsTest {

    @Test
    void shouldReturnFalseForNegativeNumber() {
        assertFalse(PrimeUtils.isPrime(-7));
    }

    @Test
    void shouldReturnFalseForZero() {
        assertFalse(PrimeUtils.isPrime(0));
    }

    @Test
    void shouldReturnFalseForOne() {
        assertFalse(PrimeUtils.isPrime(1));
    }

    @Test
    void shouldReturnTrueForTwo() {
        assertTrue(PrimeUtils.isPrime(2));
    }

    @Test
    void shouldReturnTrueForPrimeNumber() {
        assertTrue(PrimeUtils.isPrime(13));
    }

    @Test
    void shouldReturnFalseForCompositeNumber() {
        assertFalse(PrimeUtils.isPrime(15));
    }

    @Test
    void shouldReturnFalseForEvenCompositeNumber() {
        assertFalse(PrimeUtils.isPrime(100));
    }
}