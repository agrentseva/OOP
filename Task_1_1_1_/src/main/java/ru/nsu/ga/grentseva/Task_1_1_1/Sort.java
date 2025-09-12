package ru.nsu.ga.grentseva.Task_1_1_1;

public class Sort {

    private static void swap(int[] array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void heapify(int[] array, int n, int i){
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[largest] < array[left]){
            largest = left;
        }
        if (right < n && array[largest] < array[right]){
            largest = right;
        }
        if (largest != i){
            swap(array, i, largest);
            heapify(array, n, largest);
        }
    }

    public static int[] heap_sort(int[] array){
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--){
            heapify(array, n, i);
        }
        for (int i = n - 1; i > 0; i--){
            swap(array, i, 0);
            heapify(array, i, 0);
        }
        return array;
    }
}
