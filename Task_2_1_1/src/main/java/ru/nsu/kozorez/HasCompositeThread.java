package ru.nsu.kozorez;

/**
 * Class for finding composites using threads
 */
public class HasCompositeThread {

    /**
     * checks if the number is prime
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

    private volatile boolean hasComposite = false;

    /**
     * checks an array for composite numbers
     *
     * @param array       input
     * @param threadCount number of threads > 0
     * @return bool
     * @throws InterruptedException exception
     */
    public boolean hasCompositeThread(int[] array, int threadCount) throws InterruptedException {
        //AtomicBoolean hasComposite = new AtomicBoolean(false);
        if (threadCount < 1) {
            throw new IllegalArgumentException("Threads number should be greater than 0");
        }
        Thread[] threads = new Thread[threadCount];
        int chunkSize = (int) Math.ceil((double) array.length / threadCount);

        for (int t = 0; t < threadCount; t++) {
            int from = t * chunkSize;
            int to = Math.min(from + chunkSize, array.length);
            threads[t] = new Thread(() -> {
                for (int i = from; i < to && !hasComposite; i++) {
                    if (!isPrime(array[i])) {
                        //hasComposite.set(true);
                        hasComposite = true;
                        break;
                    }
                }
            });
            threads[t].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return hasComposite;
    }
}
