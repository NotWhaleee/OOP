package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeapsortTest {
    @Test
    void checkMain() {
        Main.main(new String[]{});
        assertTrue(true);
    }

    @Test
    void checkSimpleArr() {
        int[] arr = {12, 11, 13, 5, 6, 7};
        int[] correct = {5, 6, 7, 11, 12, 13};
        Heapsort.sort(arr);
        assertArrayEquals(arr, correct);
    }

    @Test
    void checkEmptyArr() {
        int[] arr = {};
        int[] correct = {};
        Heapsort.sort(arr);
        assertArrayEquals(arr, correct);
    }

    @Test
    void checkOneElemArr() {
        int[] arr = {1};
        int[] correct = {1};
        Heapsort.sort(arr);
        assertArrayEquals(arr, correct);
    }

    @Test
    void checkLargeArr() {
        int N = 99999;
        int[] arr = new int[N];
        int[] correct = new int[N];

        for (int i = 0; i < N; ++i) {
            arr[i] = N - i - 1;
            correct[i] = i;
        }
        Heapsort.sort(arr);
        assertArrayEquals(arr, correct);
    }
}
