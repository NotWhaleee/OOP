package ru.kozorez;

import java.util.ArrayList;

public class Task {
    private final int id;
    private final ArrayList<Integer> arr;
    private boolean isDone = false;
    private boolean foundPrimes = false;

    public Task(int id, ArrayList<Integer> arr) {
        this.id = id;
        this.arr = arr;
    }

    public ArrayList<Integer> getArr() {
        return arr;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isFoundPrimes() {
        return foundPrimes;
    }

    public void setFoundPrimes(boolean foundPrimes) {
        this.foundPrimes = foundPrimes;
    }

    public int getId() {
        return id;
    }
}