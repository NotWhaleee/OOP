package ru.kozorez;

import java.util.ArrayList;

/**
 * The Task class represents a task assigned to a client,
 * including its ID, the array of integers to be processed,
 * and its completion status.
 */
public class Task {
    private final int id;
    private final ArrayList<Integer> arr;
    private boolean isDone = false;
    private boolean foundPrimes = false;

    /**
     * Constructs a Task object with the specified ID and array of integers.
     *
     * @param id the unique ID of the task
     * @param arr the array of integers to be processed
     */
    public Task(int id, ArrayList<Integer> arr) {
        this.id = id;
        this.arr = arr;
    }

    /**
     * Returns the array of integers to be processed by the task.
     *
     * @return the array of integers
     */
    public ArrayList<Integer> getArr() {
        return arr;
    }

    /**
     * Checks if the task is completed.
     *
     * @return true if the task is completed, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param done the completion status to set
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Checks if the task has found any prime numbers.
     *
     * @return true if the task has found primes, false otherwise
     */
    public boolean isFoundPrimes() {
        return foundPrimes;
    }

    /**
     * Sets the status indicating if the task has found any prime numbers.
     *
     * @param foundPrimes the status to set
     */
    public void setFoundPrimes(boolean foundPrimes) {
        this.foundPrimes = foundPrimes;
    }

    /**
     * Returns the unique ID of the task.
     *
     * @return the unique ID of the task
     */
    public int getId() {
        return id;
    }
}
