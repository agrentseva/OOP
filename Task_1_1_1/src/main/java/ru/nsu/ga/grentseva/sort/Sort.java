package ru.nsu.ga.grentseva.sort;

/**
 * Class for sorting array with heapsort.
 */
public class Sort {

    /**
     * Swap the elements.
     *
     * @param i index of first element in array
     * @param j index of second element in array
     */
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Build a binary heap by sifting.
     *
     * @param n number of elements in the array
     * @param i root index
     */
    private static void heapify(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[largest] < array[left]) {
            largest = left;
        }
        if (right < n && array[largest] < array[right]) {
            largest = right;
        }
        if (largest != i) {
            swap(array, i, largest);
            heapify(array, n, largest);
        }
    }

    /**
     * Sorts an array using heapsort.
     *
     * @param array initial array
     * @return sort array
     */

    public static int[] heap_sort(int[] array) {
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            swap(array, i, 0);
            heapify(array, i, 0);
        }
        return array;
    }
}
