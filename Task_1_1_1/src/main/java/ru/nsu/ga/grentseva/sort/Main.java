package ru.nsu.ga.grentseva.sort;

/**
 * Class for starting program.
 */
public class Main {
    /**
     * Check simple test.
     */
    public static void main(String[] args) {
        int[] array = {5, 8, 5, 8, 5, 8};
        int[] sortArray = Sort.heap_sort(array);

        if (!java.util.Arrays.equals(sortArray, new int[]{5, 5, 5, 8, 8, 8})) {
            System.out.println("Failed");
        }
        else {
            System.out.println("After sorting: " + java.util.Arrays.toString(sortArray));
        }
    }
}
