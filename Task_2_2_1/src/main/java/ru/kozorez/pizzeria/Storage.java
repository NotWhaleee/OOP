package ru.kozorez.pizzeria;

import static ru.kozorez.pizzeria.Pizzeria.RESET;
import static ru.kozorez.pizzeria.Pizzeria.YELLOW;

import ru.kozorez.staff.couriers.Courier;

/**
 * storage for orders.
 */
public class Storage {
    private final int capacity;
    private volatile int stored;
    private volatile int delivered = 0;

    /**
     * initialize storage.
     *
     * @param capacity how many orders can the storage contain
     */
    public Storage(int capacity) {
        this.capacity = capacity;
    }

    /**
     * get how many orders are stored at the moment.
     *
     * @return int
     */
    public int getStored() {
        return stored;
    }

    /**
     * get number of delivered orders.
     *
     * @return int
     */
    public int getDelivered() {
        return delivered;
    }

    /**
     * increment num of stored orders.
     */
    public synchronized void incrementStored() {
        stored++;
    }

    /**
     * reset num of delivered orders.
     */
    public synchronized void resetDelivered() {
        delivered = 0;
    }

    /**
     * decrease num of stored orders.
     *
     * @param num int
     */
    public synchronized void decreaseStored(int num) {
        if (stored >= num) {
            stored -= num;
        } else {
            System.err.println("Try to take more pizzas from the storage than present!"
                    + " Stored: " + stored + "| Take: " + num);
        }
    }

    /**
     * deliver one order to the storage.
     *
     * @return success or fail
     */
    public synchronized boolean deliverToTheStorage() {
        while (capacity <= stored) {
            try {
                System.out.println("Baker waitiiing....");
                wait();
            } catch (InterruptedException e) {
                return false;
            }
        }
        incrementStored();
        notifyAll();
        System.out.println(YELLOW);
        System.out.println("ORDER COOKED!");
        System.out.println(RESET);
        return true;
    }

    /**
     * return orders that the courier is delivering at the moment back to the storage.
     *
     * @param courier courier
     */
    public synchronized void returnToTheStorage(Courier courier) {
        stored += courier.getCarries();
        delivered -= courier.getCarries();
    }

    /**
     * take max number of orders thar the courier could take from the storage.
     *
     * @param courier courier
     * @return success or fail
     */
    public synchronized boolean takeFromTheStorage(Courier courier) {
        if (stored == 0) {
            try {
                System.out.println("Courier waitiiing....");
                wait();
            } catch (InterruptedException e) {
                return false;
            }
        }
        int ableToTake = Math.min(courier.getCapacity(), stored);
        decreaseStored(ableToTake);
        courier.setCarries(ableToTake);
        delivered += courier.getCarries();
        notifyAll();
        return true;
    }

    /**
     * storage info.
     *
     * @return string
     */
    @Override
    public String toString() {
        return "Storage{"
                + "capacity=" + capacity
                + ", stored=" + stored
                + ", delivered=" + delivered
                + '}';
    }
}
