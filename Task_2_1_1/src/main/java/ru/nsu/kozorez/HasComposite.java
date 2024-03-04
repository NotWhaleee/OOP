package ru.nsu.kozorez;

/**
 * Class for finding composites without using parallel processing.
 */
public class HasComposite {

    /**
     * Checks if an array has composite numbers.
     *
     * @param array input
     * @return bool
     */
    public boolean hasComposite(int[] array) {
        IsPrime isPrime = new IsPrime();
        for (int num : array) {
            if (!isPrime.isPrime(num)) {
                return true;
            }
        }
        return false;
    }
}
