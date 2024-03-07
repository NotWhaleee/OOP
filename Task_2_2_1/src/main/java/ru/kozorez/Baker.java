package ru.kozorez;

public class Baker {
    private final long speed;

    private volatile boolean isBusy;

    public Baker(int speed) {
        this.speed = speed;
        isBusy = false;
    }

    public long getSpeed() {
        return speed;
    }

    public boolean getIsBusy() {
        return isBusy;
    }

    public void setIsBusy(boolean busy) {
        isBusy = busy;
    }

    @Override
    public String toString() {
        return "Baker{" +
                "speed=" + speed +
                ", isBusy=" + isBusy +
                '}';
    }
}
