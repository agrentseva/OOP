package ru.nsu.ga.grentseva.sort;

import java.util.Arrays;

/**
 * Class for starting program.
 */
public class Main {
    /**
     * Check simple test.
     */
    public static void main (String[] args) {
        int[] array = {5, 8, 5, 8, 5, 8};
        int[] sortArray = Sort.heap_sort(array);
        System.out.println("After sorting:  " + Arrays.toString(array));
    }
}
