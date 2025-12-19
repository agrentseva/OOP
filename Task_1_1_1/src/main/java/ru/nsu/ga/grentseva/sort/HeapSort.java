package ru.nsu.ga.grentseva.sort;

public class HeapSort {

    private HeapSort() {}

    public static int[] heap_sort(int[] array) {
        if (array == null) {
            System.out.println("array is null");
            System.exit(1);
        }
        int[] sort_array = array.clone();
        int n = sort_array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(sort_array, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            swap(sort_array, i, 0);
            heapify(sort_array, i, 0);
        }
        return sort_array;
    }

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

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
