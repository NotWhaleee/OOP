package ru.nsu.kozorez;

/**
 * class for checking if the number is prime.
 */
public class IsPrime {
    /**
     * Checks if the number is prime.
     *
     * @param num input number
     * @return bool
     */
    public boolean isPrime(int num) {
        if (num <= 0) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
