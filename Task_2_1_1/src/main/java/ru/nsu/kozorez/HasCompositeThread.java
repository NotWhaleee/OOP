package ru.nsu.kozorez;

/**
 * Class for finding composites using threads.
 */
public class HasCompositeThread {
    private volatile boolean hasComposite = false;

    /**
     * Checks an array for composite numbers.
     *
     * @param array       input
     * @param threadCount number of threads greater than 0
     * @return bool
     * @throws InterruptedException exception
     */
    public boolean hasCompositeThread(int[] array, int threadCount) throws InterruptedException {
        IsPrime isPrime = new IsPrime();
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
                    if (!isPrime.isPrime(array[i])) {
                        //hasComposite.set(true);
                        hasComposite = true;
                        for (Thread thread : threads) {
                            if (thread != null) {
                                thread.interrupt();
                            }
                        }
                        if (Thread.currentThread().isInterrupted()) {
                            break;
                        }
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
