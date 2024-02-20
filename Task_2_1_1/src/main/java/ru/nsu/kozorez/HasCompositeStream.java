package ru.nsu.kozorez;

import java.util.Arrays;

/**
 * Class for finding composites using streams.
 */
public class HasCompositeStream {
    /**
     * Checks if the number is prime.
     *
     * @param num input number
     * @return bool
     */
    private boolean isPrime(int num) {
        if (num <= 1) {
            return true;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if an array has composite numbers.
     *
     * @param array input
     * @return bool
     */
    public boolean hasCompositeStream(int[] array) {
        return Arrays.stream(array).parallel().anyMatch(num -> !isPrime(num));
    }
}
