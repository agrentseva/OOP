package ru.nsu.ga.grentseva.sort;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] array = {5, 8, 5, 8, 5, 8};
        int[] sortArray = HeapSort.heap_sort(array);
        System.out.println("After sorting:  " + Arrays.toString(sortArray));
    }
}
