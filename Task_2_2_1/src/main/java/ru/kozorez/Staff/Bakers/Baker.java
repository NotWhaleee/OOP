package ru.kozorez.Staff.Bakers;

/**
 * Bakers class.
 */
public class Baker {
    private final long speed;

    private volatile boolean isBusy;

    /**
     * initialize baker.
     *
     * @param speed speed of cooking
     */
    public Baker(int speed) {
        this.speed = speed;
        isBusy = false;
    }

    /**
     * get the speed.
     *
     * @return long
     */
    public long getSpeed() {
        return speed;
    }

    /**
     * check if the baker is busy.
     *
     * @return boolean
     */
    public boolean getIsBusy() {
        return isBusy;
    }

    /**
     * occupy baker.
     *
     * @param busy boolean
     */
    public void setIsBusy(boolean busy) {
        isBusy = busy;
    }

    /**
     * baker info.
     *
     * @return string
     */
    @Override
    public String toString() {
        return "Baker{"
                + "speed=" + speed
                + ", isBusy=" + isBusy
                + '}';
    }
}
