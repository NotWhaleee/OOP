package ru.kozorez;


public class Storage {
    private final int capacity;
    private volatile int stored;

    private volatile int delivered = 0;

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public int getStored() {
        return stored;
    }

    public int getDelivered() {
        return delivered;
    }

    public synchronized void incrementStored() {
        stored++;
    }

    public synchronized void decreaseStored(int num) {
        if (stored >= num) {
            stored -= num;
        } else {
            System.err.println("Try to take more pizzas from the storage than present! Stored: " + stored + "| Take: " + num);
        }
    }

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
        return true;
    }

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
}
