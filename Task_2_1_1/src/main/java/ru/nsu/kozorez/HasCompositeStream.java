package ru.nsu.kozorez;

import java.util.Arrays;

/**
 * Class for finding composites using streams.
 */
public class HasCompositeStream {


    /**
     * Checks if an array has composite numbers.
     *
     * @param array input
     * @return bool
     */
    public boolean hasCompositeStream(int[] array) {
        IsPrime isPrime = new IsPrime();
        return Arrays.stream(array).parallel().anyMatch(num -> !isPrime.isPrime(num));
    }
}
