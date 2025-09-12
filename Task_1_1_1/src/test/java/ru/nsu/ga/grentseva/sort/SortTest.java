package ru.nsu.ga.grentseva.sort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;


/**
 * Tests for heapsort.
 */
class SortTest {

    @Test
    void sort_example() {
        int[] array = new int[] {5, 4, 3, 2, 1};
        int[] result = Sort.heap_sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
    }

    @Test
    void sort_empty() {
        int[] array = new int[] {};
        int[] result = Sort.heap_sort(array);
        assertArrayEquals(new int[]{}, result);
    }

    @Test
    void sort_same() {
        int[] array = new int[] {1, 1, 1, 1, 1, 1, 1};
        int[] result = Sort.heap_sort(array);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 1}, result);
    }

    @Test
    void sort_different() {
        int[] array = new int[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2};
        int[] result = Sort.heap_sort(array);
        assertArrayEquals(new int[]{-2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, result);
    }
}