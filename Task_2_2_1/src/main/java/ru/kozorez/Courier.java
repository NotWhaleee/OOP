package ru.kozorez;

public class Courier {
    private final int capacity;

    private volatile int carries;

    private volatile boolean isBusy;

    private final long speed;


    public Courier(int capacity, int speed) {
        this.capacity = capacity;
        this.speed = speed;
        isBusy = false;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCarries() {
        return carries;
    }

    public void setCarries(int carries) {
        this.carries = carries;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public long getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "capacity=" + capacity +
                ", carries=" + carries +
                ", isBusy=" + isBusy +
                ", speed=" + speed +
                '}';
    }
}
