
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import ru.nsu.kozorez.Heapsort;
import ru.nsu.kozorez.Main;
import org.junit.jupiter.api.Test;


/**
 * Tests for Heapsort and Main.
 */
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
        int n = 999999;
        int[] arr = new int[n];
        int[] correct = new int[n];
        Random rand = new Random();

        for (int i = 0; i < n; ++i) {
            arr[i] = rand.nextInt(10000);
            correct[i] = arr[i];
        }
        Heapsort.sort(arr);
        Arrays.sort(correct);
        assertArrayEquals(arr, correct);
    }

    @Test
    void checkNegativeArr() {
        int[] arr = {-2, 11, 0, 4, 4, -8};
        int[] correct = {-8, -2, 0, 4, 4, 11};

        Heapsort.sort(arr);
        assertArrayEquals(arr, correct);
    }

    @Test
    void checkSortedArr() {
        int n = 10;
        int[] arr = new int[n];
        int[] correct = new int[n];

        for (int i = 0; i < n; ++i) {
            arr[i] = i;
            correct[i] = i;
        }
        Heapsort.sort(arr);
        assertArrayEquals(arr, correct);
    }
}
