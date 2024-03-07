package ru.kozorez;

public class Courier {
    private final int capacity;

    private volatile int carries;

    private volatile boolean isBusy;

    public Courier(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCarries() {
        return carries;
    }

    public void resetCarriedNum() {
        this.carries = 0;
    }

    public void setCarries (int carries) {
        this.carries = carries;
    }


    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }
}
