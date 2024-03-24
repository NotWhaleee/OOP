package ru.kozorez.staff.couriers;

/**
 * Courier class.
 */
public class Courier {
    private final int capacity;

    private volatile int carries;

    private volatile boolean isBusy;

    private final long speed;


    /**
     * initialize courier.
     *
     * @param capacity how many orders can a courier deliver in one run
     * @param speed    speed of delivery
     */
    public Courier(int capacity, int speed) {
        this.capacity = capacity;
        this.speed = speed;
        isBusy = false;
    }

    /**
     * get how many orders can a courier deliver in one run.
     *
     * @return int
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * get how many orders is the courier carrying.
     *
     * @return int
     */
    public int getCarries() {
        return carries;
    }

    /**
     * set how many orders can a courier deliver in one run.
     *
     * @param carries int
     */
    public void setCarries(int carries) {
        this.carries = carries;
    }

    /**
     * check if the courier is busy.
     *
     * @return boolean
     */
    public boolean isBusy() {
        return isBusy;
    }

    /**
     * occupy the courier.
     *
     * @param busy boolean
     */
    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    /**
     * get the speed of the delivery.
     *
     * @return long
     */
    public long getSpeed() {
        return speed;
    }

    /**
     * courier info.
     *
     * @return string
     */
    @Override
    public String toString() {
        return "Courier{"
                + "capacity=" + capacity
                + ", carries=" + carries
                + ", isBusy=" + isBusy
                + ", speed=" + speed
                + '}';
    }
}
