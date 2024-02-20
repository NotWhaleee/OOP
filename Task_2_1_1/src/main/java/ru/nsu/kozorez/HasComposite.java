package ru.nsu.kozorez;

/**
 * Class for finding composites without using parallel processing.
 */
public class HasComposite {

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
    public boolean hasComposite(int[] array) {
        for (int num : array) {
            if (!isPrime(num)) {
                return true;
            }
        }
        return false;
    }
}
